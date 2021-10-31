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

import com.aoindustries.noc.monitor.common.SingleResult;
import com.aoindustries.noc.monitor.common.SingleResultListener;
import java.rmi.RemoteException;

/**
 * @author  AO Industries, Inc.
 */
public class BlenderSingleResultListener implements SingleResultListener {

	final BlenderMonitor monitor;
	private final SingleResultListener wrapped;

	protected BlenderSingleResultListener(BlenderMonitor monitor, SingleResultListener wrapped) {
		this.monitor = monitor;
		this.wrapped = wrapped;
	}

	@Override
	public void singleResultUpdated(SingleResult singleResult) throws RemoteException {
		wrapped.singleResultUpdated(singleResult);
	}

	@Override
	public boolean equals(Object obj) {
		if(!(obj instanceof SingleResultListener)) return false;

		// Unwrap this
		SingleResultListener thisSingleResultListener = BlenderSingleResultListener.this;
		while(thisSingleResultListener instanceof BlenderSingleResultListener) thisSingleResultListener = ((BlenderSingleResultListener)thisSingleResultListener).wrapped;

		// Unwrap other
		SingleResultListener otherSingleResultListener = (SingleResultListener)obj;
		while(otherSingleResultListener instanceof BlenderSingleResultListener) otherSingleResultListener = ((BlenderSingleResultListener)otherSingleResultListener).wrapped;

		// Check equals
		return thisSingleResultListener.equals(otherSingleResultListener);
	}

	@Override
	public int hashCode() {
		return wrapped.hashCode();
	}
}
