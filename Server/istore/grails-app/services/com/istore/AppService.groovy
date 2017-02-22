package com.istore

import com.istore.util.StringUtil
import com.istore.util.FileUtil
import java.io.File
import groovy.io.FileType
//import org.apache.tools.ant.BuildException
//import org.apache.tools.ant.DefaultLogger
import org.apache.tools.ant.Project
import org.apache.tools.ant.ProjectHelper
import sun.security.tools.*


class AppService {
	def grailsApplication
	def QRCodeService
	def commonAppPackageName = "JYZLZJNFHXB20130728142717000"
	def storePass = "123456"
	def keyPass = "123456"
	def org = "eyunxun"
	def fileUtil = new FileUtil()
	Project appProject
	def userApp

    def serviceMethod() {

    }

    def createAppQrcode(String filePath, String user, String app){
        try {
			if(filePath.isEmpty() || user.isEmpty() || app.isEmpty()){
				return null
			}
			
			Map information = [:]
			def appLink = grailsApplication.config.grails.server.url + "/img/upload/" + user + "/" + app + ".apk"
			def fileName = "qrcode.png"
			information.put("chs", "250x250")
			information.put("cht", "qr")
			information.put("chl", appLink)
			information.put("chld", "H|1")
			information.put("choe", "UTF-8")
			QRCodeService.createQRCode(information, null, filePath, fileName)
			def file = new File(filePath + "/" + fileName)
			if(file.exists()){
				return filePath + "/" + fileName
			}else{
				return null
			}
        }catch(Exception e) {
            e.printStackTrace()
            println e
            return null
        }
    }
	
	def createAppPackage(String userPath, MobileApp app){
		if(userPath.isEmpty() || !app){
			return null
		}
		def userAppSrc = userPath + "/AppSrc"
		def antBuilder = new AntBuilder()
		appProject = new Project() 
		try {
			def keyStoreFile = new File(userPath + "/keystore")
			if(!keyStoreFile.exists()){
				createKeyStore(app.user.id, userPath)
			}
			copyAppSrc(userAppSrc)
			setAppSrc(app.user.id, userPath, userAppSrc, app.icon, app.name, app.user.userName, app.template, app.appVersion)
			def buildXml = userAppSrc + "/build.xml"
			File buildFile = new File(buildXml)
			if(buildFile.exists()){
				def antCleanCommand = "ant clean -f " + buildXml
				def antReleaseCommand = "ant release -f " + buildXml
				println antCleanCommand.execute().errorStream?.text;
				println antReleaseCommand.execute().errorStream?.text;
				//appProject.fireBuildStarted()
				//buildInit(buildXml, userAppSrc)
				//buildRunTarget("clean")
				//buildRunTarget("release")
				//appProject.fireBuildFinished(null)
			}
			def tempUserApp = userPath + "/" + app.name + ".apk"
			getUserApp(new File(userAppSrc+"/bin"), tempUserApp)
		} catch (Exception e) {
			//appProject.fireBuildFinished(e)
			e.printStackTrace()
			println e
		} finally{
			//antBuilder.delete(dir: userAppSrc)
			return userApp
		}
	}
	
	def copyAppSrc(String userAppSrc){
		if(userAppSrc.isEmpty()){
			return null
		}
		try {
			def commonAppSrc = grailsApplication.config.grails.server.appSrc
			fileUtil.copyDirectiory(commonAppSrc, userAppSrc)
		}catch(Exception e) {
            e.printStackTrace()
            println e
            return null
		}
	}
	
	def setAppSrc(String user, String userPath, String userAppSrc, String icon, String appName, String storeName, String template, Float appVersion){
		try {
			def srcIcon = new File(userPath + "/" + icon)
			if(!srcIcon.exists()){
				return null
			}
			// Set the package name to user id. The package name cannot be started with number!
			fileUtil.replaceDirFileText(commonAppPackageName, "NLJ"+user, new File(userAppSrc))
			// Set the app project name to user inputted app name
			def buildXml = userAppSrc + "/build.xml"
			def pattern = ~/<project name=.* default/
			def replacement = "<project name=\""+ appName + "\" default"
			fileUtil.replaceFileText(pattern, replacement, new File(buildXml))
			// Set the app version
			def manifestXml = userAppSrc + "/AndroidManifest.xml"
			pattern = ~/android:versionName=.* >/
			replacement = "android:versionName=\""+ appVersion.toString() + "\" >"
			fileUtil.replaceFileText(pattern, replacement, new File(manifestXml))
			// Set the app name and store name
			def stringsXml = userAppSrc + "/res/values/strings.xml"
			pattern = ~/<string name="app_name">.*<\/string>/
			replacement = "<string name=\"app_name\">"+ appName + "</string>"
			fileUtil.replaceFileText(pattern, replacement, new File(stringsXml))
			pattern = ~/<string name="SalerID">.*<\/string>/
			replacement = "<string name=\"SalerID\">"+ user + "</string>"
			fileUtil.replaceFileText(pattern, replacement, new File(stringsXml))
			pattern = ~/<string name="SalerName">.*<\/string>/
			replacement = "<string name=\"SalerName\">"+ storeName + "</string>"
			fileUtil.replaceFileText(pattern, replacement, new File(stringsXml))
			// Set custom_rules.xml
			def localProperties = userAppSrc + "/local.properties"
			pattern = ~/#key.store=/
			replacement = "key.store="+ userPath + "/keystore"
			fileUtil.replaceFileText(pattern, replacement, new File(localProperties))
			pattern = ~/#key.alias=/
			replacement = "key.alias=" + user
			fileUtil.replaceFileText(pattern, replacement, new File(localProperties))
			pattern = ~/#key.store.password=/
			replacement = "key.store.password="+ storePass
			fileUtil.replaceFileText(pattern, replacement, new File(localProperties))
			pattern = ~/#key.alias.password=/
			replacement = "key.alias.password="+ keyPass
			fileUtil.replaceFileText(pattern, replacement, new File(localProperties))
			// Set the icon
			def iconHdpi = new File(userAppSrc + "/res/drawable-hdpi/ic_launcher.png")
			def iconLdpi = new File(userAppSrc + "/res/drawable-ldpi/ic_launcher.png")
			def iconMdpi = new File(userAppSrc + "/res/drawable-mdpi/ic_launcher.png")
			def iconXhdpi = new File(userAppSrc + "/res/drawable-xhdpi/ic_launcher.png")
			def iconXxhdpi = new File(userAppSrc + "/res/drawable-xxhdpi/ic_launcher.png")
			iconHdpi.delete()
			iconLdpi.delete()
			iconMdpi.delete()
			iconXhdpi.delete()
			iconXxhdpi.delete()
			fileUtil.copyFile(srcIcon, iconHdpi)
			fileUtil.copyFile(srcIcon, iconLdpi)
			fileUtil.copyFile(srcIcon, iconMdpi)
			fileUtil.copyFile(srcIcon, iconXhdpi)
			fileUtil.copyFile(srcIcon, iconXxhdpi)
		}catch(Exception e) {
            e.printStackTrace()
            println e
            return null
		}
	}
	
	def createKeyStore(def user, def userPath){
		String names = "CN=" + user + ",OU=" + org + ",O=" + org +",C=CH"
		String[] keytoolArgs = ["-genkey", "-alias", user, "-dname",
			names, "-keyalg", "RSA", "-validity", "4000", "-keystore",
			userPath + "/keystore", "-storepass", storePass, "-keypass", keyPass]
		KeyTool.main(keytoolArgs)
	}
	
	def getUserApp(File userAppSrcBin, String tempApp){
		if(!userAppSrcBin.exists() || tempApp.isEmpty()){
			return null
		}
		try {
			userAppSrcBin.eachFileMatch(FileType.FILES, ~/.*release\.apk/) {File appFile ->
				def userAppFile = new File(tempApp)
				userAppFile.delete()
				fileUtil.copyFile(appFile, userAppFile)
				userApp = userAppFile.path.toString()	
			}
		}catch(Exception e) {
			e.printStackTrace()
			println e
			return null
		}
	}
	
	def buildInit(String _buildFile, String _baseDir) throws Exception {
		appProject.init();

		//DefaultLogger consoleLogger = new DefaultLogger();
		//consoleLogger.setErrorPrintStream(System.err);
		//consoleLogger.setOutputPrintStream(System.out);
		//consoleLogger.setMessageOutputLevel(Project.MSG_INFO);
		//appProject.addBuildListener(consoleLogger);
		
		// Set the base directory. If none is given, "." is used.
		if (_baseDir == null)
			_baseDir = new String(".");

		appProject.setBasedir(_baseDir);

		if (_buildFile == null)
			return null

		//ProjectHelper.getProjectHelper().parse(project, new File(_buildFile));
		ProjectHelper.configureProject(appProject, new File(_buildFile));
	}
	
	def buildRunTarget(String _target) throws Exception {
		// Test if the project exists
		if (appProject == null)
			throw new Exception(
					"No target can be launched because the project has not been initialized. Please call the 'init' method first !");
		// If no target is specified, run the default one.
		if (_target == null)
			_target = appProject.getDefaultTarget();
		
		// Run the target
		appProject.executeTarget(_target);
  
	}
}
