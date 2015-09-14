package pl.dawidgdanski.tictactoe.ui.activity;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import pl.dawidgdanski.tictactoe.Constants;
import pl.dawidgdanski.tictactoe.R;
import pl.dawidgdanski.tictactoe.ui.dialog.CpuVsCpuStartDialog;
import pl.dawidgdanski.tictactoe.ui.dialog.HumanVsCpuStartDialog;
import pl.dawidgdanski.tictactoe.ui.dialog.HumanVsHumanStartDialog;

public class MainActivity extends BaseActivity {

    @Bind(R.id.toolbar)
    Toolbar toolbar;

    @Bind(R.id.button_human_vs_human)
    Button playButtonHumanVsHuman;

    @Bind(R.id.button_human_vs_cpu)
    Button playButtonHumanVsCpu;

    @Bind(R.id.button_cpu_vs_cpu)
    Button playButtonCpuVsCpu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);
        ButterKnife.bind(this);

        setUpActionBar(toolbar);
        setUpActionBarTitle(getString(R.string.tic_tac_toe));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @OnClick(R.id.button_human_vs_human)
    public void onHumanVsHumanGameRequested() {
        HumanVsHumanStartDialog.newInstance().show(getSupportFragmentManager(), Constants.DIALOG_FRAGMENT);
    }

    @OnClick(R.id.button_human_vs_cpu)
    public void onHumanVsCpuGameRequested() {
        HumanVsCpuStartDialog.newInstance().show(getSupportFragmentManager(), Constants.DIALOG_FRAGMENT);
    }

    @OnClick(R.id.button_cpu_vs_cpu)
    public void onCpuVsCpuGameRequested() {
        CpuVsCpuStartDialog.newInstance().show(getSupportFragmentManager(), Constants.DIALOG_FRAGMENT);
    }
}
