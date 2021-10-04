/*
 * noc-monitor-blender - Combines data from multiple monitoring points.
 * Copyright (C) 2012, 2020, 2021  AO Industries, Inc.
 *     support@aoindustries.com
 *     7262 Bull Pen Cir
 *     Mobile, AL 36695
 *
 * This file is part of noc-monitor-blender.
 *
 * noc-monitor-blender is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * noc-monitor-blender is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with noc-monitor-blender.  If not, see <http://www.gnu.org/licenses/>.
 */
package com.aoindustries.noc.monitor.blender;

import com.aoindustries.noc.monitor.common.MonitoringPoint;
import com.aoindustries.noc.monitor.common.NodeSnapshot;
import com.aoindustries.noc.monitor.common.RootNode;
import com.aoindustries.noc.monitor.common.TreeListener;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.SortedSet;

/**
 * @author  AO Industries, Inc.
 */
public class BlenderRootNode extends BlenderNode implements RootNode {

	private final RootNode wrapped;

	protected BlenderRootNode(BlenderMonitor monitor, RootNode wrapped) {
		super(monitor, wrapped);
		this.wrapped = wrapped;
	}

	@Override
	public void addTreeListener(TreeListener treeListener) throws RemoteException {
		wrapped.addTreeListener(monitor.wrapTreeListener(treeListener));
	}

	@Override
	public void removeTreeListener(TreeListener treeListener) throws RemoteException {
		wrapped.removeTreeListener(monitor.wrapTreeListener(treeListener));
	}

	@Override
	public NodeSnapshot getSnapshot() throws RemoteException {
		return wrapSnapshot(monitor, wrapped.getSnapshot());
	}

	/**
	 * Recursively wraps the nodes of the snapshot.
	 */
	private static NodeSnapshot wrapSnapshot(final BlenderMonitor monitor, final NodeSnapshot snapshot) throws RemoteException {
		List<NodeSnapshot> newChildren;
		{
			List<NodeSnapshot> children = snapshot.getChildren();
			int size = children.size();
			if(size==0) {
				newChildren = Collections.emptyList();
			} else if(size==1) {
				newChildren = Collections.singletonList(wrapSnapshot(monitor, children.get(0)));
			} else {
				newChildren = new ArrayList<NodeSnapshot>(size);
				for(NodeSnapshot child : children) {
					newChildren.add(wrapSnapshot(monitor, child));
				}
			}
		}
		return new NodeSnapshot(
			monitor.wrapNode(snapshot.getNode(), snapshot.getUuid()),
			newChildren,
			snapshot.getAlertLevel(),
			snapshot.getAlertMessage(),
			snapshot.getAllowsChildren(),
			snapshot.getId(),
			snapshot.getLabel(),
			snapshot.getUuid()
		);
	}

	@Override
	public SortedSet<MonitoringPoint> getMonitoringPoints() throws RemoteException {
		return wrapped.getMonitoringPoints();
	}
}
