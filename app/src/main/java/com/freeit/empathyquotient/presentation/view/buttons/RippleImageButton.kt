package com.freeit.empathyquotient.presentation.view.buttons

import android.annotation.SuppressLint
import android.content.Context
import android.content.res.ColorStateList
import android.graphics.*
import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.RippleDrawable
import android.util.AttributeSet
import android.view.MotionEvent
import androidx.appcompat.widget.AppCompatImageButton
import androidx.appcompat.widget.AppCompatImageView
import com.freeit.empathyquotient.R
import ru.freeit.noxml.extensions.clickable
import ru.freeit.noxml.extensions.colorBy
import ru.freeit.noxml.extensions.dp
import ru.freeit.noxml.extensions.ripple

class RippleImageButton(ctx: Context) : AppCompatImageView(ctx) {

    init {
        clickable()
        ripple(R.color.yellow_500, dp(32))
    }

}
