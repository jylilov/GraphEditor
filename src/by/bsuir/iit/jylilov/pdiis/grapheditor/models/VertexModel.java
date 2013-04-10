package by.bsuir.iit.jylilov.pdiis.grapheditor.models;

import java.awt.Point;
import java.util.Observable;

public class VertexModel extends Observable {
	
	private int x;
	private int y;
	private String identifier = new String("");
	
	public VertexModel(int x, int y) {
		setLocation(Math.max(x, 0), Math.max(y, 0));
	}
	
	public void setLocation(int x, int y) {
		this.x = x;
		this.y = y;
		setChanged();
		notifyObservers(VertexModelEvent.CHANGED_LOCATION);
	}
	
	public int getX() {
		return x;
	}
	
	public int getY() {
		return y;
	}
	
	public void setIdentifier(String idtf) {
		identifier = idtf;
		setChanged();
		notifyObservers(VertexModelEvent.CHANGED_IDENTIFIER);
	}
	
	public String getIdentifier() {
		return identifier;
	}

	public Point getLocation() {
		return new Point(x, y);
	}
	
	public boolean isEqual(VertexModel vertex) {
		return vertex.x == this.x && vertex.y == this.y;
	}
	
}
