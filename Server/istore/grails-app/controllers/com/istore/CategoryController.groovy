package com.istore

import grails.converters.JSON

class CategoryController extends BaseController {
	def total;
	def offset = 0;
	def max = 15;
	
	def beforeInterceptor = [action:this.&auth, except:[
	]]
	
	def index(){
		def sessionUser = springSecurityService.currentUser
		if(params.offset==null){
			params.offset=0;
		}
		offset = params.offset
		params.max = max
		def allCategory = Category.createCriteria().list(sort:'dateCreated',order:'desc',offset:offset,max:max ) {
			and{
				eq("user", sessionUser)
				eq("isUserCreated",true)
			}
		}
		total = allCategory.totalCount
		render(view:"index", model:[allCategory:allCategory,total:total,params:params])
	}
	
	def deletecategory(){
		if(params.id){
			def sessionUser = springSecurityService.currentUser
			def category = Category.findByUserAndId(sessionUser,params.id)
			if(category){
				category.delete(flush:true)
			}
		}
		redirect(uri: "/category")
	}

}
