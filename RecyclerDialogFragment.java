package com.oxylane.android.cubeinstore.ui.fragment.dialog;

import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.oxylane.android.cubeinstore.R;
import com.oxylane.android.cubeinstore.common.RecyclerItemClickListener;
import com.oxylane.android.cubeinstore.ui.adapter.ArrayRecyclerAdapter;
import com.oxylane.android.cubeinstore.ui.adapter.RecyclerAdapter;
import com.oxylane.android.cubeinstore.ui.dialog.BaseDialogFragment;
import com.oxylane.android.cubeinstore.ui.viewholder.RecyclerViewHolder;
import com.oxylane.android.cubeinstore.util.DividerItemDecoration;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by Maxime on 04/07/2016.
 * <p>
 * Use newInstance(title, showNegativeButton) for receiv new instance Dialog<br/>
 * after use setAdapter(ArrayRecyclerAdapter<T, U>)<br/>
 * </p>
 * @see RecyclerAdapter
 * @param <T> ObjectType for recycler
 */
public final class RecyclerDialogFragment<T> extends BaseDialogFragment {

    public static final String ARG_TITLE = "arg_title";
    public static final String ARG_DISABLE_NEGATIVE = "arg_disable_negative";

    //region BindView
    @BindView(R.id.recyclerview)
    RecyclerView mRecyclerView;
    @BindView(R.id.dialog_title)
    TextView mTitleView;
    //endregion

    //region Fields
    private Unbinder mUnbinder;
    private String mTitle;
    private boolean mIsShowNegative;
    private RecyclerAdapter<T> mAdapter;
    private List<ItemSelected<T>> mSelectCallbacks;
    //endregion

    private RecyclerDialogFragment() {
        mSelectCallbacks = new ArrayList<>();
    }

    /**
     * Default instance
     *
     * @param title
     * @param showNegative
     * @return
     */
    public static <T> RecyclerDialogFragment<T> newInstance(String title, boolean showNegative) {
        RecyclerDialogFragment fragment = new RecyclerDialogFragment();
        Bundle args = new Bundle();
        args.putString(ARG_TITLE, title);
        args.putBoolean(ARG_DISABLE_NEGATIVE, showNegative);
        fragment.setArguments(args);
        return fragment;
    }

    //region LifeCycle
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            if (getArguments().containsKey(ARG_TITLE)) {
                mTitle = getArguments().getString(ARG_TITLE);
            }
            if (getArguments().containsKey(ARG_DISABLE_NEGATIVE)) {
                mIsShowNegative = getArguments().getBoolean(ARG_DISABLE_NEGATIVE);
            }
        }
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mRecyclerView.addItemDecoration(new DividerItemDecoration(getContext(), DividerItemDecoration.LIST_VERTICAL));

        /*Renvois l'item séléctionné à la vue*/
        RecyclerItemClickListener.affectOnItemClick(mRecyclerView, (position, view1) -> {
            for (ItemSelected<T> selectedCallback : mSelectCallbacks) {
                selectedCallback.onSelected(mAdapter.getItem(position));
            }
            dismiss();
        });
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder dialog = new AlertDialog.Builder(getContext(), R.style.DialogTheme);

        if (mIsShowNegative) {
            dialog.setNegativeButton(R.string.no, (dialog1, which) -> dismiss());
        }

        View view = LayoutInflater.from(getContext())
                .inflate(R.layout.dialog_fragment_recycler, null, false);
        mUnbinder = ButterKnife.bind(this, view);

        if (mTitle != null && !mTitle.isEmpty()) {
            mTitleView.setText(mTitle);
        }

        dialog.setView(view);

        initView();

        return dialog.create();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mUnbinder.unbind();
    }
    //endregion

    //region Private Methods
    public void setAdapter(@NonNull RecyclerAdapter<T> adapter) {
        mAdapter = adapter;
    }

    public void addCallbacks(ItemSelected<T> callback) {
        mSelectCallbacks.add(callback);
    }

    private void initView() {

        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mRecyclerView.addItemDecoration(new DividerItemDecoration(getContext(), DividerItemDecoration.LIST_VERTICAL));

        /*Renvois l'item séléctionné à la vue*/
        RecyclerItemClickListener.affectOnItemClick(mRecyclerView, (position, view1) -> {
            for (ItemSelected<T> selectedCallback : mSelectCallbacks) {
                selectedCallback.onSelected(mAdapter.getItem(position));
            }
            dismiss();
        });

        mRecyclerView.setAdapter(mAdapter);
    }
    //endregion

    //region Inner Interface

    /**
     * OnItem Selected Interface
     * @param <T>
     */
    public interface ItemSelected<T> {
        /**
         * On Item Selected
         * @param item
         */
        void onSelected(T item);
    }
    //endregion
}
