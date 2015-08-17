package by.jylilov.grapheditor.views;

import javafx.scene.Group;
import javafx.scene.layout.AnchorPane;

public class GraphView extends AnchorPane {
    private Group vertexGroup;
    private Group edgeGroup;

    public GraphView() {
        vertexGroup = new Group();
        edgeGroup = new Group();

        getChildren().addAll(edgeGroup, vertexGroup);
    }

    public void add(EdgeView edge) {
        edgeGroup.getChildren().add(edge);
    }

    public void add(VertexView vertex) {
        vertexGroup.getChildren().add(vertex);
    }

    public void remove(EdgeView edge) {
        edgeGroup.getChildren().remove(edge);
    }

    public void remove(VertexView vertex) {
        vertexGroup.getChildren().remove(vertex);
    }

    public void clear() {
        vertexGroup.getChildren().clear();
        edgeGroup.getChildren().clear();
    }
}
