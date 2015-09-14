package pl.dawidgdanski.tictactoe.game;

import android.os.Build;
import android.os.Parcel;

import org.fest.assertions.Assertions;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TestRule;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

import java.lang.reflect.Parameter;
import java.util.Random;

import pl.dawidgdanski.tictactoe.game.rules.TestExecutionTimeRule;

import static pl.dawidgdanski.tictactoe.game.assertions.TicTacToeGameAssert.assertThat;

@RunWith(RobolectricTestRunner.class)
@Config(sdk = Build.VERSION_CODES.LOLLIPOP, manifest = Config.NONE)
public class TicTacToeGameTest {

    @Rule
    public final TestRule TEST_EXECUTION_TIME_RULE = new TestExecutionTimeRule();

    @Test
    public void shouldReturnAllAvailableStatesAtTheBeginningOfTheGame() {
        final TicTacToeGame SUT = new TicTacToeGame();

        assertThat(SUT).hasAvailableStates(9);
    }

    @Test
    public void shouldNotHaveAnyWinnerWhenCreated() {
        final TicTacToeGame SUT = new TicTacToeGame();

        assertThat(SUT).hasNoWinner();
    }

    @Test
    public void shouldReturnOpponentForPlayer() {
        final TicTacToeGame SUT = new TicTacToeGame();

        final TicTacToeGame.Player player = SUT.getOpponentForPlayer(TicTacToeGame.Player.PLAYER_X);

        Assertions.assertThat(player).isSameAs(TicTacToeGame.Player.PLAYER_O);
    }

    @Test
    public void shouldBeParceledAndRestored() {
        Parcel parcel = Parcel.obtain();

        TicTacToeGame SUT = new TicTacToeGame();
        SUT.writeToParcel(parcel, 0);

        parcel.setDataPosition(0);

        TicTacToeGame recreatedSUT = TicTacToeGame.CREATOR.createFromParcel(parcel);

        Assertions.assertThat(recreatedSUT).isEqualTo(SUT);
    }

    @Test
    public void sampleGameTest() {

        final TicTacToeGame SUT = new TicTacToeGame();

        Random rand = new Random();

        TicTacToeGame.Player turn = rand.nextInt(2) == 1 ? TicTacToeGame.Player.PLAYER_O : TicTacToeGame.Player.PLAYER_X;


        SUT.performMove(new TicTacToePoint(rand.nextInt(TicTacToeGame.DIMENSION), rand.nextInt(TicTacToeGame.DIMENSION)), turn);

        SUT.printBoard();

        turn = SUT.getOpponentForPlayer(turn);

        while(! SUT.isGameOver()) {

            TicTacToePoint point = SUT.getBestMove(turn);
            SUT.performMove(point, turn);

            SUT.printBoard();
            turn = SUT.getOpponentForPlayer(turn);
        }

        if(SUT.hasOWon()) {
            System.out.println("Result: O wins");
        } else if(SUT.hasXWon()) {
            System.out.println("Result: X wins");
        } else if(SUT.isDraw()) {
            System.out.println("Result: Draw");
        }
    }
}
