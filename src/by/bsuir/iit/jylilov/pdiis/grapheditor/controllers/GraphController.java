package by.bsuir.iit.jylilov.pdiis.grapheditor.controllers;

import java.awt.Color;
import java.awt.Dimension;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JComponent;
import javax.swing.JOptionPane;



import by.bsuir.iit.jylilov.pdiis.grapheditor.models.EdgeModel;
import by.bsuir.iit.jylilov.pdiis.grapheditor.models.GraphModel;
import by.bsuir.iit.jylilov.pdiis.grapheditor.models.VertexModel;
import by.bsuir.iit.jylilov.pdiis.grapheditor.models.VertexModelEvent;
import by.bsuir.iit.jylilov.pdiis.grapheditor.views.EdgeView;
import by.bsuir.iit.jylilov.pdiis.grapheditor.views.GraphView;
import by.bsuir.iit.jylilov.pdiis.grapheditor.views.VertexView;

public class GraphController extends Controller {

	private final ControllerInterface graphControllerInEdgeMode = new GraphControllerInEdgeMode(this);
	private final ControllerInterface graphControllerInVertexMode = new GraphControllerInVertexMode(this);
	private final List<VertexController> vertices = new ArrayList<VertexController> ();
	private final List<EdgeController> edges = new ArrayList<EdgeController> ();
	private final List<VertexView> selectedVertices = new ArrayList<VertexView> ();
	private final GraphModel model;
	private final GraphView view;
	
	private EditMode mode = EditMode.VERTEX_MODE;
	private boolean isCreatingEdge = false;
	private EdgeView edgeToCreate;
	private EdgeView selectedEdge;
	
	private Observer observerForChangePrefferedSize = new Observer() {
		
		int maxX = 0, maxY = 0;
		
		@Override
		public void update(Observable arg0, Object arg1) {
			VertexModelEvent e = (VertexModelEvent)arg1;
			VertexModel model = (VertexModel)arg0;
			boolean isChanged = false;
			if (e == VertexModelEvent.CHANGED_LOCATION) {
				if (maxX < model.getX() || maxY < model.getY()) {
					maxX = Math.max(maxX, model.getX());
					maxY = Math.max(maxY, model.getY());					
					isChanged = true;
				}
			}
			if (isChanged) {
				view.setPreferredSize(new Dimension(maxX + VertexView.SIZE * 2, maxY + VertexView.SIZE * 2));
				view.revalidate();
			}
		}		
		
	};

	public GraphController(GraphModel model) {
		this.model = model;
		VertexController controllerV;
		EdgeController controllerE;
		view = new GraphView(this, model);
		for (VertexModel i : model.getVertices()) {
			controllerV = new VertexController(i, this);
			vertices.add(controllerV);
			view.add(controllerV.getView());
			controllerV.getModel().addObserver(observerForChangePrefferedSize);
		}
		for (EdgeModel i : model.getEdges()) {
			controllerE = new EdgeController(i, this);
			edges.add(controllerE);
			view.add(controllerE.getView());
			i.getVertex1().addObserver(i);
			i.getVertex2().addObserver(i);
		}
		view.addMouseListener(this);
		view.addMouseMotionListener(this);
	}

	public GraphModel getModel() {
		return model;
	}

	public JComponent getView() {
		return view;
	}

	public void addVertex(VertexController vertex) {
		model.addVertex(vertex.getModel());
		vertices.add(vertex);
		view.add(vertex.getView(), 0);
		view.repaint();
		vertex.getModel().addObserver(observerForChangePrefferedSize);
	}

	public void addVertex(VertexModel vertexModel) {
		addVertex(new VertexController(vertexModel, this));
	}

	public void addVertex(int x, int y) {
		addVertex(new VertexModel(x, y));
	}
	
	VertexController getController(VertexModel vertex) {
		for (VertexController i : vertices) {
			if (i.getModel().equals(vertex)) return i;
		}
		return null;
	}

	public void removeVertex(VertexModel vertex) {
		removeVertex(getController(vertex).getView());
	}

	public void removeVertex(VertexView vertex) { 
		List<EdgeModel> incidentEdges = model.getIncidentEdges(vertex.getModel());
		for (EdgeModel i : incidentEdges) {
			removeEdge(i);
		}
		view.remove(vertex);
		view.repaint();
		model.removeVertex(vertex.getModel());
		vertices.remove(getController(vertex.getModel()));
		
	}

	public void removeSelectedVertices() {
		for (VertexView i : selectedVertices) {
			removeVertex(i);			
		}
	}

	public void addEdge(VertexModel v1, VertexModel v2) {
		if (!model.isEdgeExist(v1, v2)) {
			EdgeModel edge = new EdgeModel(v1, v2);
			model.addEdge(edge);
			EdgeController edgeController = new EdgeController(edge, this);
			edges.add(edgeController);
			view.add(edgeController.getView());
			view.repaint();
		}
	}

	EdgeController getController(EdgeModel edge) {
		for (EdgeController i : edges) {
			if (i.getModel().equals(edge)) return i;
		}
		return null;
	}
	
	EdgeController getController(VertexController vertex1, VertexController vertex2) {
		VertexModel v1 = vertex1.getModel();
		VertexModel v2 = vertex2.getModel();
		for (EdgeController i: edges) {
			if ((i.getModel().getVertex1().equals(v1) && i.getModel().getVertex2().equals(v2)) ||
				(i.getModel().getVertex1().equals(v2) && i.getModel().getVertex2().equals(v1)))
				return i;
		}
		return null;
	}

	public void removeEdge(EdgeModel edge) {
		removeEdge(getController(edge).getView());
	}

	public void removeEdge(EdgeView edge) {
		view.remove(edge);
		view.repaint();
		model.removeEdge(edge.getModel());
		edges.remove(getController(edge.getModel()));
	}

	public void removeSelectedEdge() {
		if (selectedEdge != null) {
			removeEdge(selectedEdge);
			selectedEdge = null;
		}
	}

	void startCreatingEdge(VertexView vertex1, VertexModel vertex2) {
		isCreatingEdge = true;
		edgeToCreate = new EdgeView(vertex1.getModel(), vertex2);
		view.add(edgeToCreate);
		view.repaint();
	}
	
	void stopCreatingEdge() {
		if (isCreatingEdge) {
			isCreatingEdge = false;
			view.remove(edgeToCreate);
			view.repaint(); 
		}
	}
	
	void finishCreatingEdge(int x, int y) {
		JComponent component = (JComponent) view.getComponentAt(x, y);
		VertexView vertex2;
		if (component instanceof VertexView) {
			vertex2 = (VertexView) component;		
			if (isCreatingEdge) {
				addEdge(edgeToCreate.getModel().getVertex1(), vertex2.getModel());
			}
		}	
		stopCreatingEdge();
	}
	
	boolean isCreatingEdge() {
		return isCreatingEdge;
	}

	public EditMode getMode() {
		return mode;
	}
	
	public void moveSelected(int x, int y) {
		int dx = x;
		int dy = y;
		if (x < 0 || y < 0)
			for (VertexView i : selectedVertices) {
				VertexModel m = i.getModel();
				dx = Math.max(dx, - m.getX());
				dy = Math.max(dy, - m.getY());
			}
		for (VertexView i : selectedVertices) {
			VertexModel m = i.getModel();
			m.setLocation(dx + m.getX(), dy + m.getY());
		}
	}
	
	public void select(VertexView vertex) {
		deselectAll();
		addToSelection(vertex);
	}
	
	public void addToSelection(VertexView vertex) {
		if (!isSelected(vertex)) {
			selectedVertices.add(vertex);
			vertex.setColor(Color.BLUE);
			view.setComponentZOrder(vertex, 0);
		}
	}
	
	public void select(EdgeView edge) {
		deselectEdge();
		selectedEdge = edge;
		edge.setColor(Color.BLUE);
		view.setComponentZOrder(edge, model.getVerticesCount());
	}
	
	public void deselectEdge() {
		if (selectedEdge != null) {
			selectedEdge.setColor(Color.BLACK);
			selectedEdge = null;
		}
	}
	
	public boolean isSelected(VertexView vertex) {
		return selectedVertices.contains(vertex);
	}
	
	public void deselect(VertexView vertex) {
		if (isSelected(vertex)) {
			vertex.setColor(Color.BLACK);
			Iterator<VertexView> i = selectedVertices.iterator();
			while (vertex != i.next()) {}
			i.remove();
			view.setComponentZOrder(vertex, selectedVertices.size());
		}
	}
	
	public void deselectAll() {
		if (isCreatingEdge) {
			view.remove(edgeToCreate);
			view.repaint();
			isCreatingEdge = false;
		}
		for (VertexView i : selectedVertices) {
			i.setColor(Color.BLACK);
		}
		selectedVertices.clear();
		deselectEdge();
	}
	
	public void changeIdentifierOfSelectedVertex() {
		if (selectedVertices.size() == 1 ) {
			for (VertexView i : selectedVertices) {
				String s = JOptionPane.showInputDialog("Change identifier", i.getModel().getIdentifier());
				if (s != null)
					i.getModel().setIdentifier(s);
			}
		}
	}
	
	public void changeWeightOfSelectedEdge() {
		if (selectedEdge != null ) {
			String s = JOptionPane.showInputDialog("Change weight", selectedEdge.getModel().getWeight());
			if (s != null) {
				try {
					selectedEdge.getModel().setWeight(Integer.valueOf(s));
				}
				catch (Exception e) {
					JOptionPane.showMessageDialog(this.getView(), "Eggs are not supposed to be green.");
				}		
			}
		}
	}

	List<EdgeController> getIncidentEdges(VertexController vertex) {
		List<EdgeController> e = new ArrayList<EdgeController> (); 
		for (EdgeController i : edges) {
			if (i.getModel().getVertex1().equals(vertex.getModel()) || 
				i.getModel().getVertex2().equals(vertex.getModel())) {
				e.add(i);
			}
		}
		return e;
	}
	
	@Override
	protected ControllerInterface getCurrentController() {
		switch(mode) {
		case EDGE_MODE:
			return graphControllerInEdgeMode;
		case VERTEX_MODE:
			return graphControllerInVertexMode;
		default:
			return null;
		}		
	}

	List<VertexController> getVertices() {
		return new ArrayList<VertexController>(vertices); 
	}
	
	List<EdgeController> getEdges() {
		return new ArrayList<EdgeController>(edges);
	}

	public void setMode(EditMode mode) {
		this.mode = mode;
		deselectAll();
	}
	
}
