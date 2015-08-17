package by.jylilov.grapheditor.models;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

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

    public void addAll(Collection elementCollection) {
        for (Object element: elementCollection) {
            if (element instanceof VertexModel) {
                add((VertexModel)element);
            } else if (element instanceof EdgeModel) {
                add((EdgeModel)element);
            }
        }
    }

    public List<EdgeModel> getIncidentEdgeList(VertexModel vertex) {
        List<EdgeModel> answer = new ArrayList<>();
        for (EdgeModel edge: edgeList) {
            if (edge.getStartVertex() == vertex || edge.getEndVertex() == vertex) {
                answer.add(edge);
            }
        }
        return answer;
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
