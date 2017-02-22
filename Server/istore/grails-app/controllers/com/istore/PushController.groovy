package com.istore

import grails.converters.JSON

class PushController extends BaseController {
	def total;
	def offset = 0;
	def max = 15;
	
	def beforeInterceptor = [action:this.&auth, except:[
	]]
	
	def addPush(){
		
	}
	
	def savePush(){
		if(params.content){
			def sessionUser = springSecurityService.currentUser
			if(sessionUser){
				def msg = new PushMsg([user:sessionUser,content:params.content]);
				if(!msg.save(flush:true)){
					flash.message = "推送失败，请稍候再试！"
				}else{
					redirect(uri: "/push/history")
					return;
				}
			}
		}else{
			flash.message("请填写推送内容！")
		}
		render(view: "addPush");
	}
	
    def history() {
		if(params.offset==null){
			params.offset=0;
		}
		offset = params.offset
		params.max = max
		def sessionUser = springSecurityService.currentUser
		def allPush = PushMsg.createCriteria().list(sort:'dateCreated',order:'desc',offset:offset,max:max ) {
			and{
				eq("user", sessionUser)
			}
		}
		total = allPush.totalCount
		render(view: "history",model:[allPush:allPush,total:total,params:params]);
	}
	
	def deletepush(){
		if(params.id){
			def sessionUser = springSecurityService.currentUser
			def push = PushMsg.findByUserAndId(sessionUser,params.id)
			if(push){
				push.delete(flush:true)
			}
		}
		redirect(uri: "/push/history")
	}

}
