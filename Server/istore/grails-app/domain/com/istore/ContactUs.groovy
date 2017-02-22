package com.istore

class ContactUs {
	String yourName
	String yourPhone
	String yourMessage
	
    static constraints = {
		yourName nullable: true, maxSize: 127
		yourPhone nullable: true, maxSize: 20
		yourMessage nullable: false, maxSize: 1023
    }
	static mapping = {
		version(false)
	}
}
