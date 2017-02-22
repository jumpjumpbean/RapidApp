package com.istore

import java.util.Date;

class MobileApp {
    User user
	String name
	String template
	String icon
	String appFile
	String appDescription
	String appAbout
	int downTimes = 0
    String qrCode
	Float appVersion = 0.1
	String status = "未生成"
	Date dateCreated
	Date dateUpdated
    static constraints = {
		name(blank:false,nullable: false,size:1..20)
		icon(blank:false,nullable: false,size:1..255)
		template(blank:false,nullable: false,size:1..255)
		qrCode(blank:false,nullable: false,size:1..127)
		appVersion(blank:false,nullable: false)
		appFile(blank:false,nullable: false,size:1..127)
		appDescription nullable:true
		appAbout nullable:true
		dateCreated nullable:true
		dateUpdated nullable:true
		status nullable:true,maxSize: 15
    }

    static mapping = {
        version(false)
        user column: 'user_id'
    }
}
