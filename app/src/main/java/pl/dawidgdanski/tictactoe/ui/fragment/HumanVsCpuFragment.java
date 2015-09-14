package pl.dawidgdanski.tictactoe.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;

import pl.dawidgdanski.tictactoe.Constants;
import pl.dawidgdanski.tictactoe.R;
import pl.dawidgdanski.tictactoe.Utils;
import pl.dawidgdanski.tictactoe.game.TicTacToeGame;

public class HumanVsCpuFragment extends GameFragment {

    public static HumanVsCpuFragment newInstance(final TicTacToeGame.Player firstTurn,
                                                 final boolean isUserFirst) {

        HumanVsCpuFragment fragment = new HumanVsCpuFragment();

        Bundle args = new Bundle();
        args.putSerializable(Constants.GameFragments.ARG_FIRST_TURN, firstTurn);
        args.putBoolean(Constants.GameFragments.ARG_IS_USER_FIRST, isUserFirst);

        fragment.setArguments(args);

        return fragment;
    }

    private TicTacToeGame.Player userPlayer;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Utils.setOrientationChangeEnabled(false, getActivity());
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initializeMembers();

        getContract().setTitle(getString(R.string.human_vs_cpu));
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Utils.setOrientationChangeEnabled(true, getActivity());
    }

    @Override
    public void onPlayerTurn(TicTacToeGame.Player player) {
        super.onPlayerTurn(player);

        if(player == userPlayer) {
            setGameViewClickable(true);
        } else {
            setGameViewClickable(false);
            performCpuMove();
        }

    }

    private void initializeMembers() {
        Bundle bundle =  getArguments();

        TicTacToeGame.Player firstTurn = (TicTacToeGame.Player) bundle.getSerializable(Constants.GameFragments.ARG_FIRST_TURN);
        boolean isUserFirst = bundle.getBoolean(Constants.GameFragments.ARG_IS_USER_FIRST);
        userPlayer = isUserFirst ? firstTurn : getOpponentForPlayer(firstTurn);

        setFirstTurn(firstTurn);
    }
}
