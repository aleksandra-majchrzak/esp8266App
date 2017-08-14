package org.railwaymen.esp8266app;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.flask.colorpicker.ColorPickerView;
import com.flask.colorpicker.OnColorSelectedListener;

public class MainActivity extends AppCompatActivity {

    private ColorPickerView colorPicker;
    private Button turnOnButton;
    private Button turnOffButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initComponents();
    }

    private void initComponents() {
        turnOnButton = (Button) findViewById(R.id.turn_on_button);
        turnOnButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                turnOnButton.setEnabled(false);
                turnOffButton.setEnabled(true);
            }
        });
        turnOffButton = (Button) findViewById(R.id.turn_off_button);
        turnOffButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                turnOnButton.setEnabled(true);
                turnOffButton.setEnabled(false);
                colorPicker.setColor(-1, false);
            }
        });

        colorPicker = (ColorPickerView) findViewById(R.id.color_picker_view);
        colorPicker.addOnColorSelectedListener(new OnColorSelectedListener() {
            @Override
            public void onColorSelected(int color) {

                if (turnOffButton.isEnabled()) {
                    int red = Color.red(color);
                    int green = Color.green(color);
                    int blue = Color.blue(color);
                    Toast.makeText(MainActivity.this, "LED turned on", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(MainActivity.this, "LED turned off", Toast.LENGTH_SHORT).show();
                    colorPicker.setColor(-1, false);
                }
            }
        });
    }
}
