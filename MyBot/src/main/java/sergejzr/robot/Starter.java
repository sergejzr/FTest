package sergejzr.robot;

import java.io.BufferedReader;
import java.io.InputStreamReader;

public class Starter {
	Process p;
	public static void main(String[] args) {
	
//execCommand("killall -9 chrome");
//execCommand("/opt/google/chrome/chrome --start-maximized https://ru.forgeofempires.com/");

	}
	
	public  void execCommand(String str)
	{
		try{
			if(p!=null){ p.destroy(); p=null;}
			p = Runtime.getRuntime().exec(str);
			p.waitFor();
/*
			BufferedReader reader = new BufferedReader(new InputStreamReader(p.getInputStream()));
	StringBuilder sb=new StringBuilder();
			String line = "";
			while ((line = reader.readLine()) != null) {
				sb.append(line + "\n");
			}*/
			//System.out.println(sb.toString());
			p.destroy(); p=null;
		}catch(Exception e)
		{
			e.printStackTrace();
		}
	
	}
}


