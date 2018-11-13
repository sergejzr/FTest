package sergejzr.robot;

import java.awt.Point;
import java.awt.Rectangle;

import org.openimaj.image.FImage;
import org.openimaj.image.pixel.FValuePixel;

import sergejzr.image.Matcher;

public class MatcherThread extends Thread{

	private Matcher matcher;
Point p=null;
private double threshold;
private Point coord;
boolean inwork=true;
private FImage screenimage;
private String workerlable;
private float confidence;


	public MatcherThread(String workerlable, Matcher matcher, Point coord, double threshold, FImage screenimage) {
		this.matcher=matcher;
		this.threshold=threshold;
		this.coord=coord;
		this.screenimage=screenimage;
		this.workerlable=workerlable;
	}
public String getWorkerlable() {
	return workerlable;
}
@Override
public void run() {
	
	matcher.analyseImage(screenimage);
	FValuePixel[] responsew = matcher.getBestResponses(1);


	if(responsew.length==0) {
		p= null; inwork=false; return;
	}
	if(responsew[0]==null) {
		p= null;inwork=false;
		return;
	}
	for (FValuePixel p : responsew) {
		//log("x:" + p.x + "y" + p.y + " " + p.value);
	}
	if(Math.abs(responsew[0].value)>threshold){
		//if(responsew.length>2)
			
		p= null;inwork=false;
		return;
		}
	
	p= new Point(coord.x + responsew[0].x, coord.y + responsew[0].y);
	confidence=responsew[0].value;
	inwork=false;
}
public float getConfidence() {
	return confidence;
}
}
