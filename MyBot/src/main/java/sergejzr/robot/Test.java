package sergejzr.robot;

import java.awt.AWTException;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.jnativehook.keyboard.NativeKeyEvent;

public class Test extends MyBot{
	public Test() throws AWTException {
		super();
		// TODO Auto-generated constructor stub
	}

	public static void main(String[] args) {
		Test t;
		try {
			t = new Test();
			t.run();
		} catch (AWTException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	private void run() {
		Logger logger = Logger.getLogger("org.jnativehook");
		logger.setLevel(Level.WARNING);

		int o=80000;
		int u=400000;
		int j=u*o;
		
		int z=0;
		int g=-1;
		
		if(g-z==0){
			g=-1;
		}else{
			g=-1*5/5;
		}
		
		log("start");
		
		
		for(int i=j;i>0;i--)
		{
			z=z*-1;
		}
		log("time multiplication");
		for(int i=j;i>0;i--)
		{
			z=-z;
		}
		log("timr optimised");
		
		for(int i=j;i>0;i--)
		{
			if(z>g+i)
			z=z*-1;
			else
			z=z*1;
		}
		log("time multiplication 1");
		for(int i=j;i>0;i--)
		{
			z=z*g;
		}
		log("time multiplication 2");
	}



	@Override
	public void keyPressed(NativeKeyEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyReleased(NativeKeyEvent e) {
		// TODO Auto-generated method stub
		
	}

}
