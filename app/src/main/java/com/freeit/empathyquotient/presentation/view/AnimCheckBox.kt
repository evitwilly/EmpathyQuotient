package com.freeit.empathyquotient.presentation.view

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Path
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import androidx.core.content.ContextCompat
import com.freeit.empathyquotient.R

class AnimCheckBox @JvmOverloads constructor(
    ctx: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : View(ctx, attrs, defStyleAttr) {

    private val listener = mutableListOf<(Boolean) -> Unit>()

    private var isChecked = false

    fun setChecked(isChecked: Boolean) {
        if (this.isChecked != isChecked) {
            if (isChecked) {
                check()
            } else {
                uncheck()
            }
        }
    }

    fun setCheckedWithoutAnim(isChecked: Boolean) {
        if (this.isChecked != isChecked) {
            if (isChecked) {
                checkWithoutAnim()
            } else {
                uncheckWithoutAnim()
            }
        }
    }

    fun setOnCheckedListener(listener: (isChecked: Boolean) -> Unit) {
        this.listener.clear()
        this.listener.add(listener)
    }

    private fun uncheck() {
        isChecked = false
        CheckAnimator(width.toFloat(), height.toFloat()).uncheck(
            onFirstXListener = {
                firstX = it.animatedValue as Float
                invalidate()
            },
            onFirstYListener = {
                firstY = it.animatedValue as Float
                invalidate()
            },
            onLastXListener = {
                lastX = it.animatedValue as Float
                invalidate()
            },
            onLastYListener = {
                lastY = it.animatedValue as Float
                invalidate()
            }
        )
    }

    private fun uncheckWithoutAnim() {
        isChecked = false
        reset()
        invalidate()
    }

    private fun checkWithoutAnim() {
        isChecked = true
        firstX = width / 2.5f
        firstY = height - height / 3f
        lastX = width - width / 6f
        lastY = height / 3f
        invalidate()
    }

    private fun check() {
        reset()
        isChecked = true
        CheckAnimator(width.toFloat(), height.toFloat()).check(
            onFirstXListener = {
                firstX = it.animatedValue as Float
                invalidate()
            },
            onFirstYListener = {
                firstY = it.animatedValue as Float
                invalidate()
            },
            onLastXListener = {
                lastX = it.animatedValue as Float
                invalidate()
            },
            onLastYListener = {
                lastY = it.animatedValue as Float
                invalidate()
            }
        )
    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        return when (event.action) {
            MotionEvent.ACTION_DOWN -> {
                setChecked(true)
                true
            }
            MotionEvent.ACTION_UP -> {
                listener.firstOrNull()?.invoke(true)
                true
            }
            else -> super.onTouchEvent(event)
        }
    }

    private val circlePaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        color = ContextCompat.getColor(context, R.color.white)
        style = Paint.Style.STROKE
        strokeWidth = 10f
    }

    private val checkPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        color = ContextCompat.getColor(context, R.color.white)
        style = Paint.Style.STROKE
        strokeWidth = 20f
    }

    private var firstX = 0f
    private var firstY = 0f
    private var lastX = 0f
    private var lastY = 0f

    private fun reset() {
        firstX = width / 5.6f
        lastX = firstX
        firstY = height / 2.2f
        lastY = firstY
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        reset()
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        canvas.drawCircle(width / 2f, height / 2f, width / 2f - 5f, circlePaint)

        val path = Path()
        path.moveTo(width / 5.6f, height / 2.2f)
        path.lineTo(firstX, firstY)
        path.lineTo(lastX, lastY)

        canvas.drawPath(path, checkPaint)
    }
}