package pl.dawidgdanski.tictactoe;

public final class Constants {

    private Constants() { }

    public static final String DIALOG_FRAGMENT = "dialog_fragment";

    public static final class GameActivity {

        public static final String EXTRA_GAME_MODE = "extra_game_mode";

        public static final String EXTRA_FIRST_TURN = "extra_first_turn";

        public static final String EXTRA_IS_USER_FIRST = "extra_is_user_first";

        private GameActivity() { }

    }

    public static final class GameFragments {

        public static final String ARG_FIRST_TURN = "arg_first_turn";

        public static final String ARG_IS_USER_FIRST = "arg_is_user_first";

        private GameFragments() { }

    }
}
