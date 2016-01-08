package cs355.view;

import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.Observable;

import cs355.GUIFunctions;
import cs355.model.drawing.Model;
import cs355.model.drawing.Shape;
import cs355.model.scene.Instance;

public class View implements ViewRefresher {

	@Override
	public void update(Observable arg0, Object arg1) {
		GUIFunctions.refresh();
	}

	@Override
	public void refreshView(Graphics2D g2d) {
		ArrayList<Shape> shapes = (ArrayList<Shape>) Model.instance().getShapes();
		
		for(int i = 0; i < shapes.size(); i++) {
			
		}
	}
}