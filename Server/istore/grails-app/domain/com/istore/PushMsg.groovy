package com.istore

import java.util.Date;

class PushMsg {
    User user
    String content
	boolean isPushed = false
	Date dateCreated
    static constraints = {
		dateCreated nullable:true
    }

    static mapping = {
        version(false)
        user column: 'user_id'
    }
}
