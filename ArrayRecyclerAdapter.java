package com.oxylane.android.cubeinstore.ui.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;

import com.oxylane.android.cubeinstore.ui.viewholder.RecyclerViewHolder;

import java.util.List;

/**
 * @author Maxime Jallu
 * @since 30/06/2016
 *
 * Create for CubeInStore - Android (Decathlon)
 *
 * Use this Class for : <br/>
 * Crée un adapteur de recycler view de base
 * @param <T> Type d'item de la liste
 * @param <U> Type de ViewHolder doit extends de RecyclerViewHolder
 */
public abstract class ArrayRecyclerAdapter<T, U extends RecyclerViewHolder<T>> extends RecyclerView.Adapter<U> {

    private List<T> mTList;

    /**
     * Constructor
     * @param TList list items for binding views
     */
    public ArrayRecyclerAdapter(@NonNull final List<T> TList) {
        mTList = TList;
    }

    @Override
    public void onBindViewHolder(U holder, int position) {
        holder.bind(getItem(position));
    }

    @Override
    public int getItemCount() {
        return mTList.size();
    }

    /**
     * Get Item
     * @param position position founded
     * @return instance to position
     */
    public T getItem(int position){
        return mTList.get(position);
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
