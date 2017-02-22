package com.istore

import grails.converters.JSON

class OrderController extends BaseController {
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
		def now = new Date()
		now.setHours(0)
		now.setMinutes(0)
		now.setSeconds(0)
		def allOrder = Booking.createCriteria().list(sort:'dateCreated',order:'desc',offset:offset,max:max ) {
			and{
				eq("user", sessionUser)
				gt("dateCreated",now)
			}
		}
		total = allOrder.totalCount
		render(view: "index",model:[allOrder:allOrder,total:total,params:params]);
	}

	def history(){
		if(params.offset==null){
			params.offset=0;
		}
		offset = params.offset
		params.max = max
		def sessionUser = springSecurityService.currentUser
		def now = new Date()
		now.setHours(0)
		now.setMinutes(0)
		now.setSeconds(0)
		def allOrder = Booking.createCriteria().list(sort:'dateCreated',order:'desc',offset:offset,max:max ) {
			and{
				eq("user", sessionUser)
				lt("dateCreated",now)
			}
		}
		total = allOrder.totalCount
		render(view: "index",model:[allOrder:allOrder,total:total,params:params]);
	}
	
	def deleteorder(){
		if(params.id){
			def sessionUser = springSecurityService.currentUser
			def order = Booking.findByUserAndId(sessionUser,params.id)
			if(order){
				order.delete(flush:true)
			}
		}
		redirect(uri: "/order/index")
	}

}
