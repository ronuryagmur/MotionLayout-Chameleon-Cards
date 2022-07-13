package com.pluvia.material.chameleoncards.utils

import android.util.Log
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.LinearSnapHelper
import androidx.recyclerview.widget.OrientationHelper
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.SmoothScroller.ScrollVectorProvider

class StartSnapHelper: LinearSnapHelper() {
    override fun calculateDistanceToFinalSnap(layoutManager: RecyclerView.LayoutManager, targetView: View): IntArray? {
        return if (layoutManager.canScrollHorizontally()) {
            val outer = mutableListOf<Int>()
            outer.add(distanceToStart(targetView, getHorizontalHelper(layoutManager)))
            outer.add(0)

            outer.toIntArray()
        } else {
            super.calculateDistanceToFinalSnap(layoutManager, targetView)
        }
    }

    override fun findSnapView(layoutManager: RecyclerView.LayoutManager?): View? {
        return if (layoutManager is LinearLayoutManager) {
            if (layoutManager.canScrollHorizontally()) {
                getStartView(layoutManager, getHorizontalHelper(layoutManager))
            } else {
                super.findSnapView(layoutManager)
            }
        } else {
            super.findSnapView(layoutManager)
        }
    }

    /***
     * If you don't want to scroll only one item at a time then do not override this method.
     * Just comment findTargetSnapPosition() method.
     * ***/
    override fun findTargetSnapPosition(
        layoutManager: RecyclerView.LayoutManager?,
        velocityX: Int,
        velocityY: Int
    ): Int {
        if (layoutManager !is ScrollVectorProvider) {
            return RecyclerView.NO_POSITION
        }

        val currentView = findSnapView(layoutManager) ?: return RecyclerView.NO_POSITION

        val myLayoutManager = layoutManager as AutoAlignLayoutManager

        val position1 = myLayoutManager.findFirstVisibleItemPosition()
        val position2 = myLayoutManager.findLastVisibleItemPosition()

        var currentPosition = layoutManager.getPosition(currentView)

        if (velocityX > 400) {
            //only one item to the right
            currentPosition = position1 + 1
        } else if (velocityX < 400) {
            //only one item to the left
            currentPosition = position1
        }

        return if (currentPosition == RecyclerView.NO_POSITION) {
            RecyclerView.NO_POSITION
        } else currentPosition

    }

    private fun distanceToStart(targetView: View, helper: OrientationHelper): Int {
        return helper.getDecoratedStart(targetView) - helper.startAfterPadding
    }

    private fun getStartView(layoutManager: RecyclerView.LayoutManager, orientationHelper: OrientationHelper): View? {
        val firstChild = (layoutManager as LinearLayoutManager).findFirstVisibleItemPosition()
        val isLastItem = (layoutManager.findLastCompletelyVisibleItemPosition() == layoutManager.itemCount - 1)

        if (firstChild == RecyclerView.NO_POSITION || isLastItem) {
            return null
        }

        val child = layoutManager.findViewByPosition(firstChild)

        return if (orientationHelper.getDecoratedEnd(child) >= orientationHelper.getDecoratedMeasurement(child) / 2
            && orientationHelper.getDecoratedEnd(child) > 0) {
            child;
        } else {
            if (layoutManager.findFirstCompletelyVisibleItemPosition() == layoutManager.itemCount -1) {
                null
            } else {
                layoutManager.findViewByPosition(firstChild + 1)
            }
        }
    }

    private fun getHorizontalHelper(layoutManager: RecyclerView.LayoutManager): OrientationHelper {
        return OrientationHelper.createHorizontalHelper(layoutManager)
    }
}