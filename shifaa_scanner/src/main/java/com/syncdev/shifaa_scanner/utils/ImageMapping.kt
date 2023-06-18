package com.syncdev.shifaa_scanner.utils

import android.content.Context
import android.graphics.drawable.Drawable
import androidx.core.content.ContextCompat
import com.syncdev.shifaa_scanner.R

class ImageMapping(private val context: Context) {
    fun getDrawableByType(type: String): Drawable? {
        return when(type){
            "Capsule" -> ContextCompat.getDrawable(context, R.drawable.capsule)
            "Tablets" -> ContextCompat.getDrawable(context, R.drawable.tablets)
            "Drops" -> ContextCompat.getDrawable(context, R.drawable.drops)
            "Liquid" -> ContextCompat.getDrawable(context, R.drawable.liquid)
            "Spray" -> ContextCompat.getDrawable(context, R.drawable.spray)
            "Injection" -> ContextCompat.getDrawable(context, R.drawable.injection)
            "Topical" -> ContextCompat.getDrawable(context, R.drawable.topical)
            "Inhale" -> ContextCompat.getDrawable(context, R.drawable.inhaler)
            else -> null
        }
    }
}