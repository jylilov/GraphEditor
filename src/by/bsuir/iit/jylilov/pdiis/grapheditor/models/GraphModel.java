package by.bsuir.iit.jylilov.pdiis.grapheditor.models;

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
	
	public int getVerticesCount() {
		return vertices.size();
	}
	
	public EdgeModel [] getIncidentEdges(VertexModel vertex) {
		List<EdgeModel> incidentEdges = new ArrayList<EdgeModel> ();
		for (EdgeModel i : edges) {
			if (i.getVertex1() == vertex || i.getVertex2() == vertex) {
				incidentEdges.add(i);
			}
		}
		return (EdgeModel[]) incidentEdges.toArray();
	}
	
	

	public boolean isEdgeExist(VertexModel v1, VertexModel v2) {
		if (v1.isEqual(v2)) return true;
		for (EdgeModel i : edges) {
			if ((i.getVertex1() == v1 && i.getVertex2() == v2) || 
				(i.getVertex1() == v2 && i.getVertex2() == v1)) {
				return true;
			}
		}
		return false;
	}

	public void removeEdge(EdgeModel edge) {
		edges.remove(edge);		
	}
	
	public void removeVertex(VertexModel vertex) {
		vertices.remove(vertex);
	}
}