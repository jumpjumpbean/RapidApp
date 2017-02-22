package com.istore

import com.istore.util.FileUtil
import com.istore.util.ImageTool

class AjaxController {
	
    def index() {}

    def validEmailIsExisted = {
        if(User.findByUserEmail(params.email)){
            render 1
        }else{
            render 0
        }
    }
	
	/**
	 * 文件下载
	 */
   def fileDownload = {
	 def filePath = grailsApplication.config.grails.filePath.report  //文件路径
	 def fileName = encode(params.fileName,'UTF-8')  //文件名
	 response.setHeader("Content-disposition", "attachment; filename=" + fileName)
	 response.contentType = "application/application/x-excel"
	 def out = response.outputStream
	 def inputStream = new FileInputStream(filePath + fileName)
	 byte[] buffer = new byte[1024]
	 int i = -1
	 while ((i = inputStream.read(buffer)) != -1) {
		 out.write(buffer, 0, i)
	 }
	 out.flush()
	 out.close()
	 inputStream.close()
   }
   
   /**
	* 字符编码
	*/
	final def encode(String value,String charSet){
		java.net.URLEncoder.encode(value, charSet)
	}

    def sendMessage = {
        if(!params.senderName){
            render "姓名不能为空!"
            return
        }
        if(!params.cellPhone){
            render "手机不能为空!"
            return
        }
        if(!params.messageInfo){
            render "留言不能为空!"
            return
        }
        def message = new ContactUs(yourName:params.senderName,yourPhone:params.cellPhone,yourMessage:params.messageInfo)
        if (message.save(flush: true)){
            render "保存成功!"
            return
        }else{
            println message.errors
            render "保存失败!"
            return
        }
    }

    def uploadUserFile = {

        def file

        try {

            def tempFilePath = FileUtil.getFileNameByUpload(params.myfile)
            //		   def width = javax.imageio.ImageIO.read(new FileInputStream(new File(tempFilePath))).getWidth(null)
            //		   def height = javax.imageio.ImageIO.read(new FileInputStream(new File(tempFilePath))).getHeight(null)
            def imagetool= new ImageTool()
            def width = imagetool.getImgWidth(tempFilePath)
            def height = imagetool.getImgHeight(tempFilePath)

            render(status: response.SC_OK, text:"${tempFilePath}"+"v"+width+"v"+height)
        } catch(Exception e) {
            e.printStackTrace()
            render(status: response.SC_INTERNAL_SERVER_ERROR, text:"{success: false}")
        } finally {
            if(file) {
                file.delete()
            }
        }
    }
}
