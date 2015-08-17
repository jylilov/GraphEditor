package by.jylilov.grapheditor.controllers;

import by.jylilov.grapheditor.models.EdgeModel;
import by.jylilov.grapheditor.models.GraphModel;
import by.jylilov.grapheditor.models.VertexModel;
import javafx.scene.Group;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.Pane;

import java.util.ArrayList;
import java.util.List;

public class GraphController {
    private final Pane view;

    private GraphModel model;

    private Group graphGroup;
    private Group vertexGroup;
    private Group edgeGroup;

    private EdgeController creatingEdge;

    private boolean isDragging = false;
    private double prevDragX;
    private double prevDragY;

    private List<VertexController> selectedVertices = new ArrayList<>();
    private List<EdgeController> selectedEdges = new ArrayList<>();

    public GraphController(Pane workPane) {
        this.view = workPane;
    }

    public void createGraph() {
        clearGraph();

        initializeGroups();
        model = new GraphModel();

        addViewListeners();
    }

    public void createGraph(GraphModel model) {
        createGraph();
        this.model = model;

        VertexController vertexController;
        for (VertexModel vertex: model.getVertexList()) {
            vertexController = new VertexController(this, vertex);
            vertexGroup.getChildren().add(vertexController.getView());
        }

        EdgeController edgeController;
        for (EdgeModel edge: model.getEdgeList()) {
            edgeController = new EdgeController(this, edge.getStartVertex(), edge.getEndVertex());
            edgeGroup.getChildren().add(edgeController.getView());
        }

    }

    private void addViewListeners() {
        view.setOnMousePressed(event -> {
            if (isDragging()) {
                prevDragX = event.getX();
                prevDragY = event.getY();
            } else if (isEdgeCreating()) {
                updateEdgeCreating(event.getX(), event.getY());
                select(creatingEdge);
            } else if (event.getButton().equals(MouseButton.PRIMARY) && event.isShiftDown()) {
                VertexController vertex = addVertex(event.getX(), event.getY());
                select(vertex);
            } else {
                deselectAll();
            }
        });

        view.setOnMouseDragged(event -> {
            if (isDragging()) {
                List<VertexModel> draggedVertices = new ArrayList<>();
                double dX = event.getX() - prevDragX;
                double dY = event.getY() - prevDragY;

                for (VertexController vertex : selectedVertices) {
                    draggedVertices.add(vertex.getModel());
                    vertex.getModel().moveOn(dX, dY);
                }

                for (EdgeController edge : selectedEdges) {
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

                prevDragX = event.getX();
                prevDragY = event.getY();
            } else if (isEdgeCreating()) {
                updateEdgeCreating(event.getX(), event.getY());
            }
        });

        view.setOnMouseReleased(event -> {
            if (isDragging()) {
                setIsDragging(false);
            }
            if (isEdgeCreating()) {
                abortEdgeCreating();
            }
        });
    }

    private VertexController addVertex(double x, double y) {
        VertexController vertex = new VertexController(this);
        vertex.getModel().setX(x);
        vertex.getModel().setY(y);
        vertexGroup.getChildren().add(vertex.getView());
        model.add(vertex.getModel());
        return vertex;
    }

    private void initializeGroups() {
        graphGroup = new Group();
        vertexGroup = new Group();
        edgeGroup = new Group();

        graphGroup.getChildren().addAll(edgeGroup, vertexGroup);
        view.getChildren().add(graphGroup);
    }

    public void clearGraph() {
        if (graphGroup != null) {
            graphGroup.setDisable(true);
            view.getChildren().remove(graphGroup);
            model = null;
            graphGroup = null;
            edgeGroup = null;
            vertexGroup = null;
        }
    }

    public void startEdgeCreating(VertexModel startVertex) {
        VertexModel secondVertex = new VertexModel();
        creatingEdge = new EdgeController(this, startVertex, secondVertex);
        edgeGroup.getChildren().add(creatingEdge.getView());
    }

    public void finishEdgeCreating(VertexModel endVertex) {
        if (isEdgeCreating()) {
            if (creatingEdge.getModel().getStartVertex() != endVertex) {
                creatingEdge.setEndVertex(endVertex);
                model.add(creatingEdge.getModel());
            } else {
                abortEdgeCreating();
            }
            creatingEdge = null;
        }
    }

    public void abortEdgeCreating() {
        if (creatingEdge != null) {
            edgeGroup.getChildren().remove(creatingEdge.getView());
            selectedEdges.remove(creatingEdge);
            creatingEdge = null;
        }
    }

    public void updateEdgeCreating(double endX, double endY) {
        creatingEdge.getModel().getEndVertex().setX(endX);
        creatingEdge.getModel().getEndVertex().setY(endY);
    }

    public boolean isEdgeCreating() {
        return creatingEdge != null;
    }

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

    public Pane getView() {
        return view;
    }

    public GraphModel getModel() {
        return model;
    }
}
