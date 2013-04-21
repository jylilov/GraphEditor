package by.bsuir.iit.jylilov.pdiis.grapheditor.models;

import java.io.Serializable;
import java.util.Observable;
import java.util.Observer;

public class EdgeModel extends Observable implements Observer, Serializable{
	
	private static final long serialVersionUID = 2237712443253772232L;
	
	private final VertexModel vertex1;
	private final VertexModel vertex2;
	
	private int weight = 0;
	
	public EdgeModel(VertexModel v1, VertexModel v2) { 
		vertex1 = v1;
		vertex2 = v2;
		addObservers();		
	}
	
	private void addObservers() {	
		vertex1.addObserver(this);
		vertex2.addObserver(this);
	}
	
	public VertexModel getVertex1() {
		return vertex1;
	}
	
	public VertexModel getVertex2() {
		return vertex2;
	}
	
	public void setWeight(int w) {
		weight = w;
		setChanged();
		notifyObservers(EdgeModelEvent.CHANGED_WEIGHT);
	}
	
	public int getWeight() {
		return weight;
	}

	@Override
	public void update(Observable o, Object arg) {
		VertexModelEvent e = (VertexModelEvent)arg;
		setChanged();
		notifyObservers((e == VertexModelEvent.CHANGED_LOCATION) ? EdgeModelEvent.CHANGED_LOCATION : null);
	}

	public boolean equals(EdgeModel edge) {
		if (vertex1.equals(edge.getVertex1()) && vertex2.equals(edge.getVertex2())) return true;
		if (vertex1.equals(edge.getVertex2()) && vertex2.equals(edge.getVertex1())) return true;
		return false;
	}
	
}
