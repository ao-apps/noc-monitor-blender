/*
 * Copyright 2012 by AO Industries, Inc.,
 * 7262 Bull Pen Cir, Mobile, Alabama, 36695, U.S.A.
 * All rights reserved.
 */
package com.aoindustries.noc.monitor.blender;

import com.aoindustries.noc.monitor.common.TableResult;
import com.aoindustries.noc.monitor.common.TableResultListener;
import com.aoindustries.noc.monitor.common.TableResultNode;
import java.rmi.RemoteException;
import java.util.Collection;

/**
 * @author  AO Industries, Inc.
 */
public class BlenderTableResultNode extends BlenderNode implements TableResultNode {

    final private Collection<TableResultNode> wrapped;

    BlenderTableResultNode(Collection<TableResultNode> wrapped) {
        super(wrapped);
        BlenderMonitor.checkNoswing();
        this.wrapped = wrapped;
    }

    @Override
    public void addTableResultListener(TableResultListener tableResultListener) throws RemoteException {
        BlenderMonitor.checkNoswing();
        wrapped.addTableResultListener(tableResultListener);
    }

    @Override
    public void removeTableResultListener(TableResultListener tableResultListener) throws RemoteException {
        BlenderMonitor.checkNoswing();
        wrapped.removeTableResultListener(tableResultListener);
    }

    @Override
    public TableResult getLastResult() throws RemoteException {
        BlenderMonitor.checkNoswing();
        return wrapped.getLastResult();
    }
}
