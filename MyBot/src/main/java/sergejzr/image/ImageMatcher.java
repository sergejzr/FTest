package sergejzr.image;

import org.openimaj.image.FImage;
import org.openimaj.image.analysis.algorithm.FourierTemplateMatcher;
import org.openimaj.image.analysis.algorithm.TemplateMatcher;
import org.openimaj.image.analysis.algorithm.TemplateMatcher.Mode;
import org.openimaj.image.pixel.FValuePixel;

public class ImageMatcher {
	Mode mode;
	private FImage template;
	private ImageMatchType matchtype;
	public enum ImageMatchType{fourier,pixel}
	FourierTemplateMatcher fm;
	TemplateMatcher tm;
	
	public ImageMatcher(ImageMatchType matchtype, FImage template, Mode tt) {
		this.matchtype=matchtype;
		switch(matchtype){
		case fourier:
			fm=new FourierTemplateMatcher(template,FourierTemplateMatcher.Mode.SUM_SQUARED_DIFFERENCE);
			break;
		case pixel:
			tm=new TemplateMatcher(template, tt);
			break;
			
		}
		this.template=template;
		this.mode=tt;
	}
	public static Mode sumSquaredDifference() {
		// TODO Auto-generated method stub
		return Mode.SUM_SQUARED_DIFFERENCE;
	}
	public void analyseImage(FImage bigimage) {
		switch(matchtype){
		case fourier:
			fm.analyseImage(bigimage);
			break;
		case pixel:
			tm.analyseImage(bigimage);
			break;
			
		}
		
	}
	public FValuePixel[] getBestResponses(int i) {
		switch(matchtype){
		case fourier:
			return fm.getBestResponses(i);
			//break;
		case pixel:
			return tm.getBestResponses(i);
			//break;
			
		}
		return null;
	};
}
