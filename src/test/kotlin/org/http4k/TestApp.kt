package org.http4k

import org.http4k.server.SunHttp
import org.http4k.server.asServer

val app = DictionaryAppAppLoader(emptyMap())

fun main() {
    app.asServer(SunHttp(8000)).start()
}
