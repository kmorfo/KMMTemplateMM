package es.rlujancreations.core.domain.util

/**
 * Created by Raúl L.C. on 19/1/25.
 */
sealed interface DataError : Error {
    enum class Network : DataError {
        REQUEST_TIMEOUT,
        UNAUTHORIZED,
        NO_DATA,
        CONFLICT,
        UNSUPPORTED_MEDIA_TYPE,
        TOO_MANY_REQUESTS,
        NO_INTERNET,
        PAYLOAD_TOO_LARGE,
        SERVER_ERROR,
        SERIALIZATION,
        UNPROCESSABLE_ENTITY,
        TOKEN_EXPIRED,
        UNKNOWN,
    }

    enum class Local : DataError {
        DISK_FULL,
        ALL_DATA_SYNCED,
        DATA_NOT_FOUND,
        UNKNOWN,
    }
}
