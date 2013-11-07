package by.bsuir.iit.jylilov.pdiis.grapheditor;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.KeyStroke;
import javax.swing.JToggleButton;
import javax.swing.JToolBar;
import javax.swing.filechooser.FileFilter;

import by.bsuir.iit.jylilov.pdiis.grapheditor.controllers.EditMode;
import by.bsuir.iit.jylilov.pdiis.grapheditor.controllers.GraphController;
import by.bsuir.iit.jylilov.pdiis.grapheditor.models.GraphModel;
import by.bsuir.iit.jylilov.pdiis.grapheditor.controllers.Algorithm;

public class GraphEditorComponent extends JFrame {

	private final class AboutListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			JOptionPane.showMessageDialog(GraphEditorComponent.this, 
					"Author: Alexander Jylilov (alexander.jylilov@gmail.com) ");				
		}
	}

	private static final long serialVersionUID = -6765308008073278915L;
	
	private final JMenuBar mnBar = new JMenuBar();
	
	private final JMenu mnFile = new JMenu("File");
	private final JMenuItem mntNew = new JMenuItem("New");
	private final JMenuItem mntOpen = new JMenuItem("Open...");
	private final JMenuItem mntSave = new JMenuItem("Save");
	private final JMenuItem mntSaveAs = new JMenuItem("Save as...");
	private final JMenuItem mntExit = new JMenuItem("Exit");
	
	private final JMenu mnEdit = new JMenu("Edit");
	private final JMenuItem mntSetVMode = new JMenuItem("Select vertex edit mode");
	private final JMenuItem mntSetEMode = new JMenuItem("Select edge edit mode");
	private final JMenuItem mntSetIdtf = new JMenuItem("Change identifier");
	private final JMenuItem mntSetWeight = new JMenuItem("Change weight");
	private final JMenuItem mntDelete = new JMenuItem("Delete selected");
	private final JMenuItem mntMakeAlgorithm = new JMenuItem("Make algorithm");
	
	private final JMenu mnHelp = new JMenu("Help");
	private final JMenuItem mntAbout = new JMenuItem("About");
	
	private final JToolBar toolBar = new JToolBar(JToolBar.VERTICAL);
	private final JToggleButton btnSetVMode = new JToggleButton("Vertex edit mode");
	private final JToggleButton btnSetEMode = new JToggleButton("Edge edit mode");
	private final JButton btnSetIdtf = new JButton("Change identifier");
	private final JButton btnSetWeight = new JButton("Change weight");
	private final JButton btnDelete = new JButton("Delete selected");
	private final JButton btnMakeAlgorithm = new JButton("Make algorithm");
	
	private final JScrollPane workArea = new JScrollPane();
	
	private GraphController graph = new GraphController(new GraphModel());
	private String filePath = null;
	
	private final ActionListener setVMode = new ActionListener() {
		@Override
		public void actionPerformed(ActionEvent e) {
			btnSetVMode.setSelected(true);
			btnSetEMode.setSelected(false);
			graph.setMode(EditMode.VERTEX_MODE);
		}
	};
	
	private final ActionListener setEMode = new ActionListener() {
		@Override
		public void actionPerformed(ActionEvent e) {
			btnSetEMode.setSelected(true);
			btnSetVMode.setSelected(false);
			graph.setMode(EditMode.EDGE_MODE);
		}
	};
	
	private final ActionListener setWeigth = new ActionListener() {
		@Override
		public void actionPerformed(ActionEvent e) {
			graph.changeWeightOfSelectedEdge();				
		}
	};
	
	private final ActionListener delete = new ActionListener() {
		@Override
		public void actionPerformed(ActionEvent e) {
			graph.removeSelectedEdge();
			graph.removeSelectedVertices();
		}
	};
	
	private final ActionListener setIdtf =  new ActionListener() {
		@Override
		public void actionPerformed(ActionEvent e) {
			graph.changeIdentifierOfSelectedVertex();				
		}
	};
	
	private final ActionListener makeAlgorithm = new ActionListener() {		
		private Algorithm algorithm = null;
		private EditMode mode;
		@Override
		public void actionPerformed(ActionEvent e) {
				if (algorithm == null) {
					mode = graph.getMode();
					graph.setMode(EditMode.NONE_MODE);
					algorithm = new Algorithm(graph);
					algorithm.start();
					setEnabledAll(false);
					btnMakeAlgorithm.setEnabled(true);
					mntMakeAlgorithm.setEnabled(true);
					mntExit.setEnabled(true);
					btnMakeAlgorithm.setText("Stop alghorithm");
					mntMakeAlgorithm.setText("Stop alghorithm");
				} else {
					graph.setMode(mode);
					algorithm.finish();
					algorithm = null;
					setEnabledAll(true);
					btnMakeAlgorithm.setText("Make algorithm");
					mntMakeAlgorithm.setText("Make algorithm");
				}
		}
	};
	
	private ActionListener newGraph = new ActionListener() {
		@Override
		public void actionPerformed(ActionEvent e) {
			graph = new GraphController(new GraphModel());
			workArea.setViewportView(graph.getView());
			filePath = null;
			btnSetVMode.setSelected(true);
			btnSetEMode.setSelected(false);
		}
	};
	
	private ActionListener openGraph = new ActionListener() {
		@Override
		public void actionPerformed(ActionEvent e) {
			try {
				JFileChooser fileOpen = new JFileChooser();
				fileOpen.setFileFilter(fileFilter);
				fileOpen.setDialogType(JFileChooser.OPEN_DIALOG);
				int result = fileOpen.showDialog(null, "Open");
				if (result == JFileChooser.APPROVE_OPTION && fileOpen.getSelectedFile().exists()) {
				    filePath = fileOpen.getSelectedFile().getAbsolutePath();
					btnSetVMode.setSelected(true);
					btnSetEMode.setSelected(false);
					GraphModel model;
					ObjectInputStream in = new ObjectInputStream(new FileInputStream(filePath));
					model = (GraphModel)in.readObject();
					in.close();
					graph = new GraphController(model);
					workArea.setViewportView(graph.getView());
				}
			} catch (IOException | ClassNotFoundException e1) {
			}
		}
	};
	
	private ActionListener saveGraph = new ActionListener() {
		@Override
		public void actionPerformed(ActionEvent e) {
			if (filePath == null) {
				saveAs();
			} else {
				save();			
			}
		}
	};
	
	private ActionListener saveAsGraph = new ActionListener() {
		@Override
		public void actionPerformed(ActionEvent e) {
			saveAs();
		}
	};
	
	private ActionListener exit = new ActionListener() {		
		@Override
		public void actionPerformed(ActionEvent e) {
			System.exit(0);
		}
	};
	
	
	public GraphEditorComponent() {
		super("Graph Editor");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		setJMenuBar(mnBar);
		initFileMenu();
		initEditMenu();
		initToolBar();
		initHelpMenu();
		
		getContentPane().add(workArea, BorderLayout.CENTER);
		workArea.setViewportView(graph.getView());
		
		setBounds(250, 50, 800, 600);
		setVisible(true);
	}

	private void initHelpMenu() {
		mnBar.add(mnHelp);
		mnHelp.add(mntAbout);
		
		mntAbout.addActionListener(new AboutListener());
	}

	private void initToolBar() {
		getContentPane().add(toolBar, BorderLayout.WEST);
		toolBar.add(btnSetVMode); 
		toolBar.add(btnSetEMode);
		toolBar.addSeparator();
		toolBar.add(btnSetIdtf);
		toolBar.add(btnSetWeight);
		toolBar.addSeparator();
		toolBar.add(btnDelete);
		toolBar.addSeparator();
		toolBar.add(btnMakeAlgorithm);
		
		btnSetVMode.setSelected(true);
		btnSetVMode.setFocusable(false); 
		btnSetEMode.setFocusable(false);
		btnSetIdtf.setFocusable(false);
		btnSetWeight.setFocusable(false);
		btnDelete.setFocusable(false);	
		btnMakeAlgorithm.setFocusable(false);
		
		btnSetEMode.addActionListener(setEMode);
		btnSetVMode.addActionListener(setVMode);
		btnSetIdtf.addActionListener(setIdtf);
		btnSetWeight.addActionListener(setWeigth);
		btnDelete.addActionListener(delete);
		btnMakeAlgorithm.addActionListener(makeAlgorithm);
	}

	private void initEditMenu() {
		mnBar.add(mnEdit);	
		mnEdit.add(mntSetVMode);
		mnEdit.add(mntSetEMode);
		mnEdit.addSeparator();
		mnEdit.add(mntSetIdtf);
		mnEdit.add(mntSetWeight);
		mnEdit.addSeparator();
		mnEdit.add(mntDelete);
		mnEdit.addSeparator();
		mnEdit.add(mntMakeAlgorithm);
		
		mntSetEMode.addActionListener(setEMode);
		mntSetEMode.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_2, 0));
		mntSetVMode.addActionListener(setVMode);
		mntSetVMode.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_1, 0));
		mntSetIdtf.addActionListener(setIdtf);
		mntSetIdtf.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_I, 0));
		mntSetWeight.addActionListener(setWeigth);
		mntSetWeight.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_C, 0));
		mntDelete.addActionListener(delete);
		mntDelete.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_DELETE, 0));
		mntDelete.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_BACK_SPACE, 0));
		mntMakeAlgorithm.addActionListener(makeAlgorithm);
	}
	
	private void setEnabledAll(boolean value) {
		btnSetVMode.setEnabled(value); 
		btnSetEMode.setEnabled(value);
		btnSetIdtf.setEnabled(value);
		btnSetWeight.setEnabled(value);
		btnDelete.setEnabled(value);	
		btnMakeAlgorithm.setEnabled(value);	
		
		mntSetVMode.setEnabled(value); 
		mntSetEMode.setEnabled(value);
		mntSetIdtf.setEnabled(value);
		mntSetWeight.setEnabled(value);
		mntDelete.setEnabled(value);	
		mntMakeAlgorithm.setEnabled(value);	
		
		mntNew.setEnabled(value);
		mntOpen.setEnabled(value);
		mntSave.setEnabled(value);
		mntSaveAs.setEnabled(value);
		mntExit.setEnabled(value);
	}
	
	private void initFileMenu() {
		mnBar.add(mnFile);
		mnFile.add(mntNew);
		mnFile.add(mntOpen);
		mnFile.addSeparator();
		mnFile.add(mntSave);
		mnFile.add(mntSaveAs);
		mnFile.addSeparator();
		mnFile.add(mntExit);
		
		mntNew.addActionListener(newGraph);
		mntOpen.addActionListener(openGraph);
		mntOpen.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F8, 0));
		mntSave.addActionListener(saveGraph);
		mntSave.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F5, 0));
		mntSaveAs.addActionListener(saveAsGraph);
		mntExit.addActionListener(exit);
	}
	
	private FileFilter fileFilter = new FileFilter() {

        @Override
        public boolean accept(File f) {
            return f.getName().endsWith(".grf");
        }

        @Override
        public String getDescription() {
            return "*.grf Graph files";
        }

    };
	
	private void saveAs() {
		JFileChooser fileSave = new JFileChooser();
		fileSave.setFileFilter(fileFilter);
		fileSave.setDialogType(JFileChooser.SAVE_DIALOG);
		int result = fileSave.showDialog(null, "Save");
		if (result == JFileChooser.APPROVE_OPTION) {
		    filePath = fileSave.getSelectedFile().getAbsolutePath();
		    if (!filePath.endsWith(".grf"))
		    	filePath += ".grf";
		    save();
		}
	}
	
	private  void save() {
		try {
			ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(filePath));
			out.writeObject(graph.getModel());
			out.close();
		} catch (IOException e1) {
		}
	}

}
