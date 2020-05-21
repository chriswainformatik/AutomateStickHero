import java.awt.image.BufferedImage;

/**
 * This class is used to get the bounds of the black platforms of the Android game Stick Hero.
 * To do this you have to set a screenshot image first.
 */
public class PlatformBoundsFinder {
    private BufferedImage screenshot;
    private RGBValue[][] pixelValues;

    /**
     * Sets the screenshot image and get the RGB values for each pixel.
     *
     * @param screenshot The screenshot image to be checked.
     */
    public void setScreenshot(BufferedImage screenshot) {
        this.screenshot = screenshot;
        getPixelValues();
    }

    /**
     * Gets the RGB values for each pixel of the screenshot image and stores them in the <code>pixelValues</code> array.
     */
    private void getPixelValues() {
        int height = screenshot.getHeight();
        int width = screenshot.getWidth();
        pixelValues = new RGBValue[height][width];

        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                int rgb = screenshot.getRGB(j, i);
                pixelValues[i][j] = new RGBValue((rgb >> 16) & 0xff, (rgb >> 8) & 0xff, rgb & 0xff);
            }
        }
    }

    /**
     * Returns an array of type <code>int</code> that contains the platform bounds in the following order:
     * <p>
     * 0: first black (should be very first pixel of the screenshot image)<br>
     * 1: first pixel of first non-black pixel area<br>
     * 2: first pixel of second black pixel area<br>
     * 3: first pixel of second non-black pixel area
     *
     * @param rowNumber The row number where the platform bounds can be found. Adjust this for your screen resolution.
     *                  In case of 1920x1080 as resolution a value of 1330 should work.
     * @return Array that containts the x-coordinates of the platform bounds in the above described order.
     */
    public int[] getPlatformBounds(int rowNumber) {
        int firstBlack = -1, firstNonBlack = -1, secondBlack = -1, secondNonBlack = -1;

        RGBValue[] row = pixelValues[rowNumber];
        // find first black pixel
        for (int i = 0; i < row.length; i++) {
            if (isBlack(row[i])) {
                firstBlack = i;
                break;
            }
        }

        // find first non-black pixel
        for (int i = firstBlack; i < row.length; i++) {
            if (!isBlack(row[i])) {
                firstNonBlack = i;
                break;
            }
        }

        // find first pixel of second black area
        for (int i = firstNonBlack; i < row.length; i++) {
            if (isBlack(row[i])) {
                secondBlack = i;
                break;
            }
        }

        // find first non-black pixel after second black area
        for (int i = secondBlack; i < row.length; i++) {
            if (!isBlack(row[i])) {
                secondNonBlack = i;
                break;
            }
        }

        return new int[]{firstBlack, firstNonBlack, secondBlack, secondNonBlack};
    }

    /**
     * Check if an RGB value is black.
     * <p>
     * Some "black" pixels have values like RGB(2,0,1), so up to about 5 seems "black enough" to be considered as "black" by the human eye.
     *
     * @param v <code>RGBValue</code> to be checked
     * @return If the RGB value is "black enough" to be considered as "black" by the human eye.
     */
    public boolean isBlack(RGBValue v) {
        return (v.getR() < 5 && v.getG() < 5 && v.getB() < 5);
    }

}

