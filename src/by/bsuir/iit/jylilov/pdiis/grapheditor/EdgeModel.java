package by.bsuir.iit.jylilov.pdiis.grapheditor;

import java.util.Observable;
import java.util.Observer;

public class EdgeModel extends Observable implements Observer{
	
	private VertexModel vertex1;
	private VertexModel vertex2;
	private int weight;
	
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
		notifyObservers(false);
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
	
}

enum EdgeModelEvent {
	CHANGED_LOCATION;
}