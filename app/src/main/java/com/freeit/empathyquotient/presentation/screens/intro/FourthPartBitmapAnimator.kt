package com.freeit.empathyquotient.presentation.screens.intro

import android.animation.Animator
import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.appcompat.widget.AppCompatImageView
import androidx.core.animation.doOnEnd
import androidx.core.view.isVisible

class FourthPartBitmapAnimator(
    private val images: List<AppCompatImageView>
) {
    private fun List<ImageView>.img1() = this[0]
    private fun List<ImageView>.img2() = this[1]
    private fun List<ImageView>.img3() = this[2]
    private fun List<ImageView>.img4() = this[3]

    fun back(screenView: ViewGroup, onEnd: () -> Unit) {
        if (images.size > 3) {
            AnimatorSet().apply {
                duration = 400L
                val allAnimators = backImgAnim(images.img1(), -110f, (-images.img1().layoutParams.width * 2).toFloat(), (-images.img1().layoutParams.height).toFloat()) +
                        backImgAnim(images.img2(), -110f, (images.img2().layoutParams.width * 3).toFloat(), -images.img2().layoutParams.height.toFloat()) +
                        backImgAnim(images.img3(), 110f, (images.img3().layoutParams.width * 4).toFloat(), (images.img3().layoutParams.height).toFloat()) +
                        backImgAnim(images.img4(), -110f, (-images.img4().layoutParams.width * 2).toFloat(), images.img4().layoutParams.height.toFloat())
                playTogether(*allAnimators)
                doOnEnd {
                    onEnd()
                    images.forEach { image -> screenView.removeView(image) }
                }
                start()

            }
        }
    }

    fun forward(screenView: ViewGroup) {
        if (images.size > 3) {
            AnimatorSet().apply {
                duration = 700L
                val allAnimators = forwardImgAnim(images.img1(), -110f, (-images.img1().layoutParams.width * 2).toFloat(), (-images.img1().layoutParams.height).toFloat()) +
                        forwardImgAnim(images.img2(), -110f, (images.img2().layoutParams.width * 3).toFloat(), -images.img2().layoutParams.height.toFloat()) +
                        forwardImgAnim(images.img3(), 110f, (images.img3().layoutParams.width * 4).toFloat(), (images.img3().layoutParams.height).toFloat()) +
                        forwardImgAnim(images.img4(), -110f, (-images.img4().layoutParams.width * 2).toFloat(), images.img4().layoutParams.height.toFloat())

                doOnEnd {
                    (screenView.parent as? ViewGroup)?.removeView(screenView)
                }
                playTogether(*allAnimators)
                start()

            }
        }
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