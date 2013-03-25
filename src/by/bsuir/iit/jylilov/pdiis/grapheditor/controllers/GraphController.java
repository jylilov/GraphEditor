package by.bsuir.iit.jylilov.pdiis.grapheditor.controllers;

import java.awt.Color;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.swing.JComponent;


import by.bsuir.iit.jylilov.pdiis.grapheditor.models.GraphModel;
import by.bsuir.iit.jylilov.pdiis.grapheditor.models.VertexModel;
import by.bsuir.iit.jylilov.pdiis.grapheditor.views.EdgeView;
import by.bsuir.iit.jylilov.pdiis.grapheditor.views.GraphView;
import by.bsuir.iit.jylilov.pdiis.grapheditor.views.VertexView;

public class GraphController implements MouseListener{
	
	private class VertexController implements MouseListener, MouseMotionListener {
		
		private int dx, dy;

		@Override
		public void mouseClicked(MouseEvent e) {}

		@Override
		public void mouseEntered(MouseEvent e) {
			VertexView vertex = (VertexView)e.getComponent();
			if (mode == EditMode.EDGE_MODE) {
				if (isSelected(vertex))
					vertex.setBorderColor(Color.RED); 
				else if (selectedVertices.size() == 1) {
					for (VertexView i : selectedVertices) {
						if (model.isEdgeExist(i.getModel(), vertex.getModel())) {
							vertex.setBorderColor(Color.RED);
						} else
							vertex.setBorderColor(Color.GREEN);
					}
				} else
					vertex.setBorderColor(Color.GREEN);
			}
		}

		@Override
		public void mouseExited(MouseEvent e) {
			VertexView vertex = (VertexView)e.getComponent(); 
			vertex.setBorderColor((isSelected(vertex)) ? Color.BLUE : Color.BLACK);
		}

		@Override
		public void mousePressed(MouseEvent e) {
			VertexView vertex = (VertexView)e.getComponent();
			if (mode == EditMode.VERTEX_MODE) {
				if (e.getButton() == MouseEvent.BUTTON1) {
					dx = e.getX();
					dy = e.getY();
					if (!e.isControlDown() && !isSelected(vertex)) deselectAll();
					select(vertex);
				} 
				else if (e.getButton() == MouseEvent.BUTTON3) {
					deselectAll();
				}
			} else if (mode == EditMode.EDGE_MODE) {
				if (e.getButton() == MouseEvent.BUTTON1) {
					if (selectedVertices.size() == 1) {
						
						if (!isSelected(vertex)) {
							for (VertexView i : selectedVertices) {
								if (!model.isEdgeExist(i.getModel(), vertex.getModel()))
										addEdge(vertex.getModel(), i.getModel());
								else return;
							}
							deselectAll();
						}
					} else {				
						deselectAll();
						select(vertex);
						vertex.setBorderColor(Color.RED);
					}
				}
			}
		}

		@Override
		public void mouseReleased(MouseEvent e) {}

		@Override
		public void mouseDragged(MouseEvent e) {
			if (mode == EditMode.VERTEX_MODE) moveSelected(e.getX() - dx, e.getY() - dy);
		}

		@Override
		public void mouseMoved(MouseEvent e) {}
		
	}
	
	private GraphModel model;
	private GraphView view;
	private EditMode mode = EditMode.VERTEX_MODE;
	private List<VertexView> selectedVertices = new ArrayList<VertexView> ();
	private VertexController vertexController = new VertexController();
	
	public void moveSelected(int x, int y) {
		if (x < 0 || y < 0)
			for (VertexView i : selectedVertices) {
				VertexModel m = i.getModel();
				if (x + m.getX() < 0) x = - m.getX();
				if (y + m.getY() < 0) y = - m.getY();
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
		for (VertexView i : selectedVertices) {
			i.setBorderColor(Color.BLACK);
		}
		selectedVertices.clear();
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
	}
	
	public JComponent getView() {
		return view;
	}
	
	public void addVertex(int x, int y) {
		VertexView v = new VertexView(x, y);
		model.addVertex(v.getModel());
		view.add(v, 0);
		v.addMouseListener(vertexController);
		v.addMouseMotionListener(vertexController);
		view.repaint();
	}
	
	public void addEdge(VertexModel v1, VertexModel v2) {
		EdgeView edge = new EdgeView(v1, v2);
		model.addEdge(edge.getModel());
		view.add(edge);
		view.repaint();
	}

	@Override
	public void mouseClicked(MouseEvent e) {}

	@Override
	public void mouseEntered(MouseEvent e) {}

	@Override
	public void mouseExited(MouseEvent e) {}

	@Override
	public void mousePressed(MouseEvent e) {
		deselectAll();
		if (mode == EditMode.VERTEX_MODE) {
			if (e.getButton() == MouseEvent.BUTTON1 && e.getClickCount() == 2)
				addVertex(e.getX() - VertexView.SIZE / 2, e.getY() - VertexView.SIZE / 2);
		}
	}

	@Override
	public void mouseReleased(MouseEvent e) {}
}
