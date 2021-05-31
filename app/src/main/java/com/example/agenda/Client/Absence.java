package com.example.agenda.Client;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.example.agenda.Models.Seance;
import com.example.agenda.R;

import org.json.JSONArray;
import org.json.JSONException;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class Absence extends AppCompatActivity {


    CustomAdapter myAdapter;
    private String ipAddress = "http://192.168.1.121/RestAPI/";
    ArrayList<Seance> seanceList = new ArrayList<>();
    ListView SeanceList;
    Spinner days;
    Bundle d;
    TextView abs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_absence);

        abs = findViewById(R.id.abs);
        SeanceList = findViewById(R.id.rv);
        d= getIntent().getExtras();
        abs.setAlpha(0);
        Fill_Seance_Client();

    }







    public void Fill_Seance_Client(){

        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(ipAddress+"seance/absence.php?client="+d.getInt("client")+"&search"+0, response -> {
            for (int i=0; i<response.length(); i++){
                Seance seance = new Seance();
                try {
                    seance.setLabel(response.getJSONObject(i).getString("label"));
                    seance.setDateStart(response.getJSONObject(i).getString("newDate"));
                    seance.setStartAt(response.getJSONObject(i).getString("StartTime"));
                    seance.setEndAt(response.getJSONObject(i).getString("EndTime"));

                    Log.d("res ", seance.getLabel());
                    seanceList.add(seance);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }

            AdapterConfiguration(seanceList);
        }, error -> {

        });

        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        requestQueue.add(jsonArrayRequest);



    }



    public void AdapterConfiguration(ArrayList<Seance> lst){


        if(lst == null){
            abs.setAlpha(1);
            Toast.makeText(getApplicationContext(),"You do not have any absence ",Toast.LENGTH_LONG).show();
        }
        myAdapter = null;
        myAdapter = new CustomAdapter(lst);
        SeanceList.setAdapter(myAdapter);


    }




    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu,menu);

        return super.onCreateOptionsMenu(menu);
    }

    class CustomAdapter extends BaseAdapter {

        ArrayList<Seance> mData;
        TextView titre, desc;
        TextView From_To, From,Time_Num;


        CustomAdapter(ArrayList<Seance> seances){
            this.mData = seances;

        }


        @Override
        public int getCount() {
            return mData.size();
        }

        @Override
        public Seance getItem(int position) {
            return mData.get(position);
        }

        @Override
        public long getItemId(int position) {
            return mData.get(position).getId();
        }


        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            LayoutInflater layoutInflater = getLayoutInflater();
            @SuppressLint({"ViewHolder", "InflateParams"}) View holder = layoutInflater.inflate(R.layout.absence_layout,null);






            String From_To_ = ""+mData.get(position).getStartAt() + " " + mData.get(position).getEndAt();

            @SuppressLint("SimpleDateFormat") SimpleDateFormat inFormat = new SimpleDateFormat("yyyy-MM-dd");
            Date date;
            String goal = null;
            try {
                date = inFormat.parse(mData.get(position).getDateStart());
                @SuppressLint("SimpleDateFormat") SimpleDateFormat outFormat = new SimpleDateFormat("EEEE");
                assert date != null;
                goal = outFormat.format(date);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            titre =holder.findViewById(R.id.label_seance);
            From_To = holder.findViewById(R.id.From_To);
            From = holder.findViewById(R.id.seance_new_date);
            Time_Num = holder.findViewById(R.id.newDate);



            From.setText(goal);
            Time_Num.setText(mData.get(position).getDateStart().substring(0,10));
            From_To.setText(From_To_);
            titre.setText(mData.get(position).getLabel());

            return holder;


        }




    }






}