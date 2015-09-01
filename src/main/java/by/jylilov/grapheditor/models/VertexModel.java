package by.jylilov.grapheditor.models;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

import java.io.*;

public class VertexModel implements Externalizable {
    private DoubleProperty x = new SimpleDoubleProperty(0);
    private DoubleProperty y = new SimpleDoubleProperty(0);

    private StringProperty name = new SimpleStringProperty();

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

    public String getName() {
        return name.get();
    }

    public StringProperty nameProperty() {
        return name;
    }

    public void setName(String name) {
        this.name.set(name);
    }

    @Override
    public void writeExternal(ObjectOutput out) throws IOException {
        out.writeDouble(getX());
        out.writeDouble(getY());
        out.writeObject(getName());
    }

    @Override
    public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {
        setX(in.readDouble());
        setY(in.readDouble());
        setName((String)in.readObject());
    }

}
