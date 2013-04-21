package by.bsuir.iit.jylilov.pdiis.grapheditor.controllers;

import java.awt.Color;
import java.util.List;

import by.bsuir.iit.jylilov.pdiis.grapheditor.views.EdgeView;
import by.bsuir.iit.jylilov.pdiis.grapheditor.views.VertexView;

public class Algorithm extends Thread {
	
	private final GraphController graph;
	private final Color defaultVertexColor = VertexView.DEFAULT_COLOR;
	private final Color defaultEdgeColor = EdgeView.DEFAULT_COLOR;
	private final Color processColor = Color.YELLOW;
	private final Color selectColor = Color.GREEN;
	private final Color deseloctColor = Color.RED;
	private final int INF = 100000000;
	
	private int pauseTime = 1000;
	private List<VertexController> vertices;
	private int[][] matrix;
	private int size;
	
	public Algorithm (GraphController graph) {
		EdgeController edge;
		this.graph = graph;
		vertices = graph.getVertices();
		size = vertices.size();
		matrix = new int[size][size];
		for (int i = 0; i < size; ++i)
			for (int j = 0; j < size; ++j) {
				edge = graph.getController(vertices.get(i), vertices.get(j));
				if (edge != null) {
					matrix[i][j] = edge.getModel().getWeight();
				} else {
					matrix[i][j] = INF;
				}
			}
	}
	
	public void pause() {
		try {
			sleep(pauseTime);
		} catch (InterruptedException e) {
		}
	}
	
	private void makeAlgorithm() {
		boolean [] isInCarcass  = new boolean[size];
		int [] minEdgeToVertex = new int [size];
		int [] selectedEdge = new int [size];
		for (int i = 0; i < size; ++i) {
			minEdgeToVertex[i] = INF;
			selectedEdge[i] = -1;
		}
		minEdgeToVertex[0] = 0;
		for (int i = 0; i < size; ++i) {
			int vertex = -1;
			for (int j = 0; j < size; ++j)
				if (!isInCarcass[j] && (vertex == -1 || minEdgeToVertex[j] < minEdgeToVertex[vertex]))
					vertex = j;
			isInCarcass[vertex] = true;
			select(vertices.get(vertex));
			pause();
			if (selectedEdge[vertex] != -1) { 
				for (int j = 0; j < size; ++j) {
					if (matrix[vertex][j] != INF && isInCarcass[j]) {
						deselect(graph.getController(vertices.get(vertex), vertices.get(j)));
					}
				}
				select(graph.getController(vertices.get(vertex), vertices.get(selectedEdge[vertex])));
			}
			for (int j = 0; j < size; ++j) {
				if (!isInCarcass[j]) {
					if (matrix[vertex][j] < minEdgeToVertex[j]) {
						minEdgeToVertex[j] = matrix[vertex][j];
						process(graph.getController(vertices.get(vertex), vertices.get(j)));
						selectedEdge[j] = vertex;
					} else if (matrix[vertex][j] != INF){
						process(graph.getController(vertices.get(vertex), vertices.get(j)));					
					}
				}
			}
			pause();
		}
	}
	
	@Override
	public void run() {		
		makeAlgorithm();	
	}
	
	public void finish() {
		stop();
		for (VertexController i : graph.getVertices()) {
			i.getView().setColor(defaultVertexColor);
		}
		for (EdgeController i : graph.getEdges()) {
			i.getView().setColor(defaultEdgeColor);
		}
	}
	
	void deselect(EdgeController edge) {
		edge.getView().setColor(deseloctColor);		
	}
	
	void deselect(VertexController vertex) {
		vertex.getView().setColor(deseloctColor);		
	}
	
	void process(EdgeController edge) {
		edge.getView().setColor(processColor);
	}
	
	void select(EdgeController edge) {
		edge.getView().setColor(selectColor);
	}
	
	void select(VertexController vertex) {
		vertex.getView().setColor(selectColor);
	}
	
	void process(VertexController vertex) {
		vertex.getView().setColor(processColor);
	}		
}
