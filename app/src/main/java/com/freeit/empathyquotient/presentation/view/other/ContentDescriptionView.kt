package com.freeit.empathyquotient.presentation.view.other

import android.animation.ObjectAnimator
import android.content.Context
import android.graphics.Color
import android.util.AttributeSet
import android.util.TypedValue
import android.view.Gravity
import android.view.View
import android.widget.FrameLayout
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.widget.AppCompatTextView
import androidx.core.animation.doOnEnd
import androidx.core.view.isVisible
import com.freeit.empathyquotient.presentation.screens.intro.QuestionWithAnswer
import com.freeit.empathyquotient.R
import com.freeit.empathyquotient.core.App
import com.freeit.empathyquotient.presentation.view.buttons.RippleImageButton

class ContentDescriptionView @JvmOverloads constructor(
    ctx: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : LinearLayout(ctx, attrs, defStyleAttr) {

    private fun Int.dp() = (this * context.resources.displayMetrics.density).toInt()

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

    fun setDotChangeListener(listener: (index: Int) -> Unit) {
        dotIndicatorView.setDotChangeListener(listener)
    }

    fun setBackClickListener(listener: () -> Unit) {
        backButton.setOnClickListener(listener)
    }

    fun setForwardClickListener(listener: () -> Unit) {
        forwardButton.setOnClickListener(listener)
    }

    private var backButton : RippleImageButton
    private var forwardButton: RippleImageButton
    private var headerText: AppCompatTextView
    private var contentText: AppCompatTextView
    private var dotIndicatorView: DotIndicatorView

    init {
        orientation = VERTICAL

        val fontManager = (context.applicationContext as App).fontManager

        backButton = arrowButton(marginEnd = 35.dp())
        forwardButton = arrowButton(isForward = true, marginEnd = 0.dp())

        val arrowButtons = FrameLayout(context)
        arrowButtons.addView(backButton)
        arrowButtons.addView(forwardButton)

        addView(arrowButtons)

        headerText = AppCompatTextView(ctx)
        headerText.setTextSize(TypedValue.COMPLEX_UNIT_SP, 34f)
        headerText.setTextColor(Color.WHITE)
        headerText.setPadding(0, 0, 0, 7.dp())
        headerText.setBackgroundResource(R.drawable.header_background)
        headerText.typeface = fontManager.bold()

        addView(headerText.apply {
            layoutParams = LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT)
        })

        contentText = AppCompatTextView(ctx)
        contentText.setTextSize(TypedValue.COMPLEX_UNIT_SP, 25f)
        contentText.typeface = fontManager.regular()
        contentText.setTextColor(Color.WHITE)

        addView(contentText.apply {
            layoutParams = LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT).apply {
                setMargins(0, 10.dp(), 0, 0)
            }
        })

        dotIndicatorView = DotIndicatorView(context, 3)

        addView(dotIndicatorView.apply {
            layoutParams = LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT).apply {
                gravity = Gravity.END
            }
        })

    }

    private fun arrowButton(isForward: Boolean = false, marginEnd: Int) : RippleImageButton {
        val arrowButton = RippleImageButton(context)
        arrowButton.setImageResource(R.drawable.ic_arrow_right_alt_24)
        arrowButton.isVisible = false
        arrowButton.scaleX = if (isForward) 1f else -1f
        arrowButton.layoutParams = FrameLayout.LayoutParams(35.dp(), 35.dp()).apply {
                gravity = Gravity.BOTTOM or Gravity.END
            }.apply {
                setMargins(0.dp(), 0.dp(), marginEnd, 0.dp())
            }
        return arrowButton
    }

}