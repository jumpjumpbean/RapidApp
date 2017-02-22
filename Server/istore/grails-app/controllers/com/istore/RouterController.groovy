package com.istore

import grails.converters.JSON

class RouterController extends BaseController {
	def total;
	def offset = 0;
	def max = 15;
	
	def beforeInterceptor = [action:this.&auth, except:[
	]]
	
    def index() {
		if(params.offset==null){
			params.offset=0;
		}
		offset = params.offset
		params.max = max
		def sessionUser = springSecurityService.currentUser
		def allCategory = Category.findAllByUser(sessionUser,[sort:"id",order:"asc"]);
		def allImage
		def catId = params.id
		if(params.id){
			allImage = Image.createCriteria().list(sort:'dateCreated',order:'desc',offset:offset,max:max ) {
				and{
					eq("user", sessionUser)
					eq("category.id",params.long("id"))
				}
			}
			
		}else{
			allImage = Image.createCriteria().list(sort:'dateCreated',order:'desc',offset:offset,max:max ) {
				and{
					eq("user", sessionUser)
					category{
						eq("isUserCreated",false)	
					}
				}
			}
			if(allImage.size() > 0){
				catId = allImage.get(0).category.id
			}
		}
		total = allImage.totalCount
		if(catId == null){
			catId = Category.findByUserAndIsUserCreated(sessionUser,false).id
		}
		render(view: "/index",model:[allCategory:allCategory,allImage:allImage,sessionUser:sessionUser,catId:catId,total:total,params:params]);
	}
	
	def addCategory(){
		def res=[:]
		if(params.catName){
			def sessionUser = springSecurityService.currentUser
			def cat = Category.findByUserAndName(sessionUser,params.catName)
			if(cat){
				res << [error:1,msg:"分类名已经存在"]
			}else{
				cat = new com.istore.Category()
				cat.name = params.catName
				cat.user = springSecurityService.currentUser
				cat.save(flush:true)
				res<< [error:0,catId:cat.id,catName:cat.name]
			}
		}else{
			res << [error:1,msg:"请输入分类名"]
		}
		render res as JSON
	}
}
