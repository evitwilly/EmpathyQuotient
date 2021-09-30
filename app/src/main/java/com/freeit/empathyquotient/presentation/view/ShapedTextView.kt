package com.freeit.empathyquotient.presentation.view

import android.animation.ObjectAnimator
import android.animation.ValueAnimator
import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.MotionEvent
import androidx.appcompat.widget.AppCompatTextView
import androidx.core.animation.doOnEnd
import androidx.core.content.ContextCompat
import com.freeit.empathyquotient.R
import kotlin.random.Random

class ShapedTextView @JvmOverloads constructor(
    ctx: Context,
    attributeSet: AttributeSet? = null,
    defStyleAttr: Int = 0
) : AppCompatTextView(ctx, attributeSet, defStyleAttr) {

    private var inset = 20f

    override fun onTouchEvent(event: MotionEvent): Boolean {
        return when (event.action) {
            MotionEvent.ACTION_UP -> {
                val anim = ValueAnimator.ofFloat(inset, 20f)
                anim.addUpdateListener {
                    inset = it.animatedValue as Float
                    setPadding(
                        paddingLeft, paddingTop - inset.toInt() / 20, paddingRight - inset.toInt() / 20, paddingBottom
                    )
                    invalidate()
                }
                anim.duration = 500L
                anim.start()
                true
            }
            MotionEvent.ACTION_DOWN -> {
                val anim = ValueAnimator.ofFloat(inset, 30f)
                anim.addUpdateListener {
                    inset = it.animatedValue as Float
                    setPadding(
                        paddingLeft, paddingTop + inset.toInt() / 20, paddingRight + inset.toInt() / 20, paddingBottom
                    )
                    invalidate()
                }
                anim.duration = 500L
                anim.start()
                true
            }
            else -> super.onTouchEvent(event)
        }
    }

    private val circlePaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        color = Color.rgb(Random.nextInt(255), Random.nextInt(255), Random.nextInt(255))
    }

    private val bgPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        color = ContextCompat.getColor(context, R.color.primaryColor)
    }

    private val shadowPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        color = ContextCompat.getColor(context, R.color.primaryLightColor)
    }

    override fun onDraw(canvas: Canvas) {
        val cutDistance = height / 6f
//        canvas.clipPath(Path().apply {
//            moveTo(0f, cutDistance)
//            rLineTo(cutDistance, -cutDistance)
//            rLineTo(width.toFloat() - cutDistance, 0f)
//            rLineTo(0f, height.toFloat())
//            rLineTo(-(width.toFloat() + cutDistance), 0f)
//            close()
//        })
        val shadowRect = RectF(0f, 0f, width.toFloat(), height.toFloat() - 1f)
        canvas.drawRoundRect(shadowRect, inset + 10f,  inset + 10f, shadowPaint)
        val backgroundRect = RectF(0f, inset / 1.5f, width.toFloat() - inset, height.toFloat())
        canvas.drawRoundRect(backgroundRect, inset, inset, bgPaint)

        for (i in 0..50) {
            canvas.drawCircle(Random.nextFloat() * width - inset,
                Random.nextFloat() * height + inset,
                Random.nextInt(4, 10).toFloat(), circlePaint.apply {
                    color = Color.rgb(Random.nextInt(255), Random.nextInt(255), Random.nextInt(255))
                }
            )
        }

        super.onDraw(canvas)
    }
}