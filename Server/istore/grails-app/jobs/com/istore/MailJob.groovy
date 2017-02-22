package com.istore

import com.istore.util.StringUtil
import grails.util.GrailsUtil


class MailJob {

	def grailsApplication
	def messageService
	def timeout = 30000l // execute job once in 30 seconds
	def static mailLocked = 0	// 0-unlocked the thread 1-locked the thread

	/**
	 * Do Sending mail job.
	 * @return
	 */
	def execute() {
//        println "-------------------------mailLocked : " + mailLocked +" -------  mailjob : " +  grailsApplication.config.grails.job.mail +"---------------"
//        new Mail(toEmail: user.email, title:title, content: "type#:welcome#,website#:${website}#,userName#:${userName}")
//        println GrailsUtil.environment
        if(mailLocked == 0 && grailsApplication.config.grails.job.mail == "true") {
			mailLocked = 1
			try {
                def hql = "from Mail m where m.isHandled = ? and m.faildtime < ?"
                def mailList = Mail.executeQuery(hql,[false,3])
				mailList.each {
					try {
						def languageId = 1	// 邮件默认语言是英文
						def contentMap = StringUtil.parseToMap(it.content)
//						if(contentMap.languageId) {	// 如果邮件内容包含languageId
//							languageId = contentMap.languageId
//						}
//						def url = "http://localhost:8080/buymy/mail/sendEmail?fromEmail=${it.fromEmail}&toEmail=${it.toEmail}&title=${StringUtil.escapeURL(it.title)}&content=${StringUtil.escapeURL(it.content)}&languageId=${languageId}"
//						def res = HttpClientUtil.getResponseByUrl(url)
                        println new Date()
                        messageService.sendEmail(it.fromEmail, it.toEmail, it.title, it.content)
                        println new Date()
                        it.isHandled = true
                        if(!it.save(flush: true)){
							println it.errors
						}
						//messageService.sendEmail(it.fromEmail, it.toEmail, it.title, it.content)
					} catch(Exception e) {
                        it.faildtime = it.faildtime + 1;
                        it.save(flush: true)
//                        println ' -------- toEmail : ' + it.toEmail + '---------------content : ' +it.content + '-----------------'
						e.printStackTrace()
					}

				}
			} catch(Exception e) {
				e.printStackTrace()
			}finally{

				mailLocked = 0
			}
		}
	}
}
