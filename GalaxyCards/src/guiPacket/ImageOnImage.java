package guiPacket;

import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.net.URL;
import java.util.Random;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;
import javax.swing.Timer;

public class ImageOnImage {

    ImageOnImage(final BufferedImage bg, BufferedImage fg) {
        final BufferedImage scaled = new BufferedImage(
            fg.getWidth()/2,fg.getHeight()/2,BufferedImage.TYPE_INT_RGB);
        Graphics g = scaled.getGraphics();
        g.drawImage(fg,0,0,scaled.getWidth(),scaled.getHeight(),null);
        g.dispose();

        final int xMax = bg.getWidth()-scaled.getWidth();
        final int yMax = bg.getHeight()-scaled.getHeight();

        final JLabel label = new JLabel(new ImageIcon(bg));

        ActionListener listener = new ActionListener() {

            Random random = new Random();

            public void actionPerformed(ActionEvent ae) {
                Graphics g = bg.getGraphics();
                int x = random.nextInt(xMax);
                int y = random.nextInt(yMax);

                g.drawImage( scaled, x, y, null );
                g.dispose();

                label.repaint();
            }
        };

        Timer timer = new Timer(1200, listener);
        timer.start();

        JOptionPane.showMessageDialog(null, label);
    }

    public static void main(String[] args) throws Exception{
        URL url1 = new URL("C:/Users/13120dde/Documents/GitHub/GalaxyCards/GalaxyCards/files/pictures");
        final BufferedImage image1 = ImageIO.read(url1);

        URL url2 = new URL("C:/Users/13120dde/Documents/GitHub/GalaxyCards/GalaxyCards/files/pictures");
        final BufferedImage image2 = ImageIO.read(url2);

        //Create the frame on the event dispatching thread
        SwingUtilities.invokeLater(new Runnable(){
            @Override
            public void run() {
                new ImageOnImage(image2, image1);
            }
        });
    }
}