package com.sixD.leaderboard.util;

import androidx.databinding.BindingAdapter;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

/**
 * Custom Data Binding adapters for [SwipeRefreshLayout].
 */
public class SwipeRefreshBindingAdapters {

    /**
     * Controls the refreshing state of the [SwipeRefreshLayout].
     *
     * @param view         The SwipeRefreshLayout.
     * @param isRefreshing Whether the loading indicator should be visible.
     */
    @BindingAdapter("isRefreshing")
    public static void setRefreshing(SwipeRefreshLayout view, boolean isRefreshing) {
        view.setRefreshing(isRefreshing);
    }

    /**
     * Sets a refresh listener that executes a [Runnable] when the user swipes to refresh.
     *
     * @param view     The SwipeRefreshLayout.
     * @param runnable The logic to execute on refresh.
     */
    @BindingAdapter("onRefresh")
    public static void setOnRefreshListener(SwipeRefreshLayout view, final Runnable runnable) {
        view.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                if (runnable != null) {
                    runnable.run();
                }
            }
        });
    }
}
