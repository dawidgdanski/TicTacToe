package pl.dawidgdanski.tictactoe.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.common.base.Preconditions;

import butterknife.Bind;
import butterknife.ButterKnife;
import pl.dawidgdanski.tictactoe.R;
import pl.dawidgdanski.tictactoe.game.TicTacToeGame;
import pl.dawidgdanski.tictactoe.game.TicTacToePoint;
import pl.dawidgdanski.tictactoe.game.interfaces.OnGameStateChangeListener;
import pl.dawidgdanski.tictactoe.game.view.TicTacToeView;
import pl.dawidgdanski.tictactoe.ui.view.Entry;

public abstract class GameFragment extends ContractFragment<GameFragment.Contract> implements OnGameStateChangeListener {

    @Bind(R.id.game_view)
    TicTacToeView gameView;

    @Bind(R.id.game_state_entry)
    Entry gameStateEntry;

    @Bind(R.id.turn_entry)
    Entry turnEntry;

    private TicTacToeGame.Player firstTurn;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_game_layout, null);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this, view);
        setUpViews();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    @Override
    public void onPlayerTurn(TicTacToeGame.Player player) {
        turnEntry.setValueText(player.name());
        gameView.setTurn(player);
    }

    @Override
    public void onXWin() {
        gameStateEntry.setValueText(getString(R.string.x_wins));
        getContract().notifyGameFinished();
    }

    @Override
    public void onOWin() {
        gameStateEntry.setValueText(getString(R.string.o_wins));
        getContract().notifyGameFinished();
    }

    @Override
    public void onDraw() {
        gameStateEntry.setValueText(getString(R.string.draw));
        getContract().notifyGameFinished();
    }

    public void setFirstTurn(final TicTacToeGame.Player player) {
        this.firstTurn = player;

        onPlayerTurn(player);
    }

    protected void performRandomMove(TicTacToePoint point) {
        gameView.performFirstRandomMove(point);
    }

    protected TicTacToeGame.Player getOpponentForPlayer(TicTacToeGame.Player player) {
        Preconditions.checkNotNull(player);

        return gameView.getOpponentForPlayer(player);
    }

    protected void setGameViewClickable(final boolean state) {
        if(gameView != null) {
            gameView.setClickable(state);
        }
    }

    protected void performCpuMove() {
        gameView.performCpuMove();
    }

    private void setUpViews() {
        gameView.setOnGameStateChangeListener(this);
    }

    public interface Contract {
        void setTitle(String title);
        void notifyGameFinished();
    }
}
