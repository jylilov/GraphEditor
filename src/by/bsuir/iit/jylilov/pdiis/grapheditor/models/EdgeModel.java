package by.bsuir.iit.jylilov.pdiis.grapheditor.models;

import java.util.Observable;
import java.util.Observer;

public class EdgeModel extends Observable implements Observer{
	
	private VertexModel vertex1;
	private VertexModel vertex2;
	private int weight = 0;
	
	public EdgeModel(VertexModel v1, VertexModel v2) { 
		vertex1 = v1;
		vertex2 = v2;
		v1.addObserver(this);
		v2.addObserver(this);
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

	public boolean isEqual(EdgeModel edge) {
		if (vertex1.isEqual(edge.getVertex1()) && vertex2.isEqual(edge.getVertex2())) return true;
		if (vertex1.isEqual(edge.getVertex2()) && vertex2.isEqual(edge.getVertex1())) return true;
		return false;
	}
	
}
