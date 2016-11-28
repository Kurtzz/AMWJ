package pl.edu.agh.amwj.utils;

import com.google.common.graph.AbstractGraph;
import com.google.common.graph.ElementOrder;
import com.google.common.graph.GraphBuilder;
import com.google.common.graph.MutableGraph;
import pl.edu.agh.amwj.value.Value;

import java.util.Set;

/**
 * Created by P on 27.11.2016.
 */
public class GCGraph extends AbstractGraph<Value> implements MutableGraph<Value> {
    private MutableGraph<Value> graph;

    public GCGraph() {
        graph = GraphBuilder.directed().build();
    }

    public boolean addNode(Value node) {
        return graph.addNode(node);
    }

    public boolean putEdge(Value nodeU, Value nodeV) {
        return graph.putEdge(nodeU, nodeV);
    }

    public boolean removeEdge(Object nodeU, Object nodeV) {
        return graph.removeEdge(nodeU, nodeV);
    }

    public boolean removeNode(Object node) {
        return graph.removeNode(node);
    }

    public Set<Value> nodes() {
        return graph.nodes();
    }

    public boolean isDirected() {
        return graph.isDirected();
    }

    public boolean allowsSelfLoops() {
        return graph.allowsSelfLoops();
    }

    public ElementOrder<Value> nodeOrder() {
        return graph.nodeOrder();
    }

    public Set<Value> adjacentNodes(Object o) {
        return graph.adjacentNodes(o);
    }

    public Set<Value> predecessors(Object o) {
        return graph.predecessors(o);
    }

    public Set<Value> successors(Object o) {
        return graph.successors(o);
    }

    public void print() {
        System.out.println(graph);
    }
}
