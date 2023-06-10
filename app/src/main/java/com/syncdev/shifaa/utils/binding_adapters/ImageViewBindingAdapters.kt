package com.syncdev.shifaa.utils.binding_adapters

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.syncdev.shifaa.R

object ImageViewBindingAdapters {
    @JvmStatic
    @BindingAdapter("ImgGender")
    fun setGenderImage(imageView: ImageView, gender: String?) {
        val drawableResId = when (gender) {
            "Male" -> R.drawable.doctor_male
            "Female" -> R.drawable.doctor_female
            else -> R.drawable.doctor_male
        }
        imageView.setImageResource(drawableResId)
    }
}