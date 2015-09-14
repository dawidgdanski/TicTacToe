package pl.dawidgdanski.tictactoe.ui.view;

import android.content.Context;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.AttributeSet;
import android.util.SparseArray;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import butterknife.Bind;
import butterknife.ButterKnife;
import pl.dawidgdanski.tictactoe.R;
import pl.dawidgdanski.tictactoe.game.TicTacToeGame;

public class PlayerChoiceSection extends RadioGroup implements RadioGroup.OnCheckedChangeListener {

    @Bind(R.id.left_choice)
    RadioButton playerOButton;

    @Bind(R.id.right_choice)
    RadioButton playerXButton;

    public PlayerChoiceSection(Context context) {
        super(context);
        initializeView();
    }

    public PlayerChoiceSection(Context context, AttributeSet attrs) {
        super(context, attrs);
        initializeView();
    }

    private void initializeView() {
        inflate(getContext(), R.layout.merge_player_choice_radio_buttons, this);
        setOnCheckedChangeListener(this);
        setSaveEnabled(true);
    }

    @Override
    protected void dispatchSaveInstanceState(SparseArray<Parcelable> container) {
        super.dispatchFreezeSelfOnly(container);
    }

    @Override
    protected void dispatchRestoreInstanceState(SparseArray<Parcelable> container) {
        super.dispatchThawSelfOnly(container);
    }

    @Override
    protected void onRestoreInstanceState(Parcelable state) {
        if (!(state instanceof SavedState)) {
            super.onRestoreInstanceState(state);
            return;
        }

        final SavedState savedState = (SavedState) state;
        super.onRestoreInstanceState(savedState.getSuperState());

        playerOButton.setChecked(savedState.choices[0]);
        playerXButton.setChecked(savedState.choices[1]);
    }

    @Override
    protected Parcelable onSaveInstanceState() {
        Parcelable superState = super.onSaveInstanceState();
        SavedState savedState = new SavedState(superState);

        savedState.choices = new boolean[]{
                playerOButton.isChecked(),
                playerXButton.isChecked()
        };

        return savedState;
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        ButterKnife.bind(this);
        playerOButton.setText(getContext().getString(R.string.player_o));
        playerOButton.setChecked(true);

        playerXButton.setText(getContext().getString(R.string.player_x));
        playerXButton.setChecked(false);
    }

    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {

    }

    public TicTacToeGame.Player getPlayer() {
        return playerOButton.isChecked() ? TicTacToeGame.Player.PLAYER_O : TicTacToeGame.Player.PLAYER_X;
    }

    private static class SavedState extends BaseSavedState {

        private boolean[] choices;

        public static final Creator<SavedState> CREATOR = new Creator<SavedState>() {
            @Override
            public SavedState createFromParcel(Parcel source) {
                return new SavedState(source);
            }

            @Override
            public SavedState[] newArray(int size) {
                return new SavedState[size];
            }
        };

        private SavedState(Parcel source) {
            super(source);
            choices = new boolean[2];
            source.readBooleanArray(choices);
        }

        private SavedState(Parcelable superState) {
            super(superState);
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            super.writeToParcel(dest, flags);

            dest.writeBooleanArray(choices);
        }
    }
}
