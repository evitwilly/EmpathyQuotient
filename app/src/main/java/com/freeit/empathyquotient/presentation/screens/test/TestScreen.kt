package com.freeit.empathyquotient.presentation.screens.test

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.content.Context
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import androidx.core.animation.doOnEnd
import com.freeit.empathyquotient.ActivityLifecycle
import com.freeit.empathyquotient.R
import com.freeit.empathyquotient.core.App
import com.freeit.empathyquotient.core.LocalPrefsDataSource
import com.freeit.empathyquotient.core.PortraitCheck
import com.freeit.empathyquotient.core.extensions.robotoRegular
import com.freeit.empathyquotient.data.QuestionDataSource
import com.freeit.empathyquotient.core.navigator.ScreenArg
import com.freeit.empathyquotient.core.navigator.Prefix
import com.freeit.empathyquotient.core.navigator.ScreenEntry
import com.freeit.empathyquotient.core.navigator.ScreenVitals
import com.freeit.empathyquotient.presentation.screens.score.ScoreScreen
import com.freeit.empathyquotient.presentation.image.RandomBlurredImage
import com.freeit.empathyquotient.presentation.view.buttons.AnimDiagonalButton
import com.freeit.empathyquotient.presentation.view.layouts.AbsoluteLayout
import com.freeit.empathyquotient.presentation.view.other.AnswerView
import com.freeit.empathyquotient.presentation.view.other.ShapedTextView
import ru.freeit.noxml.extensions.*

class TestScreen(screenVitals: ScreenVitals, screenArg: ScreenArg, id: Int) : ScreenEntry.Abstract(screenVitals, screenArg, id) {

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
        val ctx = screenVitals.inflater().context

        val frameLayoutContainer = frameLayout(ctx) {
            layoutParams(viewGroupLayoutParams().match().build())
        }

        val backgroundImageView = imageView(ctx) {
            id(R.id.bg_img)
            layoutParams(frameLayoutParams().matchWidth().matchHeight().build())
            centerCrop()
            img(RandomBlurredImage(ctx).bitmap())
        }

        val scrollView = scrollView(ctx) {
            layoutParams(frameLayoutParams().matchWidth().wrapHeight().build())
        }

        val questionBoxView = linearLayout(ctx) {
            id(R.id.question_answer_box)
            layoutParams(viewGroupLayoutParams()
                .matchWidth().wrapHeight()
                .build())
            padding(top = dp(8), start = dp(16), end = dp(16))
            vertical()
        }

        val questionView = ShapedTextView(ctx).apply {
            fontSize(24f)
            padding(dp(16))
            colorRes(R.color.white)
            robotoRegular()

            layoutParams(linearLayoutParams().matchWidth()
                .gravity(Gravity.CENTER)
                .marginBottom(dp(8))
                .wrapHeight().build())
        }

        questionBoxView.addView(questionView)
        scrollView.addView(questionBoxView)

        val linearLayoutButtons = linearLayout(ctx) {
            horizontal()

            layoutParams(frameLayoutParams().wrapWidth().wrapHeight()
                .wrapWidth().wrapHeight().gravity(Gravity.BOTTOM or Gravity.END)
                .marginBottom(dp(16)).marginEnd(dp(16))
                .build())
        }

        val prevButton = AnimDiagonalButton(ctx).apply {
            text(ctx.getString(R.string.prev_button))
            fontSize(26f)
            colorRes(R.color.white)
            padding(horizontal = dp(24), vertical = dp(8))
            layoutParams(linearLayoutParams().wrapWidth().wrapHeight().marginEnd(dp(16)).build())
            isAllCaps = true
        }

        val nextButton = AnimDiagonalButton(ctx).apply {
            text(ctx.getString(R.string.next_button))
            fontSize(26f)
            colorRes(R.color.white)
            padding(horizontal = dp(24), vertical = dp(8))
            layoutParams(linearLayoutParams().wrapWidth().wrapHeight().marginEnd(dp(16)).build())
            isAllCaps = true
        }

        linearLayoutButtons.addView(prevButton, nextButton)
        frameLayoutContainer.addView(backgroundImageView, scrollView, linearLayoutButtons)

        questionDataSource = (ctx.applicationContext as App).questionDataSource

        val quest = questionDataSource.questById(questionId)
        val answers = quest.answers(ctx, answerId)

        questionView.text = quest.str(ctx)

        val portraitOrientation = PortraitCheck(ctx).isYes()
        if (portraitOrientation) {
            answers(answers, questionBoxView) { _, _ ->
                linearLayoutParams().matchWidth().wrapHeight().marginBottom(ctx.dp(8)).build()
            }
        } else {
            val layout = AbsoluteLayout(ctx).apply {
                layoutParams(linearLayoutParams().matchWidth().wrapHeight().build())
            }
            layout.afterMeasure {
                answers(answers, layout) { i, context ->
                    AbsoluteLayout.LayoutParams(
                        layout.width / 2 - 5.dp(context), 75.dp(context),
                        i % 2 * (layout.width / 2 + 5.dp(context)), i / 2 * 85.dp(context)
                    )
                }
            }
            questionBoxView.addView(layout, 1)
        }

        val lifecycleEvents = (ctx.applicationContext as App).lifecycleEvents
        val eventObserver = object: ActivityLifecycle {
            override fun onStop() {}
            override fun onPause() {
                saveState((ctx.applicationContext as App).localPrefsDataSource)
            }
            override fun onStart() {}
            override fun onResume() {}
        }
        lifecycleEvents.insertEventObserver(eventObserver)

        frameLayoutContainer.addOnAttachStateChangeListener(object: View.OnAttachStateChangeListener {
            override fun onViewAttachedToWindow(p0: View) {}
            override fun onViewDetachedFromWindow(p0: View) = lifecycleEvents.removeEventObserver(eventObserver)
        })

        val navigator = screenVitals.navigator()
        nextButton.setOnClickListener {
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
                        duration = 300L
                        playTogether(anim1, anim2)
                        start()
                        doOnEnd {
                            (oldRoot?.parent as? ViewGroup)?.removeView(oldRoot)
                        }
                    }
                }
            } else {
                frameLayoutContainer.toast(R.string.select_one_answer)
            }
        }

        prevButton.visible(questionId > 0)
        prevButton.setOnClickListener { navigator.back() }

        return frameLayoutContainer
    }


}