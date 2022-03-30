package com.example.springdocs.controllers

import com.example.Constants.API_VERSION_1
import com.example.Constants.API_VERSION_2
import com.example.Constants.API_VERSION_HEADER
import com.example.springdocs.config.Version1
import com.example.springdocs.config.Version2
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.security.Principal

@RequestMapping("/objects")
@RestController
class SampleController {
    @Version1
    @GetMapping(path = ["me"], headers = ["${API_VERSION_HEADER}=${API_VERSION_1}"])
    fun whoAmI(principal: Principal): Principal = principal

    @Version2
    @GetMapping(path = ["me"], headers = ["${API_VERSION_HEADER}=${API_VERSION_2}"])
    fun whatAmI(principal: Principal): Principal = principal
}