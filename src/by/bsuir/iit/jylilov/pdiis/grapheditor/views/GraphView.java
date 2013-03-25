package by.bsuir.iit.jylilov.pdiis.grapheditor.views;

import java.util.Observable;
import java.util.Observer;

import javax.swing.JPanel;

import by.bsuir.iit.jylilov.pdiis.grapheditor.controllers.GraphController;
import by.bsuir.iit.jylilov.pdiis.grapheditor.models.GraphModel;

public class GraphView extends JPanel implements Observer {

	private static final long serialVersionUID = 5915112810769913119L;
	private GraphModel model;
	private GraphController controller;
	
	public GraphView(GraphController controller, GraphModel model) {
		super();
		this.setLayout(null);
		this.model = model;
		this.controller = controller;
		model.addObserver(this);
	}

	@Override
	public void update(Observable o, Object arg) {
	}
	
}
