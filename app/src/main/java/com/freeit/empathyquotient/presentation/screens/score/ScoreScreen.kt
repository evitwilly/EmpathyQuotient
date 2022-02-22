package com.freeit.empathyquotient.presentation.screens.score

import android.animation.ObjectAnimator
import android.content.Intent
import android.graphics.Color
import android.util.TypedValue
import android.view.Gravity
import android.view.View
import android.widget.FrameLayout
import android.widget.LinearLayout
import androidx.appcompat.widget.AppCompatTextView
import com.freeit.empathyquotient.R
import com.freeit.empathyquotient.core.App
import com.freeit.empathyquotient.core.navigator.ScreenArg
import com.freeit.empathyquotient.core.navigator.TestStack
import com.freeit.empathyquotient.presentation.screens.Prefix
import com.freeit.empathyquotient.presentation.screens.ScreenEntry
import com.freeit.empathyquotient.presentation.screens.intro.IntroScreen
import com.freeit.empathyquotient.core.navigator.ScreenVitals
import com.freeit.empathyquotient.presentation.screens.test.ScoreData
import com.freeit.empathyquotient.presentation.screens.test.TestScreen
import com.freeit.empathyquotient.presentation.view.buttons.JustButton
import com.freeit.empathyquotient.presentation.view.dp
import ru.freeit.noxml.extensions.*

class ScoreScreen(screenVitals: ScreenVitals, screenArg: ScreenArg, id: Int) : ScreenEntry.Abstract(screenVitals, screenArg, id) {

    override fun prefix() = Prefix.score()

    override fun view() : View {
        val ctx = screenVitals.inflater().context

        val frameLayoutContainer = frameLayout(ctx) {
            layoutParams(frameLayoutParams().matchWidth().matchHeight().build())
        }

        frameLayoutContainer.addView(FunnyBouncesView(ctx).apply {
            layoutParams(frameLayoutParams().matchWidth().matchHeight().build())
            start {}
        })

        val verticalLayout = linearLayout(ctx) {
            vertical()
            centerHorizontal()
            layoutParams(frameLayoutParams().matchWidth()
                .gravity(Gravity.TOP or Gravity.CENTER_HORIZONTAL)
                .marginTop(dp(24)).marginStart(dp(16)).marginEnd(dp(16))
                .wrapHeight().build())
        }

        val score = ScoreData((ctx.applicationContext as App).localPrefsDataSource).score()
        verticalLayout.addView(text(ctx) {
            text(ctx.getString(R.string.your_score, score))
            colorRes(R.color.white)
            typeface((ctx.applicationContext as App).fontManager.bold())
            fontSize(53f)
            textCenter()
            layoutParams(linearLayoutParams().build())
        })

        val homeButton = JustButton(ctx).apply {
            layoutParams(linearLayoutParams().wrapWidth()
                .marginTop(dp(8)).wrapHeight().build())
            changeText(context.getString(R.string.return_to_first_screen))
            changeIcon(R.drawable.ic_return_24)
            measure(0, 0)
        }

        val startNewTestButton = JustButton(ctx).apply {
            layoutParams(linearLayoutParams().width(homeButton.measuredWidth).wrapHeight()
                .marginTop(dp(24))
                .build())
            changeText(context.getString(R.string.start_new_test))
            changeIcon(R.drawable.ic_edit_note_24)
        }

        verticalLayout.addView(startNewTestButton, homeButton)

        val navigator = screenVitals.navigator()
        val testStack = TestStack.Base((ctx.applicationContext as App).localPrefsDataSource)

        startNewTestButton.click {
            testStack.clear()
            navigator.navigate(
                { screenVitals, arg, id -> TestScreen(screenVitals, arg, id) },
                ScreenArg.Test(0),
                200,
            ) { parent, newRoot, oldRoot ->
                parent.addView(newRoot)
                parent.removeView(oldRoot)
            }
        }
        homeButton.click {
            testStack.clear()
            navigator.navigate(
                { screenVitals, arg, id -> IntroScreen(screenVitals, arg, id) },
                ScreenArg.Test(0),
                100,
            ) { parent, newRoot, oldRoot ->
                parent.addView(newRoot)
                parent.removeView(oldRoot)
            }
        }

        ObjectAnimator.ofFloat(verticalLayout, View.ALPHA, 0f, 1f).setDuration(3000L).start()

        verticalLayout.addView(JustButton(verticalLayout.context).apply {
            layoutParams(linearLayoutParams()
                .width(homeButton.measuredWidth)
                .wrapHeight().marginTop(dp(8))
                .build())
            changeText(context.getString(R.string.share_your_result))
            changeIcon(R.drawable.ic_share_24)
            click {
                val sendIntent = Intent().apply {
                    action = Intent.ACTION_SEND
                    putExtra(Intent.EXTRA_TEXT, context.getString(R.string.your_empathy_quotient, score))
                    type = "text/plain"
                }
                context.startActivity(sendIntent)
            }
        })

        frameLayoutContainer.addView(verticalLayout)
        return frameLayoutContainer
    }

    override fun isStartDestination() = false
}