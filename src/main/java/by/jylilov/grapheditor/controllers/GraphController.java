package by.jylilov.grapheditor.controllers;

import by.jylilov.grapheditor.models.EdgeModel;
import by.jylilov.grapheditor.models.GraphModel;
import by.jylilov.grapheditor.models.VertexModel;
import by.jylilov.grapheditor.views.GraphView;
import javafx.scene.input.MouseButton;

public class GraphController {
    private final GraphView view;
    private final GraphModel model;

    private SelectionController selectionController;
    private EdgeCreatingController edgeCreatingController;
    private DragSelectedController dragSelectedController;

    public GraphController(GraphView view) {
        this.view = view;
        this.model = new GraphModel();

        selectionController = new SelectionController();
        edgeCreatingController = new EdgeCreatingController(this);
        dragSelectedController = new DragSelectedController(selectionController);

        addViewListeners();
    }

    public void update(GraphModel model) {
        view.clear();
        model.getVertexList().forEach(this::addNewVertex);
        model.getEdgeList().forEach(this::addNewEdge);
    }

    private void addNewVertex(VertexModel vertex) {
        VertexController vertexController = new VertexController(this, vertex);
        model.add(vertex);
        view.add(vertexController.getView());
    }

    private void addNewVertex(double x, double y) {
        addNewVertex(new VertexModel(x, y));
    }

    private void addNewEdge(EdgeModel edge) {
        EdgeController edgeController = new EdgeController(this, edge);
        model.add(edge);
        view.add(edgeController.getView());
    }

    private void addViewListeners() {
        view.setOnMousePressed(event -> {
            if (dragSelectedController.isDragging()) {
                dragSelectedController.update(event.getX(), event.getY());
            } else if (edgeCreatingController.isEdgeCreating()) {
                edgeCreatingController.updateEdgeCreating(event.getX(), event.getY());
            } else if (event.getButton().equals(MouseButton.PRIMARY) && event.isShiftDown()) {
                addNewVertex(event.getX(), event.getY());
            } else {
                selectionController.deselectAll();
            }
        });

        view.setOnMouseDragged(event -> {
            if (dragSelectedController.isDragging()) {
                dragSelectedController.drag(event.getX(), event.getY());
            } else if (edgeCreatingController.isEdgeCreating()) {
                edgeCreatingController.updateEdgeCreating(event.getX(), event.getY());
            }
        });

        view.setOnMouseReleased(event -> {
            if (dragSelectedController.isDragging()) {
                dragSelectedController.stopDragSelected();
            }
            if (edgeCreatingController.isEdgeCreating()) {
                edgeCreatingController.abortEdgeCreating();
            }
        });
    }

    public void clear() {
        view.clear();
        model.clear();
    }

    public GraphView getView() {
        return view;
    }

    public GraphModel getModel() {
        return model;
    }

    public SelectionController getSelectionController() {
        return selectionController;
    }

    public EdgeCreatingController getEdgeCreatingController() {
        return edgeCreatingController;
    }

    public DragSelectedController getDragSelectedController() {
        return dragSelectedController;
    }
}
