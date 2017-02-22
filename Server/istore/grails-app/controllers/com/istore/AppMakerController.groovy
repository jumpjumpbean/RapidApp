package com.istore

import com.istore.util.ImageTool
import grails.converters.JSON
import org.springframework.web.multipart.MultipartHttpServletRequest
import org.springframework.web.multipart.commons.CommonsMultipartFile

class AppMakerController extends BaseController  {
	def appService

    def index() {
		def appInfo = MobileApp.findByUser(springSecurityService.currentUser)
		if(appInfo){
			render(view:"index",model:[app:appInfo])
		}
	}
	
	def uploadIcon(){
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
			def fileNameWithoutPath = "appIcon"
			def tempFilePath = grailsApplication.config.grails.filePath.imagePath
			def f = new File(tempFilePath + sessionUser.id)
			if(!f.exists()) {
				f.mkdirs()
			}
			
			def filename= tempFilePath + sessionUser.id + "/" + fileNameWithoutPath
			def iconFile = new File(filename)
			iconFile.delete()
			orginalFile.transferTo(iconFile)
			res<<[imgName:fileNameWithoutPath]
			
			res << [error:0,msg:"",imgurl:"/img/upload/${sessionUser.id}/${fileNameWithoutPath}"]
			render res.toString()
		}catch(Exception e){
			e.printStackTrace()
			res << [error:1,msg:"上传过程发生错误！请稍候"]
			render res.toString()
		}
	}
	
	def saveInfo(){
		try {
			if(params.imgFileName){
				def sessionUser = springSecurityService.currentUser
				// Clear the old app and qrcode files
				def oldApp = MobileApp.findByUser(sessionUser)
				if(oldApp){
					new File(oldApp.appFile).delete()
					new File(oldApp.qrCode).delete()
				}
				def tempFilePath = grailsApplication.config.grails.filePath.imagePath
				def app = new MobileApp()
				app.user = springSecurityService.currentUser
				app.icon = params.imgFileName
				//app.icon = tempFilePath + sessionUser.id + "/" + params.imgFileName
				app.name = params.appName
				app.template = params.appTemplate
				app.appVersion = oldApp?(oldApp.appVersion+0.1):0.1
				String userApp = appService.createAppPackage(tempFilePath + sessionUser.id, app)
				if(!userApp){
					flash.message="生成APP失败"
					render(view:"index",model:[mobileAppList:MobileApp.findAllByUser(springSecurityService.currentUser)])
				}
				app.appFile = userApp
				String qrcodeFile = appService.createAppQrcode(tempFilePath + sessionUser.id, sessionUser.id, app.name)
				if(!qrcodeFile){
					flash.message="生成二维码失败"
					render(view:"index",model:[mobileAppList:MobileApp.findAllByUser(springSecurityService.currentUser)])
				}
				app.qrCode = qrcodeFile
				MobileApp.executeUpdate("delete MobileApp a where a.user=?", [sessionUser])
				app.save(flush:true)
				render(view: "appDownload",model:[app:app])
			}else{
				flash.message="请上传图标"
				render(view:"index",model:[mobileAppList:MobileApp.findAllByUser(springSecurityService.currentUser)])
			}
		}catch(Exception e){
			e.printStackTrace()
			flash.message="生成APP发生错误！请稍候"
			render(view:"index")
		}
	}
	
}
