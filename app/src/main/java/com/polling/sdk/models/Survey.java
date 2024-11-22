package com.polling.sdk.models;

import android.app.Activity;
import android.content.Context;

import com.polling.sdk.utils.ViewType;
import com.polling.sdk.network.WebRequestHandler;
import com.polling.sdk.network.WebRequestType;
import com.polling.sdk.dialogs.helpers.DialogHelper;
import com.polling.sdk.dialogs.helpers.WebViewBottomHelper;
import com.polling.sdk.dialogs.helpers.WebViewDialogHelper;

public class Survey
{
    private final RequestIdentification requestIdentification;
    public CallbackHandler callbackHandler;

    public String url;

    public Survey(String url, RequestIdentification requestIdentification, CallbackHandler callbackHandler)
    {
        this.url = url;
        this.requestIdentification = requestIdentification;
        this.callbackHandler = callbackHandler;
    }

    public DialogHelper dialogHelper(Context context, ViewType viewType)
    {
        switch (viewType) {
            case Dialog ->
            {
                DialogRequest dialogRequest = new DialogRequest(
                        (Activity) context,
                        requestIdentification.customerId,
                        requestIdentification.apiKey);

                return new WebViewDialogHelper(dialogRequest);
            }
            case Bottom ->
            {
                DialogRequest dialogRequest = new DialogRequest(
                        (Activity) context,
                        requestIdentification.customerId,
                        requestIdentification.apiKey);

                return new WebViewBottomHelper(dialogRequest);
            }
        }

        return null;
    }


    public void updateCallbacks(CallbackHandler callbackHandler)
    {
        this.callbackHandler = callbackHandler;
    }

    public void availableSurveys()
    {
        String url = "https://demo-api.polling.com/api/sdk/surveys/available";
        requestSurvey(url);
    }

    public void availableSurveys(Context context, String viewTypeStr)
    {
        ViewType viewType = ViewType.valueOf(viewTypeStr);
        availableSurveys(context, viewType);
    }
    public void availableSurveys(Context context, ViewType viewType)
    {
        if(viewType == ViewType.None)
        {
            availableSurveys();
            return;
        }

        DialogHelper dialog = dialogHelper(context, viewType);

        if(dialog != null) dialog.availableSurveys(this);
        else this.availableSurveys();
    }

    public void singleSurvey(String surveyId)
    {
        String url = "https://demo-api.polling.com/api/sdk/surveys/" + surveyId;
        requestSurvey(url);
    }

    public void singleSurvey(String surveyId, Context context, String viewTypeStr)
    {
        ViewType viewType = ViewType.valueOf(viewTypeStr);
        singleSurvey(surveyId, context, viewType);
    }

    public void singleSurvey(String surveyId, Context context, ViewType viewType)
    {
        if(viewType == ViewType.None)
        {
            singleSurvey(surveyId);
            return;
        }

        DialogHelper dialog = dialogHelper(context, viewType);

        if(dialog != null) dialog.singleSurvey(surveyId, this);
        else this.singleSurvey(surveyId);
    }


    public void completedSurveys()
    {
        String url = "https://demo-api.polling.com/api/sdk/surveys/completed";
        requestSurvey(url);
    }

    private void requestSurvey(String url)
    {
        url = requestIdentification.ApplyKeyToURL(url);
        url = requestIdentification.ApplyCompletionBypassToURL(url);


        WebRequestHandler.makeRequest(url, WebRequestType.GET, null,
                new WebRequestHandler.ResponseCallback() {
                    @Override
                    public void onResponse(String response) {
                        callbackHandler.onSuccess(response);
                    }

                    @Override
                    public void onError(String error) {
                        callbackHandler.onFailure(error);
                    }
                }
        );
    }
}