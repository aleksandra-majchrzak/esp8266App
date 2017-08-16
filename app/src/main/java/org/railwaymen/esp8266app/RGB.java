package org.railwaymen.esp8266app;

/**
 * Created by ola on 16.08.17. <br/>
 * This class is used in the request body. It will be parsed to JSON object
 */

public class RGB {
    private int red;
    private int green;
    private int blue;

    public RGB(int red, int green, int blue) {
        this.red = red;
        this.green = green;
        this.blue = blue;
    }
}
