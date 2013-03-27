package by.bsuir.iit.jylilov.pdiis.grapheditor.controllers;

import java.awt.Component;
import java.awt.event.MouseEvent;

import by.bsuir.iit.jylilov.pdiis.grapheditor.views.GraphView;
import by.bsuir.iit.jylilov.pdiis.grapheditor.views.VertexView;

public class GraphControllerInEdgeMode implements ControllerInterface {
	
	GraphController graphController;

	public GraphControllerInEdgeMode(GraphController graphController) {
		this.graphController = graphController;
	}

	@Override
	public void mouseDragged(MouseEvent e) {	
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		if (graphController.isCreatingEdge()) {
			int x = e.getX() - VertexView.SIZE / 2;
			int y = e.getY() - VertexView.SIZE / 2;
			Component component = e.getComponent();
			if (!(component instanceof GraphView)) {
				x += component.getX();
				y += component.getY();
			}
			graphController.getHiddenVertex().setLocation(x, y);
		}
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
	}

	@Override
	public void mouseReleased(MouseEvent e) {
	}

}
