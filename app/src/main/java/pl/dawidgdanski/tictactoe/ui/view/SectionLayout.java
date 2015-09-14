package pl.dawidgdanski.tictactoe.ui.view;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.TypedArray;
import android.os.Build;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import butterknife.Bind;
import butterknife.ButterKnife;
import pl.dawidgdanski.tictactoe.R;

public class SectionLayout extends LinearLayout {

    private String text;

    @Bind(R.id.header_text)
    TextView headerText;

    public SectionLayout(Context context) {
        super(context);
        throw new UnsupportedOperationException();
    }

    public SectionLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SectionLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initializeView(attrs);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public SectionLayout(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        initializeView(attrs);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();

        View view = inflate(getContext(), R.layout.section_layout_header_content, null);
        addView(view, 0);

        ButterKnife.bind(this, view);

        setValuesOnViews();
    }

    private void setValuesOnViews() {
        headerText.setText(text);
    }

    private void initializeView(final AttributeSet attributeSet) {
        Context context = getContext();
        TypedArray array = context.obtainStyledAttributes(attributeSet, R.styleable.SectionLayout, 0, 0);

        text = array.getString(R.styleable.SectionLayout_header_text);

        if(TextUtils.isEmpty(text)) {
            text = context.getString(R.string.default_header_text);
        }

        array.recycle();
    }
}
