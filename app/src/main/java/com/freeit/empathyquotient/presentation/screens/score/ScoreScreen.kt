package com.freeit.empathyquotient.presentation.screens.score

import android.animation.Animator
import android.animation.ObjectAnimator
import android.animation.ValueAnimator
import android.content.Context
import android.content.Intent
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.util.TypedValue
import android.view.Gravity
import android.view.View
import android.widget.FrameLayout
import android.widget.LinearLayout
import androidx.appcompat.widget.AppCompatTextView
import androidx.core.animation.doOnEnd
import androidx.core.content.ContextCompat
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
import com.freeit.empathyquotient.presentation.view.RippleImageButton
import com.freeit.empathyquotient.presentation.view.dp
import kotlin.math.roundToInt
import kotlin.random.Random

class RandomBounce(
    private val x: Float,
    private val y: Float,
    private val maxRadius: Float,
    private val color: Int
) {
    private var radius = 0f

    fun incrementBy(value: Float) {
        radius += value * maxRadius
    }

    fun zero() {
        radius = 0f
    }

    fun draw(canvas: Canvas, paint: Paint) {
        paint.color = color
        canvas.drawCircle(x, y, radius, paint)
    }
}

class FunnyBouncesView @JvmOverloads constructor(
    ctx: Context,
    attrs: AttributeSet? = null,
    defStyleAttrs: Int = 0
): View(ctx, attrs, defStyleAttrs) {

    private val randomCoordinates = mutableListOf<RandomBounce>()

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        for (i in 0 until 33) {
            val primaryColor = ContextCompat.getColor(context, R.color.primaryColor)
            val randomColor = Color.argb(Random.nextInt(100, 255),
                Color.red(primaryColor),
                Color.green(primaryColor),
                Color.blue(primaryColor)
            )
            randomCoordinates.add(RandomBounce(Random.nextFloat() * w, Random.nextFloat() * h,
                Random.nextFloat() * 100f + 10f, randomColor))
        }
    }

    fun start(onEnd: (anim: Animator) -> Unit) {
        ValueAnimator.ofFloat(0f, 1f).apply {
            duration = 3000L
            addUpdateListener {  anim ->
                randomCoordinates.forEach { it.incrementBy(anim.animatedValue as Float) }
                invalidate()
            }
            doOnEnd(onEnd)
            start()
        }
    }

    private val paint = Paint(Paint.ANTI_ALIAS_FLAG)

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        for (coordinate in randomCoordinates) {
            coordinate.draw(canvas, paint)
        }
    }

}

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
//        verticalLayout.addView(LinearLayout(verticalLayout.context).apply {
//            orientation = LinearLayout.HORIZONTAL
//            gravity = Gravity.CENTER
//            setPadding(
//                (context.resources.displayMetrics.density * 24).roundToInt(),
//                (context.resources.displayMetrics.density * 16).roundToInt(),
//                (context.resources.displayMetrics.density * 24).roundToInt(),
//                (context.resources.displayMetrics.density * 16).roundToInt()
//            )
//            addView(AppCompatTextView(context).apply {
//                text = context.getString(R.string.share_your_result)
//                setTextColor(Color.WHITE)
//                setTextSize(TypedValue.COMPLEX_UNIT_SP, 17f)
//                layoutParams = LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT).apply {
//                    marginEnd = (context.resources.displayMetrics.density * 16).roundToInt()
//                }
//            })
//
//            addView(RippleImageButton(verticalLayout.context).apply {
//                layoutParams = LinearLayout.LayoutParams(
//                    (context.resources.displayMetrics.density * 40).roundToInt(),
//                    (context.resources.displayMetrics.density * 40).roundToInt()
//                )
//                setImageResource(R.drawable.ic_share_24)
//            })
//        })

        binding.root.addView(verticalLayout)


        return binding.root
    }

    override fun isStartDestination() = false
//    override fun pop() {
//        (root.parent as? ViewGroup)?.removeView(root)
//    }
}