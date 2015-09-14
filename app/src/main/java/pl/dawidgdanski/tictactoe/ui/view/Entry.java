package pl.dawidgdanski.tictactoe.ui.view;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.TypedArray;
import android.os.Build;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.widget.LinearLayout;
import android.widget.TextView;

import butterknife.Bind;
import butterknife.ButterKnife;
import pl.dawidgdanski.tictactoe.R;

public class Entry extends LinearLayout {

    @Bind(R.id.key)
    TextView keyText;

    @Bind(R.id.value)
    TextView valueText;

    private String keyString;

    private String valueString;

    public Entry(Context context) {
        super(context);
        throw new UnsupportedOperationException();
    }

    public Entry(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public Entry(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initializeView(attrs);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public Entry(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        initializeView(attrs);
    }

    private void initializeView(AttributeSet attrs) {
        Context context = getContext();

        inflate(context, R.layout.merge_entry, this);

        TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.Entry, 0, 0);

        keyString = array.getString(R.styleable.Entry_key_text);

        valueString = array.getString(R.styleable.Entry_value_text);

        array.recycle();
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        ButterKnife.bind(this, this);
        fillTexts();
    }

    public void setKeyText(final String text) {
        if(! TextUtils.isEmpty(text)) {
            keyText.setText(text);
        }
    }

    public void setValueText(final String text) {
        if(! TextUtils.isEmpty(text)) {
            valueText.setText(text);
        }
    }

    private void fillTexts() {
        keyText.setText(keyString);
        valueText.setText(valueString);
    }
}
