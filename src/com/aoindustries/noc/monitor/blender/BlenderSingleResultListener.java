/*
 * Copyright 2012 by AO Industries, Inc.,
 * 7262 Bull Pen Cir, Mobile, Alabama, 36695, U.S.A.
 * All rights reserved.
 */
package com.aoindustries.noc.monitor.blender;

import com.aoindustries.noc.monitor.common.SingleResult;
import com.aoindustries.noc.monitor.common.SingleResultListener;
import java.rmi.RemoteException;

/**
 * @author  AO Industries, Inc.
 */
public class BlenderSingleResultListener implements SingleResultListener {

    final BlenderMonitor monitor;
    final private SingleResultListener wrapped;

    protected BlenderSingleResultListener(BlenderMonitor monitor, SingleResultListener wrapped) {
        this.monitor = monitor;
        this.wrapped = wrapped;
    }

    @Override
    public void singleResultUpdated(SingleResult singleResult) throws RemoteException {
        wrapped.singleResultUpdated(singleResult);
    }

    @Override
    public boolean equals(Object O) {
        if(O==null) return false;
        if(!(O instanceof SingleResultListener)) return false;

        // Unwrap this
        SingleResultListener thisSingleResultListener = BlenderSingleResultListener.this;
        while(thisSingleResultListener instanceof BlenderSingleResultListener) thisSingleResultListener = ((BlenderSingleResultListener)thisSingleResultListener).wrapped;

        // Unwrap other
        SingleResultListener otherSingleResultListener = (SingleResultListener)O;
        while(otherSingleResultListener instanceof BlenderSingleResultListener) otherSingleResultListener = ((BlenderSingleResultListener)otherSingleResultListener).wrapped;

        // Check equals
        return thisSingleResultListener.equals(otherSingleResultListener);
    }

    @Override
    public int hashCode() {
        return wrapped.hashCode();
    }
}
