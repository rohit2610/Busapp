package com.example.busapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.github.barteksc.pdfviewer.PDFView;

public class ViewTimeTable extends AppCompatActivity {

    PDFView pdfView ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_time_table);

        pdfView = findViewById(R.id.pdfView);

        pdfView.fromAsset("bus_timetable.pdf")
                .enableSwipe(true)
                .enableDoubletap(true)
                .defaultPage(0)
                .load();
    }
}
