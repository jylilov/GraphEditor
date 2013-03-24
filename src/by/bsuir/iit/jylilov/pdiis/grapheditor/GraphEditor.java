package by.bsuir.iit.jylilov.pdiis.grapheditor;

import javax.swing.JFrame;

public class GraphEditor {

	public static void main(String[] args) {
		JFrame frame = new JFrame("GraphEditor");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLayout(null);
		frame.setVisible(true);
		frame.setBounds(400, 100, 400, 400);
		VertexModel model_v1 = new VertexModel(50, 50);
		VertexModel model_v2 = new VertexModel(200, 200);
		VertexController v1 = new VertexController(model_v1);
		VertexController v2 = new VertexController(model_v2);
		
		frame.add(v1.getView());		
		frame.add(v2.getView());
	}

}
