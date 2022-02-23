package com.freeit.empathyquotient.presentation.view.buttons

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.animation.ValueAnimator
import android.view.View
import android.view.ViewGroup
import ru.freeit.noxml.extensions.dp

class ArrowScalingAnimator(
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
                    width = ctx.dp((it.animatedValue as Int))
                    container.setPadding(ctx.dp(40) - ctx.dp((it.animatedValue as Int) / 2),
                        ctx.dp(10),
                        ctx.dp(40) - ctx.dp((it.animatedValue as Int) / 2),
                        ctx.dp(10)
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