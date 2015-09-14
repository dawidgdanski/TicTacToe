package pl.dawidgdanski.tictactoe.ui.dialog;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;

import butterknife.Bind;
import butterknife.ButterKnife;
import pl.dawidgdanski.tictactoe.Constants;
import pl.dawidgdanski.tictactoe.R;
import pl.dawidgdanski.tictactoe.game.TicTacToeGame;
import pl.dawidgdanski.tictactoe.ui.activity.ActivityHelper;
import pl.dawidgdanski.tictactoe.ui.activity.GameActivity;
import pl.dawidgdanski.tictactoe.ui.view.PlayerChoiceSection;

public class HumanVsCpuStartDialog extends DialogFragment {

    @Bind(R.id.player_choice_radio_group)
    PlayerChoiceSection playerChoiceSection;

    @Bind(R.id.turn_choice_radio_group)
    PlayerChoiceSection turnChoiceSection;

    public static HumanVsCpuStartDialog newInstance() {

        HumanVsCpuStartDialog dialog = new HumanVsCpuStartDialog();

        Bundle args = new Bundle();

        dialog.setArguments(args);

        return dialog;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        LayoutInflater inflater = getActivity().getLayoutInflater();

        View view = inflater.inflate(R.layout.dialog_human_vs_cpu, null);
        ButterKnife.bind(this, view);

        return new AlertDialog.Builder(getActivity())
                .setView(view)
                .setTitle(getString(R.string.human_vs_cpu))
                .setPositiveButton(R.string.start, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        final TicTacToeGame.Player firstTurn = turnChoiceSection.getPlayer();
                        final TicTacToeGame.Player chosenHumanPlayer = playerChoiceSection.getPlayer();
                        Bundle bundle = new Bundle();
                        bundle.putSerializable(Constants.GameActivity.EXTRA_GAME_MODE, GameActivity.GameMode.HUMAN_VS_CPU);
                        bundle.putSerializable(Constants.GameActivity.EXTRA_FIRST_TURN, firstTurn);
                        bundle.putSerializable(Constants.GameActivity.EXTRA_IS_USER_FIRST, chosenHumanPlayer == firstTurn);

                        ActivityHelper.startActivity(getActivity(), GameActivity.class, bundle);
                    }
                })
                .setNegativeButton(getString(android.R.string.cancel), null)
                .show();

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }
}
