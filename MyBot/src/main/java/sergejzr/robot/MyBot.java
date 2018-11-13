package sergejzr.robot;

import java.awt.AWTException;
import java.awt.MouseInfo;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.event.InputEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.imageio.ImageIO;

import org.jnativehook.GlobalScreen;
import org.jnativehook.NativeHookException;
import org.jnativehook.keyboard.NativeKeyEvent;
import org.openimaj.image.FImage;
import org.openimaj.image.ImageUtilities;
import org.openimaj.image.analysis.algorithm.TemplateMatcher.Mode;
//import org.openimaj.image.analysis.algorithm.FourierTemplateMatcher;
//import org.openimaj.image.analysis.algorithm.FourierTemplateMatcher.Mode;
//import org.openimaj.image.analysis.algorithm.TemplateMatcher;
//import org.openimaj.image.analysis.algorithm.TemplateMatcher.Mode;
import org.openimaj.image.pixel.FValuePixel;

import sergejzr.image.ImageMatcher;
import sergejzr.image.ImageMatcher.ImageMatchType;
import sergejzr.image.Matcher;

public abstract class MyBot extends Robot {
	private ImageMatchType matchtype = ImageMatchType.fourier;

	public MyBot() throws AWTException {
		super();
		// TODO Auto-generated constructor stub
		try {
			GlobalScreen.registerNativeHook();
		} catch (NativeHookException ex) {
			System.err.println("There was a problem registering the native hook. You will need to find another exit");
			System.err.println(ex.getMessage());

		}

		GlobalScreen.addNativeKeyListener(new MyExitListener(this));

	}

	Instant start = null;

	MouseInfo k;

	Point findImage(File subimage) {
		return findImage(subimage, null);
	}

	Hashtable<String, ImageMatcher> templateidx = new Hashtable<>();
	Hashtable<String, Matcher> mtemplateidx = new Hashtable<>();

	public void surround(Rectangle screen) {
		// this.setAutoDelay(800);
		surround(1,
				new Point[] { new Point(screen.x, screen.y), new Point(screen.x + screen.width, screen.y),
						new Point(screen.x + screen.width, screen.y + screen.height),
						new Point(screen.x, screen.y + screen.height) });
	}

	public void mouseMove(Point p) {
		mouseMove(p.x, p.y);

	}

	protected void mouseLeftClick() {

		mousePress(InputEvent.BUTTON1_MASK);
		delay(50);
		mouseRelease(InputEvent.BUTTON1_MASK);

	}

	public void mouseSlide(Point p, int ms) {
		Point loc = MouseInfo.getPointerInfo().getLocation();

		double distance = Math.pow(p.x - loc.x, 2) + Math.pow(p.y - loc.y, 2);

		int step = 100;
		double chunks = distance / step;
		int curx = loc.x, cury = loc.y;
		for (int i = 0; i < step; i++) {
			int mov_x = ((p.x * i) / step) + (loc.x * (step - 1) / step);
			int mov_y = ((p.y * i) / step) + (loc.y * (step - i) / step);
			mouseMove(curx = mov_x, cury = mov_y);
			delay(step);
			// delay((int)(ms/chunks));
		}
		if (curx != p.x || cury != p.y)
			mouseMove(p);
	}

	public void surround(int times, Point... points) {

		for (; times > 0; times--)
			for (Point p : points) {
				log("surround to " + p);
				// mouseMove(p.x, p.y);
				mouseSlide(new Point(p.x, p.y), 2000);
				try {
					Thread.sleep(500);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
	}

	protected NamedPoint findImages(ArrayList<File> subimages, Rectangle rect, double threshold) {

		// log("make screenshot");
		Point coord = new Point(0, 0);
		if (rect == null) {
			rect = new Rectangle(Toolkit.getDefaultToolkit().getScreenSize());
		} else {
			coord = new Point(rect.x, rect.y);
		}
		BufferedImage image = createScreenCapture(rect);

		// log("create F image");
		try {

			FImage screenimage = ImageUtilities.createFImage(image);

			// log("create F image from template");
			ArrayList<MatcherThread> workers = new ArrayList<>();
			for (File subimage : subimages) {

				Matcher matcher;

				if ((matcher = mtemplateidx.get(subimage.getAbsolutePath())) == null) {

					FImage template = ImageUtilities.createFImage(ImageIO.read(subimage));

					Matcher.Mode tt = Matcher.Mode.NORM_SUM_SQUARED_DIFFERENCE;
					mtemplateidx.put(subimage.getAbsolutePath(), matcher = new Matcher(template, tt));
				}
				MatcherThread t;
				workers.add(t = new MatcherThread(subimage.toString(), matcher, coord, threshold, screenimage));
				t.start();
			}
HashMap<Double, Point> idx=new HashMap<>(); 
HashMap<Double, String> idxlab=new HashMap<>();
			while (true) {
				int cntready = 0;
				for (MatcherThread t : workers) {
					if (t.inwork == false) {
						Point p = t.p;
						if (p != null) {
							//return new NamedPoint(t.getWorkerlable(), t.p);
						idx.put((double)t.getConfidence(), p);
						idxlab.put((double)t.getConfidence(), t.getWorkerlable());
						}
						cntready++;
						if (cntready == workers.size()) {
							if(idx.size()==0)
							return null;
							Point maxpoint=null;
							Double maxd=Double.MAX_VALUE;
							for(Double d:idx.keySet())
							{
								if(d<maxd)
								{
									maxd=d;
									maxpoint=idx.get(d);
								}
							}
							return new NamedPoint(t.getWorkerlable(), maxpoint);
						}
					}

				}
				delay(100);
			}
			// log("start analysis");
			// matcher.analyseImage(screenimage);
			// log("getmatches");

			// responsew[0].x
			// log("move mouse to x:"+responsew[0].x+" y:"+responsew[0].y);

			// mouseMove(responsew[0].x, responsew[0].y);

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// matcher.analyseImage(image);

		return null;

	}

	public boolean sameAs(FImage peopleframe, FImage peopleimg) {

		FImage diff = peopleframe.subtract(peopleimg);

		for (int i = 0; i < diff.pixels.length; i++) {
			for (int y = 0; y < diff.pixels[i].length; y++) {
				if (diff.pixels[i][y] != 0) {
					return false;
				}
			}
		}
		return true;
	}

	public Point matchImage(FImage bigimage, File subimage, double threshold) {
		ImageMatcher matcher;
		FImage template;
		try {
			if ((matcher = templateidx.get(subimage.getAbsolutePath())) == null) {

				template = ImageUtilities.createFImage(ImageIO.read(subimage));

				Mode tt = ImageMatcher.sumSquaredDifference();// Mode.SUM_SQUARED_DIFFERENCE;
				templateidx.put(subimage.getAbsolutePath(), matcher = new ImageMatcher(matchtype, template, tt));
			}

			matcher.analyseImage(bigimage);

			FValuePixel[] responsew = matcher.getBestResponses(1);

			if (responsew.length == 0)
				return null;
			if (responsew[0] == null)
				return null;
			for (FValuePixel p : responsew) {
				log("x:" + p.x + "y" + p.y + " " + p.value);
			}
			if (Math.abs(responsew[0].value) > threshold) {
				// if(responsew.length>2)

				return null;
			}

			return new Point(responsew[0].x, responsew[0].y);

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;

	}

	protected Point findImage(File subimage, Rectangle rect, double threshold) {

		// log("make screenshot");
		Point coord = new Point(0, 0);
		if (rect == null) {
			rect = new Rectangle(Toolkit.getDefaultToolkit().getScreenSize());
		} else {
			coord = new Point(rect.x, rect.y);
		}
		BufferedImage image = createScreenCapture(rect);
		// log("create F image");
		try {

			FImage screenimage = ImageUtilities.createFImage(image);

			// log("create F image from template");
			ImageMatcher matcher;

			if ((matcher = templateidx.get(subimage.getAbsolutePath())) == null) {

				FImage template = ImageUtilities.createFImage(ImageIO.read(subimage));

				Mode tt = Mode.SUM_SQUARED_DIFFERENCE;
				templateidx.put(subimage.getAbsolutePath(), matcher = new ImageMatcher(matchtype, template, tt));
			}

			// log("start analysis");
			matcher.analyseImage(screenimage);
			// log("getmatches");

			FValuePixel[] responsew = matcher.getBestResponses(1);

			if (responsew.length == 0)
				return null;
			if (responsew[0] == null)
				return null;
			for (FValuePixel p : responsew) {
				log("x:" + p.x + "y" + p.y + " " + p.value);
			}
			if (Math.abs(responsew[0].value) > threshold) {
				// if(responsew.length>2)

				return null;
			}

			return new Point(coord.x + responsew[0].x, coord.y + responsew[0].y);
			// responsew[0].x
			// log("move mouse to x:"+responsew[0].x+" y:"+responsew[0].y);

			// mouseMove(responsew[0].x, responsew[0].y);

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// matcher.analyseImage(image);

		return null;

	}

	Point findImage(File subimage, Rectangle rect) {
		return findImage(subimage, rect, 3);
	}

	public void saveImage(Rectangle r, File f) {
		BufferedImage image = createScreenCapture(r);
		try {
			ImageIO.write(image, "png", f);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	protected void log(String string) {
		Exception e = new Exception();
		
		
		if (start == null)
			start = Instant.now();
		Instant end = Instant.now();

		Duration timeElapsed = Duration.between(start, end);
		System.out.println(string);
		// System.out.println(dtf.format(new TemporalDuration(timeElapsed)));
		System.out.println(end+" duration: "+timeElapsed);
		System.out.println(e.getStackTrace()[1]);

		start = end;
	}

	public static void main(String[] args) {

		Logger logger = Logger.getLogger("org.jnativehook");
		logger.setLevel(Level.WARNING);

	}
	// kasten2=0.7933426

	public abstract void keyPressed(NativeKeyEvent e);

	public abstract void keyReleased(NativeKeyEvent e);

	public BufferedImage getPicture(Rectangle screen) {

		Point coord = new Point(0, 0);

		coord = new Point(screen.x, screen.y);

		return createScreenCapture(screen);

	}

}
