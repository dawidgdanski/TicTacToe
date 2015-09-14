package pl.dawidgdanski.tictactoe.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;

import pl.dawidgdanski.tictactoe.Constants;
import pl.dawidgdanski.tictactoe.R;
import pl.dawidgdanski.tictactoe.game.TicTacToeGame;

public class HumanVsHumanFragment extends GameFragment {

    public static HumanVsHumanFragment newInstance(final TicTacToeGame.Player firstTurn) {
        HumanVsHumanFragment fragment = new HumanVsHumanFragment();
        Bundle args = new Bundle();
        args.putSerializable(Constants.GameFragments.ARG_FIRST_TURN, firstTurn);

        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        TicTacToeGame.Player player = (TicTacToeGame.Player) getArguments().getSerializable(Constants.GameFragments.ARG_FIRST_TURN);
        setFirstTurn(player);
        getContract().setTitle(getString(R.string.human_vs_human));
    }
}
