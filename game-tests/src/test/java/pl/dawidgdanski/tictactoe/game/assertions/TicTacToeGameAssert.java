package pl.dawidgdanski.tictactoe.game.assertions;

import org.fest.assertions.Assertions;

import pl.dawidgdanski.tictactoe.game.TicTacToeGame;

public class TicTacToeGameAssert extends AbstractTestAssert<TicTacToeGameAssert, TicTacToeGame>  {

    public static TicTacToeGameAssert assertThat(TicTacToeGame systemUnderTest) {
        return new TicTacToeGameAssert(systemUnderTest);
    }

    private TicTacToeGameAssert(TicTacToeGame actual) {
        super(actual, TicTacToeGameAssert.class);
    }

    public TicTacToeGameAssert hasAvailableStates(final int statesCount) {
        isNotNull();

        Assertions.assertThat(actual.getAvailableStates()).hasSize(statesCount);
        return myself;
    }

    public TicTacToeGameAssert hasNoWinner() {
        isNotNull();

        Assertions.assertThat(actual.hasOWon()).isFalse();
        Assertions.assertThat(actual.hasXWon()).isFalse();

        return myself;
    }

    public TicTacToeGameAssert hasXWinner() {
        isNotNull();

        Assertions.assertThat(actual.hasXWon()).isTrue();

        return myself;
    }

    public TicTacToeGameAssert hasOWinner() {
        isNotNull();

        Assertions.assertThat(actual.hasOWon()).isTrue();

        return myself;
    }

    public TicTacToeGameAssert isGameOver() {
        isNotNull();

        Assertions.assertThat(actual.isGameOver()).isTrue();

        return myself;
    }
}
