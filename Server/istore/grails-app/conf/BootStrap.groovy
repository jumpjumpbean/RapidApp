import com.istore.util.FileUtil
class BootStrap {
	def grailsApplication
	def init = { servletContext ->
		FileUtil.grailsApplication = grailsApplication
	}
	def destroy = {
	}
}
