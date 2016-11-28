package pl.edu.agh.amwj.utils;

import com.google.common.graph.AbstractGraph;
import com.google.common.graph.ElementOrder;
import com.google.common.graph.GraphBuilder;
import com.google.common.graph.MutableGraph;
import pl.edu.agh.amwj.value.HeapValue;

import java.util.Set;

/**
 * Created by P on 27.11.2016.
 */
public class GCGraph extends AbstractGraph<HeapValue> implements MutableGraph<HeapValue> {
    private MutableGraph<HeapValue> graph;

    public GCGraph() {
        graph = GraphBuilder.directed().build();
    }

    public boolean addNode(HeapValue node) {
        return graph.addNode(node);
    }

    public boolean putEdge(HeapValue nodeU, HeapValue nodeV) {
        return graph.putEdge(nodeU, nodeV);
    }

    public boolean removeEdge(Object nodeU, Object nodeV) {
        return graph.removeEdge(nodeU, nodeV);
    }

    public boolean removeNode(Object node) {
        return graph.removeNode(node);
    }

    public Set<HeapValue> nodes() {
        return graph.nodes();
    }

    public boolean isDirected() {
        return graph.isDirected();
    }

    public boolean allowsSelfLoops() {
        return graph.allowsSelfLoops();
    }

    public ElementOrder<HeapValue> nodeOrder() {
        return graph.nodeOrder();
    }

    public Set<HeapValue> adjacentNodes(Object o) {
        return graph.adjacentNodes(o);
    }

    public Set<HeapValue> predecessors(Object o) {
        return graph.predecessors(o);
    }

    public Set<HeapValue> successors(Object o) {
        return graph.successors(o);
    }

    public void print() {
        System.out.println(graph);
    }
}
