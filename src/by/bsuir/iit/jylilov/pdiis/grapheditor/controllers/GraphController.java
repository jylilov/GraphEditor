package by.bsuir.iit.jylilov.pdiis.grapheditor.controllers;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JComponent;


import by.bsuir.iit.jylilov.pdiis.grapheditor.models.EdgeModel;
import by.bsuir.iit.jylilov.pdiis.grapheditor.models.GraphModel;
import by.bsuir.iit.jylilov.pdiis.grapheditor.models.VertexModel;
import by.bsuir.iit.jylilov.pdiis.grapheditor.models.VertexModelEvent;
import by.bsuir.iit.jylilov.pdiis.grapheditor.views.EdgeView;
import by.bsuir.iit.jylilov.pdiis.grapheditor.views.GraphView;
import by.bsuir.iit.jylilov.pdiis.grapheditor.views.VertexView;

public class GraphController implements ControllerInterface {

	private GraphModel model;
	private GraphView view;
	private EditMode mode = EditMode.VERTEX_MODE;
	private ControllerInterface graphControllerInEdgeMode = new GraphControllerInEdgeMode(this);
	private ControllerInterface graphControllerInVertexMode = new GraphControllerInVertexMode(this);

	private boolean isCreatingEdge = false;
	private EdgeView edgeToCreate;
	
	private List<VertexController> vertices = new ArrayList<VertexController> ();
	private List<EdgeController> edges = new ArrayList<EdgeController> ();
	private List<VertexView> selectedVertices = new ArrayList<VertexView> ();
	private EdgeView selectedEdge;
	
	private Observer observerForChangePrefferedSize = new Observer() {
		int maxX = 0, maxY = 0;

		@Override
		public void update(Observable arg0, Object arg1) {
			VertexModelEvent e = (VertexModelEvent)arg1;
			VertexModel model = (VertexModel)arg0;
			boolean isChanged = false;
			if (e == VertexModelEvent.CHANGED_LOCATION) {
				if (maxX < model.getX()) {
					maxX = model.getX();
					isChanged = true;
				}
				if (maxY < model.getY()) {
					maxY = model.getY();
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
		view = new GraphView(this, model);
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
		VertexController vertex = new VertexController(vertexModel, this);
		addVertex(vertex);
	}

	public void addVertex(int x, int y) {
		VertexModel model = new VertexModel(x, y);
		addVertex(model);
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

	public EditMode getMode() {
		return mode;
	}
	
	boolean isCreatingEdge() {
		return isCreatingEdge;
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
			vertex.setBorderColor(Color.BLUE);
			view.setComponentZOrder(vertex, 0);
		}
	}
	
	public void select(EdgeView edge) {
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
			vertex.setBorderColor(Color.BLACK);
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
			i.setBorderColor(Color.BLACK);
		}
		selectedVertices.clear();
		deselectEdge();
	}
	
	public void setVertexEditMode() {
		mode = EditMode.VERTEX_MODE;
		deselectAll();
	}
	
	public void setEdgeEditMode() {
		mode = EditMode.EDGE_MODE;
		deselectAll();
	}
	
	public void removeEdge(EdgeView edge) {
		view.remove(edge);
		view.repaint();
		model.removeEdge(edge.getModel());
	}
	
	public void removeSelectedEdge() {
		if (selectedEdge != null) {
			removeEdge(selectedEdge);
			selectedEdge = null;
		}
	}
	
	public void removeVertex(VertexView vertex) { 
		
	}
	
	public void removeSelectedVertices() {
		
	}
	
	private ControllerInterface getCurrentController() {
		switch(mode) {
		case EDGE_MODE:
			return graphControllerInEdgeMode;
		case VERTEX_MODE:
			return graphControllerInVertexMode;
		default:
			return null;
		}		
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		getCurrentController().mouseClicked(e);
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		getCurrentController().mouseEntered(e);
	}

	@Override
	public void mouseExited(MouseEvent e) {
		getCurrentController().mouseExited(e);
	}

	@Override
	public void mousePressed(MouseEvent e) {
		getCurrentController().mousePressed(e);
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		getCurrentController().mouseReleased(e);
	}

	@Override
	public void mouseDragged(MouseEvent e) {
		getCurrentController().mouseDragged(e);
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		getCurrentController().mouseMoved(e);
	}

	
}
