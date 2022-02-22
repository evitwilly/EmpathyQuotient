package com.freeit.empathyquotient.presentation.view.other

import android.animation.ObjectAnimator
import android.content.Context
import android.view.Gravity
import android.view.View
import android.widget.TextView
import androidx.appcompat.widget.LinearLayoutCompat
import androidx.core.animation.doOnEnd
import androidx.core.view.isVisible
import com.freeit.empathyquotient.presentation.screens.intro.QuestionWithAnswer
import com.freeit.empathyquotient.R
import com.freeit.empathyquotient.core.extensions.robotoBold
import com.freeit.empathyquotient.core.extensions.robotoRegular
import com.freeit.empathyquotient.presentation.view.buttons.RippleImageButton
import ru.freeit.noxml.extensions.*

class ContentDescriptionView(ctx: Context) : LinearLayoutCompat(ctx) {

    private fun fadeText(view: TextView, txt: String) {
        ObjectAnimator.ofFloat(headerText, View.ALPHA, 1f, 0f).apply {
            duration = 260L
            doOnEnd {
                view.text = txt
                ObjectAnimator.ofFloat(headerText, View.ALPHA, 0f, 1f).apply {
                    duration = 260L
                    start()
                }
            }
            start()
        }
    }

    fun changeQuestion(question: QuestionWithAnswer) {

        if (question.isAnim()) {
            fadeText(headerText, question.q())
        } else {
            headerText.text = question.q()
        }

        contentText.text = question.a()

        backButton.isVisible = !question.isFirst()
        forwardButton.isVisible = !question.isLast()

        dotIndicatorView.selectDot(question.i(), question.isAnim())
    }

    fun onDotChanged(listener: (index: Int) -> Unit) {
        dotIndicatorView.setDotChangeListener(listener)
    }

    fun onBackClick(listener: () -> Unit) {
        backButton.onClick(listener)
    }

    fun onForwardClick(listener: () -> Unit) {
        forwardButton.onClick(listener)
    }

    private val backButton = RippleImageButton(context).apply {
        img(R.drawable.ic_arrow_right_alt_24)
        gone()
        scaleX = -1f
        layoutParams(frameLayoutParams()
            .width(dp(35)).height(dp(35))
            .gravity(Gravity.BOTTOM or Gravity.END)
            .margins(0, 0, dp(35), 0)
            .build())
    }

    private val forwardButton = RippleImageButton(context).apply {
        img(R.drawable.ic_arrow_right_alt_24)
        gone()
        layoutParams(frameLayoutParams()
            .width(dp(32)).height(dp(32))
            .gravity(Gravity.BOTTOM or Gravity.END)
            .build())
    }

    private val headerText = text {
        fontSize(34f)
        colorRes(R.color.white)
        padding(bottom = dp(8))
        bg(R.drawable.header_background)
        robotoBold()
        layoutParams(linearLayoutParams().matchWidth().wrapHeight().build())
    }

    private val contentText = text {
        fontSize(25f)
        robotoRegular()
        colorRes(R.color.white)
        layoutParams(linearLayoutParams()
            .matchWidth().wrapHeight().marginTop(dp(8))
            .weight(1f)
            .build())
    }

    private val dotIndicatorView = DotIndicatorView(context, 3).apply {
        layoutParams(linearLayoutParams().wrapWidth().wrapHeight()
            .gravity(Gravity.END)
            .build())
    }

    init {
        vertical()

        addView(frameLayout {
            addView(backButton, forwardButton)
        })

        addView(headerText, contentText, dotIndicatorView)

    }


}