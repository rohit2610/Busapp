package com.example.busapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreSettings;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.sql.Time;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class TimeTable extends AppCompatActivity {


    FirebaseFirestore db;
    private EditText datePickerEditText ;

    ArrayList<String> timings ;
    ArrayAdapter<String> arrayAdapter;
    ListView listView ;
    Date time ;
    private ArrayList<String> displayList ;

    private Calendar calendar ;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_time_table);

        datePickerEditText = findViewById(R.id.setDateEditText);
        calendar = Calendar.getInstance();






        listView = findViewById(R.id.timingsListView);

        db = FirebaseFirestore.getInstance();
        timings = new ArrayList<>();
        displayList = new ArrayList<>();
        arrayAdapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,displayList);
        listView.setAdapter(arrayAdapter);












    }


    public void selectDate(View view){

        FirebaseFirestoreSettings settings = new FirebaseFirestoreSettings.Builder()
                .setTimestampsInSnapshotsEnabled(true)
                .build();

        db.setFirestoreSettings(settings);
        final int[] myear = {calendar.get(Calendar.YEAR)};
        int mMonth = calendar.get(Calendar.MONTH);
        int mDay = calendar.get(Calendar.DAY_OF_MONTH);
        final int mDate = calendar.get(Calendar.DATE);
        final int day = calendar.get(Calendar.DAY_OF_WEEK);
        final int[] dayofWeek = new int[1];
        dayofWeek[0] = 1000;
        final String[] date = new String[1];
        DatePickerDialog datePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                datePickerEditText.setText(dayOfMonth + " /" + (month + 1) + " /" + year);

                date[0] = dayOfMonth + "-" + (month+1) + "-" + year;
                GregorianCalendar gregorianCalendar = new GregorianCalendar(year,month,dayOfMonth-1);

                dayofWeek[0] = gregorianCalendar.get(GregorianCalendar.DAY_OF_WEEK);

                Log.i("DAy of week",String.valueOf(dayofWeek[0]));

            }
        }, myear[0],mMonth,mDay);
        datePickerDialog.show();



        datePickerDialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialog) {



                final String from = getIntent().getStringExtra("From");

                    db.collection("BusTimings").document(from).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                        @Override
                        public void onSuccess(DocumentSnapshot documentSnapshot) {
                            if (documentSnapshot != null) {


                                timings.clear();
                                displayList.clear();

                                if (dayofWeek[0] <= 5)
                                    timings = (ArrayList<String>) documentSnapshot.get("1");

                                else
                                    timings = (ArrayList<String>) documentSnapshot.get("2");

                                for (int i = 0; i < timings.size(); i++) {
                                    displayList.add(timings.get(i));
                                    listView.setAdapter(arrayAdapter);

                                }

                            }
                        }
                    });




                listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                        Log.i("position",String.valueOf(position));
                        Intent i = new Intent(TimeTable.this,BusSeat.class);
                        i.putExtra("From",from);
                        i.putExtra("Date",date[0]);
                        i.putExtra("Time",timings.get(position));
                        startActivity(i);


                    }
                });

            }
        });







    }


    public void printList(){




        timings.add("Nisha");

        /*db.collection("BusTimings").document("").get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        if(queryDocumentSnapshots != null){
                            for(QueryDocumentSnapshot q : queryDocumentSnapshots){
                                Log.i("DAta",q.getData().toString());
                            }
                        }
                    }
                });

        /*final DocumentReference documentReference = db.collection("BusTiming");

        documentReference.get()
                .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {

                        if(task.isSuccessful()){
                            DocumentSnapshot documentSnapshot = task.getResult();
                            Log.i("Document Snapshot", String.valueOf(documentSnapshot.getData()));
                        }
                    }
                });

       /* db.collection("BusTimings")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if(! task.isSuccessful()){
                            Log.i("Task," ,"failed");
                        }

                        else
                        {
                            for (QueryDocumentSnapshot documentSnapshot :task.getResult()){

                                if(documentSnapshot.get("Time") != null) {
                                    time = Calendar.getInstance().getTime();
                                    SimpleDateFormat timeformat = new SimpleDateFormat("hh:mm");

                                    try {
                                        Date date = timeformat.parse((String) documentSnapshot.get("Time"));
                                        date.setDate(time.getDate()+1);

                                        Log.i("DAte", String.valueOf(date));
                                    } catch (ParseException e) {
                                        e.printStackTrace();
                                    }


                                    Log.i("TImmings", Arrays.toString(timings.toArray()));
                                    Log.i("Time",documentSnapshot.get("Time").toString());


                                }

                            }
                        }
                    }});*/











    }




}


