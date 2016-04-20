package Audio;

import java.applet.Applet;
import java.applet.AudioClip;
import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;


public class BackgroundMusic {
	private AudioClip clientBackground = null;
	
	public void clientMusic(){
		try {
			URL url = new File("C:/lol/.wav").toURI().toURL();
			clientBackground = Applet.newAudioClip(url);
		} catch (MalformedURLException e) {
		}

		if (clientBackground != null) {
			clientBackground.play();
		}
	}
}
