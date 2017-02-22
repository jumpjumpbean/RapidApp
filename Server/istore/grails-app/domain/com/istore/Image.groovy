package com.istore

import java.util.Date;

class Image {
    User user
	Category category
    String name
	String imgName
	int width
	int height
	Date dateCreated
	String descriptions
    static constraints = {
		width nullable:true
		height nullable:true
		category nullable:true
		dateCreated nullable:true
		descriptions nullable:true
		imgName nullable:true
    }

    static mapping = {
        version(false)
        user column: 'user_id'
    }
}
