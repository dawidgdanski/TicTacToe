package pl.dawidgdanski.tictactoe.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;

import com.google.common.base.Preconditions;

import java.io.Serializable;

import pl.dawidgdanski.tictactoe.Utils;

public final class ActivityHelper {

    private ActivityHelper() { }

    public static void startActivity(final Context context, final Class activityClass) {
        startActivity(context, activityClass, null);
    }

    public static void startActivity(final Context context, final Class activityClass, final Bundle bundle) {
        Preconditions.checkNotNull(activityClass, "Activity class is null");

        if(context == null) {
            return;
        }

        Intent intent = new Intent(context, activityClass);

        if(bundle != null) {
            intent.putExtras(bundle);
        }

        context.startActivity(intent);
    }
}
