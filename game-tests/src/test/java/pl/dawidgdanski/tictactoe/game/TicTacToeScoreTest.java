package pl.dawidgdanski.tictactoe.game;

import org.fest.assertions.Assertions;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TestRule;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import pl.dawidgdanski.tictactoe.game.rules.TestExecutionTimeRule;

import static org.fest.assertions.Assertions.assertThat;

@RunWith(JUnit4.class)
public class TicTacToeScoreTest {

    @Rule
    public final TestRule TEST_EXECUTION_TIME_RULE = new TestExecutionTimeRule();


    @Test
    public void equalsContractEqualityTest() {
        TicTacToeScore SUT1 = new TicTacToeScore(0, new TicTacToePoint(0, 0));

        TicTacToeScore SUT2 = new TicTacToeScore(0, new TicTacToePoint(0, 0));

        assertThat(SUT1).isEqualTo(SUT2);
    }

    @Test
    public void equalsContractInequalityTest() {
        TicTacToeScore SUT1 = new TicTacToeScore(0, new TicTacToePoint(0, 0));

        TicTacToeScore SUT2 = new TicTacToeScore(1, new TicTacToePoint(1, 0));

        Assertions.assertThat(SUT1).isNotEqualTo(SUT2);
    }

    @Test
    public void comparableEqualityTest() {
        TicTacToeScore SUT1 = new TicTacToeScore(0, new TicTacToePoint(0, 0));

        TicTacToeScore SUT2 = new TicTacToeScore(0, new TicTacToePoint(0, 0));

        Assertions.assertThat(SUT1.compareTo(SUT2)).isEqualTo(0);
    }

    @Test
    public void comparableInequalityTest() {
        TicTacToeScore SUT1 = new TicTacToeScore(0, new TicTacToePoint(0, 0));

        TicTacToeScore SUT2 = new TicTacToeScore(-1, new TicTacToePoint(0, 0));

        TicTacToeScore SUT3 = new TicTacToeScore(1, new TicTacToePoint(0, 0));

        Assertions.assertThat(SUT1.compareTo(SUT2)).isGreaterThan(0);

        Assertions.assertThat(SUT1.compareTo(SUT3)).isLessThan(0);
    }
}
