package Audio;

import java.applet.Applet;
import java.applet.AudioClip;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

import javax.sound.sampled.*;


public class BackgroundMusic{
	private AudioClip clientBackground = null;
	
	    
		
	   public void clientMusic() {
			try {
				URL url = new File("C:/lol/GalxyCards16bit.wav").toURI().toURL();
				clientBackground = Applet.newAudioClip(url);
			} catch (MalformedURLException e) {
			}

			if (clientBackground != null) {
				clientBackground.play();
			}		
		}
}


