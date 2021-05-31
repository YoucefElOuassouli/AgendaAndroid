package com.example.agenda.Admin.SeanceManagement;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.agenda.Admin.AdminHome;
import com.example.agenda.R;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;

public class NewSeance extends AppCompatActivity {

    Bundle d;
    private String ipAddress = "http://192.168.1.121/RestAPI/";
    EditText title,date_day,date_month,date_year,start,end;
    TextView create;
    Spinner monitor;
    ArrayList<String> monitorList = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_seance);


        title = findViewById(R.id.Seance_title);
        date_day = findViewById(R.id.Seance_Day_date);
        date_month = findViewById(R.id.Seance_month_Date);
        date_year = findViewById(R.id.Seance_year_Date);
        start = findViewById(R.id.Seance_StartAt);
        end = findViewById(R.id.Seance_EndAt);
        monitor = findViewById(R.id.Seance_monitor);

        getMonitors();
        
        create = findViewById(R.id.createSeance);
        create.setOnClickListener(v -> newSeance());
    }





    public void Fill(List<String> monitors){
        ArrayAdapter<String> adapter = new ArrayAdapter<>(
                this, android.R.layout.simple_spinner_item, monitors);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        monitor.setAdapter(adapter);
    }

    public void getMonitors(){
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(ipAddress+"seance/Monitor.php", response -> {


            for (int i = 0; i<response.length(); i++){
                try {


                    monitorList.add(response.getJSONObject(i).getInt("id")+" - "+response.getJSONObject(i).getString("monitor"));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            Fill(monitorList);

        }, error -> {

        });
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        requestQueue.add(jsonArrayRequest);
    }

    public static class Monitor{
        int id;
        String name;

        public Monitor(){}
        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }



    public void newSeance(){

        StringRequest stringRequest = new StringRequest(Request.Method.GET, ipAddress + "seance/createSeance.php?label="+title.getText().toString()+"&DateStart="+date_year.getText().toString()+"-"+date_month.getText().toString()+"-"+date_day.getText().toString()+"&startAt="+start.getText().toString()+"&EndAt="+end.getText().toString()+"&Monitor_id="+monitor.getSelectedItem().toString().charAt(0)+"&description=", response -> {
            Intent intent = new Intent(getApplicationContext(), AdminHome.class);
            startActivity(intent);
        }, error -> {

        });


        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        requestQueue.add(stringRequest);
    }
}