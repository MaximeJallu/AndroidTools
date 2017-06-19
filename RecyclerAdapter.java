package com.oxylane.android.cubeinstore.ui.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.oxylane.android.cubeinstore.data.interfaces.IBaseCommunication;
import com.oxylane.android.cubeinstore.data.interfaces.IViewType;
import com.oxylane.android.cubeinstore.ui.fragment.interfaces.IAdapterChanged;
import com.oxylane.android.cubeinstore.ui.viewholder.RecyclerViewHolder;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Maxime Jallu
 * @since 03/05/2017
 * <p>
 * Create for CubeInStore - Android (Decathlon)
 * <p>
 * Use this Class for : <br/>
 * ... {DOCUMENTATION}
 */

public class RecyclerAdapter<T> extends RecyclerView.Adapter<RecyclerViewHolder<T>> {

    private List<T> mTList;
    private ViewHolderFactory<T> mFactory;
    private IAdapterChanged mAdapterChanged;

    public RecyclerAdapter(ViewHolderFactory<T> factory){
        this(new ArrayList<>(), factory);
    }

    public RecyclerAdapter(List<T> TList, ViewHolderFactory<T> factory) {
        mTList = TList;
        mFactory = factory;
    }

    public void setFactory(ViewHolderFactory<T> factory) {
        mFactory = factory;
    }

    @Override
    public RecyclerViewHolder<T> onCreateViewHolder(ViewGroup parent, int viewType) {
        return mFactory.createVH(LayoutInflater.from(parent.getContext())
                .inflate(mFactory.getLayoutRes(viewType), parent, false), viewType);
    }


    @Override
    public void onBindViewHolder(RecyclerViewHolder<T> holder, int position) {
        holder.setItem(getItem(position));
        holder.setBound(false);
        holder.bind(holder.getItem());
        holder.setBound(true);
    }

    @Override
    public int getItemCount() {
        return mTList != null ? mTList.size() : 0;
    }

    /**
     * Get Item
     * @param position position founded
     * @return instance to position
     */
    public T getItem(int position){
        return mTList.get(position);
    }

    @Override
    public int getItemViewType(int position) {
        if(getItem(position) != null && getItem(position) instanceof IViewType){
            return ((IViewType) getItem(position)).getItemViewType();
        }
        return super.getItemViewType(position);
    }

    protected void callChangedListener(){
        if (mAdapterChanged != null){
            mAdapterChanged.onItemCountChange(getItemCount());
        }
    }

    public void setOnAdapterChangedListener(IAdapterChanged adapterChanged) {
        mAdapterChanged = adapterChanged;
    }

    protected void setCommunication(IBaseCommunication communication){
        mFactory.setCommunication(communication);
    }

    /**
     * Inserts the specified element at the specified position in this list (optional operation).
     * Shifts the element currently at that position (if any) and any subsequent elements to the right (adds one to their indices).
     * @param item element to be inserted
     */
    public void addItem(T item){
        if (mTList != null) {
            mTList.add(item);
            notifyItemInserted(mTList.size());
        }
    }

    /**
     * Inserts the specified element at the specified position in this list (optional operation).
     * Shifts the element currently at that position (if any) and any subsequent elements to the right (adds one to their indices).
     * @param item element to be inserted
     * @param position index at which the specified element is to be inserted
     */
    public void addItem(T item, int position){
        if (mTList != null) {
            position = Math.min(position, mTList.size());
            mTList.add(position, item);
            notifyItemInserted(position);
        }
    }

    /**
     * Inserts the specified element at the specified position in this list (optional operation).
     * Shifts the element currently at that position (if any) and any subsequent elements to the right (adds one to their indices).
     * @param position the index of the element to be removed
     */
    public void removeItem(int position){
        if (mTList != null) {
            mTList.remove(position);
            notifyItemRemoved(position);
        }
    }

    /**
     * Set new list items and notifyDataSetChanged()
     * @link notifyDataSetChanged
     * @param list new instance items list for bind views
     */
    public void updateItems(@NonNull List<T> list){
        mTList = list;
        notifyDataSetChanged();
    }

    /**
     *
     * @return instance items list
     */
    public List<T> getTList() {
        return mTList;
    }
}
