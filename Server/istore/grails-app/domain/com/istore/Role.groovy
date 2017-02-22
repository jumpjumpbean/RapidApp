package com.istore

class Role {

    boolean enabled = true
    User createdBy
    String authority
    String comment
    Date dateCreated
    Date lastUpdated

    //static embedded = ['createdBy']
    static mapping = {
        version false
        //	cache true
    }

    static constraints = {
        authority blank: false, unique: true,maxSize: 70
        comment nullable:true,maxSize: 100
        createdBy nullable:true
    }

    String toString() {
        authority
    }

    static normalAdmin() {
        Role.findByAuthority('ROLE_NORMAL_ADMIN')
    }
}
