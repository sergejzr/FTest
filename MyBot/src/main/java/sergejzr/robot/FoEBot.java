package sergejzr.robot;

import java.awt.AWTException;
import java.awt.MouseInfo;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.InputEvent;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.Hashtable;
import java.util.Random;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.imageio.ImageIO;

import org.jnativehook.keyboard.NativeKeyEvent;
import org.openimaj.image.FImage;
import org.openimaj.image.ImageUtilities;
import org.openimaj.math.geometry.point.Point2dImpl;
import org.openimaj.math.geometry.shape.Polygon;

public class FoEBot extends MyBot {

	Rectangle screen;
	Point center;
	File maindir = new File("img/");

	public FoEBot() throws AWTException {
		super();
		getGameCoordinates();
	}

	public void clearTaverne() {
		clickaway();
zoomout();
		mouseMove(new Point(screen.x + 49, screen.y + 55));
		mouseLeftClick();
		delay(8000);
		/*
		 * mouseMove(new Point(screen.x+1270, screen.y+249)); mouseLeftClick();
		 * delay(5000);
		 */
		Rectangle chairrect = new Rectangle(center.x-367, center.y+96, 100, 100);
		
		Point gotorathausp = findImage(getIconFile("taverne", "emptychair.png"), chairrect);
		if (gotorathausp == null) {
		
		
		mouseMove(new Point(center.x - 109, center.y + 87));
		mouseLeftClick();
		mouseMove(new Point(center.x - 109, center.y + 90));
		mouseLeftClick();
		delay(5000);
		}else
		{
			mouseMove(gotorathausp);
		}
		clickaway();

	}

	public static void main(String[] args) {
		Logger logger = Logger.getLogger("org.jnativehook");
		logger.setLevel(Level.WARNING);

		FoEBot bot;
		boolean fullrestart = true;
		try {
			bot = new FoEBot();
			// bot.refreshBrowser();
			// bot.bufPeople();
			// bot.changeWorld(2);
			/// bot.surround(bot.screen);
			// bot.bufPeople();
			// bot.buf(people.friends);
			// bot.clickaway();
			/*
			 * bot.moveUpperLeft(); bot.curScreen=0; for(int i=0;i<8;i++) {
			 * bot.delay(2000); bot.goToNextScreen();
			 * 
			 * }
			 */
			// bot.walkTroughWorlds();
			Date olddtime = new Date();
			// bot.readCoordinates();
			// bot.spendMoney();
			// bot.moveUpperLeft();
			// bot.moveLeft();
			// bot.moveUp();
			// bot.moveRight();
			// bot.refreshBrowser();
			// bot.doPirate();
			//
			// if(bot.canProduce()) { bot.produce(); }
			// bot.clickaway();
			// bot.zoomout();
			// bot.spendMoney();
			// bot.resetScreen();
			// bot.gohome();
			// bot.clickaway();
			// bot.findAllProducts();
			// bot.findAllProducts();
			// bot.getScreen("screen.png");
			// bot.gatherAll();
			// bot.buf(people.gilde);
			// bot.clearTaverne();
			// bot.bufPeople();
			// if (false)
			//bot.produce();
			while (true) {
				
				
				if (fullrestart) {
					Runnable r = new Runnable() {

						@Override
						public void run() {
							Starter st = new Starter();
							st.execCommand("killall -9 chrome");
							st.execCommand(
									"/opt/google/chrome/chrome --start-maximized https://ru.forgeofempires.com/");

						}
					};

					Thread t = new Thread(r);
					t.start();

					bot.delay(10000);

					bot.mouseMove(bot.screen.x + 1899, bot.screen.y - 5);
					bot.mouseLeftClick();
					bot.delay(5000);
					bot.mouseMove(bot.screen.x + 1515, bot.screen.y + 46);
					bot.mouseLeftClick();

				}
				
				//bot.clearTaverne();
				//bot.spendMoney();
				

				Date curtime = new Date();
				long diff = curtime.getTime() - olddtime.getTime();
				if (diff > 1000 * 60 * 60) {
					olddtime = curtime;
					bot.refreshBrowser();
					bot.delay(30000);
				}
				try {
					bot.walkTroughWorlds();
					bot.clickaway();

					// bot.getDialogBounds();
				} catch (Exception e) {
					e.printStackTrace();
					System.out.println("refresh everything");
					bot.refreshBrowser();
					bot.delay(30000);
				}
				
				
				try {
					Thread.sleep(60000);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}

		} catch (AWTException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	void goToScreen(int num) {
		int x = num % 2;
		int y = num / 2;

	}

	int curScreen = 0;

	public void resetScreen() {
		moveUpperLeft();
		curScreen = 0;
	}

	void goToNextScreen() {
		switch (curScreen) {
		case 0:
			moveLeft();
			break;
		case 1:
			moveLeft();
			break;
		case 2:
			moveUp();
			break;
		case 3:
			moveRight();
			break;
		case 4:
			moveRight();
			break;
		case 5:
			moveUp();
			break;
		case 6:
			moveLeft();
			break;
		case 7:
			moveLeft();
			break;

		default:
			break;
		}
		curScreen++;

	}

	void goToNextScreen_o() {
		switch (curScreen) {
		case 0:
			moveLeft();
			break;
		case 1:
			moveUp();
			break;
		case 2:
			moveRight();
			break;
		case 3:
			moveUp();
			break;
		case 4:
			moveLeft();
			break;
		case 5:
			moveLeft();
			break;

		default:
			break;
		}
		curScreen++;

	}

	private void moveLeft() {
		mouseMove(screen.width - 100, screen.y + 100);
		delay(1000);
		mousePress(InputEvent.BUTTON1_MASK);
		int y = 100;
		for (int i = screen.width - 100; i > screen.x * 1.3; i--) {

			mouseMove(i, screen.y + 100);

			delay(1);
		}
		mouseRelease(InputEvent.BUTTON1_MASK);

	}

	private void moveRight() {
		mouseMove(screen.x + 100, screen.height - 200);
		delay(1000);
		mousePress(InputEvent.BUTTON1_MASK);
		int y = 100;
		for (int i = screen.x + 100; i < screen.width * .8; i++) {

			mouseMove(i, screen.height - 200);

			delay(1);
		}
		mouseRelease(InputEvent.BUTTON1_MASK);

	}

	private void moveUp() {
		/*
		 * mouseMove(screen.x+100, screen.y+100);
		 * 
		 * 
		 * 
		 * 
		 * 
		 * 
		 * keyPress(KeyEvent.VK_UP); keyPress(KeyEvent.VK_LEFT); delay(5000);
		 * keyRelease(KeyEvent.VK_UP); keyRelease(KeyEvent.VK_LEFT);
		 */

		// keyPress(InputEvent.U);
		mouseMove(screen.width - 100, screen.height - 100);
		delay(1000);
		mousePress(InputEvent.BUTTON1_MASK);

		for (int i = screen.height - 100; i > screen.y + (screen.height - screen.y) / 5; i--) {

			mouseMove(screen.width - 100, i);

			delay(1);
		}
		mouseRelease(InputEvent.BUTTON1_MASK);

	}

	private void moveUpperLeft() {
		for (int z = 0; z < 3; z++) {
			mouseMove(screen.x + 100, screen.y + 100);
			delay(1000);
			mousePress(InputEvent.BUTTON1_MASK);
			int y = 100;
			for (int i = 100; i < screen.x; i++) {

				mouseMove(screen.x + i, screen.y + y);
				y++;
				delay(1);
			}
			mouseRelease(InputEvent.BUTTON1_MASK);
		}
	}

	Hashtable<Integer, Point> rathhouses = new Hashtable<>();

	private void spendMoney() {
		tell("going to spend money");
		NamedPoint p = null;
		int co = -10;
		Rectangle bnds = new Rectangle(center.x - 70, screen.y + 23, 200, 100);

		ArrayList<File> money = new ArrayList<>();
		for (File f : new File(maindir, "co").listFiles()) {
			if (f.getName().endsWith("co.png")) {
				money.add(f);
			}
		}
		p = findImages(money, bnds, 1.5);

		/*
		 * for (int i = 0; i < 13; i++) {
		 * 
		 * Rectangle bnds = new Rectangle(center.x - 100, screen.y - 50, 200,
		 * 100); bnds = null; p = findImage(getIconFile("co", i + "co.png"),
		 * bnds, 1.5); if (p != null) { co = i; break; } }
		 */

		if (p == null) {
			log("unknown amount of money");
			return;
		}
/*
		for (int i = 12; i >= 0; i--) {
			if (p.getName().endsWith(i + "co.png")) {
				co = i;
				break;
			}
		}
		if (co < 5) {
			log("just " + co + " coins");
			return;
		}
		*/
		log("You are rich, " + co + " CO we spend some money");

		Point rathausp = rathhouses.get(curworld);
		log("Search for Rathaus");
		if (rathausp == null) {
			ArrayList<File> rathaueser = new ArrayList<>();
			for (File f : new File(maindir, "co").listFiles()) {
				if (!f.getName().startsWith("rathaus")) {
					continue;
				}

				rathaueser.add(f);

			}

			rathausp = findImages(rathaueser, screen(), 1);
			if (rathausp != null) {
				rathhouses.put(curworld, rathausp);
			}
		}

		if (rathausp == null) {
			log("no rathaus " + curworld);
			return;
		}
		log("Found Rathaus");
		mouseMove(rathausp);
		mouseLeftClick();
		delay(2000);
		Point gotorathausp = findImage(getIconFile("co", "rathaus_full.png"), screen());
		if (gotorathausp != null) {
			mouseMove(gotorathausp);
			mouseLeftClick();
			delay(3000);
		}

		mouseMove(center.x - 287, center.y - 175);
		mouseLeftClick();
		delay(2000);

		mouseMove(center.x + 337, center.y + 96);
		mouseLeftClick();
		delay(5000);
		openReciversWorld();

	}

	private Rectangle screen() {
		// TODO Auto-generated method stub
		return new Rectangle(screen.x, screen.y, screen.width - screen.x, screen.height - screen.y);
	}

	private void openReciversWorld() {
		Point p = new Point(center.x + 393, center.y - 85);
		mouseMove(p);
		mouseLeftClick();
		delay(3000);
		p = new Point(center.x + 234, center.y + 218);
		mouseMove(p);
		mouseLeftClick();
		delay(3000);
		p = new Point(center.x+265, center.y-88);
		mouseMove(p);
		mouseLeftClick();
		delay(500);
		mouseLeftClick();
		delay(3000);
		clickaway();
		gohome();

	}

	Point refreshButton = null;

	public void refreshBrowser() {
		tell("will refresh borwser");
		if (refreshButton == null) {
			refreshButton = findImage(getIconFile("browser", "refresh.png"),
					new Rectangle(screen.x, screen.y - 100, 100, 100));
			if (refreshButton == null) {
				log("no refresh button, strange..");
				return;
			}
		}
		mouseMove(refreshButton);
		delay(500);
		mouseLeftClick();

	}

	private boolean canProduce() {
		zoomout();
		Point p = findImage(getIconFile("produce", "free_building.png"),
				new Rectangle(screen.width - 100, screen.y, 150, center.y));
		if (p == null) {
			return false;
		}
		mouseMove(p);
		mouseLeftClick();
		delay(6000);
		mouseMove(new Point(center.x, center.y + 10));
		delay(1000);
		return true;
	}

	private void produce() {

		Point p = findImage(getIconFile("produce", "info_canstart.png"),
				new Rectangle(center.x - 29, center.y - 72, 150, 100));
		p = center;
		if (p == null) {
			ArrayList<Point> tosearch = new ArrayList<>();
			for (int i = 0; i < 100; i += 5)
				for (int y = 0; y < 100; y += 5) {
					Point cp;
					tosearch.add(cp = new Point(p.x - 50 + i, p.y - 50 + y));
					log("moveto " + cp);
					mouseMove(cp);
					p = findImage(getIconFile("produce", "info_canstart.png"),
							new Rectangle(cp.x - 50, cp.y - 50, 100, 100));
					if (p == null) {
						continue;
					} else
						break;

				}
		}
		if (p == null) {
			return;
		}
		delay(2000);
		mouseLeftClick();
		delay(rand.nextInt(7000) + 5000);
		chooseProduct();

	}

	Random rand = new Random();

	private void chooseProduct() {

		Rectangle dbounds = getDialogBounds();
		if (dbounds == null) {
			return;
		}
		int difx;
		log("x:" + (difx = (dbounds.x - center.x)));
		int dify;
		log("y:" + (dify = (dbounds.y - center.y)));
		// if(true) return;
		// surround(dbounds);
		moveaway();
		Point p = findImage(getIconFile("produce", "buildings/lab.png"),
				new Rectangle(center.x - 200, center.y - 50, 300, 300));
		if (p == null) {

			p = findImage(getIconFile("produce", "buildings/detector_money.png"),
					new Rectangle(center.x - 200, center.y - 50, 600, 600));

			if (p == null) {

				Rectangle r;
				p = findImage(getIconFile("produce", "buildings/football_detector.png"),
						r = new Rectangle(center.x - 100, center.y - 50, 500, 300), 2);
				// getScreen(r, "dialogbounds.png");
				if (p == null) {

					if (difx > -340 && difx < -310 && dify > -210 && dify < -185) {
						// x:-324
						// PT0S
						// y:-197
						// resource
						log("resource");
						cooseCell(dbounds, 2);
						delay(100);
						mouseLeftClick();
						return;
					} else {
						p = findImage(getIconFile("produce", "fighter.png"), new Rectangle(dbounds.x - 100,
								center.y - 100, dbounds.width + 120, dbounds.height + 100));
						if (p != null) {
							mouseMove(p);
							log("fighter");
							delay(100);
							mouseLeftClick();
							return;
						} else // (difx > -440 && difx < -420 && dify > -200 &&
								// dify < -160)
						{
							// x:-435
							// PT0S
							// y:-176
							log("towar");
							// cooseCell(dbounds,2);
							mouseMove(center.x - 50, center.y + 100);
							delay(100);
							mouseLeftClick();
							return;
						} /*
							 * else { log("unknown building"); clickaway();
							 * return; }
							 */
					}

				}
				cooseCell(dbounds, 6);
				delay(100);
				mouseLeftClick();
				return;
			}
			cooseCell(dbounds, 6);
			delay(100);
			mouseLeftClick();
			return;
		}
		// 15 min
		cooseCell(dbounds, 5);
		delay(100);
		mouseLeftClick();

	}

	private void moveaway() {
		mouseMove(screen.x + 100, screen.y + 100);

	}

	private void cooseCell(Rectangle dbounds, int i) {
		int idx = i;
		int sixtel = dbounds.width / 6;
		int quater = dbounds.height / 4;
		int nx;
		int ny;
		switch (idx) {
		case 1:
			nx = 1;
			ny = 1;
			break;
		case 2:
			nx = 3;
			ny = 1;
			break;
		case 3:
			nx = 5;
			ny = 1;
			break;
		case 4:
			nx = 1;
			ny = 3;
			break;
		case 5:
			nx = 3;
			ny = 3;
			break;
		case 6:
			nx = 5;
			ny = 3;
			break;
		default:
			System.err.println("no such option " + idx);
			return;
		}

		mouseMove(dbounds.x + nx * sixtel, dbounds.y + ny * quater);

	}

	private Rectangle getDialogBounds() {
		Point p = findImage(getIconFile("dialog", "icon_close.png"),
				new Rectangle(center.x + 50, center.y - 500, 500, 500));
		if (p == null) {
			log("no dialog");
			return null;
		}

		Rectangle r = new Rectangle(center.x - (p.x - center.x), p.y, (p.x - center.x) * 2, (center.y - p.y) * 2);

		// System.out.println(r);
		// mouseMove(r.x,r.y+r.height);

		return r;
	}

	private void zoomout() {

		mouseMove(screen.x + 100, screen.y + 100);
		mouseWheel(10);
		/*
		 * log("try zoomout"); Point p = findImage(getIconFile("sys",
		 * "zoomouticon.png"), new Rectangle(screen.x , screen.height - 100,
		 * 200, 200),3); if (p == null) { p = findImage(getIconFile("sys",
		 * "zoomout2.png"), new Rectangle(screen.x , screen.height - 100, 200,
		 * 200),3); if (p == null) { log("no need, all zoomed"); return; } }
		 * 
		 * 
		 * log("will zoomout"); //p=new Point(p.x+25,p.y); mouseMove(p);
		 * mouseLeftClick(); delay(500);
		 */
	}

	private void gatherPirate() {

		mouseMove(center.x, center.y + 100);
		mouseLeftClick();
		delay(1000);
		clickaway();

		// storePrice(f);
	}

	int trials;
	private int curworld;
	Point pirat = null;
	Point piratupperbound = null;

	private void doPirate() {
		if(true)
return ;
		if (pirat == null) {
			for (File f : new File(maindir, "pirat").listFiles()) {
				if (!f.getName().startsWith("pirat_")) {
					continue;
				}
				Rectangle r;
				pirat = findImage(f, r = new Rectangle(screen.x - 50, screen.y + 100, 200, screen.height - 50));
				pirat = findImage(f);
				saveImage(r, new File(f.getName()));
				saveImage(screen(), new File("screen.png"));
				if (pirat != null) {

					break;
				}
			}
		}

		if (pirat == null) {
			log("Net pirata!");
			return;
		}

		mouseMove(pirat);
		mouseLeftClick();
		delay(3000);

		if (pirat == null) {
			for (File f : new File(maindir, "pirat").listFiles()) {
				if (!f.getName().startsWith("pirat_")) {
					continue;
				}
				pirat = findImage(f, new Rectangle(screen.x - 50, screen.y + 100, 200, screen.height - 50));
				if (pirat != null) {

					break;
				}
			}
		}

		if (pirat == null) {
			log("Net pirata!");
			return;
		}
		Point p = findImage(getIconFile("pirat", "upperbound.png"),
				new Rectangle(center.x, center.y, screen.width, screen.height));

		/*
		 * p = findImage(getIconFile("pirat", "icon_close_corner.png"), new
		 * Rectangle(center.x, center.y, screen.width, screen.height));
		 */
		if (p == null) {
			System.out.println("pirat estx, a sokro net");
			if (trials-- > 0)
				doPirate();
			return;
		}
		p.x = p.x + 185;
		p.y = p.y + 20;
		mouseMove(p);
		/*
		 * if(true) { return; }
		 */
		trials = 3;

		mouseMove(p.x - 290, p.y + 140);
		mouseLeftClick();
		delay(1000);
		mouseMove(p.x - 200, p.y + 110);
		mouseLeftClick();
		delay(1000);
		mouseMove(p.x - 90, p.y + 145);
		mouseLeftClick();
		delay(1000);
		mouseMove(p.x - 150, p.y + 220);
		mouseLeftClick();
		delay(1000);
		mouseMove(p.x - 95, p.y + 280);
		mouseLeftClick();
		delay(1000);
		mouseMove(p.x - 280, p.y + 255);
		mouseLeftClick();
		delay(1000);

		gatherPirate();
	}

	private void gatherAll() {

		log("search for ready buildings");
		zoomout();
		Point p = findReadyBuilding();

		log("done " + p);

		if (p == null)
			return;

		mouseMove(p);
		boolean found = false;
		Point f = null;
		for (int i = 0; i < 100; i += 3) {
			mouseMove(p.x, p.y + i);
			delay(100);
			if (null != (f = findImage(getIconFile("gather", "info_finished.png"),
					new Rectangle(p.x, p.y - 100, 200, 100)))) {
				found = true;

				break;
			}
		}
		if (!found) {
			log("No ready Buildings, return");
			return;
		}
		log("Willslide");
		// if(true) return;
		mousePress(InputEvent.BUTTON1_DOWN_MASK);
		delay(5000);
		/*
		 * int my = p.y; for (int x = p.x; x > screen.x + 100; x--) { if (my >
		 * screen.y + 50) my--; mouseMove(x, my); delay(3); }
		 */
		int ystep = 30;
		int step = Math.abs(screen.x + 100 - (screen.width - 50)) / ystep;
		int slidestep = 4;
		for (int y = screen.y + 50; y < screen.height - 50;)
		// for(int i=screen.x+100;i<screen.width;i+=30)
		{
			int shouldbe = y + ystep;

			int start = screen.x + 100;
			int end = screen.width - 50;

			for (int i = start; i < end; i += slidestep) {
				mouseMove(i, y);
				delay(1);
				if (i % step == 0) {
					y++;
				}

			}
			y = shouldbe;
			end = screen.x + 100;
			start = screen.width - 50;

			for (int i = start; i > end; i -= slidestep) {
				mouseMove(i, y);
				delay(1);
				if (i % step == 0) {
					y++;
				}
			}
		}
		mouseRelease(InputEvent.BUTTON1_DOWN_MASK);

	}

	private void storePrice(Point f) {

		BufferedImage image = createScreenCapture(new Rectangle(center.x - 200, center.y - 200, 400, 400));

		File dir = new File("piratski");

		if (!dir.exists()) {
			dir.mkdirs();
		}
		Random r = new Random();
		try {
			ImageIO.write(image, "png", new File(dir, (new Date().getTime()) + ".png"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private Point findReadyBuilding() {
		tell("Search for ready buildings");
		// Rectangle screen = new Rectangle(this.screen.x, this.screen.y + 100,
		// this.screen.width, this.screen.height);

		ArrayList<File> readyicons = new ArrayList<>();
		for (File f : new File(maindir, "gather").listFiles()) {
			if (f.getName().startsWith("icon_")) {
				readyicons.add(f);
			}
		}
		return findImages(readyicons, screen(), 1.5);
		/*
		 * Point p = findImage(getIconFile("gather", "icon_money.png"),
		 * screen());
		 * 
		 * if (p == null) p = findImage(getIconFile("gather",
		 * "icon_moneybuf.png"), screen()); if (p == null) p =
		 * findImage(getIconFile("gather", "icon_molotok.png"), screen()); if (p
		 * == null) p = findImage(getIconFile("gather", "icon_sword.png"),
		 * screen());
		 * 
		 * if (p == null) p = findImage(getIconFile("gather",
		 * "icon_money_buf2.png"), screen()); if (p == null) p =
		 * findImage(getIconFile("gather", "icon_kasten.png"), screen()); if (p
		 * == null) p = findImage(getIconFile("gather", "icon_kasten2.png"),
		 * screen()); if (p == null) p = findImage(getIconFile("gather",
		 * "icon_kasten_buf.png"), screen()); return p;
		 */
	}

	private void tell(String string) {
		System.out.println(string);

	}

	private void walkTroughWorlds() {
		ArrayList<Integer> worlds = new ArrayList<>();
		for (int i = 1; i <= 14; i++) {
			if (i != 11)
				worlds.add(i);
		}
		// worlds.clear();
		worlds.add(7);
		Collections.shuffle(worlds);
		for (int i : worlds) {
			clickaway();
			log("will change the world to " + i);
			changeWorld(i);
			delay(60000);
			zoomout();
			startQuest();
			if (curworld !=7)
			spendMoney();
			delay(1000);
			resetScreen();
			clearTaverne();

			delay(5000);
			// if(this.curworld==7)
			for (int x = 0; x < 6; x++) {

				log("lets see what to gather here");
				gatherAll();
				delay(2000);
				goToNextScreen();
				delay(1000);
			}

			gohome();

			log("will buf people ");
			delay(1000);
			bufPeople();
			zoomout();

			log("will produce ");
			int z = 30;
			while (z-- > 0 && canProduce()) {
				produce();
				delay(2000);
			}

			refreshBrowser();
			delay(10000);
			zoomout();
			delay(2000);
			// spendMoney();

			changeWorld(7);
			delay(1000);
			trials = 3;
			doPirate();
			delay(1000);
			gatherAll();
			delay(2000);

		}

	}

	private void startQuest() {
		
		mouseMove(screen.x+42, screen.y+252);
		mouseLeftClick();
		delay(1000);
		Random r=new Random();
		if(r.nextBoolean()){
		mouseMove(screen.x+522, screen.y+591);
		}else
		{
			mouseMove(screen.x+133, screen.y+590);	
		}
		//mouseLeftClick();
		delay(1000);
		
		//(screen.x+42, screen.y+252) center: (center.x-915, center.y-214)
		
		//right: (screen.x+522, screen.y+591) center: (center.x-435, center.y+125)
		//left: (screen.x+133, screen.y+590) center: (center.x-824, center.y+124)

	}

	private void changeWorld(int world) {
		mouseMove(screen.width - 10, screen.y + 10);
		mouseLeftClick();
		delay(5000);
		mouseMove(center.x + 113, center.y + 71);
		mouseLeftClick();
		delay(5000);
		mouseMove(center.x + 305, center.y - 19);
		mouseLeftClick();
		delay(5000);

		switch (world) {
		case 1:
			mouseMove(center.x - 162, center.y - 195);
			break;
		case 2:
			mouseMove(center.x - 12, center.y - 197);
			break;
		case 3:
			mouseMove(center.x + 158, center.y - 187);
			break;
		case 4:
			mouseMove(center.x - 146, center.y - 137);
			break;
		case 5:
			mouseMove(center.x - 8, center.y - 143);
			break;
		case 6:
			mouseMove(center.x + 179, center.y - 146);
			break;
		case 7:
			mouseMove(center.x - 165, center.y - 87);
			break;
		case 8:
			mouseMove(center.x + 9, center.y - 84);
			break;
		case 9:
			mouseMove(center.x + 148, center.y - 89);
			break;
		case 10:
			mouseMove(center.x - 174, center.y - 36);
			break;
		case 11:
			mouseMove(center.x + 25, center.y - 32);
			break;
		case 12:
			mouseMove(center.x + 161, center.y - 33);
			break;
		case 13:
			mouseMove(center.x - 165, center.y + 22);
			break;

		default:
			break;
		}

		mouseLeftClick();
		delay(30000);
		clickaway();
		delay(500);
		zoomout();
		curworld = world;

	}

	enum people {
		friends, neighbours, gilde
	}

	public void bufPeople() {

		if (curworld !=7){
		buf(people.friends);
		delay(2000);

		buf(people.gilde);
		delay(2000);

		}
			buf(people.neighbours);
	}

	private void buf(people friends) {

		selectPeople(friends);

		// rewind
		mouseMove(screen.x + 240, screen.height - 25);

		mouseLeftClick();
		delay(3000);
		FImage peopleframe = null;
		// bufbyimage(peopleframe);

		Random r = new Random();
		for (int y = 0; y < 24; y++) {

			Rectangle peoplerect = new Rectangle(screen.x + 245, screen.height - 140, 535, 130);

			FImage peopleimg = ImageUtilities.createFImage(createScreenCapture(peoplerect));

			for (int i = 0; i < 5; i++) {
				if ((friends == people.neighbours) && y == 0 && i == 0) {
					continue;
				}
				log("I buf " + friends + " nr:" + ((y * 5) + (i + 1)));
				// move to next friend
				mouseMove((screen.x + 275) + i * 110, screen.height - 10);
				mouseLeftClick();
				clickaway();
				delay(2000);
				if (friends == people.friends) {

					mouseMove((screen.x + 347) + i * 110, screen.height - 25);
					mouseLeftClick();
					delay(2000);
					clickaway();
				}
				delay(2000);
				if (r.nextInt(10) == 0)
					clickaway();
				else {
					delay(500);
				}

			}
			clickaway();
			delay(3000);
			mouseMove((screen.x + 910), screen.height - 60);

			if (peopleframe != null && sameAs(peopleframe, peopleimg)) {
				return;
			}
			mouseLeftClick();
			delay(1000);

			peopleframe = peopleimg;
		}

	}

	private void bufbyimage(FImage peopleframe) {
		for (int y = 0; y < 20; y++) {
			// Object people = getScreen(new Rectangle(screen.x + 245,
			// screen.height
			// - 140,535,130),"buttons.png");
			Rectangle peoplerect = new Rectangle(screen.x + 245, screen.height - 140, 535, 130);

			FImage peopleimg = ImageUtilities.createFImage(createScreenCapture(peoplerect));
			FImage buttons = peopleimg.extractROI(0, 100, peoplerect.width, 30);
			/*
			 * try { ImageUtilities.write(peopleimg, new File("people.png"));
			 * ImageUtilities.write(buttons, new File("buttons.png")); } catch
			 * (IOException e) { // TODO Auto-generated catch block
			 * e.printStackTrace(); }
			 */

			delay(1000);
			for (int i = 0; i < 5; i++) {
				Point nextfriend = matchImage(buttons, getIconFile("help", "button_help.png"), 1.5);
				if (nextfriend != null) {
					mouseMove(nextfriend.x + screen.x + 245, nextfriend.y + screen.height - 40);
					mouseLeftClick();
					clickaway();
					delay(200);
					delay(800);
				} else {
					break;
				}
			}

			mouseMove((screen.x + 900), screen.height - 75);
			mouseLeftClick();
			delay(1000);

			if (peopleframe != null && sameAs(peopleframe, peopleimg)) {
				return;
			}

			peopleframe = peopleimg;

		}
	}

	private void gohome() {
		Point gohomebut = findImage(getIconFile("sys", "gohome.png"),
				new Rectangle(screen.x, screen.height - 200, 400, 400));

		if (gohomebut == null) {

			gohomebut = findImage(getIconFile("sys", "gohome2.png"),
					new Rectangle(screen.x, screen.height - 200, 400, 400));

			if (gohomebut == null) {
				log("no home button");
				return;
			}
		}
		mouseMove(gohomebut);
		mouseLeftClick();
		delay(10000);
		clickaway();
	}

	private void clickaway() {

		Point closebut = findImage(getIconFile("dialog", "button_close2.png"),
				new Rectangle(center.x, center.y, 500, 300));
		if (closebut == null) {
			// log("no closebutton");
			closebut = findImage(getIconFile("dialog", "icon_close.png"),
					new Rectangle(center.x, center.y - 500, 500, 500));
			if (closebut == null) {
				// log("no closeicon");
				closebut = findImage(getIconFile("dialog", "close2.png"),
						new Rectangle(center.x, center.y - 500, 500, 500));
				if (closebut == null) {
					// log("no closeicon");
					closebut = findImage(getIconFile("dialog", "close3.png"),
							new Rectangle(center.x - 1500, center.y, 300, 500));
					if (closebut == null) {
						// log("no bigdialog");
					}
				}
			}

		}
		if (closebut != null) {
			log("dilaog closed");
			mouseMove(closebut);
			mouseLeftClick();
			delay(200);
		}

		mouseMove(screen.x + 420, screen.y + 10);
		mouseLeftClick();

	}

	private void selectPeople(people friends) {

		Point toclick = null;

		switch (friends) {
		case friends:
			toclick = new Point(screen.x + 860, screen.height - 140);
			break;
		case neighbours:
			toclick = new Point(screen.x + 740, screen.height - 140);
			break;
		case gilde:
			toclick = new Point(screen.x + 800, screen.height - 140);
			break;

		default:
			toclick = null;
			break;
		}
		if (toclick != null) {

			mouseMove(toclick);

			delay(200);
			mouseLeftClick();
		}

	}

	private File getIconFile(String string, String string2) {

		return new File(new File(maindir, string), string2);
	}

	boolean testmode = false;
	boolean testmode2 = true;

	private void getGameCoordinates() {

		if (testmode) {

			screen = new Rectangle(2, 91, 1917, 1023);

			// KDE screen = new Rectangle(1294, 138, 1294 + 1886, 1064);

			// new Rectangle(1934, 138, 3821, 1064);
		} else if (testmode2) {
			// screen = new Rectangle(15, 114, 6 + 1886, 1052);
			// screen = new Rectangle(15, 136, 6 + 1886, 1064);
			// screen = new Rectangle(40, 124, 6 + 1898, 1032);
			// KDE
			// screen = new Rectangle(15, 124, 6 + 1886, 1052);
			// XFCE
			screen = new Rectangle(2, 91, 1917, 1023);
		} else {
			if (screen == null) {
				Point topleft = findImage(new File(new File(maindir, "sys"), "corner_top.png"));
				log("topleft " + topleft);
				Point bottomleft = findImage(new File(new File(maindir, "sys"), "molotok.png"),
						new Rectangle(topleft.x - 50, 200, 200, 1600));
				log("bottomleft " + bottomleft);
				Point topright = findImage(new File(new File(maindir, "sys"), "corner_right.png"));
				log("topright " + topright);

				screen = new Rectangle(topleft.x, topleft.y, topright.x, bottomleft.y);
			}
		}
		System.out.println(screen);
		center = new Point(screen.x + (screen.width - screen.x) / 2, screen.y + (screen.height - screen.y) / 2);
		// System.out.println(screen);
		// surround(screen);

	}

	@Override
	public void keyPressed(NativeKeyEvent e) {
		// System.out.println(e.getKeyCode());
	}

	@Override
	public void keyReleased(NativeKeyEvent e) {

		Point loc = MouseInfo.getPointerInfo().getLocation();
		System.out.println("current location=(" + loc.getX() + "," + loc.getY() + ")");
		System.out.println("(screen.x+" + (loc.x - screen.x) + ", screen.y+" + "" + (loc.y - screen.y)
				+ ") center: (center.x" + ((loc.x - center.x) >= 0 ? "+" : "") + (loc.x - center.x) + ", center.y" + ""
				+ ((loc.y - center.y) >= 0 ? "+" : "") + (loc.y - center.y) + ")");

	}

	private void getScreen(String fname) {
		getScreen(screen(), fname);
	}

	private void getScreen(Rectangle r, String fname) {
		if (r == null) {
			r = screen();
		}
		BufferedImage img = super.getPicture(r);

		try {
			ImageIO.write(img, "png", new File(fname));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private void findAllProducts() {

		Vector<FImage> series = new Vector<>();
		log("start");
		for (int i = 0; i < 2; i++) {
			series.add(ImageUtilities.createFImage(super.getPicture(screen())));
			delay(2000);
		}
		Vector<FImage> differences = new Vector<>();
		for (int i = 1; i < series.size(); i++) {
			differences.add(series.get(i - 1).subtract(series.get(i)));
		}
		FImage sum = differences.get(0);
		for (int i = 1; i < differences.size(); i++) {
			// sum=sum.add(differences.get(i));
			// sum=sum.multiply(differences.get(i));
		}
		try {
			ImageUtilities.write(sum, new File("example.png"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		encircle_movements(sum);

		log("end");

		/*
		 * log("start"); BufferedImage pic1 = super.getPicture(screen()); log(
		 * "picture taken"); delay(5000); log("start 2"); BufferedImage pic2 =
		 * super.getPicture(screen()); log("picture2 taken"); FImage template1 =
		 * ImageUtilities.createFImage(pic1); log("template1 created"); FImage
		 * template2 = ImageUtilities.createFImage(pic2); log(
		 * "template2 created");
		 * 
		 * FImage template3 = template1.subtract(template2);
		 * 
		 * encircle movements(template3);
		 * 
		 * log("subtracted");
		 * 
		 * try { ImageUtilities.write(template3, new File("example.png")); }
		 * catch (IOException e) { // TODO Auto-generated catch block
		 * e.printStackTrace(); } log("end");
		 */
	}

	private void encircle_movements(FImage sum) {

		float[][] pixels = sum.pixels;
		for (int i = 0; i < pixels.length; i++)
			for (int y = 0; y < pixels[i].length; y++) {
				float pixel = pixels[i][y];
				if (pixel > 0) {
					Float col = 256f;

					Point2dImpl p1 = new Point2dImpl(i - 50, y - 50);
					Point2dImpl p2 = new Point2dImpl(i + 100, y - 50);
					Point2dImpl p3 = new Point2dImpl(i + 100, y + 100);
					Point2dImpl p4 = new Point2dImpl(i - 50, y + 100);
					Polygon p = new Polygon(p1, p2, p3, p4);
					sum.drawPolygon(p, col);
				}
			}

	}

	private static int[][] convertTo2DWithoutUsingGetRGB(BufferedImage image) {

		final byte[] pixels = ((DataBufferByte) image.getRaster().getDataBuffer()).getData();
		final int width = image.getWidth();
		final int height = image.getHeight();
		final boolean hasAlphaChannel = image.getAlphaRaster() != null;

		int[][] result = new int[height][width];
		if (hasAlphaChannel) {
			final int pixelLength = 4;
			for (int pixel = 0, row = 0, col = 0; pixel < pixels.length; pixel += pixelLength) {
				int argb = 0;
				argb += (((int) pixels[pixel] & 0xff) << 24); // alpha
				argb += ((int) pixels[pixel + 1] & 0xff); // blue
				argb += (((int) pixels[pixel + 2] & 0xff) << 8); // green
				argb += (((int) pixels[pixel + 3] & 0xff) << 16); // red
				result[row][col] = argb;
				col++;
				if (col == width) {
					col = 0;
					row++;
				}
			}
		} else {
			final int pixelLength = 3;
			for (int pixel = 0, row = 0, col = 0; pixel < pixels.length; pixel += pixelLength) {
				int argb = 0;
				argb += -16777216; // 255 alpha
				argb += ((int) pixels[pixel] & 0xff); // blue
				argb += (((int) pixels[pixel + 1] & 0xff) << 8); // green
				argb += (((int) pixels[pixel + 2] & 0xff) << 16); // red
				result[row][col] = argb;
				col++;
				if (col == width) {
					col = 0;
					row++;
				}
			}
		}

		return result;
	}

}
