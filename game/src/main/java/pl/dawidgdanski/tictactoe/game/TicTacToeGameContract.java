package pl.dawidgdanski.tictactoe.game;

import java.util.List;

public interface TicTacToeGameContract {

    List<TicTacToePoint> getAvailableStates();

    List<TicTacToePoint> getXStates();

    List<TicTacToePoint> getOStates();

    boolean isGameOver();

    boolean isDraw();

    boolean hasXWon();

    boolean hasOWon();

    void performMove(TicTacToePoint point, TicTacToeGame.Player player);

    TicTacToeGame.Player getOpponentForPlayer(TicTacToeGame.Player player);

    TicTacToePoint getBestMove(TicTacToeGame.Player player);

    List<TicTacToePoint> getWinningPoints();
}
