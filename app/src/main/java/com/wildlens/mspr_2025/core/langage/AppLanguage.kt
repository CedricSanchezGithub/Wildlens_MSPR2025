package com.wildlens.mspr_2025.core.langage

import android.app.Activity
import android.content.Context
import android.content.ContextWrapper
import android.os.Handler
import android.os.Looper
import androidx.annotation.StringRes
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.os.LocaleListCompat
import com.wildlens.mspr_2025.R

enum class AppLanguage(val tag: String, @StringRes val labelRes: Int) {
    FRENCH("fr", R.string.lang_fr),
    ENGLISH("en", R.string.lang_en)
}

fun setAppLanguage(context: Context, language: AppLanguage) {
    // Set the application locales
    val locales = LocaleListCompat.forLanguageTags(language.tag)
    AppCompatDelegate.setApplicationLocales(locales)

    // Find the activity from context
    var currentContext = context
    while (currentContext is ContextWrapper && currentContext !is Activity) {
        currentContext = currentContext.baseContext
    }

    // If we found an activity, restart it to apply the language change
    if (currentContext is Activity) {
        // Use a handler to delay the recreation slightly to ensure the locale change is applied
        Handler(Looper.getMainLooper()).postDelayed({
            val intent = currentContext.intent
            currentContext.finish()
            currentContext.startActivity(intent)
            currentContext.overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out)
        }, 100)
    }
}
