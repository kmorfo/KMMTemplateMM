package es.rlujancreations.core.presentation.ui

import android.content.Context

actual class GetStringProvider(private val context: Context) : StringProvider {
    override fun getString(
        resId: String,
        vararg args: Any,
    ): String {
        val id = context.resources.getIdentifier(resId, "string", context.packageName)
        return context.getString(id, *args)
    }
}
