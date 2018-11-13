package sergejzr.robot;

import java.awt.Point;

public class NamedPoint extends Point {

	private String name;

	public NamedPoint(String workerlable, Point p) {
		super(p);
		this.name=workerlable;
	}
public String getName() {
	return name;
}
}
