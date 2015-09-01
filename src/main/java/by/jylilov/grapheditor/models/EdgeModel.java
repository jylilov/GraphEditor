package by.jylilov.grapheditor.models;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;

import java.io.*;

public class EdgeModel implements Externalizable {
    private VertexModel startVertex = null;
    private VertexModel endVertex = null;

    private IntegerProperty weight = new SimpleIntegerProperty(0);

    public EdgeModel() {
    }

    public EdgeModel(VertexModel startVertex, VertexModel endVertex) {
        this.startVertex = startVertex;
        this.endVertex = endVertex;
    }

    public boolean isIncidentTo(VertexModel vertex) {
        return startVertex == vertex || endVertex == vertex;
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

    public int getWeight() {
        return weight.get();
    }

    public IntegerProperty weightProperty() {
        return weight;
    }

    public void setWeight(int weight) {
        this.weight.set(weight);
    }

    @Override
    public void writeExternal(ObjectOutput out) throws IOException {
        out.writeObject(getStartVertex());
        out.writeObject(getEndVertex());
        out.writeInt(getWeight());
    }

    @Override
    public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {
        setStartVertex((VertexModel) in.readObject());
        setEndVertex((VertexModel) in.readObject());
        setWeight(in.readInt());
    }
}
