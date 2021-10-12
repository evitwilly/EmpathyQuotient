package com.freeit.empathyquotient.core

import android.content.Context
import android.graphics.Typeface

class FontManager(ctx: Context) {

    private val assetManager = ctx.assets
    private val robotoBold = Typeface.createFromAsset(assetManager, "Roboto-Bold.ttf")
    private val robotoRegular = Typeface.createFromAsset(assetManager, "Roboto-Regular.ttf")

    fun bold() : Typeface = robotoBold
    fun regular() : Typeface = robotoRegular

}