package com.freeit.empathyquotient.presentation.view.buttons

import android.animation.Animator
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
import com.freeit.empathyquotient.core.extensions.robotoBold
import kotlin.math.asin
import kotlin.math.sqrt

class AnimDiagonalButton(ctx: Context) : AppCompatTextView(ctx) {

    private var y1 = 0f
    private var y2 = 0f

    private val listener = mutableListOf<() -> Unit>()
    private var animator: Animator? = null

    private val bgPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        color = ContextCompat.getColor(context, R.color.primaryDarkColor)
    }

    private val strokePaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        color = ContextCompat.getColor(context, R.color.primaryColor)
    }

    init {
        robotoBold()
    }

    fun setOnClickListener(listener: () -> Unit) {
        this.listener.clear()
        this.listener.add(listener)
    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        return when (event.action) {
            MotionEvent.ACTION_DOWN -> {
                val animator = ValueAnimator.ofFloat(0f, height * 2f)
                animator.addUpdateListener {
                    y1 = it.animatedValue as Float
                    y2 = -(it.animatedValue as Float)
                    invalidate()
                }
                animator.duration = 300L
                animator.start()
                this.animator = animator
                true
            }
            MotionEvent.ACTION_UP -> {
                animator?.cancel()
                y1 = 0f
                y2 = 0f
                invalidate()
                if (event.x >= 0 && event.y >= 0 && event.x <= width && event.y <= height)
                    listener.firstOrNull()?.invoke()
                true
            }
            else -> super.onTouchEvent(event)
        }
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