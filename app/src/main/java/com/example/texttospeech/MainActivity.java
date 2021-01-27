package com.example.texttospeech;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;


import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    EditText editText;
    SeekBar seekBar;
    Button btnTextToSpeech;
    TextView speedText;

    TextToSpeech textToSpeech;
    float speed;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editText = findViewById(R.id.editText);
        speedText = findViewById(R.id.speedText);
        seekBar = findViewById(R.id.seekBar);
        btnTextToSpeech = findViewById(R.id.btnTextToSpeech);

        textToSpeech = new TextToSpeech(getApplicationContext(), status -> {

            if( status == TextToSpeech.SUCCESS){
                int lang = textToSpeech.setLanguage(Locale.US);

                if( lang == TextToSpeech.LANG_MISSING_DATA || lang == TextToSpeech.LANG_NOT_SUPPORTED){
                    Toast.makeText(MainActivity.this,"Language is not supported.", Toast.LENGTH_SHORT).show();
                }
            }
        });

        btnTextToSpeech.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String data = editText.getText().toString();
                textToSpeech.setSpeechRate(speed);
                textToSpeech.speak(data,TextToSpeech.QUEUE_FLUSH,null);

            }
        });

        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                speed = (float) i / 50;
                if(speed < 0.1) speed = 0.1f;
                speedText.setText("Speech Rate = " + speed);

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(textToSpeech != null){
            textToSpeech.stop();
            textToSpeech.shutdown();
    }
}
}