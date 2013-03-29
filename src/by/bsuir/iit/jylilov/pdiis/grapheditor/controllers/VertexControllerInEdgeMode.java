package by.bsuir.iit.jylilov.pdiis.grapheditor.controllers;

import java.awt.event.MouseEvent;

import by.bsuir.iit.jylilov.pdiis.grapheditor.models.VertexModel;
import by.bsuir.iit.jylilov.pdiis.grapheditor.views.EdgeView;
import by.bsuir.iit.jylilov.pdiis.grapheditor.views.VertexView;

class VertexControllerInEdgeMode implements ControllerInterface {
	
	GraphController graphController;

	public VertexControllerInEdgeMode(GraphController graphController) {
		this.graphController = graphController;
	}

	@Override
	public void mouseDragged(MouseEvent e) {
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		graphController.mouseMoved(e);
	}

	@Override
	public void mouseClicked(MouseEvent e) {
	}

	@Override
	public void mouseEntered(MouseEvent e) {
	}

	@Override
	public void mouseExited(MouseEvent e) {
	}

	@Override
	public void mousePressed(MouseEvent e) {
		VertexModel vertex = ((VertexView)e.getComponent()).getVertexModel();
		EdgeView edge;
		if (!graphController.isCreatingEdge()) {
			graphController.deselectEdge();
			VertexModel hiddenVertex = new VertexModel(e.getX() + vertex.getX() - VertexView.SIZE / 2,
					 									 e.getY() + vertex.getY() - VertexView.SIZE / 2);
			graphController.setCreatingEdge(true);			
			graphController.setHiddenVertex(hiddenVertex);
			edge = new EdgeView(vertex, hiddenVertex);
			graphController.setEdgeToCreate(edge);
			graphController.getView().add(edge);
		} else {
			edge = graphController.getEdgeToCreate();
			if (!graphController.getModel().isEdgeExist(edge.getModel().getVertex1(), vertex)) {
				graphController.getView().remove(edge);
				graphController.addEdge(edge.getModel().getVertex1(), vertex);
				graphController.setCreatingEdge(false);
			}
		}
	}

	@Override
	public void mouseReleased(MouseEvent e) {
	}

}
