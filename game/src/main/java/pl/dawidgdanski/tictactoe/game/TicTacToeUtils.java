package pl.dawidgdanski.tictactoe.game;

import com.google.common.base.Preconditions;

import java.util.Collection;
import java.util.Random;

final class TicTacToeUtils {

    private TicTacToeUtils() { }

    public static int max(final Collection<Integer> collection) {
        Preconditions.checkNotNull(collection, "Integers are null");

        int value = Integer.MIN_VALUE;

        for(int v : collection) {
            if(v > value) {
                value = v;
            }
        }

        return value;
    }

    public static int min(final Collection<Integer> collection) {
        Preconditions.checkNotNull(collection, "Integers are null");

        int value = Integer.MAX_VALUE;

        for(int v : collection) {
            if(v < value) {
                value = v;
            }
        }

        return value;
    }
}
