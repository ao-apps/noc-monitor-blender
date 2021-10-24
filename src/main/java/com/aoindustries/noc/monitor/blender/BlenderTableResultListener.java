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

import com.aoindustries.noc.monitor.common.TableResult;
import com.aoindustries.noc.monitor.common.TableResultListener;
import java.rmi.RemoteException;

/**
 * @author  AO Industries, Inc.
 */
public class BlenderTableResultListener implements TableResultListener {

	final BlenderMonitor monitor;
	private final TableResultListener wrapped;

	protected BlenderTableResultListener(BlenderMonitor monitor, TableResultListener wrapped) {
		this.monitor = monitor;
		this.wrapped = wrapped;
	}

	@Override
	public void tableResultUpdated(TableResult tableResult) throws RemoteException {
		wrapped.tableResultUpdated(tableResult);
	}

	@Override
	public boolean equals(Object obj) {
		if(!(obj instanceof TableResultListener)) return false;

		// Unwrap this
		TableResultListener thisTableResultListener = BlenderTableResultListener.this;
		while(thisTableResultListener instanceof BlenderTableResultListener) thisTableResultListener = ((BlenderTableResultListener)thisTableResultListener).wrapped;

		// Unwrap other
		TableResultListener otherTableResultListener = (TableResultListener)obj;
		while(otherTableResultListener instanceof BlenderTableResultListener) otherTableResultListener = ((BlenderTableResultListener)otherTableResultListener).wrapped;

		// Check equals
		return thisTableResultListener.equals(otherTableResultListener);
	}

	@Override
	public int hashCode() {
		return wrapped.hashCode();
	}
}
