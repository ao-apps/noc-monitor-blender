/*
 * Copyright 2012, 2020 by AO Industries, Inc.,
 * 7262 Bull Pen Cir, Mobile, Alabama, 36695, U.S.A.
 * All rights reserved.
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
	final private TableResultListener wrapped;

	protected BlenderTableResultListener(BlenderMonitor monitor, TableResultListener wrapped) {
		this.monitor = monitor;
		this.wrapped = wrapped;
	}

	@Override
	public void tableResultUpdated(TableResult tableResult) throws RemoteException {
		wrapped.tableResultUpdated(tableResult);
	}

	@Override
	public boolean equals(Object O) {
		if(O==null) return false;
		if(!(O instanceof TableResultListener)) return false;

		// Unwrap this
		TableResultListener thisTableResultListener = BlenderTableResultListener.this;
		while(thisTableResultListener instanceof BlenderTableResultListener) thisTableResultListener = ((BlenderTableResultListener)thisTableResultListener).wrapped;

		// Unwrap other
		TableResultListener otherTableResultListener = (TableResultListener)O;
		while(otherTableResultListener instanceof BlenderTableResultListener) otherTableResultListener = ((BlenderTableResultListener)otherTableResultListener).wrapped;

		// Check equals
		return thisTableResultListener.equals(otherTableResultListener);
	}

	@Override
	public int hashCode() {
		return wrapped.hashCode();
	}
}
