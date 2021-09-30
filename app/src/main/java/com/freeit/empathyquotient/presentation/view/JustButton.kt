package com.freeit.empathyquotient.presentation.view

import android.animation.ValueAnimator
import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.RectF
import android.util.AttributeSet
import android.util.TypedValue
import android.view.Gravity
import android.view.MotionEvent
import android.widget.LinearLayout
import androidx.appcompat.widget.AppCompatImageView
import androidx.appcompat.widget.AppCompatTextView
import androidx.core.animation.doOnEnd
import androidx.core.content.ContextCompat
import com.freeit.empathyquotient.R
import com.freeit.empathyquotient.core.App
import kotlin.math.roundToInt

class JustButton @JvmOverloads constructor(
    ctx: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : LinearLayout(ctx, attrs, defStyleAttr) {

    private val borderPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        color = ContextCompat.getColor(context, R.color.primaryDarkColor)
        strokeWidth = 8f
        style = Paint.Style.STROKE
    }

    private val bgPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        color = ContextCompat.getColor(context, R.color.primaryDarkColor)
        style = Paint.Style.FILL
    }

    fun changeText(txt: String) {
        text.text = txt
    }

    fun changeIcon(resourceId: Int) {
        icon.setImageResource(resourceId)
    }

    private val listener = mutableListOf<() -> Unit>()

    private var text: AppCompatTextView
    private var icon: AppCompatImageView

    fun setOnClickListener(listener: () -> Unit) {
        this.listener.clear()
        this.listener.add(listener)
    }

    init {
        orientation = HORIZONTAL
        setPadding(
            (context.resources.displayMetrics.density * 24).roundToInt(),
            (context.resources.displayMetrics.density * 16).roundToInt(),
            (context.resources.displayMetrics.density * 24).roundToInt(),
            (context.resources.displayMetrics.density * 16).roundToInt()
        )
        text = AppCompatTextView(context).apply {
            layoutParams = LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT).apply {
                marginEnd = (context.resources.displayMetrics.density * 16).roundToInt()
            }
            setTextSize(TypedValue.COMPLEX_UNIT_SP, 17f)
            isAllCaps = true
        }
        text.typeface = (context.applicationContext as App).fontManager.bold()
        gravity = Gravity.CENTER

        text.setTextColor(Color.WHITE)

        icon = AppCompatImageView(context).apply {
            layoutParams = LayoutParams(
                (context.resources.displayMetrics.density * 32).roundToInt(),
                (context.resources.displayMetrics.density * 32).roundToInt()
            )
        }

        addView(text)
        addView(icon)
    }

    private var animWidth = 0f

    override fun onTouchEvent(event: MotionEvent): Boolean {
        return when (event.action) {
            MotionEvent.ACTION_DOWN -> {
                val animator = ValueAnimator.ofFloat(0f, width.toFloat())
                animator.addUpdateListener {
                    animWidth = it.animatedValue as Float
                    invalidate()
                }
                animator.duration = 400L
                animator.doOnEnd {
                    animWidth = 0f
                    invalidate()
                }
                animator.start()
                true
            }
            MotionEvent.ACTION_UP -> {
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

//    override fun onDraw(canvas: Canvas) {
//
//        super.onDraw(canvas)
//    }
}