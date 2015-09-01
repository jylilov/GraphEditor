package by.jylilov.grapheditor.controllers;

import by.jylilov.grapheditor.models.EdgeModel;
import by.jylilov.grapheditor.models.VertexModel;
import by.jylilov.grapheditor.utils.DialogUtils;
import by.jylilov.grapheditor.views.EdgeView;
import javafx.beans.property.DoubleProperty;

public class EdgeController {
    private final SelectionController selectionController;
    private final DragSelectedController dragSelectedController;

    private final EdgeModel model;
    private final EdgeView view;

    public EdgeController(GraphController graphController, VertexModel startVertex, VertexModel endVertex) {
        this(graphController, new EdgeModel(startVertex, endVertex));
    }

    public EdgeController(GraphController graphController, EdgeModel model) {
        this.selectionController = graphController.getSelectionController();
        this.dragSelectedController = graphController.getDragSelectedController();
        this.model = model;
        view = new EdgeView();

        setStartVertex(model.getStartVertex());
        setEndVertex(model.getEndVertex());
        view.labelProperty().bind(model.weightProperty().asString());

        addListenersOnView();
    }

    private void addListenersOnView() {
        view.setOnMousePressed(event -> {
            if (event.isControlDown()) {
                selectionController.addToSelection(this);
                dragSelectedController.startDragSelected();
            } else {
                if (event.getClickCount() == 2) {
                    DialogUtils.chooseEdgeWeight(model.weightProperty());
                    dragSelectedController.stopDragSelected();
                } else {
                    selectionController.select(this);
                    dragSelectedController.startDragSelected();
                }
            }
        });
    }

    public void setStartVertex(VertexModel vertex) {
        model.setStartVertex(vertex);
        bind(view.startXProperty(), vertex.xProperty());
        bind(view.startYProperty(), vertex.yProperty());
    }

    public void setEndVertex(VertexModel vertex) {
        model.setEndVertex(vertex);
        bind(view.endXProperty(), vertex.xProperty());
        bind(view.endYProperty(), vertex.yProperty());
    }

    private void bind(DoubleProperty targetProperty, DoubleProperty sourceProperty) {
        targetProperty.unbind();
        targetProperty.bind(sourceProperty);
    }

    public EdgeModel getModel() {
        return model;
    }

    public EdgeView getView() {
        return view;
    }
}
