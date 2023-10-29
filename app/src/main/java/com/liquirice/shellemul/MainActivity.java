package com.liquirice.shellemul;

import android.graphics.Color;
import android.os.Bundle;
import android.app.Activity;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ScrollView;
import android.widget.TextView;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class MainActivity extends Activity {

    private TextView outputTextView;
    private EditText inputEditText;
    private Button executeButton;
    private ScrollView scrollView;
    private AdView adView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initialize AdMob
        //MobileAds.initialize(this, "YOUR_ADMOB_APP_ID");

        // Load the banner ad
        adView = findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        adView.loadAd(adRequest);

        outputTextView = findViewById(R.id.outputTextView);
        inputEditText = findViewById(R.id.inputEditText);
        executeButton = findViewById(R.id.executeButton);
        scrollView = findViewById(R.id.scrollView);
            outputTextView.setTextColor(Color.BLACK);
            outputTextView.setBackgroundColor(Color.WHITE);
            outputTextView.setTextSize(14);
            outputTextView.setMovementMethod(new ScrollingMovementMethod());

            executeButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    executeCommand(inputEditText.getText().toString());
                }
            });
        }

        private void executeCommand(String command) {
            try {
                Process process = Runtime.getRuntime().exec(new String[] { "sh", "-c", command });
                BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
                String line;
                StringBuilder output = new StringBuilder();

                while ((line = reader.readLine()) != null) {
                    output.append(line).append("\n");
                }

                process.waitFor();
                outputTextView.setText(output.toString());
            } catch (IOException | InterruptedException e) {
                e.printStackTrace();
            }
        }
}
