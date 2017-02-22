package com.istore

import org.codehaus.groovy.grails.plugins.springsecurity.SpringSecurityUtils
class StoreController extends BaseController{

    def beforeInterceptor = [action:this.&auth, except:[]]

    def index() {
		render(view: "index",model:[loginUser: springSecurityService.currentUser])
	}

    def saveProfile(){
		def sessionUser = springSecurityService.currentUser
		sessionUser.userName = params.userName
		sessionUser.userAddress = params.userAddress
		sessionUser.userPhone = params.userPhone
		sessionUser.storeDesc = params.storeDesc
		sessionUser.save(flush: true)
		redirect(uri: '/store/index')
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
        springSecurityService.reauthenticate(user?.userEmail)
        uc.isUsed = true
        uc.save(flush: true)
        redirect(uri: '/')
        return
    }
    def changePassword = {
        render view: "/store/resetpassword"
    }
    def doChangePassword = {
        def sessionUser = springSecurityService.currentUser
        if (params.userPassword == ""){
            flash.message = "原密码不能为空!"
            redirect(uri: "/store/changePassword")
            return
        }else if (params.new_userPassword == ""){
            flash.message = "新密码不能为空!"
            redirect(uri: "/store/changePassword")
            return
        }else if (params.again_userPassword == ""){
            flash.message = "重复密码不能为空!"
            redirect(uri: "/store/changePassword")
            return
        }
        if (sessionUser.userPassword != springSecurityService.encodePassword(params.userPassword)){
            flash.message = "原密码输入不正确！"
            redirect(uri: "/store/changePassword")
            return
        }
        if(params.new_userPassword != params.again_userPassword){
            flash.message = "新密码和重复密码不一致!"
            redirect(uri: "/store/changePassword")
            return
        }
        sessionUser.userPassword = springSecurityService.encodePassword(params.new_userPassword)
        if (!sessionUser.save(flush: true)){
            flash.message = "保存失败！"
            redirect(uri: "/store/changePassword")
            return
        }
        render(view: 'index',model: [loginUser: sessionUser])
    }

    def forgetpassword = {
        render view: "/user/forgetpassword"
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
