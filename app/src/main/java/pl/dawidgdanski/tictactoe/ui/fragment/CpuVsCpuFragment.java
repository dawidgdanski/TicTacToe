package pl.dawidgdanski.tictactoe.ui.fragment;

import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;
import android.os.Message;
import android.support.annotation.Nullable;

import java.util.Random;
import java.util.concurrent.TimeUnit;

import pl.dawidgdanski.tictactoe.Constants;
import pl.dawidgdanski.tictactoe.R;
import pl.dawidgdanski.tictactoe.Utils;
import pl.dawidgdanski.tictactoe.game.TicTacToeGame;
import pl.dawidgdanski.tictactoe.game.TicTacToePoint;

public class CpuVsCpuFragment extends GameFragment implements PlayerTurnTrigger {

    public static CpuVsCpuFragment newInstance(final TicTacToeGame.Player firstTurn) {

        CpuVsCpuFragment fragment = new CpuVsCpuFragment();
        Bundle args = new Bundle();
        args.putSerializable(Constants.GameFragments.ARG_FIRST_TURN, firstTurn);

        fragment.setArguments(args);
        return fragment;
    }

    private boolean isFirstMove = true;

    private HandlerThread handlerThread;

    private DeferringHandler deferringHandler;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Utils.setOrientationChangeEnabled(false, getActivity());
        handlerThread = new HandlerThread("DefferingThread");
        handlerThread.start();
        deferringHandler = new DeferringHandler(handlerThread.getLooper(), this);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        TicTacToeGame.Player player = (TicTacToeGame.Player) getArguments().getSerializable(Constants.GameFragments.ARG_FIRST_TURN);
        setFirstTurn(player);

        getContract().setTitle(getString(R.string.cpu_vs_cpu));

        gameView.setClickable(false);
    }

    @Override
    public void onPlayerTurn(TicTacToeGame.Player player) {
        super.onPlayerTurn(player);

        deferringHandler.sendEmptyMessage(0);
    }

    @Override
    public void triggerPlayerTurn() {
        if(isFirstMove) {
            isFirstMove = false;
            performRandomMove(generateRandomTicTacToePoint());
        } else {
            performCpuMove();
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Utils.setOrientationChangeEnabled(true, getActivity());
        handlerThread.quit();
        deferringHandler.clear();
    }


    private static TicTacToePoint generateRandomTicTacToePoint() {
        Random rand = new Random(System.currentTimeMillis());

        return new TicTacToePoint(rand.nextInt(TicTacToeGame.DIMENSION), rand.nextInt(TicTacToeGame.DIMENSION));
    }

    private static class DeferringHandler extends Handler {

        private Handler uiHandler;

        public DeferringHandler(Looper looper, final PlayerTurnTrigger trigger) {
            super(looper);
            uiHandler = new Handler(Looper.getMainLooper()) {
                @Override
                public void handleMessage(Message msg) {
                    super.handleMessage(msg);
                    trigger.triggerPlayerTurn();
                }
            };
        }

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            uiHandler.sendEmptyMessageDelayed(0, TimeUnit.SECONDS.toMillis(1));
        }


        private void clear() {
            uiHandler = null;
        }
    }
}
