package com.oxylane.android.cubeinstore.ui.fragment.dialog;

import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.decathlon.geodata.models.SpinnerDialogItem;
import com.decathlon.views.AbsSearchDialogFragment;
import com.decathlon.views.IOnSelectedResetCallback;
import com.oxylane.android.cubeinstore.R;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;


public class SearchDialogFragment<T extends SpinnerDialogItem> extends AbsSearchDialogFragment<T> {

    //region BindViews
    @BindView(R.id.title_search_dialog_input_layout)
    TextInputLayout mTextInputLayout;
    @BindView(R.id.resulat_search_dialog_recycler)
    RecyclerView mRecyclerView;
    private View mCustomView;
    //endregion

    public static <U extends SpinnerDialogItem> SearchDialogFragment<U> newInstance(@Nullable String searchHintTitle, @NonNull List<U> data, @Nullable U selectedValue, String empty) {

        SearchDialogFragment<U> fragment = new SearchDialogFragment<>();
        Bundle bundle = new Bundle();
        if (searchHintTitle != null) {
            bundle.putString(ARG_TITLE, searchHintTitle);
        }
        bundle.putSerializable(ARG_LIST, new ArrayList<>(data));
        if (selectedValue != null) {
            bundle.putParcelable(ARG_SELECTED, selectedValue);
        }
        if (empty != null) {
            bundle.getString(ARG_EMPTY, empty);
        }
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    protected View getCustomView(ViewGroup container) {
        if (mCustomView != null) {
            return mCustomView;
        }
        View root = LayoutInflater.from(getContext()).inflate(R.layout.search_dialog_fragment, container, false);
        ButterKnife.bind(this, root);
        mCustomView = root;
        return root;
    }

    @Override
    protected Dialog createDialog(View customView, IOnSelectedResetCallback callback, boolean emptyChoice) {
        Log.d("LIFECYCLE", "onCreateDialog: call before onCreateView");

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity(), R.style.DialogTheme);
        builder.setView(getCustomView(null));

        if (emptyChoice) {
            builder.setNeutralButton(R.string.reset, (dialog, which) -> callback.onSelectedReset());
        }

        return builder.show();
    }

    @Override
    protected EditText getEditText() {
        return mTextInputLayout.getEditText();
    }

    @Override
    protected RecyclerView getRecyclerView() {
        return mRecyclerView;
    }


}
