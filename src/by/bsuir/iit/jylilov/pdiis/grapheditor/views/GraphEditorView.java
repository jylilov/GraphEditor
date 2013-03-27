package by.bsuir.iit.jylilov.pdiis.grapheditor.views;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JToggleButton;
import javax.swing.JToolBar;

import by.bsuir.iit.jylilov.pdiis.grapheditor.controllers.GraphController;
import by.bsuir.iit.jylilov.pdiis.grapheditor.controllers.GraphEditorController;
import by.bsuir.iit.jylilov.pdiis.grapheditor.models.GraphModel;

public class GraphEditorView extends JFrame {

	private static final long serialVersionUID = -6765308008073278915L;
	private GraphEditorController controller;
	
	public GraphEditorView(GraphEditorController controller) {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.controller = controller;
		
		JMenuBar menuBar = new JMenuBar();
		setJMenuBar(menuBar);
		
		String [] menus = {"File", "Edit", "Help"};
		String [][] menuItems = {{"New", "Open...", "", "Close", "Close all", "", "Save", "Save as...", "", "Exit"}, {}, {"About"}}; 	
		
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
		
		final JToggleButton button_v = new JToggleButton("Vertex Edit Mode");
		final JToggleButton button_e = new JToggleButton("Edge Edit Mode");
		button_v.setSelected(true);
		button_v.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				button_v.setSelected(true);
				button_e.setSelected(false);
				c.setVertexEditMode();
			}
		});
		
		button_e.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				button_e.setSelected(true);
				button_v.setSelected(false);
				c.setEdgeEditMode();
			}
		});
		
		button_v.setFocusable(false);
		button_e.setFocusable(false);
		
		toolBar.add(button_v);
		toolBar.add(button_e);
		
		pack();
		setVisible(true);
	}

}
