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
import com.freeit.empathyquotient.databinding.ScoreScreenBinding
import com.freeit.empathyquotient.presentation.navigator.ScreenArg
import com.freeit.empathyquotient.presentation.navigator.TestStack
import com.freeit.empathyquotient.presentation.screens.Prefix
import com.freeit.empathyquotient.presentation.screens.ScreenEntry
import com.freeit.empathyquotient.presentation.screens.intro.IntroScreen
import com.freeit.empathyquotient.presentation.screens.intro.ScreenVitals
import com.freeit.empathyquotient.presentation.screens.test.ScoreData
import com.freeit.empathyquotient.presentation.screens.test.TestScreen
import com.freeit.empathyquotient.presentation.view.JustButton
import com.freeit.empathyquotient.presentation.view.dp

class ScoreScreen(screenVitals: ScreenVitals, screenArg: ScreenArg, id: Int) : ScreenEntry.Abstract(screenVitals, screenArg, id) {

    override fun prefix() = Prefix.score()


    override fun view() : View {
        val binding = ScoreScreenBinding.inflate(screenVitals.inflater())

        val funny = FunnyBouncesView(binding.root.context).apply {
            layoutParams = FrameLayout.LayoutParams(
                FrameLayout.LayoutParams.MATCH_PARENT,
                FrameLayout.LayoutParams.MATCH_PARENT
            )
        }
        binding.root.addView(funny)
        funny.start {}

        val verticalLayout = LinearLayout(binding.root.context).apply {
            gravity = Gravity.CENTER_HORIZONTAL
            orientation = LinearLayout.VERTICAL
            layoutParams = FrameLayout.LayoutParams(
                FrameLayout.LayoutParams.MATCH_PARENT,
                FrameLayout.LayoutParams.WRAP_CONTENT
            ).apply {
                gravity = Gravity.TOP or Gravity.CENTER_HORIZONTAL
                topMargin = 24.dp(context)
                marginStart = 16.dp(context)
                marginEnd = 16.dp(context)
            }
        }

        val score = ScoreData((binding.root.context.applicationContext as App).localPrefsDataSource).score()

        val scoreText = AppCompatTextView(binding.root.context)
        scoreText.text = scoreText.context.getString(R.string.your_score, score)
        scoreText.setTextColor(Color.WHITE)
        scoreText.typeface = (scoreText.context.applicationContext as App).fontManager.bold()
        scoreText.setTextSize(TypedValue.COMPLEX_UNIT_SP, 53f)
        scoreText.textAlignment = AppCompatTextView.TEXT_ALIGNMENT_CENTER
        verticalLayout.addView(scoreText.apply {
            layoutParams = LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT)
        })

        val homeButton = JustButton(binding.root.context).apply {
            layoutParams = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
            ).apply {
                topMargin = 8.dp(context)
            }
        }
        homeButton.changeText(homeButton.context.getString(R.string.return_to_first_screen))
        homeButton.changeIcon(R.drawable.ic_return_24)

        homeButton.measure(0, 0)

        val startNewTestButton = JustButton(binding.root.context).apply {
            layoutParams = LinearLayout.LayoutParams(
                homeButton.measuredWidth,
                LinearLayout.LayoutParams.WRAP_CONTENT
            ).apply {
                topMargin = 24.dp(context)
            }
        }
        startNewTestButton.changeText(startNewTestButton.context.getString(R.string.start_new_test))
        startNewTestButton.changeIcon(R.drawable.ic_edit_note_24)

        verticalLayout.addView(startNewTestButton)
        verticalLayout.addView(homeButton)

        val navigator = screenVitals.navigator()
        val testStack = TestStack.Base((startNewTestButton.context.applicationContext as App).localPrefsDataSource)

        startNewTestButton.setOnClickListener {
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
        homeButton.setOnClickListener {
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

        ObjectAnimator.ofFloat(verticalLayout, View.ALPHA, 0f, 1f)
            .setDuration(3000L)
            .start()

        verticalLayout.addView(JustButton(verticalLayout.context).apply {
            layoutParams = LinearLayout.LayoutParams(
                homeButton.measuredWidth,
                LinearLayout.LayoutParams.WRAP_CONTENT
            ).apply {
                topMargin = 8.dp(context)
            }
            changeText(context.getString(R.string.share_your_result))
            changeIcon(R.drawable.ic_share_24)
            setOnClickListener {
                val sendIntent = Intent().apply {
                    action = Intent.ACTION_SEND
                    putExtra(Intent.EXTRA_TEXT, context.getString(R.string.your_empathy_quotient, score))
                    type = "text/plain"
                }
                context.startActivity(sendIntent)
            }
        })

        binding.root.addView(verticalLayout)


        return binding.root
    }

    override fun isStartDestination() = false
}