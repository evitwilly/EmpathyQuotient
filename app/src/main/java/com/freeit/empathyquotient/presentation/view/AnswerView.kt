package com.freeit.empathyquotient.presentation.view

import android.animation.ValueAnimator
import android.content.Context
import android.graphics.*
import android.os.Bundle
import android.os.Parcelable
import android.util.AttributeSet
import android.util.TypedValue
import android.view.Gravity
import android.view.ViewTreeObserver
import android.widget.LinearLayout
import androidx.appcompat.widget.AppCompatTextView
import androidx.core.content.ContextCompat
import androidx.core.os.bundleOf
import com.freeit.empathyquotient.R
import com.freeit.empathyquotient.core.App

class AnswerView @JvmOverloads constructor(
    ctx: Context,
    attributeSet: AttributeSet? = null,
    defStyleAttr: Int = 0,
    private val isInitialChecked : Boolean = false
) : LinearLayout(ctx, attributeSet, defStyleAttr) {

    private fun Int.dp() = (this * context.resources.displayMetrics.density).toInt()

    private var selectWidth = 0f
    private var isChecked = false

    private val listener = mutableListOf<() -> Unit>()

    private var animCheckBox: AnimCheckBox
    private var answerText: AppCompatTextView

    init {
        isClickable = true
        setPadding(16.dp(), 16.dp(), 16.dp(), 16.dp())
        gravity = Gravity.CENTER_VERTICAL

        animCheckBox = AnimCheckBox(context).apply {
            layoutParams = LayoutParams(40.dp(), 40.dp())
            setOnCheckedListener { isChecked ->
                this@AnswerView.setCheckedOnlyBg(isChecked)
                this@AnswerView.listener.firstOrNull()?.invoke()
            }
        }

        answerText = AppCompatTextView(context).apply {
            layoutParams = LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT).apply {
                setMargins(10.dp(), 0, 0, 0)
            }
            typeface = (context.applicationContext as App).fontManager.bold()
            setTextSize(TypedValue.COMPLEX_UNIT_SP, 23f)
            setTextColor(Color.WHITE)
        }

        addView(animCheckBox)
        addView(answerText)

        viewTreeObserver.addOnGlobalLayoutListener(object: ViewTreeObserver.OnGlobalLayoutListener {
            override fun onGlobalLayout() {
                viewTreeObserver.removeOnGlobalLayoutListener(this)
                setCheckedWithoutAnim(isInitialChecked)
            }
        })
    }

    override fun performClick(): Boolean {
        if (!isChecked) {
            check()
            animCheckBox.setChecked(true)
            this.listener.firstOrNull()?.invoke()
        }
        return true
    }

    fun setText(text: String) {
        answerText.text = text
    }

    private fun check() {
        isChecked = true
        ValueAnimator.ofFloat(0f, width.toFloat()).apply {
            duration = 500L
            addUpdateListener {
                selectWidth = it.animatedValue as Float
                invalidate()
            }
            start()
        }
    }

    private fun checkWithoutAnim() {
        isChecked = true
        selectWidth = width.toFloat()
        invalidate()
    }

    private fun uncheckWithoutAnim() {
        isChecked = false
        selectWidth = 0f
        invalidate()
    }

    private fun uncheck() {
        isChecked = false
        ValueAnimator.ofFloat(width.toFloat(), 0f).apply {
            duration = 500L
            addUpdateListener {
                selectWidth = it.animatedValue as Float
                invalidate()
            }
            start()
        }
    }

    fun setChecked(isChecked: Boolean) {
        setCheckedOnlyBg(isChecked)
        animCheckBox.setChecked(isChecked)
    }

    fun setCheckedWithoutAnim(isChecked: Boolean) {
        checkWithoutAnim()
        setCheckedOnlyBgWithoutAnim(isChecked)
        animCheckBox.setCheckedWithoutAnim(isChecked)
    }

    private fun setCheckedOnlyBgWithoutAnim(isChecked: Boolean) {
        if (this.isChecked != isChecked) {
            if (isChecked) {
                checkWithoutAnim()
            } else {
                uncheckWithoutAnim()
            }
        }
    }

    private fun setCheckedOnlyBg(isChecked: Boolean) {
        if (this.isChecked != isChecked) {
            if (isChecked) {
                check()
            } else {
                uncheck()
            }
        }
    }

    fun setOnClickListener(listener: () -> Unit) {
        this.listener.clear()
        this.listener.add(listener)
    }

    private val selectedBgPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        color = ContextCompat.getColor(context, R.color.primaryDarkColor)
    }

    private val bgPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        color = ContextCompat.getColor(context, R.color.primaryColor)
    }

    override fun dispatchDraw(canvas: Canvas) {
        val rect = RectF(0f, 0f, width.toFloat(), height.toFloat())
        canvas.drawRoundRect(rect, 20.dp().toFloat(), 20.dp().toFloat(), bgPaint)
        val selectedRect = RectF(0f, 0f, selectWidth, height.toFloat())
        canvas.drawRoundRect(selectedRect, 20.dp().toFloat(), 20.dp().toFloat(), selectedBgPaint)
        super.dispatchDraw(canvas)
    }

    override fun onSaveInstanceState(): Parcelable {
        return bundleOf(
            "superState" to super.onSaveInstanceState(),
            "isChecked" to isChecked
        )
    }

    override fun onRestoreInstanceState(state: Parcelable?) {
        if (state is Bundle) {
            setCheckedOnlyBg(state.getBoolean("isChecked", false))
        }
        super.onRestoreInstanceState(state)
    }



}
