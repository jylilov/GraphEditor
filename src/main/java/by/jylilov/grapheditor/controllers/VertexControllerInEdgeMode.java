package by.jylilov.grapheditor.controllers;

import java.awt.event.MouseEvent;

import by.jylilov.grapheditor.models.VertexModel;
import by.jylilov.grapheditor.views.VertexView;

class VertexControllerInEdgeMode implements ControllerInterface {
	
	private final GraphController graphController;
	private final VertexController controller;
	
	private VertexModel hiddenVertex; //for create edge

	public VertexControllerInEdgeMode(VertexController controller, GraphController graphController) {
		this.controller = controller;
		this.graphController = graphController;
	}

	@Override
	public void mouseDragged(MouseEvent e) {
		hiddenVertex.setLocation(controller.getView().getX() + e.getX() - VertexView.SIZE / 2,
								 controller.getView().getY() + e.getY() - VertexView.SIZE / 2);
	}

	@Override
	public void mouseMoved(MouseEvent e) {
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
		graphController.deselectAll();
		graphController.stopCreatingEdge();
		VertexView vertex = controller.getView();
		hiddenVertex = new VertexModel(e.getComponent().getX(), e.getComponent().getY());
		graphController.startCreatingEdge(vertex, hiddenVertex);
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		if (graphController.isCreatingEdge()) {
			graphController.finishCreatingEdge(controller.getView().getX() + e.getX(),
											   controller.getView().getY() + e.getY());
		}
	}

}
