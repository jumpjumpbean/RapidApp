package com.istore

import grails.converters.JSON
import java.text.SimpleDateFormat

class PromotionController extends BaseController {
	def total;
	def offset = 0;
	def max = 15;
	
	def beforeInterceptor = [action:this.&auth, except:[
	]]
	
	def addPromotion(){
		
	}
	
	def savePromotion(){
		if(params.content){
			def sessionUser = springSecurityService.currentUser
			if(sessionUser){
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd"); 
				def promotion = new Promotion([user:sessionUser,content:params.content,beginDate:sdf.parse(params.startDate),endDate:sdf.parse(params.endDate)])
				if(!promotion.save(flush:true)){
					println promotion.errors
					flash.message = "优惠保存失败，请稍候再试！"
				}else{
					if(params.ispush == '1'){
						def newContent = params.startDate + "~" + params.endDate + "期间,"+params.content
						def pushmsg = new PushMsg([user:sessionUser,content:newContent])
						if(!pushmsg.save(flush:true)){
							println pushmsg.errors
						}
					}
					redirect(uri: "/promotion/history")
					return;
				}
			}
		}else{
			flash.message = "请填写优惠内容！"
		}
		render(view: "addPromotion");
	}
	
    def history() {
		if(params.offset==null){
			params.offset=0;
		}
		offset = params.offset
		params.max = max
		def sessionUser = springSecurityService.currentUser
		def allpromotion = Promotion.createCriteria().list(sort:'dateCreated',order:'desc',offset:offset,max:max ) {
			and{
				eq("user", sessionUser)
			}
		}
		total = allpromotion.totalCount
		render(view: "history",model:[allpromotion:allpromotion,total:total,params:params]);
	}
	
	def deletepromotion(){
		if(params.id){
			def sessionUser = springSecurityService.currentUser
			def promotion = Promotion.findByUserAndId(sessionUser,params.id)
			if(promotion){
				promotion.delete(flush:true)
			}
		}
		redirect(uri: "/promotion/history")
	}

}
