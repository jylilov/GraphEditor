package by.jylilov.grapheditor.views;

import javafx.beans.property.StringProperty;
import javafx.scene.Group;
import javafx.scene.control.Label;

public class VertexView extends Group {

    private Label label = new Label();

    public VertexView() {
        getChildren().addAll(label);

        label.getStyleClass().add("vertex-view");

        label.layoutXProperty().bind(label.widthProperty().divide(-2));
        label.layoutYProperty().bind(label.heightProperty().divide(-2));
    }

    public void select() {
        label.getStyleClass().add("vertex-view-selected");
    }

    public void deselect() {
        label.getStyleClass().remove("vertex-view-selected");
    }

    public StringProperty textProperty() {
        return label.textProperty();
    }
}
