package by.bsuir.iit.jylilov.pdiis.grapheditor.views;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JToggleButton;
import javax.swing.JToolBar;

import by.bsuir.iit.jylilov.pdiis.grapheditor.controllers.EditMode;
import by.bsuir.iit.jylilov.pdiis.grapheditor.controllers.GraphController;
import by.bsuir.iit.jylilov.pdiis.grapheditor.controllers.GraphEditorController;
import by.bsuir.iit.jylilov.pdiis.grapheditor.models.GraphModel;
import by.bsuir.iit.jylilov.pdiis.grapheditor.controllers.Algorithm;

public class GraphEditorView extends JFrame {

	private static final long serialVersionUID = -6765308008073278915L;
	
	private final GraphEditorController controller;
	
	public GraphEditorView(GraphEditorController controller) {
		super("Graph Editor");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.controller = controller;
		
		JMenuBar menuBar = new JMenuBar();
		setJMenuBar(menuBar);
		
		String [] menus = {"File", "Edit", "Help"};
		String [][] menuItems = {{"New", "Open...", "", "Close", "Close all", "", "Save", "Save as...", "", "Exit"}, 
								 {"Select vertex edit mode", "Select edge edit mode", "", "Change identifier", "Change weight of vertex", "", "Delete selected"},
								 {"About"}}; 	
		
		for (int i = 0; i < menus.length; i++) {
			JMenu menu = new JMenu(menus[i]);
			menuBar.add(menu);
			for (String j: menuItems[i]) {
				if (j.equals("")) {
					menu.addSeparator();
				} else {
					JMenuItem item = new JMenuItem(j);
					menu.add(item);
				}
			}
		}
		
		JToolBar toolBar = new JToolBar(JToolBar.VERTICAL);
		
		getContentPane().add(toolBar, BorderLayout.WEST);
		
		JTabbedPane tabbedPane = new JTabbedPane();
		
		getContentPane().add(tabbedPane, BorderLayout.CENTER);
		final GraphController c = new GraphController(new GraphModel());
		JScrollPane pane = new JScrollPane(c.getView());
		tabbedPane.add("Tab 1", pane);
		
		final JButton deleteBtn = new JButton("Delete selected");
		deleteBtn.setFocusable(false);
		deleteBtn.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				c.removeSelectedEdge();
				c.removeSelectedVertices();
			}
		});
		
		final JButton changeIdtf = new JButton("Change identifier");
		changeIdtf.setFocusable(false);
		changeIdtf.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				c.changeIdentifierOfSelectedVertex();				
			}
			
		});
		
		final JButton make = new JButton("makeAlgorithm");
		make.setFocusable(false);
		make.addActionListener(new ActionListener() {
			
			Algorithm algo = null;
			EditMode mode;
			
			@Override
			public void actionPerformed(ActionEvent e) {
					if (algo == null) {
						mode = c.getMode();
						c.setMode(EditMode.NONE_MODE);
						algo = new Algorithm(c);
						algo.start();
					} else {
						c.setMode(mode);
						algo.finish();
						algo = null;
					}
			}
			
		});
		
		final JButton changeWeight = new JButton("Change weight");
		changeWeight.setFocusable(false);
		changeWeight.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				c.changeWeightOfSelectedEdge();				
			}
			
		});
		
		final JToggleButton button_v = new JToggleButton("Vertex Edit Mode");
		final JToggleButton button_e = new JToggleButton("Edge Edit Mode");
		button_v.setSelected(true);
		button_v.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				button_v.setSelected(true);
				button_e.setSelected(false);
				c.setMode(EditMode.VERTEX_MODE);
			}
		});
		
		button_e.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				button_e.setSelected(true);
				button_v.setSelected(false);
				c.setMode(EditMode.EDGE_MODE);
			}
		});
		
		button_v.setFocusable(false);
		button_e.setFocusable(false);
		
		toolBar.add(button_v);
		toolBar.add(button_e);
		toolBar.addSeparator();
		toolBar.add(deleteBtn);		
		toolBar.addSeparator();
		toolBar.add(changeWeight);
		toolBar.add(changeIdtf);
		toolBar.addSeparator();
		toolBar.add(make);
		
		pack();
		setVisible(true);
	}

}
