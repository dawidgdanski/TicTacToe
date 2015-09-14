package pl.dawidgdanski.tictactoe.ui.fragment;

import android.app.Activity;
import android.support.v4.app.Fragment;

abstract class ContractFragment<Contract> extends Fragment {

    private Contract contract;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        initializeContract(activity);
    }
    
    @Override
    public void onDetach() {
        super.onDetach();
        contract = null;
    }

    public final Contract getContract() {
        if(contract == null) {
            initializeContract(getActivity());
        }

        return contract;
    }

    private void initializeContract(Activity activity) {
        try {
            contract = (Contract) activity;
        } catch (ClassCastException e) {
            throw new IllegalStateException("Activity does not implement Contract interface");
        }
    }
}
