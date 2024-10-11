package org.http4k


import com.amazonaws.services.lambda.runtime.events.ScheduledEvent
import com.natpryce.hamkrest.assertion.assertThat
import com.natpryce.hamkrest.equalTo
import java.lang.reflect.Proxy
import org.junit.jupiter.api.Test

class DictionaryAppFnHandlerTest {

    @Test
    fun `event function test`() {
        assertThat(DictionaryAppFnHandler(ScheduledEvent().apply {
            account = "1234567890"
        }, proxy()), equalTo("{account: 1234567890,}"))
    }
}

// this is here in place of a mock
inline fun <reified T> proxy(): T =
    Proxy.newProxyInstance(T::class.java.classLoader, arrayOf(T::class.java)) { _, _, _ -> TODO("") } as T
