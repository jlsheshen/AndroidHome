package com.edu.jilin.demo1027retrofit;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.joanzapata.pdfview.PDFView;


public class MainActivity extends AppCompatActivity {
PDFView pdfView ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        pdfView = (PDFView) findViewById(R.id.pdfview);
        pdfView.fromAsset("about.pdf")
                .defaultPage(1)
                .showMinimap(false)
                .enableSwipe(true)
                .load();
    }





}
