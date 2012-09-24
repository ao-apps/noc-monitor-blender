/*
 * Copyright 2012 by AO Industries, Inc.,
 * 7262 Bull Pen Cir, Mobile, Alabama, 36695, U.S.A.
 * All rights reserved.
 */
package com.aoindustries.noc.monitor.blender;

import com.aoindustries.noc.monitor.common.MonitoringPoint;
import com.aoindustries.noc.monitor.common.NodeSnapshot;
import com.aoindustries.noc.monitor.common.RootNode;
import com.aoindustries.noc.monitor.common.TreeListener;
import java.rmi.RemoteException;
import java.util.SortedSet;

/**
 * @author  AO Industries, Inc.
 */
public class BlenderRootNode extends BlenderNode implements RootNode {

    final private RootNode wrapped;

    BlenderRootNode(RootNode wrapped) {
        super(wrapped);
        BlenderMonitor.checkNoswing();
        this.wrapped = wrapped;
    }

    @Override
    public void addTreeListener(TreeListener treeListener) throws RemoteException {
        BlenderMonitor.checkNoswing();
        wrapped.addTreeListener(treeListener);
    }

    @Override
    public void removeTreeListener(TreeListener treeListener) throws RemoteException {
        BlenderMonitor.checkNoswing();
        wrapped.removeTreeListener(treeListener);
    }

    @Override
    public NodeSnapshot getSnapshot() throws RemoteException {
        BlenderMonitor.checkNoswing();
        NodeSnapshot nodeSnapshot = wrapped.getSnapshot();
        wrapSnapshot(nodeSnapshot);
        return nodeSnapshot;
    }

    /**
     * Recursively wraps the nodes of the snapshot.
     */
    private static void wrapSnapshot(NodeSnapshot snapshot) {
        snapshot.setNode(BlenderNode.wrap(snapshot.getNode()));
        for(NodeSnapshot child : snapshot.getChildren()) wrapSnapshot(child);
    }

    @Override
    public SortedSet<MonitoringPoint> getMonitoringPoints() throws RemoteException {
        BlenderMonitor.checkNoswing();
        return wrapped.getMonitoringPoints();
    }
}
