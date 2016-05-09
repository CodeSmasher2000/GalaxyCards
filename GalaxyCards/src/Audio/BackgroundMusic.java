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
	
//	  private final int BUFFER_SIZE = 128000;
//	    private File soundFile;
//	    private AudioInputStream audioStream;
//	    private AudioFormat audioFormat;
//	    private SourceDataLine sourceLine;
	    
		
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
//	
//		@Override
//		public void run() {
//			playSound();
//			
//		}
//
//	    /**
//	     * @param filename the name of the file that is going to be played
//	     */
//	    public void playSound() {
//
//	       
//
//	        try {
//	            soundFile = new File("C:/lol/GalxyCards8bit.wav");
//	        } catch (Exception e) {
//	            e.printStackTrace();
//	            System.exit(1);
//	        }
//
//	        try {
//	            audioStream = AudioSystem.getAudioInputStream(soundFile);
//	        } catch (Exception e){
//	            e.printStackTrace();
//	            System.exit(1);
//	        }
//
//	        audioFormat = audioStream.getFormat();
//
//	        DataLine.Info info = new DataLine.Info(SourceDataLine.class, audioFormat);
//	        try {
//	            sourceLine = (SourceDataLine) AudioSystem.getLine(info);
//	            sourceLine.open(audioFormat);
//	        } catch (LineUnavailableException e) {
//	            e.printStackTrace();
//	            System.exit(1);
//	        } catch (Exception e) {
//	            e.printStackTrace();
//	            System.exit(1);
//	        }
//
//	        sourceLine.start();
//
//	        int nBytesRead = 0;
//	        byte[] abData = new byte[BUFFER_SIZE];
//	        while (nBytesRead != -1) {
//	            try {
//	                nBytesRead = audioStream.read(abData, 0, abData.length);
//	            } catch (IOException e) {
//	                e.printStackTrace();
//	            }
//	            if (nBytesRead >= 0) {
//	                @SuppressWarnings("unused")
//	                int nBytesWritten = sourceLine.write(abData, 0, nBytesRead);
//	            }
//	        }
//
//	        sourceLine.drain();
//	        sourceLine.close();
//	    }
//
//	
//	}
	
	
//	public void clientMusic(){
//		try {
//		    File yourFile = new File("C:/lol/GalxyCardsSoundTrack.wav");
//		    AudioInputStream stream;
//		    AudioFormat format;
//		    DataLine.Info info;
//		    Clip clip;
//
//		    stream = AudioSystem.getAudioInputStream(yourFile);
//		    format = stream.getFormat();
//		    info = new DataLine.Info(Clip.class, format);
//		    clip = (Clip) AudioSystem.getLine(info);
//		    clip.open(stream);
//		    clip.start();
//		}
//		catch (Exception e) {
//		    e.printStackTrace();
//		}
//
////		try {
////			URL url = new File("C:/lol/GalxyCardsSoundTrack.wav").toURI().toURL();
////			clientBackground = Applet.newAudioClip(url);
////		} catch (MalformedURLException e) {
////		}
////
////		if (clientBackground != null) {
////			clientBackground.play();
////		}
//	}


