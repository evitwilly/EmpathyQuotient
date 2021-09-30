package com.freeit.empathyquotient.presentation.view

import android.animation.ValueAnimator
import android.content.Context
import android.graphics.*
import android.graphics.drawable.GradientDrawable
import android.util.AttributeSet
import android.view.MotionEvent
import androidx.appcompat.widget.AppCompatImageView
import androidx.core.animation.doOnEnd
import androidx.core.animation.doOnStart

private class RippleAnimator(private var radius: Float, private var delay: Long) {

    private val animators = mutableListOf<ValueAnimator>()
    private var isRunning: Boolean = false

    fun changeRippleRadius(radius: Float) {
        this.radius = radius
    }

    fun changeDelay(delay: Long) {
        this.delay = delay
    }

    fun start(updateListener: ValueAnimator.AnimatorUpdateListener) {
        val anim = ValueAnimator.ofFloat(0f, radius)
        anim.addUpdateListener(updateListener)

        anim.doOnStart { isRunning = true }
        anim.doOnEnd { isRunning = false }
        anim.duration = delay

        animators.firstOrNull()?.cancel()
        animators.clear()

        animators.add(anim)
        anim.start()
    }

    fun stop(after: () -> Unit) {
        if (isRunning) {
            animators.firstOrNull()?.doOnEnd {
                after()
            }
        } else {
            after()
        }
    }
}

class RippleImageButton @JvmOverloads constructor(
    ctx: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : AppCompatImageView(ctx, attrs, defStyleAttr) {

    private var radius: Float = 0f
    private var initial: Float = 0f

    private var pointX = 0f
    private var pointY = 0f


    private val paint = Paint(Paint.ANTI_ALIAS_FLAG)

    private val rippleAnimator = RippleAnimator(0f, 300L)

    init {
        isClickable = true
        changeRippleColor(Color.GREEN)
    }

    fun changeRippleDuration(duration: Long) {
        rippleAnimator.changeDelay(duration)
    }

    fun changeRippleColor(color: Int) {
        paint.color = Color.argb(90, Color.red(color), Color.green(color), Color.blue(color))
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        rippleAnimator.changeRippleRadius(w * 2f)
        radius = w * 2f
    }

    private var listener = mutableListOf<() -> Unit>()

    fun setOnClickListener(onClick: () -> Unit) {
        listener.clear()
        listener.add(onClick)
    }

    override fun onTouchEvent(event: MotionEvent): Boolean {

        when (event.action) {
            MotionEvent.ACTION_DOWN -> {
                pointX = event.x
                pointY = event.y

                rippleAnimator.start {
                    initial = it.animatedValue as Float
                    invalidate()
                }
            }
            MotionEvent.ACTION_UP -> {
                rippleAnimator.stop {
                    initial = 0f
                    invalidate()
                }
                listener.firstOrNull()?.invoke()
            }
        }
        return true
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        canvas.clipPath(Path().apply {
            addRoundRect(RectF(0f, 0f, width.toFloat(), height.toFloat()), 100f, 100f, Path.Direction.CW)
        })
        canvas.drawCircle(pointX, pointY, initial, paint)
    }

}
