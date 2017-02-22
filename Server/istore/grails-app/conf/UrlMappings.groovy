class UrlMappings {

	static mappings = {
		"/$controller/$action?/$id?"{
			constraints {
				// apply constraints here
			}
		}
		"/user/$action?/$id?"(controller: "user")
		"/image/$action?/$id?"(controller: "image")
		"/app/$action?/$userid?/$objid?"(controller: "mobile")
		"/"(controller: "router",action:"index")
		"500"(view:'/error')
	}
}
