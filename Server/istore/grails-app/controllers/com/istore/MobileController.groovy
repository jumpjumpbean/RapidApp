package com.istore


import grails.converters.JSON

class MobileController{

    def index() {
	}
	
	def MAX_COUNT = 200
	
	def imglist(){
		if(params.userid){
			def imglist
			if(params.objid){
				imglist = Image.executeQuery("select id,height,width,name,imgName from Image where user.id=? and category.id=?",[params.userid,params.long("objid")],[max:MAX_COUNT])
			}else{
				imglist = Image.executeQuery("select id,height,width,name,imgName from Image where user.id=?",[params.userid],[max:MAX_COUNT])
			}
			render(contentType:"text/json"){
				imglist.collect(){[
					  id:it[0],
					  height:it[1],
					  width:it[2],
					  name:it[3],
					  imgName:it[4]
				  ]}
			};
		}else{
			def sessionUser = springSecurityService.currentUser
			def imglist = Image.findAllByUser(sessionUser,[max:MAX_COUNT]);
			render imglist as JSON;
		}
	}
	
	def imgdetail(){
		if(params.userid){
			if(params.objid){
				def img = Image.executeQuery("select id,height,width,name,category.id as catId, descriptions,imgName from Image where id=?",[params.long("objid")])
				render(contentType:"text/json"){
					img.collect(){[
						  id:it[0],
						  height:it[1],
						  width:it[2],
						  name:it[3],
						  category_id:it[4],
						  descriptions:it[5],
						  imgName:it[6]
					  ]}
				}
			}
		}
	}
	
	def storedetail(){
		if(params.userid){
			def store = User.get(params.userid)
			render(contentType:"text/json"){
				store.collect(){[
					  name:it.userName,
					  email:it.userEmail,
					  address:it.userAddress,
					  phone:it.userPhone,
					  descriptions:it.storeDesc
				  ]}
			}
		}
	}
	
	def catlist(){
		if(params.userid){
			def categorylist = Category.executeQuery("select id,name from Category where user.id=?",[params.userid],[max:MAX_COUNT])//Category.findAllByUser(User.get(params.userid));
			render(contentType:"text/json"){
				categorylist.collect(){[
	                  id:it[0],  
	                  name:it[1]
	              ]}  
			};
		}else{
			render "invalid request";
		}
	}
	
	def pushlist(){
		if(params.userid){
			def pushlist
			if(params.objid){
				pushlist = PushMsg.executeQuery("select id,content from PushMsg where user.id=? and id>?",[params.userid,params.long("objid")],[max:MAX_COUNT])
			}else{
				pushlist = PushMsg.executeQuery("select id,content from PushMsg where user.id=? order by dateCreated desc",[params.userid],[max:1])
			}
			render(contentType:"text/json"){
				pushlist.collect(){[
					  id:it[0],
					  name:it[1]
				  ]}
			};
		}else{
			render "invalid request";
		}
	}
	
	def promotionlist(){
		if(params.userid){
			def promotionlist = Promotion.executeQuery("select id,content,beginDate,endDate from Promotion where user.id=? and endDate>? order by dateCreated desc",[params.userid, new Date()-1],[max:MAX_COUNT])
			render(contentType:"text/json"){
				promotionlist.collect(){[
					  id:it[0],
					  content:it[1],
					  beginDate:it[2].format("yyyy-MM-dd"),
					  endDate:it[3].format("yyyy-MM-dd")
				  ]}
			};
		}else{
			render "invalid request";
		}
	}
	
	def addOrder(){
		if(params.userid){
			def user = User.get(params.userid)
			if(user){
				try{
					def order = new Booking()
					if(params.bookDate){
						order.bookDate = params.date("bookDate",'yyyy-MM-dd HH:mm:ss')
					}else{
						order.bookDate = new Date();
					}
					order.content = params.content
					order.tel = params.tel
					order.customerName = params.customerName
					order.type = "app"
					order.user = user
					if(order.save(flush:true)){
						render 'success'
					}else{
						println order.errors
						render 'error'
					}
				}catch(Exception e){
					println e
					render 'dataType error'
				}
			}else{
				render 'not exist'
			}
		}else{
				render 'no userid'
		}
	}
}
