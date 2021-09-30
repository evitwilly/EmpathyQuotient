package com.freeit.empathyquotient.presentation.screens.test

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.content.Context
import android.view.View
import android.view.ViewGroup
import android.view.ViewTreeObserver.OnGlobalLayoutListener
import android.widget.LinearLayout
import android.widget.Toast
import androidx.core.animation.doOnEnd
import androidx.core.view.isVisible
import com.freeit.empathyquotient.ActivityLifecycle
import com.freeit.empathyquotient.R
import com.freeit.empathyquotient.core.App
import com.freeit.empathyquotient.core.LocalPrefsDataSource
import com.freeit.empathyquotient.data.QuestionDataSource
import com.freeit.empathyquotient.databinding.TestScreenBinding
import com.freeit.empathyquotient.presentation.navigator.ScreenArg
import com.freeit.empathyquotient.presentation.screens.Prefix
import com.freeit.empathyquotient.presentation.screens.ScreenEntry
import com.freeit.empathyquotient.presentation.screens.intro.ScreenVitals
import com.freeit.empathyquotient.presentation.screens.score.ScoreScreen
import com.freeit.empathyquotient.presentation.utils.RandomBlurredImage
import com.freeit.empathyquotient.presentation.view.AbsoluteLayout
import com.freeit.empathyquotient.presentation.view.AnswerView
import com.freeit.empathyquotient.presentation.view.dp

class ScoreData(private val appPrefs: LocalPrefsDataSource) {
    companion object {
        private const val scoreKey = "score_key"
        private val defaultValue = List(60) { 0 }.joinToString(",")
    }

    fun select(questionId: Int, score: Int) {
        val lastValue = appPrefs.str(scoreKey, defaultValue)
        val scores = lastValue.split(",").map { it.toInt() }.toMutableList()
        scores[questionId] = score
        appPrefs.save(scoreKey, scores.joinToString(","))
    }

    fun score() : String {
        val lastValue = appPrefs.str(scoreKey, defaultValue)
        return lastValue.split(",").map { it.toInt() }.sum().toString()
    }
}

class TestScreen(
    screenVitals: ScreenVitals,
    screenArg: ScreenArg,
    id: Int
) : ScreenEntry.Abstract(screenVitals, screenArg, id) {

    override fun prefix() = Prefix.test()
    override fun isStartDestination() = false

    private val questionId = (screenArg as ScreenArg.Test).qId()
    private var answerId: Int = -1

    override fun saveState(appPrefs: LocalPrefsDataSource) {
        State.Test(answerId).save(id, appPrefs)
        if (answerId != -1) {
            ScoreData(appPrefs).select(questionId, questionDataSource.questById(questionId).rawAnswers()[answerId].sc())
        }
    }

    override fun clearState(appPrefs: LocalPrefsDataSource) {
        State.Test(-1).save(id, appPrefs)
    }

    override fun restoreState(appPrefs: LocalPrefsDataSource) {
        answerId = State.Test.fromId(id, appPrefs).aId()
    }

    override fun pop(newScreen: View?) {
            newScreen?.let { screen ->
                (screen.parent as? ViewGroup)?.let { parent ->
                    parent.addView(root, 0)
                    screen.measure(0, 0)
                    val anim1 = ObjectAnimator.ofFloat(screen, View.TRANSLATION_Y,  0f, screen.measuredWidth.toFloat())
                    val anim2 = ObjectAnimator.ofFloat(screen, View.TRANSLATION_X, 0f, screen.measuredHeight.toFloat())
                    AnimatorSet().apply {
                        duration = 600L
                        playTogether(anim1, anim2)
                        start()
                        doOnEnd {
                            parent.removeView(screen)
                        }
                    }
                }


            }

    }

    private fun answers(
        answers: List<AnswerView>,
        parent: ViewGroup,
        layoutParams: (index: Int, context: Context) -> ViewGroup.LayoutParams
    ) {
        val isLandscape = parent is AbsoluteLayout
        for ((index, answer) in answers.withIndex()) {
            parent.addView(answer.apply {
                this.layoutParams = layoutParams(index, answer.context)
                setOnClickListener {
                    answerId = index
                    answers.filter { answer != it }.forEach { item -> item.setChecked(false) }
                }

            }, if (isLandscape) index else 1 + index)
        }
    }

    private lateinit var questionDataSource: QuestionDataSource

    override fun view() : View {
        val layoutInflater = screenVitals.inflater()

        val binding = TestScreenBinding.inflate(layoutInflater)

        binding.bgImg.setImageBitmap(RandomBlurredImage(layoutInflater.context).bitmap())

        questionDataSource = (binding.root.context.applicationContext as App).questionDataSource

        val quest = questionDataSource.questById(questionId)
        val answers = quest.answers(layoutInflater.context, answerId)
        val fontManager = (binding.root.context.applicationContext as App).fontManager

        binding.questionText.text = quest.str(layoutInflater.context)
        binding.questionText.typeface = fontManager.regular()

        PortraitCheck(layoutInflater.context).run(
            onPortrait = { answers(answers, binding.questionAnswerBox) { _, _ ->
                    LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT).apply {
                        setMargins(0, 0, 0, 10.dp(binding.root.context))
                    }
                }
            },
            onLandscape = {
                val layout = AbsoluteLayout(binding.root.context).apply {
                    layoutParams = LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.MATCH_PARENT,
                        LinearLayout.LayoutParams.WRAP_CONTENT
                    )
                }
                layout.viewTreeObserver.addOnGlobalLayoutListener(object: OnGlobalLayoutListener {
                    override fun onGlobalLayout() {
                        layout.viewTreeObserver.removeOnGlobalLayoutListener(this)
                        answers(answers, layout) { i, context ->
                            AbsoluteLayout.LayoutParams(
                                layout.width / 2 - 5.dp(context), 75.dp(context),
                                i % 2 * (layout.width / 2 + 5.dp(context)), i / 2 * 85.dp(context)
                            )
                        }
                    }

                })
                binding.questionAnswerBox.addView(layout, 1)
            }
        )
        val lifecycleEvents = (binding.root.context.applicationContext as App).lifecycleEvents
        val eventObserver = object: ActivityLifecycle {
            override fun onStop() {}
            override fun onPause() {
                saveState((binding.root.context.applicationContext as App).localPrefsDataSource)
            }
            override fun onStart() {}
            override fun onResume() {}
        }
        lifecycleEvents.insertEventObserver(eventObserver)

        binding.root.addOnAttachStateChangeListener(object: View.OnAttachStateChangeListener {
            override fun onViewAttachedToWindow(p0: View) {}
            override fun onViewDetachedFromWindow(p0: View) = lifecycleEvents.removeEventObserver(eventObserver)
        })

        val navigator = screenVitals.navigator()
        binding.nextButton.setOnClickListener {
            val isHaveYetQuestions = questionDataSource.hasNext(questionId)
            val screenBuilder = if (isHaveYetQuestions) {
                { screenVitals: ScreenVitals, args: ScreenArg, id: Int -> TestScreen(screenVitals, args, id) }
            } else {
                { screenVitals: ScreenVitals, args: ScreenArg, id: Int -> ScoreScreen(screenVitals, args, id) }
            }
            val screenArg = if (isHaveYetQuestions) {

                    ScreenArg.Test(questionId + 1)

            } else {
                ScreenArg.Empty()
            }
            if (answerId != -1) {
                navigator.navigate(screenBuilder, screenArg, -1) { parent, newRoot, oldRoot ->
                    parent.addView(newRoot)
                    newRoot.measure(0, 0)
                    val anim1 = ObjectAnimator.ofFloat(newRoot, View.TRANSLATION_Y,  newRoot.measuredWidth / 2f, 0f)
                    val anim2 = ObjectAnimator.ofFloat(newRoot, View.TRANSLATION_X, newRoot.measuredHeight / 2f, 0f)
                    AnimatorSet().apply {
                        duration = 600L
                        playTogether(anim1, anim2)
                        start()
                        doOnEnd {
                            (oldRoot?.parent as? ViewGroup)?.removeView(oldRoot)
                        }
                    }
                }
            } else {
                Toast.makeText(binding.root.context, R.string.select_one_answer, Toast.LENGTH_SHORT).show()
            }
        }

        binding.prevButton.isVisible = questionId > 0
        binding.prevButton.setOnClickListener {
            navigator.back()
        }

        return binding.root
    }


}