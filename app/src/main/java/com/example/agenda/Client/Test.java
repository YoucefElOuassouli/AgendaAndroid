package com.example.agenda.Client;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;

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
import java.util.List;
import java.util.Random;

public class Test extends AppCompatActivity {

    CustomAdapter myAdapter;
    private String ipAddress = "http://192.168.1.121/RestAPI/";
    ArrayList<Seance> seanceList = new ArrayList<>();
    ListView SeanceList;
    Spinner days;
    Bundle d;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);

        d = getIntent().getExtras();
        SeanceList = findViewById(R.id.rv);


        Fill_Seance_Client();


        EditText search = findViewById(R.id.search_for_seance_or_day);

        search.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                    ArrayList<Seance> newList = new ArrayList<>();
                    if(!TextUtils.isEmpty(s)){
                        for (Seance seance:seanceList){
                            @SuppressLint("SimpleDateFormat") SimpleDateFormat inFormat = new SimpleDateFormat("yyyy-MM-dd");
                            Date date;
                            String goal = null;
                            try {
                                date = inFormat.parse(seance.getDateStart());
                                @SuppressLint("SimpleDateFormat") SimpleDateFormat outFormat = new SimpleDateFormat("EEEE");
                                assert date != null;
                                goal = outFormat.format(date);
                            } catch (ParseException e) {
                                e.printStackTrace();
                            }
                            assert goal != null;
                            if(goal.toLowerCase().contains(s)){
                                newList.add(seance);
                            }
                            else if(seance.getLabel().toLowerCase().contains(s)){
                                newList.add(seance);
                            }
                        }
                        AdapterConfiguration(newList);
                        myAdapter.notifyDataSetChanged();

                    }else{
                        AdapterConfiguration(seanceList);
                        myAdapter.notifyDataSetChanged();
                    }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });



    }





    public void Fill_Seance_Client(){

        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(ipAddress+"seance/seanceByClient.php?client="+d.getInt("client"), response -> {
            for (int i=0; i<response.length(); i++){
                Seance seance = new Seance();
                try {
                    seance.setLabel(response.getJSONObject(i).getString("label"));
                    seance.setDescription(response.getJSONObject(i).getString("description"));
                    seance.setDateStart(response.getJSONObject(i).getString("DateStart"));
                    seance.setStartAt(response.getJSONObject(i).getString("startAt"));
                    seance.setEndAt(response.getJSONObject(i).getString("EndAt"));

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
            @SuppressLint({"ViewHolder", "InflateParams"}) View holder = layoutInflater.inflate(R.layout.seance_view_with_progress,null);






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

            titre =holder.findViewById(R.id.Seance_title);
            desc = holder.findViewById(R.id.seance_desc);
            From_To = holder.findViewById(R.id.From_To);
            From = holder.findViewById(R.id.Time);
            Time_Num = holder.findViewById(R.id.Time_Num);


            From.setText(goal);
            Time_Num.setText(mData.get(position).getDateStart().substring(0,10));
            From_To.setText(From_To_);
            desc.setText(mData.get(position).getDescription());
            titre.setText(mData.get(position).getLabel());

            return holder;


        }




    }





}