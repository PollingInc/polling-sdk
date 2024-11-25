package com.polling.sdk;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.polling.sdk.dialogs.WebViewBottom;
import com.polling.sdk.dialogs.WebViewBottomSheet;
import com.polling.sdk.dialogs.helpers.WebViewDialogHelper;
import com.polling.sdk.models.DialogRequest;
import com.polling.sdk.models.JavaCallbackHandler;
import com.polling.sdk.models.RequestIdentification;
import com.polling.sdk.models.Survey;
import com.polling.sdk.utils.ViewType;

import java.util.Random;

public class LegacyTestActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Set a simple layout with a button
        setContentView(R.layout.legacy_activity_test);

        Button buttonAvailableSurveysDialog = findViewById(R.id.buttonAvailableSurveysDialog);
        Button buttonAvailableSurveysBottom = findViewById(R.id.buttonAvailableSurveysBottom);

        Button buttonSingleSurveyDialog = findViewById(R.id.buttonSingleSurveyDialog);
        Button buttonSingleSurveyBottom = findViewById(R.id.buttonSingleSurveyBottom);

        Button buttonCompletedSurveysApi = findViewById(R.id.buttonCompletedSurveysApi);


        //Available Surveys
        buttonAvailableSurveysDialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //newDialog().availableSurveys();
                survey().availableSurveys(LegacyTestActivity.this , ViewType.Dialog);
            }
        });

        buttonAvailableSurveysBottom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*
                WebViewBottom newBottom = newBottom("https://demo.polling.com/sdk/available-surveys");
                newBottom.show(getSupportFragmentManager(), newBottom.getTag());
                */
                WebViewBottom newBottom = newBottomCustom("https://demo.polling.com/sdk/available-surveys");
                newBottom.show();

            }
        });


        //Single Survey
        buttonSingleSurveyDialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                survey().singleSurvey("3875c65f-1e7a-411f-b8c3-be2ce19a9c6e", LegacyTestActivity.this, ViewType.Dialog);
            }
        });


        buttonSingleSurveyBottom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*
                WebViewBottom newBottom = newBottom("https://demo.polling.com/sdk/survey/3875c65f-1e7a-411f-b8c3-be2ce19a9c6e");
                newBottom.show(getSupportFragmentManager(), newBottom.getTag());
                */
                WebViewBottom newBottom = newBottomCustom("https://demo.polling.com/sdk/survey/3875c65f-1e7a-411f-b8c3-be2ce19a9c6e");
                newBottom.show();

            }
        });




        //Completed Surveys
        buttonCompletedSurveysApi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        buttonCompletedSurveysApi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Survey survey = newApi();

                TextView dataField = (TextView) findViewById(R.id.apiResponseData);
                survey.completedSurveys();
            }
        });

    }

    private Survey survey()
    {
        Activity activity = LegacyTestActivity.this;

        int random = new Random().nextInt(1000);
        String customerId = "223";//String.valueOf(random);
        String apiKey = "cli_wZJW1tH39TfUMbEumPLrDy15EXDqJA0a";

        DialogRequest dialogRequest = new DialogRequest
                (
                        activity,
                        customerId,
                        apiKey
                );
        return new Survey("", dialogRequest,new JavaCallbackHandler() {
            @Override
            public void onSuccess(String response) {

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        updateText(response);
                    }
                });

            }

            @Override
            public void onFailure(String error) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        updateText(error);
                    }
                });
            }
        });
    }


    private WebViewDialogHelper newDialog()
    {

        Activity activity = LegacyTestActivity.this;

        int random = new Random().nextInt(1000);
        String customerId = "199";//String.valueOf(random);
        String apiKey = "cli_wZJW1tH39TfUMbEumPLrDy15EXDqJA0a";

        DialogRequest dialogRequest = new DialogRequest
        (
            activity,
            customerId,
            apiKey
        );


        return new WebViewDialogHelper(dialogRequest);
    }

    private WebViewBottomSheet newBottom(String url)
    {
        int random = new Random().nextInt(1000);
        String customerId = String.valueOf(random);
        //String customerId = "199";

        String apiKey = "cli_wZJW1tH39TfUMbEumPLrDy15EXDqJA0a";

        RequestIdentification requestIdentification = new RequestIdentification
        (
            customerId,
            apiKey
        );

        return new WebViewBottomSheet(url, requestIdentification);
    }

    private WebViewBottom newBottomCustom(String url)
    {
        int random = new Random().nextInt(1000);
        String customerId = String.valueOf(random);
        //String customerId = "199";

        String apiKey = "cli_wZJW1tH39TfUMbEumPLrDy15EXDqJA0a";

        DialogRequest dialogRequest = new DialogRequest
                (
                        (Activity) this,
                        customerId,
                        apiKey
                );

        return new WebViewBottom(url, dialogRequest);
    }


    private Survey newApi()
    {
        int random = new Random().nextInt(1000);
        String customerId = String.valueOf(random);
        //String customerId = "199";
        
        String apiKey = "cli_wZJW1tH39TfUMbEumPLrDy15EXDqJA0a";

        RequestIdentification requestIdentification = new RequestIdentification
        (
                customerId,
                apiKey
        );

        return new Survey("", requestIdentification,


                new JavaCallbackHandler() {
                    @Override
                    public void onSuccess(String response) {

                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                updateText(response);
                            }
                        });

                    }

                    @Override
                    public void onFailure(String error) {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                updateText(error);
                            }
                        });
                    }
                }

                );
    }

    private void updateText(String text)
    {
        TextView dataField = (TextView) findViewById(R.id.apiResponseData);
        dataField.setText(text);
    }


}
