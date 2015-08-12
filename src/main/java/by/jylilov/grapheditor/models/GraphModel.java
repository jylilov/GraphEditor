package by.jylilov.grapheditor.models;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Observable;


public class GraphModel extends Observable implements Serializable {

	private static final long serialVersionUID = 2034560809598304488L;
	
	private final List<VertexModel> vertices;
	private final List<EdgeModel> edges;

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
	
	public List<EdgeModel> getIncidentEdges(VertexModel vertex) {
		List<EdgeModel> incidentEdges = new ArrayList<EdgeModel> ();
		for (EdgeModel i : edges) {
			if (i.getVertex1() == vertex || i.getVertex2() == vertex) {
				incidentEdges.add(i);
			}
		}
		return incidentEdges;
	}
	
	public boolean isEdgeExist(VertexModel v1, VertexModel v2) {
		if (v1.equals(v2)) return true;
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

	public List<VertexModel> getVertices() {
		return vertices;
	}
	
	public List<EdgeModel> getEdges() {
		return edges;
	}
}