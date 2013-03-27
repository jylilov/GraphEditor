package by.bsuir.iit.jylilov.pdiis.grapheditor;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import by.bsuir.iit.jylilov.pdiis.grapheditor.controllers.GraphController;
import by.bsuir.iit.jylilov.pdiis.grapheditor.models.GraphModel;
import by.bsuir.iit.jylilov.pdiis.grapheditor.views.GraphEditorView;

public class GraphEditor {

	public static void main(String[] args) {
		GraphEditorView ge = new GraphEditorView(null);
	/*	JFrame frame = new JFrame("GraphEditor");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setBounds(250, 100, 800, 600);
		GraphModel m = new GraphModel();
		final GraphController c = new GraphController(m);

		JPanel panel = new JPanel();
		panel.add(button_e);
		panel.add(button_v);
		frame.getContentPane().add(panel, BorderLayout.WEST);
				
		JScrollPane scrollPane = new JScrollPane();
		frame.getContentPane().add(scrollPane, BorderLayout.CENTER);
		
		scrollPane.setViewportView(c.getView());		
		
		frame.setVisible(true);*/
	}

}
