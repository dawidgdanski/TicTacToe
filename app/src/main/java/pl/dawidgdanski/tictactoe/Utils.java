package pl.dawidgdanski.tictactoe;

import android.app.Activity;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.os.Build;
import android.view.Surface;

public final class Utils {

    private Utils() { }

    public static void setOrientationChangeEnabled(final boolean state, final Activity activity) {
        if(activity == null) {
            return;
        }

        if (!state) {
            int orientation = 0;
            int currentOrientation = activity.getResources().getConfiguration().orientation;
            final int rotation = activity.getWindowManager().getDefaultDisplay().getRotation();
            switch (currentOrientation) {
                case Configuration.ORIENTATION_LANDSCAPE:
                    if (rotation == Surface.ROTATION_0 || rotation == Surface.ROTATION_90) {
                        orientation = ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE;
                    } else {
                        orientation = ActivityInfo.SCREEN_ORIENTATION_REVERSE_LANDSCAPE;
                    }
                    break;
                case Configuration.ORIENTATION_PORTRAIT:
                    if (rotation == Surface.ROTATION_0 || rotation == Surface.ROTATION_270) {
                        orientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT;
                    } else {
                        orientation = ActivityInfo.SCREEN_ORIENTATION_REVERSE_PORTRAIT;
                    }
                    break;
            }
            activity.setRequestedOrientation(orientation);
        } else {
            activity.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED);
        }
    }

    public static boolean isAPIHigherThan(final int sdkVersion) {
        return Build.VERSION.SDK_INT > sdkVersion;
    }

    public static boolean isAPIEqualTo(final int sdkVersion) {
        return Build.VERSION.SDK_INT == sdkVersion;
    }

    public static boolean isAPILowerThan(final int sdkVersion) {
        return Build.VERSION.SDK_INT < sdkVersion;
    }

    public static boolean isAPIEqualOrHigherThan(final int sdkVersion) {
        return isAPIEqualTo(sdkVersion) || isAPIHigherThan(sdkVersion);
    }

    public static boolean isAPIEqualOrLowerThan(final int sdkVersion) {
        return isAPIEqualTo(sdkVersion) || isAPILowerThan(sdkVersion);
    }
}
