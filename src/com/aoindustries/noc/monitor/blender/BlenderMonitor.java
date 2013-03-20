/*
 * Copyright 2012 by AO Industries, Inc.,
 * 7262 Bull Pen Cir, Mobile, Alabama, 36695, U.S.A.
 * All rights reserved.
 */
package com.aoindustries.noc.monitor.blender;

import com.aoindustries.noc.monitor.common.Monitor;
import com.aoindustries.noc.monitor.common.Node;
import com.aoindustries.noc.monitor.common.RootNode;
import com.aoindustries.noc.monitor.common.SingleResultListener;
import com.aoindustries.noc.monitor.common.SingleResultNode;
import com.aoindustries.noc.monitor.common.TableMultiResult;
import com.aoindustries.noc.monitor.common.TableMultiResultListener;
import com.aoindustries.noc.monitor.common.TableMultiResultNode;
import com.aoindustries.noc.monitor.common.TableResultListener;
import com.aoindustries.noc.monitor.common.TableResultNode;
import com.aoindustries.noc.monitor.common.TreeListener;
import com.aoindustries.util.AoCollections;
import com.aoindustries.util.IdentityKey;
import com.aoindustries.util.concurrent.ExecutorService;
import java.io.IOException;
import java.rmi.RemoteException;
import java.sql.SQLException;
import java.util.Collection;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.UUID;
import java.util.WeakHashMap;
import java.util.concurrent.TimeUnit;

/**
 * Wraps multiple monitors presenting them as a single monitor, completely hides
 * both the monitors from the caller and the callbacks from the monitors.  If at
 * least one monitor is available, the errors will not be propagated to the
 * caller.
 *
 * @author  AO Industries, Inc.
 */
public class BlenderMonitor implements Monitor {

    private static final long DEFAULT_TIMEOUT = 15;
    private static final TimeUnit DEFAULT_TIMEOUT_UNIT = TimeUnit.SECONDS;

    private final List<Monitor> wrapped;
    private final long timeout;
    private final TimeUnit timeoutUnit;

    // TODO: Create a way to stop monitors, and release the executor service when stopped.
    private final ExecutorService executorService = ExecutorService.newInstance();

    /**
     * Wraps the provided monitors, uses the default timeout.
     */
    public BlenderMonitor(Collection<Monitor> wrapped) {
        this(wrapped, DEFAULT_TIMEOUT, DEFAULT_TIMEOUT_UNIT);
    }

    /**
     * Wraps the provided monitors, uses the provided timeout.
     */
    public BlenderMonitor(Collection<Monitor> wrapped, long timeout, TimeUnit timeoutUnit) {
        this.wrapped = AoCollections.unmodifiableCopyList(wrapped);
        this.timeout = timeout;
        this.timeoutUnit = timeoutUnit;
    }

    /**
     * Gets the root node for the given locale, username, and password.  May
     * reuse existing root nodes.  Calls login on each of the wrapped monitors,
     * returning success if at least one of the wrapped root nodes is successful.
     *
     * When a root node fails initial login, it will still be connected to for
     * subsequent calls.
     */
    @Override
    public BlenderRootNode login(Locale locale, String username, String password) throws RemoteException, IOException, SQLException {
        
        RootNode wrappedRootNode = getWrapped().login(locale, username, password);
        return wrapRootNode(wrappedRootNode, wrappedRootNode.getUuid());
    }
}
