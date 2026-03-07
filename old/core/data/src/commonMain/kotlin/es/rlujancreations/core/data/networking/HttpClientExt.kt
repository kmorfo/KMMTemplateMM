package es.rlujancreations.core.data.networking

import es.rlujancreations.core.data.BuildConfig
import es.rlujancreations.core.domain.util.DataError
import es.rlujancreations.core.domain.util.Result
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.delete
import io.ktor.client.request.get
import io.ktor.client.request.parameter
import io.ktor.client.request.patch
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.client.request.url
import io.ktor.client.statement.HttpResponse
import io.ktor.http.ContentType
import io.ktor.http.HttpHeaders
import io.ktor.http.HttpStatusCode
import io.ktor.http.contentType
import io.ktor.http.headers
import io.ktor.util.network.UnresolvedAddressException
import kotlinx.coroutines.CancellationException
import kotlinx.serialization.SerializationException

/**
 * Created by Raúl L.C. on 19/1/25.
 */
suspend inline fun <reified Response : Any> HttpClient.get(
    route: String,
    queryParameters: Map<String, String> = mapOf(),
): Result<Response, DataError.Network> {
    return safeCall {
        get {
            url(constructRoute(route))
            queryParameters.forEach { (key, value) ->
                parameter(key, value)
            }
        }
    }
}

suspend inline fun HttpClient.getPDF(
    route: String,
    queryParameters: Map<String, String> = mapOf(),
): Result<ByteArray, DataError.Network> {
    return try {
        val response =
            get {
                url(constructRoute(route))
                queryParameters.forEach { (key, value) ->
                    parameter(key, value)
                }
                headers {
                    append(HttpHeaders.Accept, "application/pdf")
                }
            }

        when (response.status) {
            HttpStatusCode.OK -> {
                val bytes = response.body<ByteArray>()
                if (response.contentType()?.match(ContentType.Application.Pdf) == true) {
                    Result.Success(bytes)
                } else {
                    Result.Error(DataError.Network.UNSUPPORTED_MEDIA_TYPE)
                }
            }

            else -> Result.Error(DataError.Network.SERVER_ERROR)
        }
    } catch (e: Exception) {
        Result.Error(DataError.Network.UNKNOWN)
    }
}

suspend inline fun <reified Request, reified Response : Any> HttpClient.post(
    route: String,
    queryParameters: Map<String, Any?> = mapOf(),
    body: Request,
): Result<Response, DataError.Network> {
    return safeCall {
        post {
            url(constructRoute(route))
            queryParameters.forEach { (key, value) ->
                parameter(key, value)
            }
            setBody(body)
        }
    }
}

suspend inline fun <reified Request, reified Response : Any> HttpClient.patch(
    route: String,
    queryParameters: Map<String, Any?> = mapOf(),
    body: Request,
): Result<Response, DataError.Network> {
    return safeCall {
        patch {
            url(constructRoute(route))
            queryParameters.forEach { (key, value) ->
                parameter(key, value)
            }
            setBody(body)
        }
    }
}

suspend inline fun <reified Response : Any> HttpClient.delete(
    route: String,
    queryParameters: Map<String, Any?> = mapOf(),
): Result<Response, DataError.Network> {
    return safeCall {
        delete {
            url(constructRoute(route))
            queryParameters.forEach { (key, value) ->
                parameter(key, value)
            }
        }
    }
}

suspend inline fun <reified T> safeCall(execute: () -> HttpResponse): Result<T, DataError.Network> {
    val response =
        try {
            execute()
        } catch (e: UnresolvedAddressException) {
            e.printStackTrace()
            return Result.Error(DataError.Network.NO_INTERNET)
        } catch (e: SerializationException) {
            e.printStackTrace()
            return Result.Error(DataError.Network.SERIALIZATION)
        } catch (e: Exception) {
            if (e is CancellationException) throw e
            e.printStackTrace()
            return Result.Error(DataError.Network.UNKNOWN)
        }

    return responseToResult(response)
}

suspend inline fun <reified T> responseToResult(
    response: HttpResponse,
): Result<T, DataError.Network> {
    return when (response.status.value) {
        in 200..299 -> Result.Success(response.body<T>())
        in 401..403 -> Result.Error(DataError.Network.UNAUTHORIZED)
        404 -> Result.Error(DataError.Network.NO_DATA)
        408 -> Result.Error(DataError.Network.REQUEST_TIMEOUT)
        409 -> Result.Error(DataError.Network.CONFLICT)
        415 -> Result.Error(DataError.Network.UNSUPPORTED_MEDIA_TYPE)
        413 -> Result.Error(DataError.Network.PAYLOAD_TOO_LARGE)
        422 -> Result.Error(DataError.Network.UNPROCESSABLE_ENTITY)
        429 -> Result.Error(DataError.Network.TOO_MANY_REQUESTS)
        in 500..599 -> Result.Error(DataError.Network.SERVER_ERROR)
        else -> Result.Error(DataError.Network.UNKNOWN)
    }
}

fun constructRoute(route: String): String {
    return when {
        BuildConfig.BASE_URL.let { route.contains(it) } -> route
        route.startsWith("/") -> BuildConfig.BASE_URL + route
        else -> BuildConfig.BASE_URL + "/$route"
    }
}
