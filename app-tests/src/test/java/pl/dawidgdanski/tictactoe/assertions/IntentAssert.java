package pl.dawidgdanski.tictactoe.assertions;

import android.content.Intent;

import org.fest.assertions.Assertions;

import pl.dawidgdanski.tictactoe.game.TicTacToeGame;
import pl.dawidgdanski.tictactoe.ui.activity.GameActivity;

public class IntentAssert extends AbstractTestAssert<IntentAssert, Intent> {

    public static IntentAssert assertThat(final Intent systemUnderTest) {
        return new IntentAssert(systemUnderTest);
    }

    private IntentAssert(Intent actual) {
        super(actual, IntentAssert.class);
        isNotNull();
    }

    public IntentAssert hasExtraWithValue(final String extraName, final boolean value) {
        Assertions.assertThat(actual.getExtras().getBoolean(extraName)).isEqualTo(value);
        return myself;
    }

    public IntentAssert hasExtraWithValue(final String extraName, TicTacToeGame.Player player) {
        Assertions.assertThat(actual.getSerializableExtra(extraName)).isSameAs(player);

        return myself;
    }

    public IntentAssert hasComponentWithClassName(Class<GameActivity> gameActivityClass) {
        Assertions.assertThat(actual.getComponent().getClassName()).isEqualTo(gameActivityClass.getName());

        return myself;
    }
}
