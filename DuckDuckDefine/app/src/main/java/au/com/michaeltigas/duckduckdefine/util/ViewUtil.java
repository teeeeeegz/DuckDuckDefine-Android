package au.com.michaeltigas.duckduckdefine.util;

import android.support.v4.widget.SwipeRefreshLayout;
import android.view.View;
import android.widget.LinearLayout;

import au.com.michaeltigas.duckduckdefine.R;

/**
 * Created by Michael Tigas on 15/3/17.
 *
 * This utility class provides static methods to alter UI elements
 */
public class ViewUtil {
    // Prevent constructor initialisation
    private ViewUtil() {
    }

    /**
     * Enable/Disable refreshing state of SwipeRefreshLayout
     *
     * @param swipeRefreshLayout
     * @param setRefresh
     */
    public static void setRefreshing(SwipeRefreshLayout swipeRefreshLayout, boolean setRefresh) {
        if (swipeRefreshLayout != null) {
            boolean isRefreshing = swipeRefreshLayout.isRefreshing();

            if (setRefresh) {
                if (!isRefreshing) swipeRefreshLayout.setRefreshing(true);
            } else {
                if (isRefreshing) swipeRefreshLayout.setRefreshing(false);
            }
        }
    }

    /**
     * Create an int array with colour values, to theme the Swipe Refresh Layout palette
     *
     * @return
     */
    public static int[] colorPaletteSwipeRefresh() {
        int[] colorPalette = new int[4];
        colorPalette[0] = R.color.swiperefreshcolor_one;
        colorPalette[1] = R.color.swiperefreshcolor_two;
        colorPalette[2] = R.color.swiperefreshcolor_three;
        colorPalette[3] = R.color.swiperefreshcolor_four;

        return colorPalette;
    }

    /**
     * Fix MATCH_PARENT bug of a layout within a list not stretching to its full width
     *
     * @param layoutView
     */
    public static void setListLayoutBoundsMaxWidth(View layoutView) {
        layoutView.setLayoutParams(new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
    }
}
