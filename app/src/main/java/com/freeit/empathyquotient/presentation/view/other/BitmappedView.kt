package com.freeit.empathyquotient.presentation.view.other

import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Color
import android.view.View

class BitmappedView(private val view: View) {
    fun bitmap(width: Int = view.measuredWidth, height: Int = view.measuredHeight) : Bitmap {
        val bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888)
        val canvas = Canvas(bitmap)
        val drawable = view.background
        if (drawable != null) {
            drawable.draw(canvas)
        } else {
            canvas.drawColor(Color.WHITE)
        }
        view.draw(canvas)
        return bitmap
    }

    fun fourBitmaps(width: Int = view.measuredWidth, height: Int = view.measuredHeight) : List<Bitmap> {

        val bitmap = bitmap(width, height)

        val bits = mutableListOf<Bitmap>()

        val width = bitmap.width / 2
        val height = bitmap.height / 2

        for (x in 0 until 2) {
            for (y in 0 until 2) {
                bits.add(Bitmap.createBitmap(bitmap, x * width, y * height, width, height))
            }
        }

        return bits
    }

}