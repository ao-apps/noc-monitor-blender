/*
 * Copyright 2012 by AO Industries, Inc.,
 * 7262 Bull Pen Cir, Mobile, Alabama, 36695, U.S.A.
 * All rights reserved.
 */
package com.aoindustries.noc.monitor.blender;

import com.aoindustries.noc.monitor.common.SingleResultListener;
import com.aoindustries.noc.monitor.common.SingleResultNode;
import com.aoindustries.noc.monitor.common.SingleResult;
import java.rmi.RemoteException;

/**
 * @author  AO Industries, Inc.
 */
public class BlenderSingleResultNode extends BlenderNode implements SingleResultNode {

    final private SingleResultNode wrapped;

    BlenderSingleResultNode(SingleResultNode wrapped) {
        super(wrapped);
        BlenderMonitor.checkNoswing();
        this.wrapped = wrapped;
    }

    @Override
    public void addSingleResultListener(SingleResultListener singleResultListener) throws RemoteException {
        BlenderMonitor.checkNoswing();
        wrapped.addSingleResultListener(singleResultListener);
    }

    @Override
    public void removeSingleResultListener(SingleResultListener singleResultListener) throws RemoteException {
        BlenderMonitor.checkNoswing();
        wrapped.removeSingleResultListener(singleResultListener);
    }

    @Override
    public SingleResult getLastResult() throws RemoteException {
        BlenderMonitor.checkNoswing();
        return wrapped.getLastResult();
    }
}
