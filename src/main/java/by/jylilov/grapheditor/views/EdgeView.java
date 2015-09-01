package by.jylilov.grapheditor.views;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.StringProperty;
import javafx.scene.Group;
import javafx.scene.control.Label;
import javafx.scene.shape.Line;

public class EdgeView extends Group {

    private final Line line = new Line();
    private final Label label = new Label();

    public EdgeView() {
        getChildren().addAll(line, label);
        label.getStyleClass().add("label-edge-view");
        line.getStyleClass().add("line-edge-view");

        label.layoutXProperty().bind(line.startXProperty().add(line.endXProperty()).subtract(label.widthProperty()).divide(2));
        label.layoutYProperty().bind(line.startYProperty().add(line.endYProperty()).subtract(label.heightProperty()).divide(2));
    }

    public void select() {
        label.getStyleClass().add("label-edge-view-selected");
        line.getStyleClass().add("line-edge-view-selected");
    }

    public void deselect() {
        label.getStyleClass().remove("label-edge-view-selected");
        line.getStyleClass().remove("line-edge-view-selected");
    }


    public DoubleProperty startXProperty() {
        return line.startXProperty();
    }

    public DoubleProperty startYProperty() {
        return line.startYProperty();
    }

    public DoubleProperty endXProperty() {
        return line.endXProperty();
    }

    public DoubleProperty endYProperty() {
        return line.endYProperty();
    }

    public StringProperty labelProperty() {
        return label.textProperty();
    }
}
