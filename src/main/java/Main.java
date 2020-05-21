import se.vidstige.jadb.JadbException;

import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;

public class Main {
    /**
     * Plays a single round of the Android game Stick Hero.
     * @param args
     * @throws IOException
     * @throws JadbException
     * @throws InterruptedException
     */
    public static void main(String[] args) throws IOException, JadbException, InterruptedException {
        ADBWrapper adbWrapper = new ADBWrapper();
        PlatformBoundsFinder platformBoundsFinder = new PlatformBoundsFinder();

        adbWrapper.takeScreenshot();
        File f = new File("src/main/resources/screencap.png");
        platformBoundsFinder.setScreenshot(ImageIO.read(f));
        int[] bounds = platformBoundsFinder.getPlatformBounds(1330);

        int center = (bounds[3] + bounds[2])/2;
        int distance = center - bounds[1];
        adbWrapper.pressAndHold(distance);
    }
}
