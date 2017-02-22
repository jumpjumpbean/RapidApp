package com.istore

import java.util.Date;

class Booking {
    User user
    String content
	Date bookDate
	String tel
	String type
	String customerName
	String status
	Date dateCreated
    static constraints = {
		dateCreated nullable:true
		content nullable:true,maxSize: 127
		tel nullable:true,maxSize: 31
		type nullable:true,maxSize: 15
		customerName nullable:true,maxSize: 63
		status nullable:true,maxSize: 15
    }

    static mapping = {
        version(false)
        user column: 'user_id'
    }
}
