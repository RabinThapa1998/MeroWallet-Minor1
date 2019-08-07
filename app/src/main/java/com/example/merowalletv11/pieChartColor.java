
package com.example.merowalletv11;

import android.content.res.Resources;
import android.graphics.Color;

import java.util.ArrayList;
import java.util.List;

/**
 * Class that holds predefined color integer arrays (e.g.
 * ColorTemplate.VORDIPLOM_COLORS) and convenience methods for loading colors
 * from resources.
 *
 * @author Philipp Jahoda
 */
public class pieChartColor {

    /**
     * an "invalid" color that indicates that no color is set
     */
    public static final int COLOR_NONE = 0x00112233;

    /**
     * this "color" is used for the Legend creation and indicates that the next
     * form should be skipped
     */
    public static final int COLOR_SKIP = 0x00112234;

    /**
     * THE COLOR THEMES ARE PREDEFINED (predefined color integer arrays), FEEL
     * FREE TO CREATE YOUR OWN WITH AS MANY DIFFERENT COLORS AS YOU WANT
     */

    public static final int[] JOYFUL_COLORS = {
            Color.rgb(217, 80, 138), Color.rgb(254, 149, 7), Color.rgb(106, 167, 134),
            Color.rgb(245, 199, 0), Color.rgb(53, 194, 209), Color.rgb(230, 95, 92),
            Color.rgb(179, 216, 156),Color.rgb(141,119,95),Color.rgb(213,191,134),
            Color.rgb(199,146,223),Color.rgb(255,161,197),Color.rgb(123,136,255),
            Color.rgb(255,130,96),Color.rgb(253,95,0)
    };


    public static int rgb(String hex) {
        int color = (int) Long.parseLong(hex.replace("#", ""), 16);
        int r = (color >> 16) & 0xFF;
        int g = (color >> 8) & 0xFF;
        int b = (color >> 0) & 0xFF;
        return Color.rgb(r, g, b);
    }


    public static int getHoloBlue() {
        return Color.rgb(51, 181, 229);
    }


    public static int colorWithAlpha(int color, int alpha) {
        return (color & 0xffffff) | ((alpha & 0xff) << 24);
    }


    public static List<Integer> createColors(Resources r, int[] colors) {

        List<Integer> result = new ArrayList<Integer>();

        for (int i : colors) {
            result.add(r.getColor(i));
        }

        return result;
    }


    public static List<Integer> createColors(int[] colors) {

        List<Integer> result = new ArrayList<Integer>();

        for (int i : colors) {
            result.add(i);
        }

        return result;
    }
}
