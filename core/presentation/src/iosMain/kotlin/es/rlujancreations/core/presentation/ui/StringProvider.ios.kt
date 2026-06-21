package es.rlujancreations.core.presentation.ui

import platform.Foundation.NSBundle

actual class GetStringProvider : StringProvider {
    override fun getString(
        resId: String,
        vararg args: Any,
    ): String {
        val bundle = NSBundle.mainBundle
        return bundle.localizedStringForKey(resId, value = "", table = null)
    }
}
