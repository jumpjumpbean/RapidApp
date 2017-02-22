package com.istore

import com.istore.util.StringUtil

/**
 * Class for transactions related to message.
 * @author michael
 *
 */
class MessageService {

	def grailsApplication
	def mailService

	/**
	 * Send mail.
	 * @param fromUser
	 * @param toUser
	 * @param title
	 * @param content
	 * @return
	 */
	def sendEmail(String fromMail, String toMail, String title, String content) {
		try {
            if(!fromMail) {
                fromMail = grailsApplication.config.grails.mail.username
            }
            if(!title) {
                title = "Title"
            }
            def contentMap = StringUtil.parseToMap(content)
            def path = contentMap.type?"/mail/"+contentMap.type:"/mail"
            if(!path) {
                log.error("mail template not found")
            }
            //println "start---${fromMail}---${toMail}--=${path}=-:" + new Date()
			if(mailService == null) println "null service"
            mailService.sendMail {
                to "${toMail}"
                from "IStore<${fromMail}>"
                subject "${title}"
                body( view:path,
                        model:StringUtil.parseToMap(content))
            }
            //println "stop---${path}------:" + new Date()
        } catch (Exception e) {
           // println ' -------- toEmail : ' + toMail + '---------------content : ' +content + '-----------------'
			e.printStackTrace()
           // println e
            throw e
            
        }
	}

	/**
	 * Do save or update.
	 * @param mail
	 * @return
	 */
	def saveMail(def mail) {
		mail.save(flush:true)
	}


}
