package com.freeit.empathyquotient.presentation.view.other

import android.content.Context
import android.view.Gravity
import androidx.appcompat.widget.AppCompatImageView
import androidx.appcompat.widget.LinearLayoutCompat
import androidx.core.view.isVisible
import com.freeit.empathyquotient.presentation.screens.intro.QuestionWithAnswer
import com.freeit.empathyquotient.R
import com.freeit.empathyquotient.core.extensions.robotoBold
import com.freeit.empathyquotient.core.extensions.robotoRegular
import ru.freeit.noxml.extensions.*

class ContentDescriptionView(ctx: Context) : LinearLayoutCompat(ctx) {

    fun changeQuestion(question: QuestionWithAnswer) {

        headerText.animate()
            .withEndAction {
                headerText.text(question.questionString(resources))
                headerText.animate().alpha(1f).setDuration(260L).start()
            }
            .alpha(0f).setDuration(260L).start()

//        if (question.isAnim()) {
//            headerText.animate()
//                .withEndAction {
//                    headerText.text(question.questionString())
//                    headerText.animate().alpha(1f).setDuration(260L).start()
//                }
//                .alpha(0f).setDuration(260L).start()
//        } else {
//            headerText.text = question.questionString()
//        }

        contentText.text = question.answerString(resources)

        question.backButtonVisible(backButton)
        question.forwardButtonVisible(forwardButton)

//        backButton.isVisible = !question.isFirst()
//        forwardButton.isVisible = !question.isLast()

        dotIndicatorView.selectDot(question.index(), true)
    }

    fun onDotChanged(listener: (index: Int) -> Unit) {
        dotIndicatorView.setDotChangeListener(listener)
    }

    fun onBackClick(listener: () -> Unit) {
        backButton.click(listener)
    }

    fun onForwardClick(listener: () -> Unit) {
        forwardButton.click(listener)
    }

    private val backButton = AppCompatImageView(context).apply {
        clickable()
        ripple(R.color.yellow_500, dp(32))
        img(R.drawable.ic_forward)
        scaleX = -1f
        gone()
        layoutParams(frameLayoutParams()
            .width(dp(35)).height(dp(32)).marginEnd(dp(40))
            .gravity(Gravity.BOTTOM or Gravity.END)
            .build())
    }

    private val forwardButton = AppCompatImageView(context).apply {
        clickable()
        ripple(R.color.yellow_500, dp(32))
        img(R.drawable.ic_forward)
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