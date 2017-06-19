package com.oxylane.android.cubeinstore.ioc;

import android.support.annotation.LayoutRes;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author Maxime Jallu
 * @since 09/06/2017
 * <p>
 * Create for CubeInStore - Android (Decathlon)
 * <p>
 * Use this Class for : <br/>
 * ... {DOCUMENTATION}
 */
//@Scope
@Target({ElementType.TYPE, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
public @interface BindLayoutRes {
    /**
     * View ID to which the field will be bound.
     */
    @LayoutRes int value();
}
