import se.vidstige.jadb.JadbConnection;
import se.vidstige.jadb.JadbDevice;
import se.vidstige.jadb.JadbException;
import se.vidstige.jadb.RemoteFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * This class provides methods that wrap ADB commands.
 */
public class ADBWrapper {
    private JadbConnection connection;
    private JadbDevice device;

    /**
     * Creates a new instance and establishes the ADB connection.
     *
     * @throws IOException
     * @throws JadbException
     */
    public ADBWrapper() throws IOException, JadbException {
        connection = new JadbConnection();
        // make sure to have connected only your testing device
        device = connection.getDevices().get(0);
    }

    /**
     * Sends a press and hold command via ADB to your device. The duration of the hold action can be specified.
     *
     * @param duration Duration of the hold action in milliseconds
     * @throws IOException
     * @throws JadbException
     */
    public void pressAndHold(int duration) throws IOException, JadbException {
        device.execute("input", "touchscreen", "swipe", "500", "500", "500", "500", Integer.toString(duration));
    }

    /**
     * Takes a screenshot and saves it to the device in <code>/sdcard/screencap.png</code>.
     * Retrieves the image file afterwards.
     * <p>
     * Taking a screenshot over ADB unfortunately takes a pretty long time (therefore the delay).
     *
     * @throws IOException
     * @throws JadbException
     * @TODO find a quicker solution
     */
    public void takeScreenshot() throws IOException, JadbException, InterruptedException {
        File file = new File("src/main/resources/screencap.png");
        // avoid using an old screenshot
        if (file.exists())
            file.delete();
        FileOutputStream out = new FileOutputStream(file);
        InputStream in = device.execute("screencap", "-p", "/sdcard/screencap.png");

        // capturing the screen takes damn long...
        Thread.sleep(2000);
        device.pull(new RemoteFile("/sdcard/screencap.png"), out);
        out.close();
    }
}
