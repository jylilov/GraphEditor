package by.bsuir.iit.jylilov.pdiis.grapheditor;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;

public class GraphModel extends Observable {
	
	List<VertexModel> vertices;
	List<EdgeModel> edges;
	
	public GraphModel() {
		vertices = new ArrayList<VertexModel> ();
		edges = new ArrayList<EdgeModel> ();
	}
	
	public void addVertex(VertexModel vertex) {
		vertices.add(vertex);
	}
	
	public void addEdge(EdgeModel edge) {
		edges.add(edge);
	}
	
	public boolean isEdgeExist(VertexModel v1, VertexModel v2) { 
		for (EdgeModel i : edges) {
			if ((i.getVertex1() == v1 && i.getVertex2() == v2) || 
				(i.getVertex1() == v2 && i.getVertex2() == v1)) {
				return true;
			}
		}
		return false;
	}
}
