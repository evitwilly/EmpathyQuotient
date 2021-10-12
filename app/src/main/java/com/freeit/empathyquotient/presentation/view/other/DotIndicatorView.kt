package com.freeit.empathyquotient.presentation.view.other

import android.animation.ValueAnimator
import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import com.freeit.empathyquotient.presentation.view.layouts.AbsoluteLayout

class DotView @JvmOverloads constructor(
    ctx: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : View(ctx, attrs, defStyleAttr) {

    private var isChecked = false

    init {
        isClickable = true
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

    private val listener = mutableListOf<() -> Unit>()

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

class DotIndicatorView  @JvmOverloads constructor(
    ctx: Context,
    private val dotCount: Int
): AbsoluteLayout(ctx) {

    private fun Int.dp() = (context.resources.displayMetrics.density * this).toInt()

    private val gap = 5.dp()

    init {
        val dotSize = 20.dp()
        for (i in 0 until dotCount) {
            addView(DotView(ctx).apply {
                layoutParams = LayoutParams(dotSize, dotSize, i * dotSize + (gap * i), height / 2)
                setOnClickListener {
                    selectDot(i, true)
                    listener.firstOrNull()?.invoke(i)
                }
            })
        }
    }

    fun selectDot(dot: Int, isAnim: Boolean) {
        if (dot >= 0 || dot < dotCount) {
            for (i in 0 until dotCount) {
                (getChildAt(i) as DotView).hide(isAnim = isAnim)
            }
            (getChildAt(dot) as DotView).show(isAnim = isAnim)
        }
    }

    private val listener = mutableListOf<(dot: Int) -> Unit>()

    fun setDotChangeListener(listener: (dot: Int) -> Unit) {
        this.listener.clear()
        this.listener.add(listener)
    }



}
