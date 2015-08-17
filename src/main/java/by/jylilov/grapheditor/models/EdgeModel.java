package by.jylilov.grapheditor.models;

import java.io.Serializable;

public class EdgeModel implements Serializable {
    private VertexModel startVertex = null;
    private VertexModel endVertex = null;

    public EdgeModel() {
    }

    public EdgeModel(VertexModel startVertex, VertexModel endVertex) {
        this.startVertex = startVertex;
        this.endVertex = endVertex;
    }

    public VertexModel getStartVertex() {
        return startVertex;
    }

    public void setStartVertex(VertexModel startVertex) {
        this.startVertex = startVertex;
    }

    public VertexModel getEndVertex() {
        return endVertex;
    }

    public void setEndVertex(VertexModel endVertex) {
        this.endVertex = endVertex;
    }
}
