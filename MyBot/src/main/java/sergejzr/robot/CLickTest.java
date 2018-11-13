package sergejzr.robot;
import java.awt.AWTException;
import java.awt.Robot;
import java.awt.event.InputEvent;

public class CLickTest {
public static void main(String[] args) {
	try {
		Robot r=new Robot();
		
		r.mouseMove(30, 400);
		r.mousePress(InputEvent.BUTTON1_DOWN_MASK);
		r.mouseRelease(InputEvent.BUTTON1_DOWN_MASK);
	} catch (AWTException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	
}

}
