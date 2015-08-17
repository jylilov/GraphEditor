package by.jylilov.grapheditor.models;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;

import java.io.*;

public class VertexModel implements Externalizable {
    private DoubleProperty x = new SimpleDoubleProperty(0);
    private DoubleProperty y = new SimpleDoubleProperty(0);

    public VertexModel() {
    }

    public VertexModel(double x, double y) {
        setX(x);
        setY(y);
    }

    public void moveOn(double x, double y) {
        setX(getX() + x);
        setY(getY() + y);
    }

    public double getX() {
        return x.get();
    }

    public DoubleProperty xProperty() {
        return x;
    }

    public void setX(double x) {
        this.x.set(x);
    }

    public double getY() {
        return y.get();
    }

    public DoubleProperty yProperty() {
        return y;
    }

    public void setY(double y) {
        this.y.set(y);
    }

    @Override
    public void writeExternal(ObjectOutput out) throws IOException {
        out.writeDouble(getX());
        out.writeDouble(getY());
    }

    @Override
    public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {
        setX(in.readDouble());
        setY(in.readDouble());
    }
}
