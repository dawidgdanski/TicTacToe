package pl.dawidgdanski.tictactoe.game.interfaces;

import pl.dawidgdanski.tictactoe.game.TicTacToeGame;

public interface OnGameStateChangeListener {

    void onPlayerTurn(TicTacToeGame.Player player);

    void onXWin();

    void onOWin();

    void onDraw();
}
