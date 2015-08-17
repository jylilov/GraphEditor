package by.jylilov.grapheditor.views;

import javafx.scene.paint.Color;
import javafx.scene.shape.Line;

public class EdgeView extends Line {

    public EdgeView() {
        setStroke(Color.BLACK);
        setStrokeWidth(7);
    }

    public void select() {
        setStroke(Color.DARKCYAN);
    }

    public void deselect() {
        setStroke(Color.BLACK);
    }
}
