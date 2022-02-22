package com.freeit.empathyquotient.presentation.screens.intro

import android.animation.Animator
import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.graphics.Bitmap
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.appcompat.widget.AppCompatImageView
import androidx.core.animation.doOnEnd
import ru.freeit.noxml.extensions.frameLayoutParams
import ru.freeit.noxml.extensions.imageView
import ru.freeit.noxml.extensions.img
import ru.freeit.noxml.extensions.layoutParams

class FourthPartBitmapAnimator(
    private val imageContainer: ViewGroup,
    private val bits: List<Bitmap>
) {

    private val img1 = addImgPart(bits[0], Gravity.START or Gravity.TOP)
    private val img2 = addImgPart(bits[2], Gravity.END or Gravity.TOP)
    private val img3 = addImgPart(bits[3], Gravity.END or Gravity.BOTTOM)
    private val img4 = addImgPart(bits[1], Gravity.START or Gravity.BOTTOM)

    fun back(imageContainer: ViewGroup, onEnd: () -> Unit) {
        AnimatorSet().apply {
            duration = 400L
            val allAnimators = backImgAnim(img1, -110f, (-img1.layoutParams.width * 2).toFloat(), (-img1.layoutParams.height).toFloat()) +
                    backImgAnim(img2, -110f, (img2.layoutParams.width * 3).toFloat(), -img2.layoutParams.height.toFloat()) +
                    backImgAnim(img3, 110f, (img3.layoutParams.width * 4).toFloat(), (img3.layoutParams.height).toFloat()) +
                    backImgAnim(img4, -110f, (-img4.layoutParams.width * 2).toFloat(), img4.layoutParams.height.toFloat())
            playTogether(*allAnimators)
            doOnEnd {
                onEnd()
                imageContainer.removeView(img1)
                imageContainer.removeView(img2)
                imageContainer.removeView(img3)
                imageContainer.removeView(img4)
            }
            start()

        }
    }

    fun forward(imageContainer: ViewGroup) {
        if (bits.size > 3) {
            AnimatorSet().apply {
                duration = 700L
                val allAnimators = forwardImgAnim(img1, -110f, (-img1.layoutParams.width * 2).toFloat(), (-img1.layoutParams.height).toFloat()) +
                        forwardImgAnim(img2, -110f, (img2.layoutParams.width * 3).toFloat(), -img2.layoutParams.height.toFloat()) +
                        forwardImgAnim(img3, 110f, (img3.layoutParams.width * 4).toFloat(), (img3.layoutParams.height).toFloat()) +
                        forwardImgAnim(img4, -110f, (-img4.layoutParams.width * 2).toFloat(), img4.layoutParams.height.toFloat())

                doOnEnd {
                    (imageContainer.parent as? ViewGroup)?.removeView(imageContainer)
                }
                playTogether(*allAnimators)
                start()
            }
        }
    }

    private fun addImgPart(bitmap: Bitmap, gravity: Int): AppCompatImageView {
        val img = imageView(imageContainer.context).apply {
            layoutParams(frameLayoutParams()
                .width(imageContainer.measuredWidth / 2)
                .height(imageContainer.measuredHeight / 2)
                .gravity(gravity)
                .build())
            img(bitmap)
        }
        imageContainer.addView(img)
        return img
    }

    private fun backImgAnim(img: ImageView, startDegree: Float, startTranslationX: Float, startTranslationY: Float) : Array<Animator> {
        val rotateAnim = ObjectAnimator.ofFloat(img, View.ROTATION, startDegree, 0f)
        val translateAnimX = ObjectAnimator.ofFloat(img, View.TRANSLATION_X, startTranslationX, 0f)
        val translateAnimY = ObjectAnimator.ofFloat(img, View.TRANSLATION_Y, startTranslationY, 0f)
        return arrayOf(rotateAnim, translateAnimX, translateAnimY)
    }

    private fun forwardImgAnim(img: ImageView, endDegree: Float, endTranslationX: Float, endTranslationY: Float) : Array<Animator> {
        val rotateAnim = ObjectAnimator.ofFloat(img, View.ROTATION, 0f, endDegree)
        val translateAnimX = ObjectAnimator.ofFloat(img, View.TRANSLATION_X, 0f, endTranslationX)
        val translateAnimY = ObjectAnimator.ofFloat(img, View.TRANSLATION_Y, 0f, endTranslationY)
        return arrayOf(rotateAnim, translateAnimX, translateAnimY)
    }
}