package com.freeit.empathyquotient.presentation.view

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.animation.ValueAnimator
import android.content.Context
import android.graphics.*
import android.graphics.drawable.GradientDrawable
import android.util.AttributeSet
import android.util.TypedValue
import android.view.Gravity
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.appcompat.widget.AppCompatImageView
import androidx.appcompat.widget.AppCompatTextView
import androidx.core.animation.doOnEnd
import androidx.core.animation.doOnStart
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import com.freeit.empathyquotient.R
import com.freeit.empathyquotient.core.App
import com.freeit.empathyquotient.core.extensions.robotoBold
import ru.freeit.noxml.extensions.colorRes
import ru.freeit.noxml.extensions.fontSize
import ru.freeit.noxml.extensions.text

fun Int.dp(ctx: Context) = (ctx.resources.displayMetrics.density * this).toInt()

private class ArrowScalingAnimator(
    private var delay: Long = 500L,
    private val arrowScaleStart: Int = 0,
    private val arrowScaleEnd: Int = 50,
    private val containerScaleStart: Float = 1f,
    private val containerScaleEnd: Float = 1.03f
) {

    private fun anim(container: ViewGroup, arrowIcon: View, isForward: Boolean = false) {
        val ctx = container.context

        val arrScaleStart = if (isForward) arrowScaleStart else arrowScaleEnd
        val arrScaleEnd = if (isForward) arrowScaleEnd else arrowScaleStart

        ValueAnimator.ofInt(arrScaleStart, arrScaleEnd).apply {
            duration = delay
            addUpdateListener {
                arrowIcon.layoutParams = arrowIcon.layoutParams.apply {
                    width = (it.animatedValue as Int).dp(ctx)
                    container.setPadding(40.dp(ctx) - ((it.animatedValue as Int) / 2).dp(ctx),
                        10.dp(ctx), 40.dp(ctx) - ((it.animatedValue as Int) / 2).dp(ctx), 10.dp(ctx)
                    )
                }
            }
            start()
        }

        val contScaleStart = if (isForward) containerScaleStart else containerScaleEnd
        val contScaleEnd = if (isForward) containerScaleEnd else containerScaleStart

        val animator1 = ObjectAnimator.ofFloat(container, View.SCALE_X, contScaleStart, contScaleEnd)
        val animator2 = ObjectAnimator.ofFloat(container, View.SCALE_Y, contScaleStart, contScaleEnd)
        val animSet = AnimatorSet()
        animSet.playTogether(animator1, animator2)
        animSet.duration = delay
        animSet.start()
    }

    fun forward(container: ViewGroup, arrowIcon: View) = anim(container, arrowIcon, true)
    fun reverse(container: ViewGroup, arrowIcon: View) = anim(container, arrowIcon, false)

}

class ArrowScalingButton @JvmOverloads constructor(
    ctx: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : LinearLayout(ctx, attrs, defStyleAttr) {

    private var radius: Float = 0f

    private var pointX = 0f
    private var pointY = 0f

    private val arrowScalingAnimator = ArrowScalingAnimator()


    // create dp() extension for translating dp to pixels
    private fun Int.dp() = (context.resources.displayMetrics.density * this).toInt()

    init {
        // using our extension
        setPadding(40.dp(), 10.dp(), 40.dp(), 10.dp())

        isClickable = true

        orientation = HORIZONTAL
        gravity = Gravity.CENTER_HORIZONTAL or Gravity.CENTER_VERTICAL


        background = GradientDrawable().apply {
            setColor(ContextCompat.getColor(context, R.color.primaryColor))
        }

        val buttonText = AppCompatTextView(context).apply {
            text(context.getString(R.string.start_testing).uppercase())
            colorRes(R.color.white)
            robotoBold()
            fontSize(26f)
        }
        val icon = AppCompatImageView(context)

        addView(buttonText.apply {
            layoutParams = LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT)
        })

        addView(icon.apply {
            setImageResource(R.drawable.ic_arrow_right_alt_48)
            layoutParams = LayoutParams(
                0.dp(),
                LayoutParams.WRAP_CONTENT
            ).apply {
                marginStart = 5.dp()
            }
        })
    }


    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        radius = w * 2f
    }

    private val listener = mutableListOf<() -> Unit>()

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
