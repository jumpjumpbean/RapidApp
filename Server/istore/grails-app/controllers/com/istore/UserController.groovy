package com.istore

import org.codehaus.groovy.grails.plugins.springsecurity.SpringSecurityUtils

class UserController extends BaseController{
    def userService
	
    def total;
    def offset = 0;
    def max = 15;

    def beforeInterceptor = [action:this.&auth, except:[
            'register',
            'doRegister',
            'activeUser',
            'doRegister',
            'forgetpassword',
            'doEmailforgetpassword',
            'emailresetpassword',
            'doEmailresetpassword',
            'success'

    ]]

    def index() {}

    def register(){
//        if(springSecurityService.isLoggedIn()){
//            redirect(controller: "weixin",action: "weixinlist")
//            return
//        }
        render(view: '/user/register')
    }
    def doRegister(){
        if (!params.userEmail){
            flash.message = '请填写邮箱信息！'
            redirect(uri:"/user/register")
            return
        }
        if (User.findByUserEmail(params.userEmail)) {
            flash.message = '邮箱已经存在！'
            redirect(uri:"/user/register")
            return
        }
        def user = userService.doRegister(params,request)
        if (!user){
            flash.message = 'Error '
            redirect(uri: "/user/register")
            return
        }
        redirect(controller: "user",action: "success")
        return

    }

    def activeUser = {
        def user = User.findByUserEmail(params.email)
        if(!user){
            render(view: "/wrong",model:[title : "激活失败",message: 'Email不存在'])
            return
        }
        def uc = UserCode.findByUserAndCodeAndTypeAndIsUsed(user,params.c,1,false)
        if (!uc){
            render(view: "/wrong",model:[title : "激活失败",message: 'Email不存在'])
            return
        }
        user.isActive = true
        if (!user.save(flush: true)){
            render(view: "/wrong",model:[title : "激活失败",message: '激活失败'])
            return
        }
        springSecurityService.clearCachedRequestmaps()
        //springSecurityService.reauthenticate(user?.userEmail)
        uc.isUsed = true
        uc.save(flush: true)
        render(view: 'actived')
        return
    }
    def resetpassword = {
        render view: "/user/resetpassword"
    }
    def doResetpassword = {
        println springSecurityService.encodePassword("2")
        def sessionUser = springSecurityService.currentUser
        if (params.userPassword == ""){
            flash.message = "原密码不能为空!"
            redirect(uri: "/user/resetpassword")
            return
        }else if (params.new_userPassword == ""){
            flash.message = "新密码不能为空!"
            redirect(uri: "/user/resetpassword")
            return
        }else if (params.again_userPassword == ""){
            flash.message = "重复密码不能为空!"
            redirect(uri: "/user/resetpassword")
            return
        }
        if (sessionUser.userPassword != springSecurityService.encodePassword(params.userPassword)){
            flash.message = "原密码输入不正确！"
            redirect(uri: "/user/resetpassword")
            return
        }
        if(params.new_userPassword != params.again_userPassword){
            flash.message = "新密码和重复密码不一致!"
            redirect(uri: "/user/resetpassword")
            return
        }
        sessionUser.userPassword = springSecurityService.encodePassword(params.new_userPassword)
        if (!sessionUser.save(flush: true)){
            flash.message = "保存失败！"
            redirect(uri: "/user/resetpassword")
            return
        }
        render(view: '/success',model: [title: '重置密码成功',message:'重置密码成功'])
        return
    }

    def forgetpassword = {
        render view: "/user/forgetpassword"
    }
    def doEmailforgetpassword = {
//        def user = User.findByUserEmail(params.email)
//        if (!user){
//            flash.message = '该Email不存在！'
//            redirect(uri: '/user/forgetpassword')
//            return
//        }
//        def randomCode = StringUtil.getRandomNumber(10)
//        def uc = new UserCode(user: user,code: randomCode, type: 2)
//        def website = 'http://'+request.serverName+(request.serverPort==80?'':':'+request.serverPort) + request.contextPath
//        def mail = new Mail(toEmail:params.email, title:"重置密码", content:"type#:resetPassword#,email#:${user.userEmail}#,randomCode#:${randomCode}#,website#:${website}")
//        if (uc.save(flush: true) && mail.save(flush: true)){
//            render(view: '/user/success',model: [title: 'Email发送成功',message:'已经发送重置密码Email，请查收您的邮箱。'])
//            return
//        }else{
//            flash.message = '提交失败！'
//            redirect(uri: '/user/forgetpassword')
//            return
//        }
    }
    def emailresetpassword = {
        def user = User.findByUserEmail(params.email)
        if (!user){
            render(view: '/user/error',model: [message: "用户不存在"])
            return
        }
        def uc = UserCode.findByUserAndCodeAndTypeAndIsUsed(user,params.c,2,false)
        if (!uc){
            render(view: '/user/error',model: [message: "邮件过期，请重新申请"])
            return
        }
        render view: "/user/emailresetpassword", model: [email: user.userEmail]
        return
    }

    def doEmailresetpassword = {
        def user = User.findByUserEmail(params.email)
        if (!user){
            render(view: '/user/error',model: [message: "用户不存在"])
            return
        }
        if (params.new_userPassword == "" || params.again_userPassword == ""){
            flash.message = "密码不能为空"
            redirect(uri: "/user/emailresetpassword?email=${params.email}&c=${params.c}")
            return
        }
        if(params.new_userPassword != params.again_userPassword){
            flash.message = "密码不匹配！"
            redirect(uri: "/user/emailresetpassword?email=${params.email}&c=${params.c}")
            return
        }
        user.userPassword = springSecurityService.encodePassword(params.new_userPassword)
        if (!user.save(flush: true)){
            println user.errors
            flash.message = "保存失败！"
            redirect(uri: "/user/emailresetpassword?email=${params.email}&c=${params.c}")
            return
        }
        def uc = UserCode.findByUserAndCodeAndTypeAndIsUsed(user,params.c,2,false)
        uc.isUsed = true
        uc.save(flush: true)
        springSecurityService.clearCachedRequestmaps()
        springSecurityService.reauthenticate(user?.userEmail)
        render(view: '/user/success',model: [title: '重置密码成功',message:'重置密码成功'])
        return

    }

    def success = {

    }
}
