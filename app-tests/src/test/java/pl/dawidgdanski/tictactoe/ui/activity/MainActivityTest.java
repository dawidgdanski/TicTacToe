package pl.dawidgdanski.tictactoe.ui.activity;

import android.app.AlertDialog;
import android.content.Intent;
import android.support.v4.app.DialogFragment;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.shadows.ShadowApplication;
import org.robolectric.util.ActivityController;

import pl.dawidgdanski.tictactoe.AppRobolectricTestRunner;
import pl.dawidgdanski.tictactoe.Constants;
import pl.dawidgdanski.tictactoe.game.TicTacToeGame;

import static pl.dawidgdanski.tictactoe.assertions.IntentAssert.assertThat;

@RunWith(AppRobolectricTestRunner.class)
public class MainActivityTest {

    @Test
    public void shouldLaunchGameActivityWith_HUMAN_VS_HUMAN_ModeWhenOnAppropriateButtonClicked() {
        MainActivity SUT = getResumedActivity();

        SUT.playButtonHumanVsHuman.performClick();

        AlertDialog dialog = (AlertDialog) ((DialogFragment) SUT.getSupportFragmentManager().findFragmentByTag(Constants.DIALOG_FRAGMENT)).getDialog();

        dialog.getButton(AlertDialog.BUTTON_POSITIVE).performClick();

        final Intent intent = ShadowApplication.getInstance().getNextStartedActivity();

        assertThat(intent)
                .hasComponentWithClassName(GameActivity.class)
                .hasExtraWithValue(Constants.GameActivity.EXTRA_FIRST_TURN, TicTacToeGame.Player.PLAYER_O);
    }

    @Test
    public void shouldLaunchGameActivityWith_HUMAN_VS_CPU_ModeWithInfoWhetherUserStartsWhenAppropriateButtonClicked() {
        MainActivity SUT = getResumedActivity();

        SUT.playButtonHumanVsCpu.performClick();

        DialogFragment dialogFragment = (DialogFragment) SUT.getSupportFragmentManager().findFragmentByTag(Constants.DIALOG_FRAGMENT);

        AlertDialog dialog = (AlertDialog) dialogFragment.getDialog();

        dialog.getButton(AlertDialog.BUTTON_POSITIVE).performClick();

        final Intent intent = ShadowApplication.getInstance().getNextStartedActivity();

        assertThat(intent)
                .hasComponentWithClassName(GameActivity.class)
                .hasExtraWithValue(Constants.GameActivity.EXTRA_FIRST_TURN, TicTacToeGame.Player.PLAYER_O)
                .hasExtraWithValue(Constants.GameActivity.EXTRA_IS_USER_FIRST, true);
    }

    @Test
    public void shouldLaunchGameActivityWith_CPU_VS_CPU_ModeWhenOnAppropriateButtonClicked() {
        MainActivity SUT = getResumedActivity();

        SUT.playButtonCpuVsCpu.performClick();

        AlertDialog dialog = (AlertDialog) ((DialogFragment) SUT.getSupportFragmentManager().findFragmentByTag(Constants.DIALOG_FRAGMENT)).getDialog();

        dialog.getButton(AlertDialog.BUTTON_POSITIVE).performClick();

        final Intent intent = ShadowApplication.getInstance().getNextStartedActivity();

        assertThat(intent)
                .hasComponentWithClassName(GameActivity.class)
                .hasExtraWithValue(Constants.GameActivity.EXTRA_FIRST_TURN, TicTacToeGame.Player.PLAYER_O);
    }


    private MainActivity getResumedActivity() {
        return ActivityController.of(Robolectric.getShadowsAdapter(), MainActivity.class)
                .attach()
                .create()
                .start()
                .resume().get();
    }

}
