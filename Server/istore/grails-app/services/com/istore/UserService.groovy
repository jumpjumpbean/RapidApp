package com.istore

import com.istore.User
import com.istore.util.StringUtil

class UserService {

    def serviceMethod() {

    }

    def doRegister(def params,def request){
        try {
            def userName = params.userName
            def userEmail = params.userEmail
            def userPassword = params.userPassword
            def user = new User(userEmail:userEmail,userPassword:userPassword,userName:userName)

            if(!user.save(flush: true)){
                println user.errors
                return null
            }
			def category = new Category(user:user,isUserCreated:false)
			category.save(flush: true)
            def randomCode = StringUtil.getRandomNumber(10)
            def userCode = new UserCode(type:1,code: randomCode,user: user)
            userCode.save(flush: true)
            def website = 'http://'+request.serverName+(request.serverPort==80?'':':'+request.serverPort) + request.contextPath
            def mail = new Mail(toEmail:userEmail, title:"欢迎加入", content:"type#:welcome#,email#:${userEmail}#,userName#:${userName}#,password#:${userPassword}#,randomCode#:${randomCode}#,website#:${website}")
            mail.save(flush: true)
            return user
        }catch(Exception e) {
            e.printStackTrace()
            println e
            return null
        }

    }
}
