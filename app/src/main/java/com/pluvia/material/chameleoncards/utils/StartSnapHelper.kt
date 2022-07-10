package com.pluvia.material.chameleoncards.utils

import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.LinearSnapHelper
import androidx.recyclerview.widget.OrientationHelper
import androidx.recyclerview.widget.RecyclerView

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