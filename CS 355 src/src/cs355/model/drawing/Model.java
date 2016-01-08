package cs355.model.drawing;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

public class Model extends CS355Drawing {

	//Use a singleton so that the model can be accessed by the view when repainting
	private static Model _instance;
	
	public int currentMode = -1;
	private Color selectedColor;
	private ArrayList<Shape> shapes;
	private ArrayList<Observer> observers;

	//If the model had not been initialized, it will be.
	public static Model instance() {
		if (_instance == null) 
			_instance = new Model();
		return _instance;
	}
	
	public Model() {
		selectedColor = Color.WHITE;
		shapes = new ArrayList<Shape>();
		observers = new ArrayList<Observer>();
	}
	
	@Override
	public Shape getShape(int index) {
		return shapes.get(index);
	}

	@Override
	public int addShape(Shape s) {
		shapes.add(s);
		return shapes.size();
	}

	@Override
	public void deleteShape(int index) {
		shapes.remove(index);
	}

	@Override
	public void moveToFront(int index) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void movetoBack(int index) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void moveForward(int index) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void moveBackward(int index) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public List<Shape> getShapes() {
		return shapes;
	}

	@Override
	public List<Shape> getShapesReversed() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setShapes(List<Shape> shapes) {
		// TODO Auto-generated method stub
		
	}
}
