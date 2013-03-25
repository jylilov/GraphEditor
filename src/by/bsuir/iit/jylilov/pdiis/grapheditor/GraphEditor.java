package by.bsuir.iit.jylilov.pdiis.grapheditor;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class GraphEditor {

	public static void main(String[] args) {
		JFrame frame = new JFrame("GraphEditor");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setBounds(250, 100, 800, 600);
		GraphModel m = new GraphModel();
		final GraphController c = new GraphController(m);
		JButton button_v = new JButton("Vertex");
		button_v.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				c.setVertexEditMode();
			}
		});
		JButton button_e = new JButton("Edge");
		button_e.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				c.setEdgeEditMode();
			}
		});
		JPanel panel = new JPanel();
		panel.add(button_e);
		panel.add(button_v);
		frame.getContentPane().add(panel, BorderLayout.WEST);
		frame.getContentPane().add(c.getView(), BorderLayout.CENTER);
		frame.setVisible(true);
	}

}
