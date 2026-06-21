package es.rlujancreations.core.presentation.ui

import java.util.ResourceBundle

actual class GetStringProvider : StringProvider {
    private val bundle: ResourceBundle by lazy {
        ResourceBundle.getBundle("strings")
    }

    override fun getString(
        resId: String,
        vararg args: Any,
    ): String {
        val template = bundle.getString(resId)
        return if (args.isEmpty()) template else String.format(template, *args)
    }
}
