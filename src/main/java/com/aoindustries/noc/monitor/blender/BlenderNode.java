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

import com.aoindustries.noc.monitor.common.AlertLevel;
import com.aoindustries.noc.monitor.common.Node;
import com.aoindustries.util.WrappedException;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

/**
 * @author  AO Industries, Inc.
 */
public class BlenderNode implements Node {

	final BlenderMonitor monitor;
	private final Node wrapped;
	private final UUID uuid;

	protected BlenderNode(BlenderMonitor monitor, Node wrapped) {
		this.monitor = monitor;
		this.wrapped = wrapped;
		this.uuid    = UUID.randomUUID();
	}

	@Override
	public BlenderNode getParent() throws RemoteException {
		Node wrappedParent = wrapped.getParent();
		return monitor.wrapNode(wrappedParent, wrappedParent.getUuid());
	}

	@Override
	public List<? extends BlenderNode> getChildren() throws RemoteException {
		List<? extends Node> children = wrapped.getChildren();
		// Wrap
		List<BlenderNode> localWrapped = new ArrayList<>(children.size());
		for(Node child : children) {
			localWrapped.add(monitor.wrapNode(child, child.getUuid()));
		}
		return Collections.unmodifiableList(localWrapped);
	}

	@Override
	public AlertLevel getAlertLevel() throws RemoteException {
		return wrapped.getAlertLevel();
	}

	@Override
	public String getAlertMessage() throws RemoteException {
		return wrapped.getAlertMessage();
	}

	@Override
	public boolean getAllowsChildren() throws RemoteException {
		return wrapped.getAllowsChildren();
	}

	@Override
	public String getId() throws RemoteException {
		return wrapped.getId();
	}

	@Override
	public String getLabel() throws RemoteException {
		return wrapped.getLabel();
	}

	/**
	 * After wrapping, the wrapped node gets a new UUID.
	 */
	@Override
	public UUID getUuid() throws RemoteException {
		return uuid;
	}

	@Override
	public boolean equals(Object obj) {
		if(!(obj instanceof Node)) return false;
		Node other = (Node)obj;
		try {
			return uuid.equals(other.getUuid());
		} catch(RemoteException err) {
			throw new WrappedException(err);
		}
	}

	@Override
	public int hashCode() {
		return uuid.hashCode();
	}

	@Override
	public String toString() {
		try {
			return getLabel();
		} catch(RemoteException err) {
			throw new WrappedException(err);
		}
	}
}
