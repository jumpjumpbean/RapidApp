package com.istore

import com.istore.util.ImageTool
import grails.converters.JSON
import org.springframework.web.multipart.MultipartHttpServletRequest
import org.springframework.web.multipart.commons.CommonsMultipartFile

class ImageController extends BaseController {
	
	def beforeInterceptor = [action:this.&auth, except:[
		'index',
		'upload',
		'imglist'
	]]
	
	def DEF_HEIGHT = 450
	def DEF_WIDTH = 450
	def IMG_MAX_WIDTH = 600
	
    def index() {
		render(view:"index",model:[categoryList:Category.findAllByUser(springSecurityService.currentUser)])
	}
	
	def upload(){
		def res = [:]
		if (!request instanceof MultipartHttpServletRequest) {
			res << [error:1,msg:"系统故障，没有启动上传模式"]
			render res.toString()
			return
		}
		MultipartHttpServletRequest multiRequest = (MultipartHttpServletRequest) request
		CommonsMultipartFile orginalFile = (CommonsMultipartFile) multiRequest.getFile("imagefile")
		// 判断是否上传文件
		if (orginalFile == null || orginalFile.isEmpty()) {
			res << [error:1,msg:"没有选择上传文件或者上传文件内容为空，请检查"]
			render res.toString()
			return
		}
		try{
			
			def sessionUser = springSecurityService.currentUser
			def originame = orginalFile.fileItem.name
			def fileNameWithoutPath = System.currentTimeMillis() + originame.substring(originame.lastIndexOf("."))
			def tempFilePath = grailsApplication.config.grails.filePath.imagePath
			def f = new File(tempFilePath + sessionUser.id)
			if(!f.exists()) {
				f.mkdirs()
			}
			
			def filename= tempFilePath + sessionUser.id + "/" + fileNameWithoutPath
			orginalFile.transferTo(new File(filename))
			def imagetool= new ImageTool()
			def originalWidth = imagetool.getImgWidth(filename)
			def originalHeight = imagetool.getImgHeight(filename)
			
			res<<[imgWidth:originalWidth,imgHeight:originalHeight,imgName:fileNameWithoutPath]
			if(originalWidth > originalHeight && originalWidth > DEF_WIDTH){
				res << [style:"width:${DEF_WIDTH}px;"]
			}else if(originalHeight > originalWidth && originalHeight > DEF_HEIGHT){
				res << [style:"height:${DEF_HEIGHT}px;"]
			}else{
				res << [style:""]
			}

//			def image = new Image();
//			image.name = fileNameWithoutPath
//			image.user = sessionUser
//			image.width=originalWidth;
//			image.height=originalHeight;
			
//			if(originalWidth > originalHeight)
//			{
//				println "${commond}convert ${tempFileName} -resize x200 -gravity center -extent 200x200 ${tFileNameWithPath}".execute().errorStream?.text
//			}else{
//				println "${commond}convert ${tempFileName} -resize 200x -gravity center -extent 200x200 ${tFileNameWithPath}".execute().errorStream?.text
//			}
				
//			if(!image.save(flush:true)){
//				flash.message = "上传过程发生错误！"
//				println image.errors
//				redirect(action: "upload")
//			}
			res << [error:0,msg:"",imgurl:"/img/upload/${sessionUser.id}/${fileNameWithoutPath}"]
			render res.toString()
		}catch(Exception e){
			e.printStackTrace()
			res << [error:1,msg:"上传过程发生错误！请稍候"]
			render res.toString()
		}
	}
	
	def saveimg(){
		if(params.imgFileName){
			def image
			if(params.imgId){
				image = Image.get(params.long("imgId"))
			}else{
				image = new Image();
			}
			if(image){
				def sessionUser = springSecurityService.currentUser
				def imgWidth = params.int("imgWidth")
				def imgHeight = params.int("imgHeight")
				if(imgWidth > IMG_MAX_WIDTH){
					def commond = ""
					if (System.getProperties().getProperty("os.name").indexOf("indows") != -1){
						commond = "cmd /c "
					}
					def filename= grailsApplication.config.grails.filePath.imagePath + sessionUser.id + "/" + params.imgFileName
					println "${commond}convert ${filename} -resize ${IMG_MAX_WIDTH}x ${filename}".execute().errorStream?.text
					imgHeight = Math.round(IMG_MAX_WIDTH * imgHeight / imgWidth)
					imgWidth = IMG_MAX_WIDTH
				}
				
				image.imgName = params.imgFileName
				image.name = params.prdName
				image.descriptions = params.prdDesc
				image.category = Category.get(params.long('imgCategory'))
				image.user = sessionUser
				image.width=params.int("imgWidth")
				image.height=params.int("imgHeight")
				image.save(flush:true)
			}
			redirect(uri: "/router/index/${params.imgCategory}")
		}else{
			flash.message="请上传商品图片"
			render(view:"index",model:[categoryList:Category.findAllByUser(springSecurityService.currentUser)])
		}
	}
	
	def imglist(){
		if(params.id){
			def imglist = Image.findAllByUser(User.get(params.id),[max:5]);
			render imglist as JSON;
		}else{
			def sessionUser = springSecurityService.currentUser
			def imglist = Image.findAllByUser(sessionUser,[max:5]);
			render imglist as JSON;
		}
	}
	
	def editimg(){
		def sessionUser = springSecurityService.currentUser
		def img = Image.findByUserAndId(sessionUser,params.id);
		if(img){
			def imgstyle=""
			if(img.width > img.height && img.width > DEF_WIDTH){
				imgstyle = "style='width:${DEF_WIDTH}px;'"
			}else if(img.height > img.width && img.height > DEF_HEIGHT){
				imgstyle = "style='height:${DEF_HEIGHT}px;'"
			}
			render(view:"index",model:[img:img,imgstyle:imgstyle,categoryList:Category.findAllByUser(sessionUser)])
		}else{
			flash.message="图片不存在"
			redirect(uri: "/router/index")
		}
	}
	
	def deleteimg(){
		if(params.id){
			def sessionUser = springSecurityService.currentUser
			def img = Image.findByUserAndId(sessionUser,params.id);
			if(img){
				def tempFilePath = grailsApplication.config.grails.filePath.imagePath + sessionUser.id + "/" + img.name
				def f = new File(tempFilePath)
				if(f.isFile() && f.exists()) {
					f.delete();
				}
				img.delete(flush:true)
			}
		}
		redirect(uri: "/router/index")
	}
	
}
