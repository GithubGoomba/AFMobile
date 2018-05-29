package com.test.addisongalambos.testapp_af;

import android.app.Application;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.content.Intent;

import com.appsflyer.AppsFlyerConversionListener;
import com.appsflyer.AppsFlyerLib;
import com.appsflyer.*;


import java.util.HashMap;
import java.util.Map;


public class MainActivity extends AppCompatActivity{

    final Handler handler = new Handler();

    public static final String mess = "com.test.addisongalambos.testapp_af.MESSAGE";
    public static final String AF_DEV_KEY = "mbfN4YAfHoQS5m6Ago4nH8";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        AppsFlyerConversionListener conversionListener = new AppsFlyerConversionListener() {

            /* Returns the attribution data. Note - the same conversion data is returned every time per install */
            @Override
            public void onInstallConversionDataLoaded(Map<String, String> conversionData) {
                for (String attrName : conversionData.keySet()) {
                    Log.d(AppsFlyerLib.LOG_TAG, "attribute: " + attrName + " = " + conversionData.get(attrName));
                }
            }

            @Override
            public void onInstallConversionFailure(String errorMessage) {
                Log.d(AppsFlyerLib.LOG_TAG, "error getting conversion data: " + errorMessage);
            }

            /* Called only when a Deep Link is opened */
            @Override
            public void onAppOpenAttribution(Map<String, String> conversionData) {
                for (String attrName : conversionData.keySet()) {
                    Log.d(AppsFlyerLib.LOG_TAG, "attribute: " + attrName + " = " + conversionData.get(attrName));
                }
            }

            @Override
            public void onAttributionFailure(String errorMessage) {
                Log.d(AppsFlyerLib.LOG_TAG, "error onAttributionFailure : " + errorMessage);
            }
        };


        /* This API enables AppsFlyer to detect installations, sessions, and updates. */

        AppsFlyerLib.getInstance().init(AF_DEV_KEY, conversionListener, getApplicationContext());



        /* Set to true to see the debug logs. Comment out or set to false to stop the function */

        AppsFlyerLib.getInstance().setDebugLog(true);


        AppsFlyerLib.getInstance().startTracking(this.getApplication(),AF_DEV_KEY);
        setContentView(R.layout.activity_main);


        Button trackEventButton = findViewById(R.id.trackEventButton);
        trackEventButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*Track when I click this button */
                Map<String, Object> eventValue = new HashMap<String, Object>();
                eventValue.put(AFInAppEventParameterName.REVENUE, 0.1);
                AppsFlyerLib.getInstance().trackEvent(getApplicationContext(), AFInAppEventType.PURCHASE, eventValue);
            }
        });
    }

    /** Called when the user taps the Send button */
    public void sendMessage(View view) {
       Intent intent = new Intent(this, DisplayMessageActivity.class);
       EditText editText = (EditText) findViewById(R.id.editText);
       String message = editText.getText().toString();
       intent.putExtra(mess, message);
       startActivity(intent);
    }

}

