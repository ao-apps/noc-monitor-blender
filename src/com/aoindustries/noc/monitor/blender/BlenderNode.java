/*
 * Copyright 2012 by AO Industries, Inc.,
 * 7262 Bull Pen Cir, Mobile, Alabama, 36695, U.S.A.
 * All rights reserved.
 */
package com.aoindustries.noc.monitor.blender;

import com.aoindustries.noc.monitor.common.AlertLevel;
import com.aoindustries.noc.monitor.common.SingleResultNode;
import com.aoindustries.noc.monitor.common.Node;
import com.aoindustries.noc.monitor.common.RootNode;
import com.aoindustries.noc.monitor.common.TableMultiResultNode;
import com.aoindustries.noc.monitor.common.TableResultNode;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

/**
 * @author  AO Industries, Inc.
 */
public class BlenderNode implements Node {

    final private Collection<Node> wrapped;
    final private Node wrapped;

    BlenderNode(Node wrapped) {
        BlenderMonitor.checkNoswing();
        this.wrapped = wrapped;
    }

    @Override
    public Node getParent() throws RemoteException {
        BlenderMonitor.checkNoswing();
        return new BlenderNode(wrapped.getParent());
    }

    @Override
    public List<? extends Node> getChildren() throws RemoteException {
        BlenderMonitor.checkNoswing();
        List<? extends Node> children = wrapped.getChildren();
        // Wrap
        List<Node> localWrapped = new ArrayList<Node>(children.size());
        for(Node child : children) {
            localWrapped.add(wrap(child));
        }
        return Collections.unmodifiableList(localWrapped);
    }

    @SuppressWarnings("unchecked")
    static Node wrap(Node node) {
        if(node instanceof SingleResultNode) return new BlenderSingleResultNode((SingleResultNode)node);
        if(node instanceof TableResultNode) return new BlenderTableResultNode((TableResultNode)node);
        if(node instanceof TableMultiResultNode) return new BlenderTableMultiResultNode((TableMultiResultNode)node);
        if(node instanceof RootNode) return new BlenderRootNode((RootNode)node);
        return new BlenderNode(node);
    }

    @Override
    public AlertLevel getAlertLevel() throws RemoteException {
        BlenderMonitor.checkNoswing();
        return wrapped.getAlertLevel();
    }

    @Override
    public String getAlertMessage() throws RemoteException {
        BlenderMonitor.checkNoswing();
        return wrapped.getAlertMessage();
    }

    @Override
    public boolean getAllowsChildren() throws RemoteException {
        BlenderMonitor.checkNoswing();
        return wrapped.getAllowsChildren();
    }

    @Override
    public String getId() throws RemoteException {
        BlenderMonitor.checkNoswing();
        return wrapped.getId();
    }

    @Override
    public String getLabel() throws RemoteException {
        BlenderMonitor.checkNoswing();
        return wrapped.getLabel();
    }
    
    @Override
    public boolean equals(Object O) {
        if(O==null) return false;
        if(!(O instanceof Node)) return false;

        // Unwrap this
        Node thisNode = this;
        while(thisNode instanceof BlenderNode) thisNode = ((BlenderNode)thisNode).wrapped;

        // Unwrap other
        Node otherNode = (Node)O;
        while(otherNode instanceof BlenderNode) otherNode = ((BlenderNode)otherNode).wrapped;

        // Check equals
        return thisNode.equals(otherNode);
    }

    @Override
    public int hashCode() {
        BlenderMonitor.checkNoswing();
        return wrapped.hashCode();
    }
}
