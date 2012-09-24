/*
 * Copyright 2012 by AO Industries, Inc.,
 * 7262 Bull Pen Cir, Mobile, Alabama, 36695, U.S.A.
 * All rights reserved.
 */
package com.aoindustries.noc.monitor.blender;

import com.aoindustries.noc.monitor.common.TableMultiResult;
import com.aoindustries.noc.monitor.common.TableMultiResultListener;
import com.aoindustries.noc.monitor.common.TableMultiResultNode;
import java.rmi.RemoteException;
import java.util.List;

/**
 * @author  AO Industries, Inc.
 */
public class BlenderTableMultiResultNode<R extends TableMultiResult> extends BlenderNode implements TableMultiResultNode<R> {

    final private TableMultiResultNode<R> wrapped;

    BlenderTableMultiResultNode(TableMultiResultNode<R> wrapped) {
        super(wrapped);
        BlenderMonitor.checkNoswing();
        this.wrapped = wrapped;
    }

    @Override
    public void addTableMultiResultListener(TableMultiResultListener<? super R> tableMultiResultListener) throws RemoteException {
        BlenderMonitor.checkNoswing();
        wrapped.addTableMultiResultListener(tableMultiResultListener);
    }

    @Override
    public void removeTableMultiResultListener(TableMultiResultListener<? super R> tableMultiResultListener) throws RemoteException {
        BlenderMonitor.checkNoswing();
        wrapped.removeTableMultiResultListener(tableMultiResultListener);
    }

    @Override
    public List<?> getColumnHeaders() throws RemoteException {
        BlenderMonitor.checkNoswing();
        return wrapped.getColumnHeaders();
    }

    @Override
    public List<? extends R> getResults() throws RemoteException {
        BlenderMonitor.checkNoswing();
        return wrapped.getResults();
    }
}
