package com.lukmannudin.githubapp.common

import android.view.View
import android.widget.TextView

fun View.visible() {
    this.visibility = View.VISIBLE
}

fun View.gone() {
    this.visibility = View.GONE
}

fun TextView.showIfNotEmpty(string: String) {
    if (string.isNotEmpty()) {
        this.visible()
        this.text = string
    } else {
        this.gone()
    }
}