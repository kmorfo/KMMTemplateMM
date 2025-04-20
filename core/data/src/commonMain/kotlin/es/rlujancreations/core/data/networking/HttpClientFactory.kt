package es.rlujancreations.core.data.networking

import io.ktor.client.HttpClient
import io.ktor.client.engine.cio.CIO
import io.ktor.client.plugins.HttpTimeout
import io.ktor.client.plugins.auth.Auth
import io.ktor.client.plugins.auth.providers.BearerTokens
import io.ktor.client.plugins.auth.providers.bearer
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.defaultRequest
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging
import io.ktor.http.ContentType
import io.ktor.http.contentType
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json

/**
 * Created by Raúl L.C. on 19/1/25.
 */

class HttpClientFactory(
//    Inject your own session storage to manage tokens
) {
    companion object {
        const val REQUEST_TIMEOUT = 5000L
        const val CONNECTION_TIMEOUT = 5000L
        const val SOCKET_TIMEOUT = 5000L
    }

    fun build(): HttpClient {
        return HttpClient(CIO) { // CIO for not https
            install(ContentNegotiation) {
                json(
                    json =
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
                contentType(ContentType.Application.Json)
            }
            install(HttpTimeout) {
                requestTimeoutMillis = REQUEST_TIMEOUT
                connectTimeoutMillis = CONNECTION_TIMEOUT
                socketTimeoutMillis = SOCKET_TIMEOUT
            }
            install(Auth) {
                bearer {
                    loadTokens {
                        // TODO 1 Save your tokens in a secure way
                        // TODO 2 DELETE this line on production
//                        println(
//                            "TOKEN core.data.networking: ${info?.accessToken}",
//                        )
//                        BearerTokens(
//                            accessToken = info?.accessToken ?: "",
//                            refreshToken = info?.accessToken ?: "",
//                        )
                        BearerTokens(
                            accessToken = "",
                            refreshToken = "",
                        )
                    }
                }
            }
        }
    }
}
