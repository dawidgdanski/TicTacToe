package pl.dawidgdanski.tictactoe.ui.activity;

import android.support.v7.internal.view.ContextThemeWrapper;

import pl.dawidgdanski.tictactoe.R;
import pl.dawidgdanski.tictactoe.testcase.ActivityUnitTestCase2;

public class MainActivityTest extends ActivityUnitTestCase2<MainActivity> {

    public MainActivityTest() {
        super(MainActivity.class, "Tests for MainActivity");
    }

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        ContextThemeWrapper context = new ContextThemeWrapper(getInstrumentation().getTargetContext(), R.style.AppTheme);
        setActivityContext(context);
    }


    //Skipped because of a lack support for ButterKnife
    /*public void testShouldLaunchGameActivityWithHumanVsHumanGameModeWhenClickedOnAppropriateButton() {
        MainActivity SUT = startActivity(new Intent(getInstrumentation().getTargetContext(), MainActivity.class), null, null);

        SUT.playButtonHumanVsHuman.performClick();

        HumanVsHumanStartDialog dialog = (HumanVsHumanStartDialog) SUT.getSupportFragmentManager().findFragmentByTag(Constants.DIALOG_FRAGMENT);
        ((AlertDialog)dialog.getDialog()).getButton(AlertDialog.BUTTON_POSITIVE).performClick();

        final Intent intent = getStartedActivityIntent();

        assertThat(intent, hasComponentNameOfClass(GameActivity.class));
        assertThat(intent, hasExtraGameModeSameAs(GameActivity.GameMode.HUMAN_VS_HUMAN));
        assertThat(intent, hasExtraFirstTurnSameAs(TicTacToeGame.Player.PLAYER_O));
    }*/
}
