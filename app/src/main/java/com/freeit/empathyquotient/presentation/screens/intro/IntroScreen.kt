package com.freeit.empathyquotient.presentation.screens.intro

import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelStore
import androidx.lifecycle.ViewModelStoreOwner
import com.freeit.empathyquotient.core.App
import com.freeit.empathyquotient.presentation.screens.ScreenEntry
import com.freeit.empathyquotient.presentation.screens.test.TestScreen
import android.view.Gravity
import androidx.core.view.isVisible
import com.freeit.empathyquotient.R
import com.freeit.empathyquotient.core.navigator.ScreenArg
import com.freeit.empathyquotient.core.navigator.ScreenVitals
import com.freeit.empathyquotient.presentation.screens.Prefix
import com.freeit.empathyquotient.core.PortraitCheck
import com.freeit.empathyquotient.presentation.image.RandomBlurredImage
import java.util.*
import com.freeit.empathyquotient.core.navigator.TestStack
import com.freeit.empathyquotient.presentation.view.ArrowScalingButton
import com.freeit.empathyquotient.presentation.view.other.BitmappedView
import com.freeit.empathyquotient.presentation.view.other.ContentDescriptionView
import ru.freeit.noxml.extensions.*


class IntroScreen(screenVitals: ScreenVitals, screenArg: ScreenArg, id: Int) : ScreenEntry.Abstract(screenVitals, screenArg, id), ViewModelStoreOwner {

    private val viewModelStore = ViewModelStore()

    override fun prefix() = Prefix.intro()

    override fun pop(oldScreen: View?) {
        (oldScreen?.parent as? ViewGroup)?.let { parent ->
            parent.addView(root, 0)

            root.post {
                val bits = BitmappedView(root).fourBitmaps(parent.measuredWidth, parent.measuredHeight)
                FourthPartBitmapAnimator(parent, bits).back(parent) { parent.removeView(oldScreen) }
            }
        }

    }

    override fun view(): View {

        val layoutInflater = screenVitals.inflater()
        val ctx = layoutInflater.context

        val frameLayoutContainer = frameLayout(ctx) {
            layoutParams(viewGroupLayoutParams().match().build())
        }

        val backgroundImage = imageView(ctx) {
            id(R.id.bg_img)
            layoutParams(frameLayoutParams().matchWidth().matchHeight().build())
            centerCrop()
            img(RandomBlurredImage(ctx).bitmap())
        }

        val isPortraitOrientation = PortraitCheck(ctx).isYes()

        val contentDescriptionView = ContentDescriptionView(ctx).apply {
            id(R.id.desc_view)
            padding(dp(12))
            bg(R.drawable.intro_box_bg)

            val layoutParams = frameLayoutParams()

            val sixteen = dp(16)
            layoutParams(if (isPortraitOrientation) {
                layoutParams.matchWidth()
                    .height(dp(400))
                    .margins(sixteen, dp(60), sixteen, sixteen)
                    .build()
            } else {
                layoutParams.width(dp(380))
                    .matchHeight()
                    .margins(sixteen, sixteen, sixteen, sixteen)
                    .gravity(Gravity.START or Gravity.TOP)
                    .build()
            })
        }

        val startTestButton = ArrowScalingButton(ctx).apply {
            id(R.id.start_test_button)

            val layoutParams = frameLayoutParams().wrapHeight()
                .marginStart(dp(16)).marginEnd(dp(16))

            layoutParams(if (isPortraitOrientation) {
                layoutParams.matchWidth()
                    .marginBottom(dp(16))
                    .gravity(Gravity.BOTTOM)
                    .build()
            } else {
                layoutParams.wrapWidth()
                    .marginBottom(dp(24))
                    .gravity(Gravity.BOTTOM or Gravity.END)
                    .build()
            })
        }

        frameLayoutContainer.addView(backgroundImage, contentDescriptionView, startTestButton)

        val localPrefsDataSource = (layoutInflater.context.applicationContext as App).localPrefsDataSource
        val viewModel  = ViewModelProvider(this, IntroViewModelFactory(layoutInflater.context, localPrefsDataSource)).get(IntroViewModel::class.java)
        val testStack = TestStack.Base((ctx.applicationContext as App).localPrefsDataSource)

        startTestButton.setOnClickListener {
            testStack.clear()
            screenVitals.navigator().navigate({ screenVitals, arg, id -> TestScreen(screenVitals, arg, id) }, ScreenArg.Test(0), -1) { parent, newRoot, oldRoot ->
                parent.addView(newRoot, 0)

                oldRoot?.let { screenView ->
                    if (screenView !is ViewGroup) { return@navigate }

                    for (i in 0 until screenView.childCount) { screenView.getChildAt(i).isVisible = false }

                    val bits = BitmappedView(screenView).fourBitmaps()
                    FourthPartBitmapAnimator(screenView, bits).forward(screenView)
                }
            }
        }

        contentDescriptionView.onDotChanged { viewModel.select(it) }
        viewModel.selectedQuestion.observe(screenVitals.lifecycleOwner()) { question -> contentDescriptionView.changeQuestion(question) }

        contentDescriptionView.onForwardClick { viewModel.next() }
        contentDescriptionView.onBackClick { viewModel.prev() }

        frameLayoutContainer.addOnAttachStateChangeListener(object: View.OnAttachStateChangeListener {
            override fun onViewAttachedToWindow(p0: View) {}
            override fun onViewDetachedFromWindow(p0: View) {
                viewModelStore.clear()
            }
        })

        return frameLayoutContainer
    }

    override fun isStartDestination() = true

    override fun getViewModelStore() = viewModelStore

}