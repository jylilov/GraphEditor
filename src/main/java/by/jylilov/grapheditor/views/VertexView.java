package by.jylilov.grapheditor.views;

import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

public class VertexView extends Circle {

    public VertexView() {
        setRadius(15);
        setStrokeWidth(7);
        setFill(Color.WHITESMOKE);
        setStroke(Color.BLACK);
    }

    public void select() {
        setStroke(Color.DARKCYAN);
    }

    public void deselect() {
        setStroke(Color.BLACK);
    }
}
