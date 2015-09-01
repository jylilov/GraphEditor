package by.jylilov.grapheditor.models;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class GraphModel implements Serializable {
    private final List<VertexModel> vertexList = new ArrayList<>();
    private final List<EdgeModel> edgeList = new ArrayList<>();

    public void add(VertexModel vertex) {
        if (!vertexList.contains(vertex)) {
            vertexList.add(vertex);
        }
    }

    public void add(EdgeModel edge) {
        if (!edgeList.contains(edge)) {
            edgeList.add(edge);
        }
    }

    public void remove(VertexModel vertex) {
        vertexList.remove(vertex);
    }

    public void remove(EdgeModel edge) {
        edgeList.remove(edge);
    }

    public List<EdgeModel> getIncidentEdgeList(VertexModel vertex) {
        return edgeList.stream().filter(edge -> edge.isIncidentTo(vertex)).collect(Collectors.toList());
    }

    public List<VertexModel> getVertexList() {
        return vertexList;
    }

    public List<EdgeModel> getEdgeList() {
        return edgeList;
    }

    public void clear() {
        edgeList.clear();
        vertexList.clear();
    }
}
