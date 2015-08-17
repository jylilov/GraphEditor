package by.jylilov.grapheditor.controllers;

import by.jylilov.grapheditor.models.VertexModel;
import by.jylilov.grapheditor.views.VertexView;
import javafx.scene.input.MouseButton;

public class VertexController {
    private final GraphController graphController;

    private final VertexView view;
    private final VertexModel model;

    public VertexController(GraphController graphController) {
        this(graphController, new VertexModel());
    }

    public VertexController(GraphController graphController, VertexModel model) {
        this.graphController = graphController;
        this.model = model;
        view = new VertexView();

        view.layoutXProperty().bindBidirectional(model.xProperty());
        view.layoutYProperty().bindBidirectional(model.yProperty());

        addViewListeners();

    }

    private void addViewListeners() {
        view.setOnMousePressed(event -> {
            if (event.getButton().equals(MouseButton.PRIMARY)) {
                if (event.isShiftDown()) {
                    event.setDragDetect(true);
                    graphController.startEdgeCreating(model);
                } else {
                    if (event.isControlDown()) {
                        graphController.addToSelection(this);
                    } else {
                        graphController.select(this);
                    }
                    graphController.startDragSelected();
                }
            }
        });

        view.setOnDragDetected(event -> {
            if (event.getButton().equals(MouseButton.PRIMARY)) {
                if (event.isShiftDown()) {
                    view.startFullDrag();
                    event.consume();
                }
            }
        });

        view.setOnMouseDragReleased(event -> {
            graphController.finishEdgeCreating(model);
            event.consume();
        });
    }

    public VertexView getView() {
        return view;
    }

    public VertexModel getModel() {
        return model;
    }

}
