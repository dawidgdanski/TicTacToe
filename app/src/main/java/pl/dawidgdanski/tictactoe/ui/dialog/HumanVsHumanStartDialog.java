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

public class HumanVsHumanStartDialog extends DialogFragment {

    @Bind(R.id.turn_choice_radio_group)
    PlayerChoiceSection playerChoiceSection;

    public static HumanVsHumanStartDialog newInstance() {
        HumanVsHumanStartDialog dialog = new HumanVsHumanStartDialog();

        Bundle args = new Bundle();
        dialog.setArguments(args);

        return dialog;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_human_vs_human, null);
        ButterKnife.bind(this, view);

        return new AlertDialog.Builder(getActivity())
                .setView(view)
                .setTitle(getString(R.string.human_vs_human))
                .setPositiveButton(R.string.start, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        final TicTacToeGame.Player player = playerChoiceSection.getPlayer();
                        Bundle bundle = new Bundle();
                        bundle.putSerializable(Constants.GameActivity.EXTRA_GAME_MODE, GameActivity.GameMode.HUMAN_VS_HUMAN);
                        bundle.putSerializable(Constants.GameActivity.EXTRA_FIRST_TURN, player);

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
