package com.example.hwhong.googleplacepicker;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlacePicker;

public class MainActivity extends AppCompatActivity {

    private Button button, webClick;
    private int REQUEST_CODE = 100;

    private TextView nameTV, addressTV, numberTV;
    private WebView webView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        nameTV = (TextView) findViewById(R.id.name);
        addressTV = (TextView) findViewById(R.id.address);
        numberTV = (TextView) findViewById(R.id.number);

        webView = (WebView) findViewById(R.id.webView);

        webClick = (Button) findViewById(R.id.webBut);

        button = (Button) findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PlacePicker.IntentBuilder builder = new PlacePicker.IntentBuilder();

                try {
                    Intent intent = builder.build(getApplicationContext());
                    startActivityForResult(intent, REQUEST_CODE );
                } catch (GooglePlayServicesRepairableException e) {
                    e.printStackTrace();
                } catch (GooglePlayServicesNotAvailableException e) {
                    e.printStackTrace();
                }
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode == REQUEST_CODE) {
            if(resultCode == RESULT_OK) {
                Place place = PlacePicker.getPlace(data, getApplicationContext());

                //getting the information of the place chosen
                String name = place.getName().toString();
                String address = String.format("%s", place.getAddress());
                String number = place.getPhoneNumber().toString();
                final String url = place.getWebsiteUri().toString();

                nameTV.setText(name);
                addressTV.setText(address);
                numberTV.setText(number);

                webView.getSettings().setJavaScriptEnabled(true);
                webView.loadUrl(url);


            }
        }
    }
}
