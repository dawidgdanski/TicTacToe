package pl.dawidgdanski.tictactoe.game;

import org.fest.assertions.Assertions;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TestRule;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.util.Arrays;
import java.util.Collection;

import pl.dawidgdanski.tictactoe.game.rules.TestExecutionTimeRule;

@RunWith(JUnit4.class)
public class TicTacToeUtilsTest {

    @Rule
    public final TestRule TEST_EXECUTION_TIME_RULE = new TestExecutionTimeRule();


    @Test
    public void shouldReturnMaxValue() {
        final Collection<Integer> boxedIntegers = Arrays.asList(1, 2, 3, 3);


        Assertions.assertThat(TicTacToeUtils.max(boxedIntegers)).isEqualTo(3);
    }

    @Test
    public void shouldReturnMinValue() {
        final Collection<Integer> boxedIntegers = Arrays.asList(1, 2, 3,3, -5);

        Assertions.assertThat(TicTacToeUtils.min(boxedIntegers)).isEqualTo(-5);
    }

}
