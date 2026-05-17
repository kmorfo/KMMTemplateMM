package es.rlujancreations.core.data.networking

import io.ktor.client.HttpClient
import io.ktor.client.engine.HttpClientEngine
import io.ktor.client.plugins.HttpTimeout
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.defaultRequest
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging
import io.ktor.client.request.header
import io.ktor.http.ContentType
import io.ktor.http.contentType
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json

class HttpClientFactory(
    private val apiKey: String? = null,
    private val baseUrl: String,
    private val engine: HttpClientEngine,
) {
    fun build(): HttpClient =
        HttpClient(engine) {
            install(ContentNegotiation) {
                json(
                    Json {
                        ignoreUnknownKeys = true
                    },
                )
            }

            install(Logging) {
                logger =
                    object : Logger {
                        override fun log(message: String) {
                            println(message)
                        }
                    }

                level = LogLevel.ALL
            }

            defaultRequest {
                url(baseUrl)
                contentType(ContentType.Application.Json)
                header(
                    API_KEY_HEADER,
                    apiKey,
                )
            }

            install(HttpTimeout) {
                requestTimeoutMillis = REQUEST_TIMEOUT
                connectTimeoutMillis = CONNECTION_TIMEOUT
                socketTimeoutMillis = SOCKET_TIMEOUT
            }
        }
}

private const val API_KEY_HEADER = "X-API-Key"
private const val CONNECTION_TIMEOUT = 5_000L
private const val REQUEST_TIMEOUT = 5_000L
private const val SOCKET_TIMEOUT = 5_000L
