package com.freeit.empathyquotient.presentation.view.other

import android.animation.ValueAnimator
import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import ru.freeit.noxml.extensions.clickable

class DotView(ctx: Context) : View(ctx) {

    private val listener = mutableListOf<() -> Unit>()
    private var isChecked = false

    init {
        clickable()
    }

    private val selectedDotPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        style = Paint.Style.FILL
        strokeWidth = 3f
        color = Color.WHITE
    }

    private val unselectedDotPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        style = Paint.Style.STROKE
        color = Color.WHITE
        strokeWidth = 3f
    }


    fun setOnClickListener(listener: () -> Unit) {
        this.listener.clear()
        this.listener.add(listener)
    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        if (event.action == MotionEvent.ACTION_UP) {
            if (!isChecked) {
                listener.firstOrNull()?.invoke()
                isChecked = true
            }
            invalidate()
        }
        return super.onTouchEvent(event)
    }

    private var radius = 0f

    fun show(isAnim: Boolean = true) {
        isChecked = true
        if (isAnim) {
            ValueAnimator.ofFloat(0f, layoutParams.width / 2f - 3f).apply {
                addUpdateListener {
                    radius = it.animatedValue as Float
                    invalidate()
                }
                duration = 250L
                start()
            }
        } else {
            radius = layoutParams.width / 2f - 3f
            invalidate()
        }
    }

    fun hide(isAnim: Boolean = true) {
        if (isAnim) {
            ValueAnimator.ofFloat(radius, 0f).apply {
                addUpdateListener {
                    radius = it.animatedValue as Float
                    invalidate()
                }
                duration = 250L
                start()
            }
        } else {
            radius = 0f
            invalidate()
        }
        isChecked = false
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        val strokeRadius = width / 2f - 3f
        canvas.drawCircle(width / 2f, height / 2f, strokeRadius, unselectedDotPaint)
        canvas.drawCircle(width / 2f, height / 2f, radius, selectedDotPaint)
    }

}