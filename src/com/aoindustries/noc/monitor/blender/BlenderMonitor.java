/*
 * Copyright 2012 by AO Industries, Inc.,
 * 7262 Bull Pen Cir, Mobile, Alabama, 36695, U.S.A.
 * All rights reserved.
 */
package com.aoindustries.noc.monitor.blender;

import com.aoindustries.noc.monitor.common.Monitor;
import com.aoindustries.noc.monitor.common.RootNode;
import java.io.IOException;
import java.rmi.RemoteException;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.Locale;

/**
 * @author  AO Industries, Inc.
 */
public class BlenderMonitor implements Monitor {

    final private Monitor[] wrapped;

    public BlenderMonitor(Monitor ... wrapped) {
        this.wrapped = Arrays.copyOf(wrapped, wrapped.length);
    }

    @Override
    public RootNode login(Locale locale, String username, String password) throws RemoteException, IOException, SQLException {
        return new BlenderRootNode(wrapped.login(locale, username, password));
    }
}
