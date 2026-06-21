package es.rlujancreations.core.presentation.ui

import es.rlujancreations.core.domain.util.DataError
import kmmtemplatemm.core.designsystem.generated.resources.Res
import kmmtemplatemm.core.designsystem.generated.resources.all_data_synced
import kmmtemplatemm.core.designsystem.generated.resources.data_not_found
import kmmtemplatemm.core.designsystem.generated.resources.error_disk_full
import kmmtemplatemm.core.designsystem.generated.resources.error_no_internet
import kmmtemplatemm.core.designsystem.generated.resources.error_payload_too_large
import kmmtemplatemm.core.designsystem.generated.resources.error_request_timeout
import kmmtemplatemm.core.designsystem.generated.resources.error_serialization
import kmmtemplatemm.core.designsystem.generated.resources.error_server_error
import kmmtemplatemm.core.designsystem.generated.resources.error_too_many_requests
import kmmtemplatemm.core.designsystem.generated.resources.error_unknown
import kmmtemplatemm.core.designsystem.generated.resources.invalid_token
import kmmtemplatemm.core.designsystem.generated.resources.unprocessable_entity
import kmmtemplatemm.core.designsystem.generated.resources.unsupported_media_type

fun DataError.asUiText(): UiText {
    return when (this) {
        DataError.Local.DISK_FULL ->
            UiText.StringResource(
                Res.string.error_disk_full,
            )

        DataError.Local.ALL_DATA_SYNCED ->
            UiText.StringResource(
                Res.string.all_data_synced,
            )

        DataError.Local.DATA_NOT_FOUND ->
            UiText.StringResource(
                Res.string.data_not_found,
            )

        DataError.Network.REQUEST_TIMEOUT ->
            UiText.StringResource(
                Res.string.error_request_timeout,
            )

        DataError.Network.TOO_MANY_REQUESTS ->
            UiText.StringResource(
                Res.string.error_too_many_requests,
            )

        DataError.Network.UNSUPPORTED_MEDIA_TYPE ->
            UiText.StringResource(
                Res.string.unsupported_media_type,
            )

        DataError.Network.NO_INTERNET ->
            UiText.StringResource(
                Res.string.error_no_internet,
            )

        DataError.Network.PAYLOAD_TOO_LARGE ->
            UiText.StringResource(
                Res.string.error_payload_too_large,
            )

        DataError.Network.SERVER_ERROR ->
            UiText.StringResource(
                Res.string.error_server_error,
            )

        DataError.Network.SERIALIZATION ->
            UiText.StringResource(
                Res.string.error_serialization,
            )

        DataError.Network.UNPROCESSABLE_ENTITY ->
            UiText.StringResource(
                Res.string.unprocessable_entity,
            )

        DataError.Network.TOKEN_EXPIRED -> UiText.StringResource(Res.string.invalid_token)

        else -> UiText.StringResource(Res.string.error_unknown)
    }
}
