package by.bsuir.iit.jylilov.pdiis.grapheditor;

import java.util.Observable;

public class VertexModel extends Observable {
	
	private int x;
	private int y;
	private String identifier = new String();
	
	public VertexModel(int x, int y) {
		setLocation(x, y);
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
	
}

enum VertexModelEvent {
	CHANGED_LOCATION, CHANGED_IDENTIFIER;
}
