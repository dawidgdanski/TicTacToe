package pl.dawidgdanski.tictactoe.game;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.common.base.Objects;
import com.google.common.base.Preconditions;
import com.google.common.collect.Lists;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class TicTacToeGame implements TicTacToeGameContract, Parcelable {

    public static final int DIMENSION = 3;

    public static final int STATE_AVAILABLE = 0;

    public static final Creator<TicTacToeGame> CREATOR = new Creator<TicTacToeGame>() {
        @Override
        public TicTacToeGame createFromParcel(Parcel source) {
            return new TicTacToeGame(source);
        }

        @Override
        public TicTacToeGame[] newArray(int size) {
            return new TicTacToeGame[size];
        }
    };

    private final int[][] board;

    private final int hashCode;

    private TicTacToePoint point;

    public TicTacToeGame() {
        this.board = new int[DIMENSION][DIMENSION];
        this.hashCode = Objects.hashCode(board[0], board[1], board[2]);
    }

    private TicTacToeGame(Parcel parcel) {
        this.board = new int[DIMENSION][DIMENSION];
        parcel.readIntArray(board[0]);
        parcel.readIntArray(board[1]);
        parcel.readIntArray(board[2]);
        this.hashCode = Objects.hashCode(board[0], board[1], board[2]);
    }

    @Override
    public synchronized List<TicTacToePoint> getAvailableStates() {
        return getStatesForValue(STATE_AVAILABLE);
    }

    private List<TicTacToePoint> getStatesForValue(final int value) {
        List<TicTacToePoint> availableStates = Lists.newArrayList();

        for (int i = 0, size = 3; i < size; i++) {
            for (int j = 0; j < size; j++) {
                if (board[i][j] == value) {
                    availableStates.add(new TicTacToePoint(i, j));
                }
            }
        }

        return Collections.unmodifiableList(availableStates);
    }

    @Override
    public synchronized List<TicTacToePoint> getXStates() {
        return getStatesForValue(Player.PLAYER_X.index);
    }

    @Override
    public synchronized List<TicTacToePoint> getOStates() {
        return getStatesForValue(Player.PLAYER_O.index);
    }

    @Override
    public synchronized boolean isGameOver() {

        return hasXWon() || hasOWon() || isDraw();
    }

    @Override
    public synchronized boolean isDraw() {
        return getAvailableStates().isEmpty() && !hasXWon() && !hasOWon();
    }

    @Override
    public synchronized boolean hasXWon() {
        return hasWon(Player.PLAYER_X);
    }

    @Override
    public synchronized boolean hasOWon() {
        return hasWon(Player.PLAYER_O);
    }

    @Override
    public synchronized void performMove(final TicTacToePoint point, final Player player) {
        board[point.getX()][point.getY()] = player.index;
    }

    @Override
    public synchronized Player getOpponentForPlayer(Player player) {
        Preconditions.checkNotNull(player, "Player is null");

        return player == Player.PLAYER_X ? Player.PLAYER_O : Player.PLAYER_X;
    }

    @Override
    public synchronized TicTacToePoint getBestMove(final Player player) {
        Preconditions.checkNotNull(player, "Player is null");

        Collection<TicTacToePoint> availableStates = getAvailableStates();

        point = null;

        point = getWinningMoveIfExists(player, availableStates);

        if (point != null) {
            return point;
        }

        point = getBlockingMoveIfExists(player, availableStates);

        if (point != null) {
            return point;
        }

        minimax(0, player, player);

        Preconditions.checkNotNull(point, "Could not find best move. Game must have ended.");

        return point;
    }

    @Override
    public List<TicTacToePoint> getWinningPoints() {
        if(hasWon(Player.PLAYER_O)) {
            return getWinningPointsForPlayer(Player.PLAYER_O);
        }

        if(hasWon(Player.PLAYER_X)) {
            return getWinningPointsForPlayer(Player.PLAYER_X);
        }

        throw new IllegalStateException("Game has not ended");
    }

    private List<TicTacToePoint> getWinningPointsForPlayer(final Player player) {
        final int playerIndex = player.index;

        if (board[0][0] == board[1][1] && board[0][0] == board[2][2] && board[0][0] == playerIndex) {
            return Collections.unmodifiableList(Arrays.asList(
                    new TicTacToePoint(0, 0),
                    new TicTacToePoint(1, 1),
                    new TicTacToePoint(2, 2)
            ));
        }

        if (board[0][2] == board[1][1] && board[0][2] == board[2][0] && board[0][2] == playerIndex) {
            return Collections.unmodifiableList(Arrays.asList(
                    new TicTacToePoint(0, 2),
                    new TicTacToePoint(1, 1),
                    new TicTacToePoint(2, 0)
            ));
        }

        for (int i = 0; i < 3; ++i) {
            if (board[i][0] == board[i][1] && board[i][0] == board[i][2] && board[i][0] == playerIndex) {
                return Collections.unmodifiableList(Arrays.asList(
                        new TicTacToePoint(i, 0),
                        new TicTacToePoint(i, 1),
                        new TicTacToePoint(i, 2)
                ));
            }

            if (board[0][i] == board[1][i] && board[0][i] == board[2][i] && board[0][i] == playerIndex) {
                return Collections.unmodifiableList(Arrays.asList(
                        new TicTacToePoint(0, i),
                        new TicTacToePoint(1, i),
                        new TicTacToePoint(2, i)
                ));
            }
        }

        throw new IllegalStateException();
    }

    @Override
    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }

        if (o == null || !(o instanceof TicTacToeGame)) {
            return false;
        }

        TicTacToeGame game = (TicTacToeGame) o;

        for(int i = 0, size = board.length; i < size; i++) {
            for(int j = 0; j < size; j++) {
                if(this.board[i][j] != game.board[i][j]) {
                    return false;
                }
            }
        }

        return true;
    }

    @Override
    public int hashCode() {
        return hashCode;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();

        for (int i = 0; i < DIMENSION; ++i) {
            for (int j = 0; j < DIMENSION; ++j) {
                builder.append(board[i][j]).append(" ");
            }
            builder.append("\n");
        }

        return builder.toString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeIntArray(board[0]);
        dest.writeIntArray(board[1]);
        dest.writeIntArray(board[2]);
    }

    void printBoard() {
        System.out.println();

        for (int i = 0; i < DIMENSION; ++i) {
            for (int j = 0; j < DIMENSION; ++j) {
                System.out.print(board[i][j] + " ");
            }
            System.out.println();

        }
    }

    private TicTacToePoint getWinningMoveIfExists(Player player, Collection<TicTacToePoint> availableStates) {
        return getGreedyMoveIfExists(player, availableStates);
    }

    private TicTacToePoint getBlockingMoveIfExists(Player player, Collection<TicTacToePoint> availableStates) {
        return getGreedyMoveIfExists(getOpponentForPlayer(player), availableStates);
    }

    private TicTacToePoint getGreedyMoveIfExists(final Player player, Collection<TicTacToePoint> states) {
        for (TicTacToePoint state : states) {
            performMove(state, player);

            if (hasWon(player)) {
                resetMove(state);
                return state;
            }

            resetMove(state);
        }

        return null;
    }

    private boolean hasWon(final Player player) {

        final int playerIndex = player.index;

        if ((board[0][0] == board[1][1] && board[0][0] == board[2][2] && board[0][0] == playerIndex) ||
                (board[0][2] == board[1][1] && board[0][2] == board[2][0] && board[0][2] == playerIndex)) {
            return true;
        }

        for (int i = 0; i < 3; ++i) {
            if ((board[i][0] == board[i][1] && board[i][0] == board[i][2] && board[i][0] == playerIndex) ||
                    (board[0][i] == board[1][i] && board[0][i] == board[2][i] && board[0][i] == playerIndex)) {
                return true;
            }
        }

        return false;
    }

    private int minimax(int depth, Player currentPlayer, Player playerWithTurn) {

        if (hasWon(playerWithTurn)) {
            return 1;
        }

        if (hasWon(currentPlayer)) {
            return -1;
        }

        List<TicTacToePoint> availableStates = getAvailableStates();

        if (availableStates.isEmpty()) {
            return 0;
        }

        int min = Integer.MAX_VALUE, max = Integer.MIN_VALUE;


        for (int i = 0, size = availableStates.size(), end = size - 1; i < size; i++) {
            TicTacToePoint state = availableStates.get(i);
            performMove(state, currentPlayer);

            if (currentPlayer == playerWithTurn) {
                int currentScore = minimax(depth + 1, getOpponentForPlayer(currentPlayer), playerWithTurn);

                max = Math.max(currentScore, max);

                if (currentScore >= 0 && depth == 0) {
                    point = state;
                }

                if (currentScore == 1) {
                    resetMove(state);
                    break;
                }

                if ((i == end) && max < 0 && depth == 0) {
                    point = state;
                }

            } else {
                final int currentScore = minimax(depth + 1, getOpponentForPlayer(currentPlayer), playerWithTurn);
                min = Math.min(currentScore, min);

                if (min == -1) {
                    resetMove(state);
                    break;
                }
            }

            resetMove(state);
        }

        return currentPlayer == playerWithTurn ? max : min;
    }

    private void resetMove(final TicTacToePoint point) {
        board[point.getX()][point.getY()] = STATE_AVAILABLE;
    }

    public enum Player {
        PLAYER_X(1),
        PLAYER_O(2);

        final int index;

        Player(int index) {
            this.index = index;
        }
    }
}
