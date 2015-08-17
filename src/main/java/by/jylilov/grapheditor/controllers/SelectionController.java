package by.jylilov.grapheditor.controllers;

import java.util.ArrayList;
import java.util.List;

public class SelectionController {
    private List<VertexController> selectedVertices = new ArrayList<>();
    private List<EdgeController> selectedEdges = new ArrayList<>();

    public void select(VertexController vertexController) {
        deselectAll();
        addToSelection(vertexController);
    }

    public void select(EdgeController edgeController) {
        deselectAll();
        addToSelection(edgeController);
    }

    public void deselectAll() {
        for (VertexController vertex: selectedVertices) {
            vertex.getView().deselect();
        }
        for (EdgeController edge: selectedEdges) {
            edge.getView().deselect();
        }
        selectedEdges.clear();
        selectedVertices.clear();
    }

    public void addToSelection(VertexController vertex) {
        if (!selectedVertices.contains(vertex)) {
            selectedVertices.add(vertex);
            vertex.getView().select();
        }
    }

    public void addToSelection(EdgeController edge) {
        if (!selectedEdges.contains(edge)) {
            selectedEdges.add(edge);
            edge.getView().select();
        }
    }

    public List<VertexController> getSelectedVertices() {
        return selectedVertices;
    }

    public List<EdgeController> getSelectedEdges() {
        return selectedEdges;
    }
}
