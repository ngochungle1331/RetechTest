package com.hungln.retechtest.util

import android.graphics.Typeface
import android.text.SpannableString
import android.text.Spanned
import android.text.style.StyleSpan
import android.view.View
import java.util.regex.Pattern

fun View.makeVisible() {
    visibility = View.VISIBLE
}

fun View.makeInVisible() {
    visibility = View.INVISIBLE
}

fun View.makeGone() {
    visibility = View.GONE
}

fun makeBoldFollowersText(followersCount: Int?): SpannableString {
    val text = "$followersCount followers"
    val spannableString = SpannableString(text)
    spannableString.setSpan(
        StyleSpan(Typeface.BOLD),
        0,
        followersCount.toString().length,
        Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
    )

    return spannableString
}
