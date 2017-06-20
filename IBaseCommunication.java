package com.oxylane.android.cubeinstore.data.interfaces;

import android.annotation.SuppressLint;

/**
 * @author Maxime Jallu
 * @since 10/05/2017
 * Use this Class for : <br/>
 * ... {DOCUMENTATION}
 */
@SuppressLint("NewApi")
public interface IBaseCommunication {

    default void onDeleteClicked(int position) {
        /*nothing*/
    }

    default void onEditClicked(int position) {
        /*nothing*/
    }
}
