package by.jylilov.grapheditor.controllers;

import by.jylilov.grapheditor.models.VertexModel;
import by.jylilov.grapheditor.utils.DialogUtils;
import by.jylilov.grapheditor.views.VertexView;
import javafx.scene.input.MouseButton;

public class VertexController {
    private final EdgeCreatingController edgeCreatingController;
    private final SelectionController selectionController;
    private final DragSelectedController dragSelectedController;

    private final VertexView view;
    private final VertexModel model;

    public VertexController(GraphController graphController, VertexModel model) {
        this.edgeCreatingController = graphController.getEdgeCreatingController();
        this.selectionController = graphController.getSelectionController();
        this.dragSelectedController = graphController.getDragSelectedController();
        this.model = model;
        view = new VertexView();

        view.layoutXProperty().bindBidirectional(model.xProperty());
        view.layoutYProperty().bindBidirectional(model.yProperty());
        view.textProperty().bind(model.nameProperty());

        addViewListeners();

    }

    private void addViewListeners() {
        view.setOnMousePressed(event -> {
            if (event.getButton().equals(MouseButton.PRIMARY)) {
                if (event.isShiftDown()) {
                    event.setDragDetect(true);
                    edgeCreatingController.startEdgeCreating(model);
                } else {
                    if (event.isControlDown()) {
                        selectionController.addToSelection(this);
                        dragSelectedController.startDragSelected();
                    } else {
                        if (event.getClickCount() == 2) {
                            DialogUtils.chooseVertexName(model.nameProperty());
                            dragSelectedController.stopDragSelected();
                        } else {
                            selectionController.select(this);
                            dragSelectedController.startDragSelected();
                        }
                    }
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
            edgeCreatingController.finishEdgeCreating(model);
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
