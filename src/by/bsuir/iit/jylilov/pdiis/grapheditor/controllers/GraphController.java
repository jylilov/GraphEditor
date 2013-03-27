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
	private boolean isCreatingEdge = false;
	private VertexModel hiddenVertex;
	private EdgeView edgeToCreate;

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

	public EdgeView getEdgeToCreate() {
		return edgeToCreate;
	}
	
	public GraphModel getModel() {
		return model;
	}

	public void setEdgeToCreate(EdgeView edgeToCreate) {
		this.edgeToCreate = edgeToCreate;
	}

	public VertexModel getHiddenVertex() {
		return hiddenVertex;
	}

	public void setHiddenVertex(VertexModel creatingVertex) {
		this.hiddenVertex = creatingVertex;
	}

	private List<VertexView> selectedVertices = new ArrayList<VertexView> ();
	private EdgeView selectedEdge;
	private VertexController vertexController = new VertexController(this);
	private EdgeController edgeController = new EdgeController(this);
	private ControllerInterface graphControllerInEdgeMode = new GraphControllerInEdgeMode(this);
	private ControllerInterface graphControllerInVertexMode = new GraphControllerInVertexMode(this);
	
	public EditMode getMode() {
		return mode;
	}
	
	public boolean isCreatingEdge() {
		return isCreatingEdge;
	}

	public void setCreatingEdge(boolean isCreatingEdge) {
		this.isCreatingEdge = isCreatingEdge;
	}
	
	public void moveSelected(int x, int y) {
		if (x < 0 || y < 0)
			for (VertexView i : selectedVertices) {
				VertexModel m = i.getModel();
				x = Math.max(x, - m.getX());
				y = Math.max(y, - m.getY());
			}
		for (VertexView i : selectedVertices) {
			VertexModel m = i.getModel();
			m.setLocation(x + m.getX(), y + m.getY());
		}
	}
	
	public void select(VertexView vertex) {
		if (!isSelected(vertex)) {
			selectedVertices.add(vertex);
			vertex.setBorderColor(Color.BLUE);
			view.setComponentZOrder(vertex, 0);
		};
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
	
	public GraphController(GraphModel model) {
		this.model = model;
		view = new GraphView(this, model);
		view.addMouseListener(this);
		view.addMouseMotionListener(this);
	}
	
	public JComponent getView() {
		return view;
	}
	
	public void addVertex(int x, int y) {
		VertexView v = new VertexView(x, y);
		model.addVertex(v.getModel());
		view.add(v, 0);
		v.getModel().addObserver(observerForChangePrefferedSize);
		v.addMouseListener(vertexController);
		v.addMouseMotionListener(vertexController);
		view.repaint();
	}
	
	public void addEdge(VertexModel v1, VertexModel v2) {
		EdgeView edge = new EdgeView(v1, v2);
		model.addEdge(edge.getModel());
		view.add(edge);
		edge.addMouseListener(edgeController);
		edge.addMouseMotionListener(edgeController);
		view.repaint();
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
