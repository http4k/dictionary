package org.http4k

import com.amazonaws.services.lambda.runtime.Context
import com.amazonaws.services.lambda.runtime.events.ScheduledEvent
import org.http4k.serverless.FnHandler

val DictionaryAppFnHandler = FnHandler { e: ScheduledEvent, _: Context ->
    println("Received $e")
    e.toString()
}
