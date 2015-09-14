package pl.dawidgdanski.tictactoe.game;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TestRule;
import org.junit.runner.RunWith;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;

import junitparams.JUnitParamsRunner;
import junitparams.Parameters;
import pl.dawidgdanski.tictactoe.game.rules.TestExecutionTimeRule;

import static pl.dawidgdanski.tictactoe.game.assertions.TicTacToeGameAssert.assertThat;

@RunWith(JUnitParamsRunner.class)
public class TicTacToeGameStatesTest {

    @Rule
    public final TestRule TEST_EXECUTION_TIME_RULE = new TestExecutionTimeRule();


    private Object[] getWinningStatesForPlayer(final TicTacToeGame.Player player) {
        return new Object[]{
                new Object[]{
                        Collections.unmodifiableCollection(
                                Arrays.asList(
                                        new TicTacToePoint(0, 0),
                                        new TicTacToePoint(1, 1),
                                        new TicTacToePoint(2, 2)
                                )
                        ),
                        player
                },

                new Object[]{
                        Collections.unmodifiableCollection(
                                Arrays.asList(
                                        new TicTacToePoint(0, 0),
                                        new TicTacToePoint(1, 0),
                                        new TicTacToePoint(2, 0)
                                )
                        ),
                        player
                },

                new Object[]{
                        Collections.unmodifiableCollection(
                                Arrays.asList(
                                        new TicTacToePoint(0, 1),
                                        new TicTacToePoint(1, 1),
                                        new TicTacToePoint(2, 1)
                                )
                        ),
                        player
                },

                new Object[]{
                        Collections.unmodifiableCollection(
                                Arrays.asList(
                                        new TicTacToePoint(0, 2),
                                        new TicTacToePoint(1, 2),
                                        new TicTacToePoint(2, 2)
                                )
                        ),
                        player
                },

                new Object[]{
                        Collections.unmodifiableCollection(
                                Arrays.asList(
                                        new TicTacToePoint(0, 0),
                                        new TicTacToePoint(0, 1),
                                        new TicTacToePoint(0, 2)
                                )
                        ),
                        player
                },

                new Object[]{
                        Collections.unmodifiableCollection(
                                Arrays.asList(
                                        new TicTacToePoint(1, 0),
                                        new TicTacToePoint(1, 1),
                                        new TicTacToePoint(1, 2)
                                )
                        ),
                        player
                },

                new Object[]{
                        Collections.unmodifiableCollection(
                                Arrays.asList(
                                        new TicTacToePoint(2, 0),
                                        new TicTacToePoint(2, 1),
                                        new TicTacToePoint(2, 2)
                                )
                        ),
                        player
                },

                new Object[]{
                        Collections.unmodifiableCollection(
                                Arrays.asList(
                                        new TicTacToePoint(0, 2),
                                        new TicTacToePoint(1, 1),
                                        new TicTacToePoint(2, 0)
                                )
                        ),
                        player
                }
        };
    }

    private Object[] getXWinningStates() {
        return getWinningStatesForPlayer(TicTacToeGame.Player.PLAYER_X);
    }

    private Object[] getOWinningStates() {
        return getWinningStatesForPlayer(TicTacToeGame.Player.PLAYER_O);
    }

    @Test
    @Parameters(method = "getXWinningStates")
    public void shouldReturnTrueWhen_hasXWon(Collection<TicTacToePoint> points, final TicTacToeGame.Player xPlayer) {
        final TicTacToeGame SUT = new TicTacToeGame();

        for (TicTacToePoint point : points) {
            SUT.performMove(point, xPlayer);
        }

        assertThat(SUT).hasXWinner()
                .isGameOver();
    }

    @Test
    @Parameters(method = "getOWinningStates")
    public void shouldReturnTrueWhen_hasOWon(Collection<TicTacToePoint> points, final TicTacToeGame.Player oPlayer) {
        final TicTacToeGame SUT = new TicTacToeGame();

        for (TicTacToePoint point : points) {
            SUT.performMove(point, oPlayer);
        }

        assertThat(SUT).hasOWinner()
                .isGameOver();
    }
}
