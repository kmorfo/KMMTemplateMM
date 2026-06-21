package es.rlujancreations.core.presentation.ui

interface StringProvider {
    fun getString(
        resId: String,
        vararg args: Any,
    ): String
}

expect class GetStringProvider : StringProvider
