package com.freeit.empathyquotient.presentation.view

import android.animation.AnimatorSet
import android.animation.ValueAnimator
import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.MotionEvent
import androidx.appcompat.widget.AppCompatTextView
import androidx.core.animation.doOnEnd
import androidx.core.content.ContextCompat
import com.freeit.empathyquotient.R
import com.freeit.empathyquotient.core.App
import kotlin.math.asin
import kotlin.math.sqrt

class AnimDiagonalButton @JvmOverloads constructor(
    ctx: Context,
    attributeSet: AttributeSet? = null,
    defStyleAttr: Int = 0
) : AppCompatTextView(ctx, attributeSet, defStyleAttr) {

    private var y1 = 0f
    private var y2 = 0f

    private val listener = mutableListOf<() -> Unit>()

    init {
        typeface = (context.applicationContext as App).fontManager.bold()
    }

    fun setOnClickListener(listener: () -> Unit) {
        this.listener.clear()
        this.listener.add(listener)
    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        return when (event.action) {
            MotionEvent.ACTION_DOWN -> {

                val animHeight = ValueAnimator.ofFloat(0f, height * 2f)
                animHeight.addUpdateListener {
                    y1 = it.animatedValue as Float
                    y2 = -(it.animatedValue as Float)
                    invalidate()
                }
                animHeight.duration = 300L
                animHeight.doOnEnd {
                    y1 = 0f
                    y2 = 0f
                    invalidate()
                }
                animHeight.start()
                true
            }
            MotionEvent.ACTION_UP -> {
                listener.firstOrNull()?.invoke()
                true
            }
            else -> super.onTouchEvent(event)
        }
    }

    private val bgPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        color = ContextCompat.getColor(context, R.color.primaryDarkColor)
    }

    private val strokePaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        color = ContextCompat.getColor(context, R.color.primaryColor)
    }

    override fun onDraw(canvas: Canvas) {

        canvas.drawRect(0f, 0f, width.toFloat(), height.toFloat(), strokePaint)

        canvas.save()

        val sinA = height / sqrt((width * width.toFloat() + height * height))

        val angle = Math.toDegrees(asin(sinA).toDouble())

        canvas.rotate(angle.toFloat())
        canvas.drawRect(0f, 0f, width * 1.5f, y1, bgPaint)
        canvas.rotate(-angle.toFloat())
        canvas.rotate(angle.toFloat())
        canvas.drawRect(0f, 0f, width * 1.5f, y2, bgPaint)

        canvas.restore()

        super.onDraw(canvas)
    }
}