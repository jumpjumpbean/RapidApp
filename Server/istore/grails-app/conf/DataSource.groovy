import java.io.IOException

import org.springframework.core.io.ClassPathResource
import org.springframework.core.io.support.PropertiesLoaderUtils

def pros

try {
    pros = PropertiesLoaderUtils.loadProperties(new ClassPathResource("app-config.properties"))
} catch (IOException e) {
    e.printStackTrace();
}

dataSource {
    pooled = true
    driverClassName = pros.getProperty("jdbc.driverClassName")
    username = pros.getProperty("jdbc.username")
    password = pros.getProperty("jdbc.password")
    //loggingSql=true
    //	dialect = org.hibernate.dialect.MySQLInnoDBDialect
    properties {
        maxActive = Integer.parseInt(pros.getProperty("jdbc.properties.maxActive"))
        maxIdle = Integer.parseInt(pros.getProperty("jdbc.properties.maxIdle"))
        minIdle = Integer.parseInt(pros.getProperty("jdbc.properties.minIdle"))
        initialSize = Integer.parseInt(pros.getProperty("jdbc.properties.initialSize"))
        minEvictableIdleTimeMillis = Integer.parseInt(pros.getProperty("jdbc.properties.minEvictableIdleTimeMillis"))
        timeBetweenEvictionRunsMillis = Integer.parseInt(pros.getProperty("jdbc.properties.timeBetweenEvictionRunsMillis"))
        maxWait = Integer.parseInt(pros.getProperty("jdbc.properties.maxWait"))
        validationQuery = "select 1"
        testOnBorrow = true
        testWhileIdle = true
    }
}
hibernate {
    cache.use_second_level_cache=true
    cache.use_query_cache=true
    cache.provider_class='net.sf.ehcache.hibernate.EhCacheProvider'
}


// environment specific settings
environments {
    development {
        dataSource {
            dbCreate = "update" // one of 'create', 'create-drop', 'update', 'validate', ''
            url = pros.getProperty("jdbc.url")
        }
    }
    test {
        dataSource {
            dbCreate = "update"
            url = pros.getProperty("jdbc.url")
        }
    }
    production {
        dataSource {
            dbCreate = "update"
            url = pros.getProperty("jdbc.url")
        }
    }
}
