/*
 * Copyright 2012 by AO Industries, Inc.,
 * 7262 Bull Pen Cir, Mobile, Alabama, 36695, U.S.A.
 * All rights reserved.
 */
package com.aoindustries.noc.monitor.blender;

import com.aoindustries.noc.monitor.common.TableMultiResult;
import com.aoindustries.noc.monitor.common.TableMultiResultListener;
import java.rmi.RemoteException;

/**
 * @author  AO Industries, Inc.
 */
public class BlenderTableMultiResultListener<R extends TableMultiResult> implements TableMultiResultListener<R> {

    final BlenderMonitor monitor;
    final private TableMultiResultListener<R> wrapped;

    protected BlenderTableMultiResultListener(BlenderMonitor monitor, TableMultiResultListener<R> wrapped) {
        this.monitor = monitor;
        this.wrapped = wrapped;
    }

    @Override
    public void tableMultiResultAdded(R multiTableResult) throws RemoteException {
        wrapped.tableMultiResultAdded(multiTableResult);
    }

    @Override
    public void tableMultiResultRemoved(R multiTableResult) throws RemoteException {
        wrapped.tableMultiResultRemoved(multiTableResult);
    }

    @Override
    public boolean equals(Object O) {
        if(O==null) return false;
        if(!(O instanceof TableMultiResultListener<?>)) return false;

        // Unwrap this
        TableMultiResultListener<?> thisTableMultiResultListener = BlenderTableMultiResultListener.this;
        while(thisTableMultiResultListener instanceof BlenderTableMultiResultListener<?>) thisTableMultiResultListener = ((BlenderTableMultiResultListener<?>)thisTableMultiResultListener).wrapped;

        // Unwrap other
        TableMultiResultListener<?> otherTableMultiResultListener = (TableMultiResultListener<?>)O;
        while(otherTableMultiResultListener instanceof BlenderTableMultiResultListener<?>) otherTableMultiResultListener = ((BlenderTableMultiResultListener<?>)otherTableMultiResultListener).wrapped;

        // Check equals
        return thisTableMultiResultListener.equals(otherTableMultiResultListener);
    }

    @Override
    public int hashCode() {
        return wrapped.hashCode();
    }
}
