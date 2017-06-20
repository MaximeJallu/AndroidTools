package com.oxylane.android.cubeinstore.ui.adapter;

import android.support.annotation.LayoutRes;
import android.util.Log;
import android.view.View;

import com.oxylane.android.cubeinstore.data.interfaces.IBaseCommunication;
import com.oxylane.android.cubeinstore.ui.viewholder.RecyclerViewHolder;

import java.lang.ref.WeakReference;

/**
 * @author Maxime Jallu
 * @since 03/05/2017
 * Use this Class for : <br/>
 * ... {DOCUMENTATION}
 */
public abstract class ViewHolderFactory<T> {

    protected WeakReference<IBaseCommunication> mCommunication;

    void setCommunication(IBaseCommunication communication) {
        mCommunication = new WeakReference<>(communication);
    }

    public final IBaseCommunication getCommunication() {
        return mCommunication.get();
    }

    protected <I extends IBaseCommunication> I getInterfaceCallback(){
        I i = null;
        try {
            //noinspection unchecked
            i = (I) getCommunication();
        }catch (ClassCastException e){
            Log.e("ViewHolderFactory", "getInterfaceCallback: ", e);
        }
        return i;
    }

    abstract protected RecyclerViewHolder<T> createVH(View view, int viewType);

    abstract protected @LayoutRes int getLayoutRes(int viewType);
}
