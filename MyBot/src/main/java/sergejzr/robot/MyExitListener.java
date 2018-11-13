package sergejzr.robot;

import org.jnativehook.keyboard.NativeKeyEvent;
import org.jnativehook.keyboard.NativeKeyListener;

public class MyExitListener implements NativeKeyListener {

	private MyBot bot;

	public MyExitListener(MyBot myBot) {
		this.bot=myBot;
		}

	@Override
	public void nativeKeyPressed(NativeKeyEvent e) {
		// TODO Auto-generated method stub
		bot.keyPressed(e);
	}

	@Override
	public void nativeKeyReleased(NativeKeyEvent e) {
		if(e.getKeyCode()==1)
		{
			System.out.println("User exit signal");
			System.exit(0);
		}else
		{
			bot.keyReleased(e);
		}

	}

	@Override
	public void nativeKeyTyped(NativeKeyEvent e) {
		

	}

}
