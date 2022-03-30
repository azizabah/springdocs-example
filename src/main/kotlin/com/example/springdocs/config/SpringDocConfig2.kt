package com.example.springdocs.config

import com.example.Constants.API_VERSION_1
import com.example.Constants.API_VERSION_2
import io.swagger.v3.oas.models.Paths
import org.springdoc.core.AbstractRequestService
import org.springdoc.core.GenericResponseService
import org.springdoc.core.GroupedOpenApi
import org.springdoc.core.OpenAPIService
import org.springdoc.core.OperationService
import org.springdoc.core.SpringDocConfigProperties
import org.springdoc.core.SpringDocProviders
import org.springdoc.webmvc.api.MultipleOpenApiWebMvcResource
import org.springframework.beans.factory.ObjectFactory
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Profile

@Profile("manual")
@Configuration
class SpringDocsConfig2{

    val swaggerBasePath = "objects"

    @Bean
    fun buildApi() : GroupedOpenApi {
        return GroupedOpenApi.builder()
            .group(API_VERSION_1)
            .addOpenApiMethodFilter { method -> method.isAnnotationPresent(Version1::class.java) }
            .addOpenApiCustomiser { openApi ->
                openApi?.paths = Paths().extensions(openApi?.paths?.mapKeys {
                    it.key.replace(swaggerBasePath, "")
                })
            }
            .build()
    }

    @Bean
    fun buildApi2() : GroupedOpenApi {
        return GroupedOpenApi.builder()
            .group(API_VERSION_2)
            .addOpenApiMethodFilter { method -> method.isAnnotationPresent(Version2::class.java) }
            .addOpenApiCustomiser { openApi ->
                openApi?.paths = Paths().extensions(openApi?.paths?.mapKeys {
                    it.key.replace(swaggerBasePath, "")
                })
            }
            .build()
    }
}