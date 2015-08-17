package by.jylilov.grapheditor.controllers;

import by.jylilov.grapheditor.models.VertexModel;

import java.util.ArrayList;
import java.util.List;

public class DragSelectedController {
    private final SelectionController selectionController;

    private boolean isDragging = false;
    private double prevDragX;
    private double prevDragY;

    public DragSelectedController(SelectionController selectionController) {
        this.selectionController = selectionController;
    }

    public boolean isDragging() {
        return isDragging;
    }

    public void startDragSelected() {
        setIsDragging(true);
    }

    public void stopDragSelected() {
        setIsDragging(false);
    }

    private void setIsDragging(boolean isDragging) {
        this.isDragging = isDragging;
    }

    public void update(double x, double y) {
        prevDragX = x;
        prevDragY = y;
    }

    public void drag(double x, double y) {
        List<VertexModel> draggedVertices = new ArrayList<>();
        double dX = x - prevDragX;
        double dY = y - prevDragY;

        for (VertexController vertex : selectionController.getSelectedVertices()) {
            draggedVertices.add(vertex.getModel());
            vertex.getModel().moveOn(dX, dY);
        }

        for (EdgeController edge : selectionController.getSelectedEdges()) {
            VertexModel startVertex = edge.getModel().getStartVertex();
            VertexModel endVertex = edge.getModel().getEndVertex();
            if (!draggedVertices.contains(startVertex)) {
                draggedVertices.add(startVertex);
                startVertex.moveOn(dX, dY);
            }
            if (!draggedVertices.contains(endVertex)) {
                draggedVertices.add(endVertex);
                endVertex.moveOn(dX, dY);
            }
        }

        draggedVertices.clear();
        update(x, y);
    }
}
