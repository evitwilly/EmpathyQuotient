package com.freeit.empathyquotient.presentation.view.buttons

import android.animation.Animator
import android.animation.ValueAnimator
import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.util.TypedValue
import android.view.Gravity
import android.view.MotionEvent
import android.widget.LinearLayout
import androidx.appcompat.widget.AppCompatImageView
import androidx.appcompat.widget.AppCompatTextView
import androidx.appcompat.widget.LinearLayoutCompat
import androidx.core.animation.doOnEnd
import androidx.core.content.ContextCompat
import com.freeit.empathyquotient.R
import com.freeit.empathyquotient.core.extensions.robotoBold
import ru.freeit.noxml.extensions.*
import kotlin.math.roundToInt

class JustButton(ctx: Context) : LinearLayoutCompat(ctx) {

    private val borderPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        color = ContextCompat.getColor(context, R.color.primaryDarkColor)
        strokeWidth = 8f
        style = Paint.Style.STROKE
    }

    private val bgPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        color = ContextCompat.getColor(context, R.color.primaryDarkColor)
        style = Paint.Style.FILL
    }

    private var animWidth = 0f
    private val listener = mutableListOf<() -> Unit>()
    private var animator: Animator? = null

    private val text = text {
        robotoBold()
        fontSize(17f)
        colorRes(R.color.white)
        isAllCaps = true
        layoutParams(linearLayoutParams().wrapWidth().wrapHeight()
            .gravity(Gravity.CENTER_VERTICAL)
            .marginEnd(dp(16))
            .build())
    }

    private val icon = imageView {
        layoutParams(linearLayoutParams()
            .width(dp(32)).height(dp(32))
            .gravity(Gravity.CENTER_VERTICAL)
            .build())
    }

    fun changeText(textString: String) { text.text(textString) }
    fun changeIcon(resourceId: Int) { icon.img(resourceId) }

    fun onClick(listener: () -> Unit) {
        this.listener.clear()
        this.listener.add(listener)
    }

    init {
        horizontal()
        padding(dp(24), dp(16), dp(24), dp(16))
        addView(text, icon)
    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        return when (event.action) {
            MotionEvent.ACTION_DOWN -> {
                val animator = ValueAnimator.ofFloat(0f, width.toFloat())
                animator.addUpdateListener {
                    animWidth = it.animatedValue as Float
                    invalidate()
                }
                animator.duration = 400L
                animator.start()
                this.animator = animator
                true
            }
            MotionEvent.ACTION_UP -> {
                animator?.cancel()
                animWidth = 0f
                invalidate()
                if (event.x >= 0 && event.y >= 0 && event.x <= width && event.y <= height)
                    listener.firstOrNull()?.invoke()
                true
            }
            else -> super.onTouchEvent(event)
        }
    }

    override fun dispatchDraw(canvas: Canvas) {
        canvas.drawRect(8f, 8f, width - 8f, height - 8f, borderPaint)
        canvas.drawRect(0f, 0f, animWidth, height.toFloat(), bgPaint)
        super.dispatchDraw(canvas)
    }
}