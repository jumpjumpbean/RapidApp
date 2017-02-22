package com.istore

import java.util.Date;

/**
 * 邮件
 * @author Michael
 *
 */
class Mail {

    boolean isHandled = false
    String toEmail
    String fromEmail
    String title
    String content
    Date dateCreated
    Integer faildtime = 0

    static mapping = {
        version false
    }

    static constraints = {
        fromEmail nullable: true,maxSize: 70
        toEmail nullable: true,maxSize: 70
        title nullable: true,maxSize: 100
        content nullable: true,maxSize: 200
    }
}

