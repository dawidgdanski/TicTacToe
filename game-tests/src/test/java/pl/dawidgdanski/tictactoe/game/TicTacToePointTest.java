package pl.dawidgdanski.tictactoe.game;

import android.os.Parcel;

import org.fest.assertions.Assertions;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TestRule;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;

import pl.dawidgdanski.tictactoe.game.rules.TestExecutionTimeRule;

import static org.junit.Assert.fail;

@RunWith(RobolectricTestRunner.class)
public class TicTacToePointTest {

    @Rule
    public final TestRule TEST_EXECUTION_TIME_RULE = new TestExecutionTimeRule();

    @Test
    public void shouldRaise_IAE_WhenIncorrectPointsInsertedDuringConstruction() {
        try {
            new TicTacToePoint(-1, 0);
            fail();
        } catch (IllegalArgumentException e) {

        }

        try {
            new TicTacToePoint(0, -1);
            fail();
        } catch (IllegalArgumentException e) {

        }

        try {
            new TicTacToePoint(3, 0);
            fail();
        } catch (IllegalArgumentException e) {

        }

        try {
            new TicTacToePoint(0, 3);
            fail();
        } catch (IllegalArgumentException e) {

        }
    }

    @Test
    public void shouldBeParceledAndRestored() {
        Parcel parcel = Parcel.obtain();

        final TicTacToePoint SUT = new TicTacToePoint(0, 1);
        SUT.writeToParcel(parcel, 0);

        parcel.setDataPosition(0);

        final TicTacToePoint recreatedSUT = TicTacToePoint.CREATOR.createFromParcel(parcel);

        Assertions.assertThat(recreatedSUT).isEqualTo(SUT);
    }

}
