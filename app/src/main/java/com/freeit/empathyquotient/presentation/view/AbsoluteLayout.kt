package com.freeit.empathyquotient.presentation.view

import android.content.Context
import android.util.AttributeSet
import android.view.View.MeasureSpec
import android.view.ViewGroup
import kotlin.math.max

open class AbsoluteLayout @JvmOverloads constructor(
    ctx: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : ViewGroup(ctx, attrs, defStyleAttr) {


    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        val count = childCount
        var maxHeight = 0
        var maxWidth = 0

        measureChildren(widthMeasureSpec, heightMeasureSpec)

        for (i in 0 until count) {
            val child = getChildAt(i)
            if (child.visibility != GONE) {
                var childRight: Int
                var childBottom: Int
                val lp = child.layoutParams as LayoutParams
                childRight = lp.x + child.measuredWidth
                childBottom = lp.y + child.measuredHeight
                maxWidth = max(maxWidth, childRight)
                maxHeight = max(maxHeight, childBottom)
            }
        }

        // Account for padding too
        maxWidth += paddingLeft + paddingRight
        maxHeight += paddingTop + paddingBottom

        // Check against minimum height and width
        maxHeight = max(maxHeight, suggestedMinimumHeight)
        maxWidth = max(maxWidth, suggestedMinimumWidth)
        setMeasuredDimension(
            resolveSizeAndState(maxWidth, widthMeasureSpec, 0),
            resolveSizeAndState(maxHeight, heightMeasureSpec, 0)
        )
    }

    override fun generateDefaultLayoutParams(): ViewGroup.LayoutParams? {
        return LayoutParams(
            ViewGroup.LayoutParams.WRAP_CONTENT,
            ViewGroup.LayoutParams.WRAP_CONTENT,
            0,
            0
        )
    }

    override fun onLayout(
        changed: Boolean, l: Int, t: Int,
        r: Int, b: Int
    ) {
        val count = childCount
        for (i in 0 until count) {
            val child = getChildAt(i)
            if (child.visibility != GONE) {
                val lp = child.layoutParams as LayoutParams
                val childLeft: Int = paddingLeft + lp.x
                val childTop: Int = paddingTop + lp.y
                child.layout(
                    childLeft, childTop,
                    childLeft + child.measuredWidth,
                    childTop + child.measuredHeight
                )
            }
        }
    }


    override fun generateLayoutParams(attrs: AttributeSet?): ViewGroup.LayoutParams {
        return LayoutParams(context, attrs)
    }
    override fun checkLayoutParams(p: ViewGroup.LayoutParams?): Boolean {
        return p is LayoutParams
    }

    override fun generateLayoutParams(p: ViewGroup.LayoutParams?): ViewGroup.LayoutParams? {
        return LayoutParams(p)
    }

    override fun shouldDelayChildPressedState(): Boolean {
        return false
    }

    class LayoutParams : ViewGroup.LayoutParams {
        var x = 0
        var y = 0

        constructor(width: Int, height: Int, x: Int, y: Int) : super(width, height) {
            this.x = x
            this.y = y
        }

        constructor(c: Context, attrs: AttributeSet?) : super(c, attrs) {}


        constructor(source: ViewGroup.LayoutParams?) : super(source)

        fun debug(output: String): String {
            return ""
        }
    }
}