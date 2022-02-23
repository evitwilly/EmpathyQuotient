package com.freeit.empathyquotient.presentation.view.buttons

import android.content.Context
import android.graphics.*
import android.graphics.drawable.GradientDrawable
import android.util.AttributeSet
import android.view.Gravity
import android.view.MotionEvent
import android.widget.LinearLayout
import androidx.appcompat.widget.AppCompatImageView
import androidx.appcompat.widget.AppCompatTextView
import androidx.appcompat.widget.LinearLayoutCompat
import androidx.core.content.ContextCompat
import com.freeit.empathyquotient.R
import com.freeit.empathyquotient.core.extensions.robotoBold
import ru.freeit.noxml.extensions.*

class ArrowScalingButton(ctx: Context) : LinearLayoutCompat(ctx) {

    private var radius: Float = 0f

    private var pointX = 0f
    private var pointY = 0f

    private val listener = mutableListOf<() -> Unit>()
    private val arrowScalingAnimator = ArrowScalingAnimator()

    init {
        padding(horizontal = dp(40), vertical = dp(10))
        clickable()
        horizontal()
        center()
        bg(GradientDrawable().apply {
            setColor(colorBy(R.color.primaryColor))
        })

        val buttonText = text {
            text(context.getString(R.string.start_testing).uppercase())
            colorRes(R.color.white)
            robotoBold()
            fontSize(26f)
            layoutParams(linearLayoutParams().wrapWidth().wrapHeight().build())
        }
        val icon = imageView {
            img(R.drawable.ic_forward)
            layoutParams(linearLayoutParams().width(0).wrapHeight()
                .marginStart(dp(4))
                .build())
        }

        addView(buttonText, icon)
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        radius = w * 2f
    }

    fun setOnClickListener(listener: () -> Unit) {
        this.listener.clear()
        this.listener.add(listener)
    }

    override fun onTouchEvent(event: MotionEvent): Boolean {

        when (event.action) {
            MotionEvent.ACTION_DOWN -> {
                pointX = event.x
                pointY = event.y

                arrowScalingAnimator.forward(this, getChildAt(1))
            }
            MotionEvent.ACTION_UP -> {
                if (event.x >= 0 && event.y >= 0 && event.x <= width && event.y <= height)
                    listener.firstOrNull()?.invoke()
                arrowScalingAnimator.reverse(this, getChildAt(1))
            }
        }
        return true
    }

    override fun dispatchDraw(canvas: Canvas) {
        canvas.clipPath(Path().apply {
            addRoundRect(RectF(0f, 0f, width.toFloat(), height.toFloat()), 50f, 50f, Path.Direction.CW)
        })
        super.dispatchDraw(canvas)
    }


}
