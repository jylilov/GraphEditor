package by.jylilov.grapheditor.controllers;

import by.jylilov.grapheditor.models.VertexModel;

public class EdgeCreatingController {
    private final GraphController graphController;

    private EdgeController creatingEdge = null;

    public EdgeCreatingController(GraphController graphController) {
        this.graphController = graphController;
    }

    public void startEdgeCreating(VertexModel startVertex) {
        VertexModel secondVertex = new VertexModel();
        creatingEdge = new EdgeController(graphController, startVertex, secondVertex);
        graphController.getView().add(creatingEdge.getView());
    }

    public void finishEdgeCreating(VertexModel endVertex) {
        if (isEdgeCreating()) {
            if (creatingEdge.getModel().getStartVertex() != endVertex) {
                creatingEdge.setEndVertex(endVertex);
                graphController.getModel().add(creatingEdge.getModel());
                creatingEdge = null;
            } else {
                abortEdgeCreating();
            }
        }
    }

    public void abortEdgeCreating() {
        if (creatingEdge != null) {
            graphController.getView().remove(creatingEdge.getView());
            creatingEdge = null;
        }
    }

    public void updateEdgeCreating(double endX, double endY) {
        VertexModel endVertex = creatingEdge.getModel().getEndVertex();
        endVertex.setX(endX);
        endVertex.setY(endY);
    }

    public boolean isEdgeCreating() {
        return creatingEdge != null;
    }

}
