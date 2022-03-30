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

@Profile("dynamic")
@Configuration
class SpringDocsConfig1{

    val swaggerBasePath = "objects"

    @Bean
    fun multipleOpenApiResource(
        defaultOpenAPIBuilder: ObjectFactory<OpenAPIService?>?, requestBuilder: AbstractRequestService?,
        responseBuilder: GenericResponseService?, operationParser: OperationService?,
        springDocConfigProperties: SpringDocConfigProperties?,
        springDocProviders: SpringDocProviders?
    ): MultipleOpenApiWebMvcResource? {
        return MultipleOpenApiWebMvcResource(
            generateMultipleSpecs(),
            defaultOpenAPIBuilder, requestBuilder,
            responseBuilder, operationParser,
            springDocConfigProperties,
            springDocProviders
        )
    }

    fun generateMultipleSpecs(): List<GroupedOpenApi> {
        val listOfVersions = listOf(
            Pair(API_VERSION_1, Version1::class.java),
            Pair(API_VERSION_2, Version2::class.java)
        )
        val listOfGroupedOpenApi = mutableListOf<GroupedOpenApi>()
        listOfVersions.forEach {
            listOfGroupedOpenApi.add(buildApi(it))
        }
        return listOfGroupedOpenApi
    }

    fun buildApi(pair: Pair<String, Class<out Annotation>>) : GroupedOpenApi {
        return GroupedOpenApi.builder()
            .group(pair.first)
            .addOpenApiMethodFilter { method -> method.isAnnotationPresent(pair.second) }
            .addOpenApiCustomiser { openApi ->
                openApi?.paths = Paths().extensions(openApi?.paths?.mapKeys {
                    it.key.replace(swaggerBasePath, "")
                })
            }
            .build()
    }
}

@Target(AnnotationTarget.FUNCTION)
annotation class Version1

@Target(AnnotationTarget.FUNCTION)
annotation class Version2