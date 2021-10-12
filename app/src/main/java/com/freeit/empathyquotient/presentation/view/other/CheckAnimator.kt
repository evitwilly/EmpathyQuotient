package com.freeit.empathyquotient.presentation.view.other

import android.animation.AnimatorSet
import android.animation.ValueAnimator
import androidx.core.animation.doOnEnd

class CheckAnimator(
    private val width: Float,
    private val height: Float
) {
    fun uncheck(onFirstXListener: ValueAnimator.AnimatorUpdateListener,
                onFirstYListener: ValueAnimator.AnimatorUpdateListener,
                onLastXListener: ValueAnimator.AnimatorUpdateListener,
                onLastYListener: ValueAnimator.AnimatorUpdateListener
    ) {

        AnimatorSet().apply {
            val lastXAnim = ValueAnimator.ofFloat(width - width / 6f, width / 2.5f).apply {
                addUpdateListener(onLastXListener)
            }
            val lastYAnim = ValueAnimator.ofFloat(height / 3f, height - height / 3f).apply {
                addUpdateListener(onLastYListener)
            }
            duration = 250L
            playTogether(lastXAnim, lastYAnim)
            doOnEnd {
                AnimatorSet().apply {
                    val firstXAnim = ValueAnimator.ofFloat(width / 2.5f, width / 5.6f).apply {
                        addUpdateListener(onFirstXListener)
                        addUpdateListener(onLastXListener)
                    }
                    val firstYAnim = ValueAnimator.ofFloat(height - height / 3f, height / 2.2f).apply {
                        addUpdateListener(onFirstYListener)
                        addUpdateListener(onLastYListener)
                    }
                    playTogether(firstXAnim, firstYAnim)
                    duration = 250L
                    start()
                }
            }

            start()
        }
    }

    fun check(onFirstXListener: ValueAnimator.AnimatorUpdateListener,
              onFirstYListener: ValueAnimator.AnimatorUpdateListener,
              onLastXListener: ValueAnimator.AnimatorUpdateListener,
              onLastYListener: ValueAnimator.AnimatorUpdateListener
    ) {

        AnimatorSet().apply {
            val firstXAnim = ValueAnimator.ofFloat(width / 5.6f, width / 2.5f).apply {
                addUpdateListener(onFirstXListener)
            }
            val firstYAnim = ValueAnimator.ofFloat(height / 2.2f, height - height / 3f).apply {
                addUpdateListener(onFirstYListener)
            }
            duration = 250L
            playTogether(firstXAnim, firstYAnim)

            doOnEnd {
                AnimatorSet().apply {
                    val lastXAnim = ValueAnimator.ofFloat(width / 2.5f, width - width / 6f).apply {
                        addUpdateListener(onLastXListener)
                    }
                    val lastYAnim = ValueAnimator.ofFloat(height - height / 3f, height / 3f).apply {
                        addUpdateListener(onLastYListener)
                    }
                    playTogether(lastXAnim, lastYAnim)
                    doOnEnd {}
                    duration = 250L
                    start()
                }
            }

            start()
        }
    }

}