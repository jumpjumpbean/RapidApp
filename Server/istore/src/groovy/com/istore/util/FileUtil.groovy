package com.istore.util

class FileUtil {
    def static grailsApplication
    def static getFileNameByUpload(def multipartFile) {
        try {

            def tempFilePath = "${grailsApplication.config.grails.filePath.imagePath}temp/"
            def f = new File(tempFilePath)
            if (!f.exists()){
                f.mkdir()
            }
            def fileNameWithoutPath
            def fileAbsolutePath = multipartFile.fileItem.name
            def filename
            if(fileAbsolutePath.indexOf('.') != -1) {
                fileNameWithoutPath = NumberUtil.getRandomNumber(10) + fileAbsolutePath.substring(fileAbsolutePath.lastIndexOf("."), fileAbsolutePath.length())
            } else {
                fileNameWithoutPath = NumberUtil.getRandomNumber(10) + '.jpg'
            }

            filename= tempFilePath + fileNameWithoutPath

            multipartFile.transferTo(new File(filename))

            return filename

        } catch(Exception e) {
            println e
            e.printStackTrace()
        }
    }

    // 复制文件
    public static void copyFile(File sourceFile, File targetFile) throws IOException {
        BufferedInputStream inBuff = null;
        BufferedOutputStream outBuff = null;
        try {
            // 新建文件输入流并对它进行缓冲
            inBuff = new BufferedInputStream(new FileInputStream(sourceFile));
            // 新建文件输出流并对它进行缓冲
            outBuff = new BufferedOutputStream(new FileOutputStream(targetFile));
            // 缓冲数组
            byte[] b = new byte[1024 * 5];
            int len;
            while ((len = inBuff.read(b)) != -1) {
                outBuff.write(b, 0, len);
            }
            // 刷新此缓冲的输出流
            outBuff.flush();
        }catch (Exception e){
            println e
            e.printStackTrace()
        }finally {
            // 关闭流
            if (inBuff != null)
                inBuff.close();
            if (outBuff != null)
                outBuff.close();
        }
    }
	
	/*
	 * Copy the entire directory from sourceDir to targetDir
	 * nf 2013/7/29
	 */
	def static copyDirectiory(String sourceDir, String targetDir) throws IOException {
		def antBuilder = new AntBuilder()
		antBuilder.delete(dir: targetDir)
		antBuilder.copy(todir: targetDir) {
			fileset(dir : sourceDir)
		}
	}
	
	/*
	 * Find and replace the src to dest in all the java and xml utf-8 files under dir
	 * nf 2013/7/29
	 */
	def static replaceDirFileText(String src, String dest, File dir){		
		def fileText
		def exts = [".java", ".xml"]
		dir.eachFileRecurse(
			{file ->
				for (ext in exts){
					if (file.name.endsWith(ext)) {
						fileText = file.text
						fileText = fileText.replaceAll(src, dest)
						file.write(fileText, "utf-8")
					}
				}
			}
		)
	}

	def static replaceFileText(def pattern, String replacement, File file){
		def fileText
		fileText = file.text
		fileText = fileText.replaceFirst(pattern, replacement)
		file.write(fileText, "utf-8")
	}
}
