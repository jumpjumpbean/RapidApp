package com.istore

import java.util.Date;

class Category {
    User user
    String name = "商品展示"
	boolean isUserCreated = true
	Date dateCreated
    static constraints = {
		dateCreated nullable:true
    }

    static mapping = {
        version(false)
        user column: 'user_id'
    }
}
