import se.vidstige.jadb.JadbConnection;
import se.vidstige.jadb.JadbDevice;
import se.vidstige.jadb.JadbException;

import java.io.IOException;

public class ADBWrapper {
    private JadbConnection connection;
    private JadbDevice device;

    public ADBWrapper() throws IOException, JadbException {
        connection = new JadbConnection();
        // make sure to have connected only your testing device
        device = connection.getDevices().get(0);
    }

    public void pressAndHold(int duration) throws IOException, JadbException {
        device.execute("input", "touchscreen", "swipe", "500", "500", "500", "500", Integer.toString(duration));
    }
}
