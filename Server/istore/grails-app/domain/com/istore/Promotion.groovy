package com.istore

import java.util.Date;

class Promotion {
    User user
    String content
	Date beginDate
	Date endDate
	Date dateCreated
    static constraints = {
		dateCreated nullable:true
		content (nullable: false,maxSize: 255)
    }

    static mapping = {
        version(false)
        user column: 'user_id'
    }
}
