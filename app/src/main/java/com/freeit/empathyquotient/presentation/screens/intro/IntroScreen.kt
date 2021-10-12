package com.freeit.empathyquotient.presentation.screens.intro

import android.graphics.Bitmap
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelStore
import androidx.lifecycle.ViewModelStoreOwner
import com.freeit.empathyquotient.core.App
import com.freeit.empathyquotient.databinding.IntroScreenBinding
import com.freeit.empathyquotient.databinding.IntroScreenLandscapeBinding
import com.freeit.empathyquotient.presentation.screens.ScreenEntry
import com.freeit.empathyquotient.presentation.screens.test.TestScreen
import android.view.Gravity
import android.widget.FrameLayout
import androidx.core.view.isVisible
import androidx.viewbinding.ViewBinding
import com.freeit.empathyquotient.core.navigator.ScreenArg
import com.freeit.empathyquotient.core.navigator.ScreenVitals
import com.freeit.empathyquotient.presentation.screens.Prefix
import com.freeit.empathyquotient.core.PortraitCheck
import com.freeit.empathyquotient.presentation.utils.RandomBlurredImage
import java.util.*
import androidx.appcompat.widget.AppCompatImageView as AppCompatImageView1
import com.freeit.empathyquotient.core.navigator.TestStack
import com.freeit.empathyquotient.presentation.view.other.BitmappedView


class IntroScreen(screenVitals: ScreenVitals, screenArg: ScreenArg, id: Int) : ScreenEntry.Abstract(screenVitals, screenArg, id), ViewModelStoreOwner {

    private val viewModelStore = ViewModelStore()

    override fun prefix() = Prefix.intro()

    private fun ViewBinding.toPortrait() = this as IntroScreenBinding
    private fun ViewBinding.toLandscape() = this as IntroScreenLandscapeBinding

    private lateinit var binding: ViewBinding

    override fun pop(oldScreen: View?) {
        (oldScreen?.parent as? ViewGroup)?.let { parent ->
            parent.addView(root, 0)

            root.post {
                val bits = BitmappedView(root).fourBitmaps(parent.measuredWidth, parent.measuredHeight)

                val img1 = addImgPart(parent, bits.first(),Gravity.START or Gravity.TOP)
                val img2 = addImgPart(parent, bits[2], Gravity.END or Gravity.TOP)
                val img3 = addImgPart(parent, bits[3], Gravity.END or Gravity.BOTTOM)
                val img4 = addImgPart(parent, bits[1], Gravity.START or Gravity.BOTTOM)
                FourthPartBitmapAnimator(listOf(img1, img2, img3, img4)).back(parent) {
                    parent.removeView(oldScreen)
                }
            }
        }

    }

    override fun view(): View {

        val layoutInflater = screenVitals.inflater()
        val portraitCheck = PortraitCheck(layoutInflater.context)
        binding = portraitCheck.get(
            onPortrait = { IntroScreenBinding.inflate(layoutInflater) },
            onLandscape = { IntroScreenLandscapeBinding.inflate(layoutInflater) }
        )

        val localPrefsDataSource = (layoutInflater.context.applicationContext as App).localPrefsDataSource
        val viewModel  = ViewModelProvider(this, IntroViewModelFactory(layoutInflater.context, localPrefsDataSource)).get(IntroViewModel::class.java)

        val contentDescView = if (portraitCheck.isYes()) binding.toPortrait().descView else binding.toLandscape().contentDescView
        val startTestButton = if (portraitCheck.isYes()) binding.toPortrait().startTestButton else binding.toLandscape().startTestButton
        val bgImage = if (portraitCheck.isYes()) binding.toPortrait().bgImg else binding.toLandscape().bgImg

        bgImage.setImageBitmap(RandomBlurredImage(binding.root.context).bitmap())

        val testStack = TestStack.Base((bgImage.context.applicationContext as App).localPrefsDataSource)

        startTestButton.setOnClickListener {
            testStack.clear()
            screenVitals.navigator().navigate({ screenVitals, arg, id -> TestScreen(screenVitals, arg, id) }, ScreenArg.Test(0), -1) { parent, newRoot, oldRoot ->
                parent.addView(newRoot, 0)

                oldRoot?.let { screenView ->
                    val bits = BitmappedView(screenView).fourBitmaps()

                    if (screenView !is ViewGroup) { return@navigate }

                    for (i in 0 until screenView.childCount) { screenView.getChildAt(i).isVisible = false }

                    val img1 = addImgPart(screenView, bits.first(),Gravity.START or Gravity.TOP)
                    val img2 = addImgPart(screenView, bits[2], Gravity.END or Gravity.TOP)
                    val img3 = addImgPart(screenView, bits[3], Gravity.END or Gravity.BOTTOM)
                    val img4 = addImgPart(screenView, bits[1], Gravity.START or Gravity.BOTTOM)
                    FourthPartBitmapAnimator(listOf(img1, img2, img3, img4)).forward(screenView)
                }
            }
        }

        contentDescView.setDotChangeListener { viewModel.select(it) }
        viewModel.selectedQuestion.observe(screenVitals.lifecycleOwner()) { question -> contentDescView.changeQuestion(question) }

        contentDescView.setForwardClickListener { viewModel.next() }
        contentDescView.setBackClickListener { viewModel.prev() }

        binding.root.addOnAttachStateChangeListener(object: View.OnAttachStateChangeListener {
            override fun onViewAttachedToWindow(p0: View) {}
            override fun onViewDetachedFromWindow(p0: View) {
                viewModelStore.clear()
            }
        })

        return binding.root
    }

    override fun isStartDestination() = true

    private fun addImgPart(screenView: ViewGroup, bitmap: Bitmap, gravity: Int): androidx.appcompat.widget.AppCompatImageView {
        val img = AppCompatImageView1(screenView.context).apply {
            layoutParams = FrameLayout.LayoutParams(
                screenView.measuredWidth / 2, screenView.measuredHeight / 2
            ).apply {
                this.gravity = gravity
            }
            setImageBitmap(bitmap)
        }
        screenView.addView(img)
        return img
    }

    override fun getViewModelStore() = viewModelStore

}