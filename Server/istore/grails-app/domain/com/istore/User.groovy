package com.istore

class User {
    transient springSecurityService
	String id
	String userName
    String userEmail
    String userPassword
	String userAddress
	String userPhone
	String store
	String storeDesc
	boolean isActive = false
    boolean accountLocked = false
	boolean accountExpired = false
	boolean passwordExpired = false
	Date dateCreated
	
    static constraints = {
		id(maxSize: 32)
        userEmail(blank:false,nullable: false,email: true,size:1..100)
        userPassword(blank:false,nullable: false,size:1..64)
		userAddress (nullable: true,maxSize: 255)
		userPhone (nullable: true,maxSize: 63)
		store (nullable: true,maxSize: 127)
		storeDesc (nullable: true,maxSize: 255)
		dateCreated nullable: true
    }
    static mapping = {
		id generator:'uuid.hex'
        version(false)
    }

    Set<Role> getAuthorities() {
        UserRole.findAllByUser(this).collect { it.role } as Set
    }

    def beforeInsert() {
        encodePassword()
    }

    def beforeUpdate() {
    }
    protected void encodePassword() {
        userPassword = springSecurityService.encodePassword(userPassword)
    }
}
