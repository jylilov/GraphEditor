package by.bsuir.iit.jylilov.pdiis.grapheditor;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JFrame;

public class GraphEditor {

	public static void main(String[] args) {
		JFrame frame = new JFrame("GraphEditor");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLayout(null);
		frame.setVisible(true);
		frame.setBounds(400, 100, 400, 400);
		final VertexModel model_v1 = new VertexModel(50, 50);
		final VertexController v1 = new VertexController(model_v1);
		final VertexModel model_v2 = new VertexModel(200, 200);
		final VertexController v2 = new VertexController(model_v2);
		final VertexModel model_v3 = new VertexModel(400, 150);
		final VertexController v3 = new VertexController(model_v3);
		
		frame.add(v1.getView());		
		frame.add(v2.getView());
		frame.add(v3.getView());
		EdgeModel model_e = new EdgeModel(model_v1, model_v2);
		EdgeController e = new EdgeController(model_e);
		EdgeModel model_e1 = new EdgeModel(model_v2, model_v3);
		EdgeController e1 = new EdgeController(model_e1);
		EdgeModel model_e2 = new EdgeModel(model_v1, model_v3);
		EdgeController e2 = new EdgeController(model_e2);
		frame.add(e.getView());
		frame.add(e1.getView());
		frame.add(e2.getView());
		
		frame.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				v1.setLocation(model_v1.getX() + 10, model_v1.getY());
			}
		});
	}

}
