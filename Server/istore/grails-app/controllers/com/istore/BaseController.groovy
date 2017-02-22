package com.istore

class BaseController {
    def springSecurityService
    def auth() {
        if(!springSecurityService.isLoggedIn()) {
            session['referer'] = '/'+ controllerName +'/' + actionName + (params?.id?'/'+params?.id:'')
            redirect(uri:"/login")
            return false
        }
        return true
    }
}
