package pl.dawidgdanski.tictactoe.matchers;

import android.content.Intent;


import org.hamcrest.BaseMatcher;
import org.hamcrest.Description;
import org.hamcrest.Matcher;

import pl.dawidgdanski.tictactoe.Constants;
import pl.dawidgdanski.tictactoe.game.TicTacToeGame;
import pl.dawidgdanski.tictactoe.ui.activity.GameActivity;

public final class IntentMatchers {

    public static Matcher<Intent> hasComponentNameOfClass(final Class clazz) {
        return new BaseMatcher<Intent>() {
            @Override
            public boolean matches(Object item) {
                final Intent intent = (Intent) item;

                return intent.getComponent().getClassName().equals(clazz.getName());
            }

            @Override
            public void describeTo(Description description) {

            }
        };
    }

    public static Matcher<Intent> hasExtraGameModeSameAs(final GameActivity.GameMode mode) {
        return new BaseMatcher<Intent>() {
            @Override
            public boolean matches(Object item) {
                final Intent intent = (Intent) item;

                return intent.getSerializableExtra(Constants.GameActivity.EXTRA_GAME_MODE) == mode;
            }

            @Override
            public void describeTo(Description description) {

            }
        };
    }

    public static Matcher<Intent> hasExtraFirstTurnSameAs(final TicTacToeGame.Player player) {
        return new BaseMatcher<Intent>() {

            @Override
            public void describeTo(Description description) {

            }

            @Override
            public boolean matches(Object item) {
                Intent intent = (Intent) item;

                return intent.getSerializableExtra(Constants.GameActivity.EXTRA_FIRST_TURN) == player;
            }
        };
    }
}
