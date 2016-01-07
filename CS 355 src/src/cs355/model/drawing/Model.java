package cs355.model.drawing;

import java.awt.Color;
import java.util.ArrayList;

public class Model extends Shape {

	ArrayList<Shape> shapes;
	
	public Model(Color color) {
		super(color);
		// TODO Auto-generated constructor stub
	}
	
	public ArrayList<Shape> getShapes() {
		return shapes;
	}

}
