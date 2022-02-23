package com.freeit.empathyquotient.presentation.view.other

import android.content.Context
import com.freeit.empathyquotient.presentation.view.layouts.AbsoluteLayout
import ru.freeit.noxml.extensions.dp

class DotIndicatorView(ctx: Context, private val dotCount: Int): AbsoluteLayout(ctx) {

    private val listener = mutableListOf<(dot: Int) -> Unit>()
    private val gap = dp(5)

    init {
        val dotSize = dp(20)
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

    fun setDotChangeListener(listener: (dot: Int) -> Unit) {
        this.listener.clear()
        this.listener.add(listener)
    }



}
