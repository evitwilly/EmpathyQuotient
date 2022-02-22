package com.freeit.empathyquotient.core.extensions

import android.graphics.Typeface
import android.widget.TextView
import ru.freeit.noxml.extensions.typeface

fun TextView.robotoBold() {
    typeface("Roboto-Bold.ttf")
}

fun TextView.robotoRegular() {
    typeface("Roboto-Regular.ttf")
}