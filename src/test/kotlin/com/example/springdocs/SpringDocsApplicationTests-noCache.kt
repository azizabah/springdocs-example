package com.example.springdocs

import org.hamcrest.CoreMatchers.containsString
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.get
import java.awt.geom.Path2D.contains

@ActiveProfiles(value = ["dynamic", "test"])
@AutoConfigureMockMvc(addFilters = false) //Disables security
@SpringBootTest
class `SpringDocsApplicationTests-noCache` {
	@Autowired
	private lateinit var mockMvc : MockMvc

	@Test
	fun contextLoads() {
	}

	@Test
	fun `Get First OpenAPI Group`() {
		mockMvc.get("/v3/api-docs/1")
			.andExpect {
				status { isOk() }
				content {
					this.string(containsString("whoAmI"))
				}
			}

	}

	@Test
	fun `Get Second OpenAPI Group`() {
		mockMvc.get("/v3/api-docs/2")
			.andExpect {
				status { isOk() }
				content {
					this.string(containsString("whatAmI"))
				}
			}

	}

}
