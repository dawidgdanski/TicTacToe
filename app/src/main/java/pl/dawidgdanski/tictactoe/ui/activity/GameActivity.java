package pl.dawidgdanski.tictactoe.ui.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.widget.Toast;

import com.google.common.base.Preconditions;

import butterknife.Bind;
import butterknife.ButterKnife;
import pl.dawidgdanski.tictactoe.Constants;
import pl.dawidgdanski.tictactoe.R;
import pl.dawidgdanski.tictactoe.game.TicTacToeGame;
import pl.dawidgdanski.tictactoe.ui.fragment.CpuVsCpuFragment;
import pl.dawidgdanski.tictactoe.ui.fragment.GameFragment;
import pl.dawidgdanski.tictactoe.ui.fragment.HumanVsCpuFragment;
import pl.dawidgdanski.tictactoe.ui.fragment.HumanVsHumanFragment;

public class GameActivity extends BaseActivity implements GameFragment.Contract {

    @Bind(R.id.toolbar)
    Toolbar toolbar;

    private boolean isGameFinished = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.game_activity);
        ButterKnife.bind(this);
        setUpActionBar(toolbar);
        initializeGameFragment(savedInstanceState);
    }

    @Override
    public void setTitle(String title) {
        if(! TextUtils.isEmpty(title)) {
            setUpActionBarTitle(title);
        }
    }

    @Override
    public void notifyGameFinished() {
        isGameFinished = true;
    }

    @Override
    public void onBackPressed() {
        if(isGameFinished) {
            super.onBackPressed();
        } else {
            Toast.makeText(this, getString(R.string.game_not_finished_yet), Toast.LENGTH_SHORT).show();
        }
    }

    private void initializeGameFragment(final Bundle savedInstanceState) {

        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.container, getGameFragmentByGameMode(getIntent().getExtras()))
                    .commit();
        }
    }

    static Fragment getGameFragmentByGameMode(Bundle configuration) {

        GameMode gameMode = (GameMode) configuration.getSerializable(Constants.GameActivity.EXTRA_GAME_MODE);
        Preconditions.checkNotNull(gameMode, "Noe GameMode requested");

        TicTacToeGame.Player firstTurn = (TicTacToeGame.Player) configuration.getSerializable(Constants.GameActivity.EXTRA_FIRST_TURN);

        switch (gameMode) {
            case HUMAN_VS_HUMAN:
                return HumanVsHumanFragment.newInstance(firstTurn);
            case HUMAN_VS_CPU:
                boolean isUserFirst = configuration.getBoolean(Constants.GameActivity.EXTRA_IS_USER_FIRST);
                return HumanVsCpuFragment.newInstance(firstTurn, isUserFirst);
            case CPU_VS_CPU:
                return CpuVsCpuFragment.newInstance(firstTurn);
            default:
                throw new IllegalStateException();
        }
    }

    public enum GameMode {
        HUMAN_VS_HUMAN,
        HUMAN_VS_CPU,
        CPU_VS_CPU
    }
}
