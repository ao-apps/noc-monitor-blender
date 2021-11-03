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
 * along with noc-monitor-blender.  If not, see <https://www.gnu.org/licenses/>.
 */
package com.aoindustries.noc.monitor.blender;

import com.aoindustries.noc.monitor.common.AlertLevelChange;
import com.aoindustries.noc.monitor.common.Node;
import com.aoindustries.noc.monitor.common.TreeListener;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @author  AO Industries, Inc.
 */
public class BlenderTreeListener implements TreeListener {

	final BlenderMonitor monitor;
	private final TreeListener wrapped;

	protected BlenderTreeListener(BlenderMonitor monitor, TreeListener wrapped) {
		this.monitor = monitor;
		this.wrapped = wrapped;
	}

	@Override
	public void nodeAdded() throws RemoteException {
		wrapped.nodeAdded();
	}

	@Override
	public void nodeRemoved() throws RemoteException {
		wrapped.nodeRemoved();
	}

	@Override
	public void nodeAlertLevelChanged(List<AlertLevelChange> changes) throws RemoteException {
		List<AlertLevelChange> wrappedChanges = new ArrayList<>(changes.size());
		for(AlertLevelChange change : changes) {
			Node node = change.getNode();
			wrappedChanges.add(change.setNode(monitor.wrapNode(node, node.getUuid())));
		}
		wrappedChanges = Collections.unmodifiableList(wrappedChanges);
		wrapped.nodeAlertLevelChanged(wrappedChanges);
	}

	@Override
	public boolean equals(Object obj) {
		if(!(obj instanceof TreeListener)) return false;

		// Unwrap this
		TreeListener thisTreeListener = BlenderTreeListener.this;
		while(thisTreeListener instanceof BlenderTreeListener) thisTreeListener = ((BlenderTreeListener)thisTreeListener).wrapped;

		// Unwrap other
		TreeListener otherTreeListener = (TreeListener)obj;
		while(otherTreeListener instanceof BlenderTreeListener) otherTreeListener = ((BlenderTreeListener)otherTreeListener).wrapped;

		// Check equals
		return thisTreeListener.equals(otherTreeListener);
	}

	@Override
	public int hashCode() {
		return wrapped.hashCode();
	}
}
