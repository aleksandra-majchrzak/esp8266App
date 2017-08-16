package org.railwaymen.esp8266app;

/**
 * Created by ola on 16.08.17. <br/>
 * This class enables the activity to react to the server response.
 */

public interface OnLedChangedListener {

    void onLedOn();

    void onLedOff();

    void onErrorOccurred();
}
