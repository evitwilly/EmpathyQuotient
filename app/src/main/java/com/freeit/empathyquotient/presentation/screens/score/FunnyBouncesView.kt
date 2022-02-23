package com.freeit.empathyquotient.presentation.screens.score

import android.animation.Animator
import android.animation.ValueAnimator
import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.view.View
import androidx.core.animation.doOnEnd
import androidx.core.content.ContextCompat
import com.freeit.empathyquotient.R
import ru.freeit.noxml.extensions.colorBy
import kotlin.random.Random

class FunnyBouncesView(ctx: Context): View(ctx) {

    private val randomCoordinates = mutableListOf<RandomBounce>()

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        for (i in 0 until 33) {
            val primaryColor = context.colorBy(R.color.primaryColor)
            val randomColor = Color.argb(
                Random.nextInt(100, 255),
                Color.red(primaryColor),
                Color.green(primaryColor),
                Color.blue(primaryColor)
            )
            val x = Random.nextFloat() * w
            val y = Random.nextFloat() * h
            val maxRadius = Random.nextFloat() * 100f + 10f
            randomCoordinates.add(RandomBounce(x, y, maxRadius, randomColor))
        }
    }

    fun start(onEnd: (anim: Animator) -> Unit) {
        ValueAnimator.ofFloat(0f, 1f).apply {
            duration = 3000L
            addUpdateListener {  anim ->
                randomCoordinates.forEach { it.incrementBy(anim.animatedValue as Float) }
                invalidate()
            }
            doOnEnd(onEnd)
            start()
        }
    }

    private val paint = Paint(Paint.ANTI_ALIAS_FLAG)

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        for (coordinate in randomCoordinates) {
            coordinate.draw(canvas, paint)
        }
    }

}