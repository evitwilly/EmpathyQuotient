package com.freeit.empathyquotient.presentation.screens.score

import android.graphics.Canvas
import android.graphics.Paint

class RandomBounce(
    private val x: Float,
    private val y: Float,
    private val maxRadius: Float,
    private val color: Int
) {
    private var radius = 0f

    fun incrementBy(value: Float) {
        radius += value * maxRadius
    }

    fun zero() {
        radius = 0f
    }

    fun draw(canvas: Canvas, paint: Paint) {
        paint.color = color
        canvas.drawCircle(x, y, radius, paint)
    }
}