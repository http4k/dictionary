package org.http4k.dictionary

import org.http4k.client.JavaHttpClient
import org.http4k.core.Method
import org.http4k.core.Method.GET
import org.http4k.core.Request
import org.http4k.core.Response
import org.http4k.core.Status
import org.http4k.core.Status.Companion.OK
import org.http4k.routing.bind
import org.http4k.routing.path
import org.http4k.routing.routes
import org.http4k.serverless.ApiGatewayV2LambdaFunction

val http4kApp = routes(
    "/{word}" bind GET to { request: Request ->
        val words = setOf("the", "quick", "brown", "fox", "jumps", "over", "the", "lazy", "dog", "cat")
        if (request.path("word") in words) Response(OK) else Response(Status.NOT_FOUND)
    },
    "/" bind GET to { Response(OK).body("Welcome to the http4k Dictionary app!").header("content-type", "text/html; charset=utf-8") }
)

@Suppress("unused")
class DictionaryApp : ApiGatewayV2LambdaFunction(http4kApp)
