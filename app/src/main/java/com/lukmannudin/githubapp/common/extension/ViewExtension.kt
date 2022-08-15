package com.lukmannudin.githubapp.common.extension

import android.graphics.drawable.ColorDrawable
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import com.bumptech.glide.Glide
import com.lukmannudin.githubapp.R

fun View.visible() {
    this.visibility = View.VISIBLE
}

fun View.gone() {
    this.visibility = View.GONE
}

fun View.invisible() {
    this.visibility = View.INVISIBLE
}

fun TextView.showIfNotEmpty(string: String) {
    if (string.isNotEmpty()) {
        this.visible()
        this.text = string
    } else {
        this.gone()
    }
}

fun ImageView.showAsCircle(url: String?) {
    Glide.with(this.context)
        .load(url)
        .placeholder(ColorDrawable(ContextCompat.getColor(this.context, R.color.shuttle_gray)))
        .error(ColorDrawable(ContextCompat.getColor(this.context, R.color.shuttle_gray)))
        .dontAnimate()
        .circleCrop()
        .into(this)
}