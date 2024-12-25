package com.example.testassessment

import androidx.recyclerview.widget.RecyclerView
import androidx.test.espresso.IdlingResource

class RecyclerViewIdlingResource(
    private val recyclerView: RecyclerView
) : IdlingResource {

    @Volatile
    private var resourceCallback: IdlingResource.ResourceCallback? = null

    override fun getName() = "RecyclerViewIdlingResource"

    override fun isIdleNow(): Boolean {
        val isIdle = recyclerView.adapter?.itemCount ?: 0 > 0
        if (isIdle) {
            resourceCallback?.onTransitionToIdle()
        }
        return isIdle
    }

    override fun registerIdleTransitionCallback(callback: IdlingResource.ResourceCallback?) {
        resourceCallback = callback
    }
}
