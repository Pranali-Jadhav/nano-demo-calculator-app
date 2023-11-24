package com.nano
import io.micronaut.runtime.EmbeddedApplication
import io.micronaut.test.extensions.junit5.annotation.MicronautTest
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import jakarta.inject.Inject

@MicronautTest
class DemoCalculatorAppTest {

    @Inject
    lateinit var application: EmbeddedApplication<*>

    @Inject
    @field:Client("/calculator")
    lateinit var client: RxHttpClient

    @Test
    fun testGreeting() {
        val result = client.toBlocking().exchange("/greeting", String::class.java)
        Assertions.assertEquals(HttpStatus.OK, result.status)
        Assertions.assertEquals("Hello world!", result.body())
    }

    @Test
    fun testAdd() {
        val numbers = Numbers(10, 5)
        val result = client.toBlocking().exchange("/add", numbers, Map::class.java)
        Assertions.assertEquals(HttpStatus.OK, result.status)
        Assertions.assertEquals(mapOf("result" to 15), result.body())
    }

    @Test
    fun testSubtract() {
        val numbers = Numbers(10, 5)
        val result = client.toBlocking().exchange("/subtract", numbers, Map::class.java)
        Assertions.assertEquals(HttpStatus.OK, result.status)
        Assertions.assertEquals(mapOf("result" to 5), result.body())
    }

    @Test
    fun testItWorks() {
        Assertions.assertTrue(application.isRunning)
    }

}
