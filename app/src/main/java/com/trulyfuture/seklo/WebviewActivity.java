package com.trulyfuture.seklo;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebViewClient;

import com.trulyfuture.seklo.databinding.ActivityWebviewBinding;
import com.trulyfuture.seklo.models.HRServices;

import java.util.Objects;

public class WebviewActivity extends AppCompatActivity {

    private ActivityWebviewBinding binding;
    private HRServices mHrServices;
    private String baseUrl = "https://seklo.pk/#/mobile-payment?";
    private String queryUrl="";
    private static final String TAG = "WebviewActivity";

    public enum ServicesName {
        ResumeWriting("resumeWriting",3),
        ResumeReview("resumeReview",1),
        CareerCounseling("careerCounseling",2),
        CoverLetter("coverLetter",4);

        public String stringValue;
        public int intValue;

        ServicesName(String stringValue, int intValue) {
            this.stringValue = stringValue;
            this.intValue = intValue;
        }
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityWebviewBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setupViews();
    }

    @SuppressLint({"JavascriptInterface", "SetJavaScriptEnabled"})
    private void setupViews() {

        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            mHrServices = (HRServices) bundle.getSerializable("servicesData");
            String serviceName=bundle.getString("serviceName");


            if(serviceName.equals(ServicesName.ResumeReview.stringValue)){
                queryUrl=resumeReviewUrl();
            }
            else if(serviceName.equals(ServicesName.ResumeWriting.stringValue)){
                queryUrl=resumeWritingUrl();
            }
            else if(serviceName.equals(ServicesName.CareerCounseling.stringValue)){
                queryUrl=careerCounselingUrl();
            }
            else if(serviceName.equals(ServicesName.CoverLetter.stringValue)){
                queryUrl=coverLetterUrl();
            }


        }


        binding.servicesWebview.setWebViewClient(new WebViewClient());


        binding.servicesWebview.getSettings().setJavaScriptEnabled(true);
        binding.servicesWebview.getSettings().setAllowFileAccess(true);
        binding.servicesWebview.getSettings().setJavaScriptCanOpenWindowsAutomatically(true);
//        binding.servicesWebview.getSettings().setBuiltInZoomControls(true);
        binding.servicesWebview.getSettings().setAllowContentAccess(true);
        binding.servicesWebview.getSettings().setLoadsImagesAutomatically(true);
        binding.servicesWebview.getSettings().setLoadWithOverviewMode(true);
        binding.servicesWebview.getSettings().setSupportZoom(true);
        binding.servicesWebview.getSettings().setUseWideViewPort(true);
        binding.servicesWebview.getSettings().setDomStorageEnabled(true);
        binding.servicesWebview.getSettings().setDatabaseEnabled(true);


        binding.servicesWebview.loadUrl(queryUrl);

//        Log.v(TAG, Objects.requireNonNull(binding.servicesWebview.getUrl()));

//        binding.servicesWebview.addJavascriptInterface(new Object() {
//            public void performClick() {
//                // Deal with a click on the OK button
//            }
//        }, "ok");
    }

    //Methods
    private String resumeReviewUrl(){
        String url=baseUrl;
        HRServices.DBObj dbObj= mHrServices.getdBObj();
        HRServices.EmailObj emailObj=mHrServices.getEmailObj();

        //Make db obj
        url+="HR_ID="+dbObj.gethRID()+"&User_ID="+dbObj.getUserID()+"&Payment="+dbObj.getPayment()+"&Currency="+dbObj.getCurrency()+"&Days="+dbObj.getDays();

        //Make email obj
        url+="&User_Email="+emailObj.getUserEmail()+"&HR_Email="+emailObj.gethREmail()+"&User_Name="+emailObj.getUserName();

        url+="&type="+ServicesName.ResumeReview.intValue;

        return url;
    }

    private String resumeWritingUrl(){
        String url=baseUrl;
        HRServices.DBObj dbObj= mHrServices.getdBObj();
        HRServices.EmailObj emailObj=mHrServices.getEmailObj();

        //Make db obj
        url+="HR_ID="+dbObj.gethRID()+"&User_ID="+dbObj.getUserID()+"&Payment="+dbObj.getPayment()+"&Currency="+dbObj.getCurrency()+"&Days="+dbObj.getDays()+"&Resume_Write="+dbObj.getResumeType();

        //Make email obj
        url+="&User_Email="+emailObj.getUserEmail()+"&HR_Email="+emailObj.gethREmail()+"&User_Name="+emailObj.getUserName();

        url+="&type="+ServicesName.ResumeWriting.intValue;

        return url;
    }

    private String careerCounselingUrl(){
        String url=baseUrl;
        HRServices.DBObj dbObj= mHrServices.getdBObj();
        HRServices.EmailObj emailObj=mHrServices.getEmailObj();

        //Make db obj
        url+="HR_ID="+dbObj.gethRID()+"&User_ID="+dbObj.getUserID()+"&Payment="+dbObj.getPayment()+"&Currency="+dbObj.getCurrency()+"&Days="+dbObj.getDays()+"&TimeHours="+dbObj.getTimeHours();

        //Make email obj
        url+="&User_Email="+emailObj.getUserEmail()+"&HR_Email="+emailObj.gethREmail()+"&User_Name="+emailObj.getUserName();

        url+="&type="+ServicesName.CareerCounseling.intValue;

        return url;
    }

    private String coverLetterUrl(){
        String url=baseUrl;
        HRServices.DBObj dbObj= mHrServices.getdBObj();
        HRServices.EmailObj emailObj=mHrServices.getEmailObj();

        //Make db obj
        url+="HR_ID="+dbObj.gethRID()+"&User_ID="+dbObj.getUserID()+"&Payment="+dbObj.getPayment()+"&Currency="+dbObj.getCurrency()+"&Days="+dbObj.getDays()+"&Company="+dbObj.getCompanyName()+"&Job="+dbObj.getJobName();

        //Make email obj
        url+="&User_Email="+emailObj.getUserEmail()+"&HR_Email="+emailObj.gethREmail()+"&User_Name="+emailObj.getUserName();

        url+="&type="+ServicesName.CoverLetter.intValue;

        return url;
    }

//    private String makeQueryUrl() {
//        baseUrl = baseUrl + "Currency=PKR&Days=2&HR_ID=6&Payment=2106&TimeHours=45 minuts&User_ID=9&HR_Email=muneeza.tahir@seklo.pk&User_Email=arsalan.fakhar@yahoo.com&User_Name=Arsalan Fakhar Siddiqui";
//        return baseUrl;
//    }


    @Override
    public void onBackPressed() {
        if (binding.servicesWebview.canGoBack()) {
            binding.servicesWebview.goBack();
        } else {
            super.onBackPressed();
        }

    }
}