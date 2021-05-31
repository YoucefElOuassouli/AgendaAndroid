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
import com.example.agenda.Models.Comment;
import com.example.agenda.Models.Seance;
import com.example.agenda.R;

import org.json.JSONArray;
import org.json.JSONException;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class Comments extends AppCompatActivity {



   CustomAdapter myAdapter;
    private String ipAddress = "http://192.168.1.121/RestAPI/";
    ArrayList<Comment> seanceList = new ArrayList<>();
    ListView SeanceList;
    Spinner days;
    Bundle d;
    TextView abs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comments);
        abs = findViewById(R.id.abs);
        SeanceList = findViewById(R.id.rv);
        d= getIntent().getExtras();
        abs.setAlpha(0);
        Fill_Seance_Client();

    }







    public void Fill_Seance_Client(){

        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(ipAddress+"seance/comments.php?client="+d.getInt("client"), response -> {
            for (int i=0; i<response.length(); i++){
                Comment comment = new Comment();
                try {
                    comment.setLabel(response.getJSONObject(i).getString("label"));
                    comment.setDate(response.getJSONObject(i).getString("DateStart"));
                    comment.setComments(response.getJSONObject(i).getString("comments"));
                    comment.setMonitor(response.getJSONObject(i).getString("full_name"));

                    Log.d("res ", comment.getLabel());
                    seanceList.add(comment);
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



    public void AdapterConfiguration(ArrayList<Comment> lst){


        if(lst == null){
            abs.setAlpha(1);
            Toast.makeText(getApplicationContext(),"You do not have any Comments ",Toast.LENGTH_LONG).show();
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

        ArrayList<Comment> mData;
        TextView titre, monitor, From,Time_Num,comment;


        CustomAdapter(ArrayList<Comment> seances){
            this.mData = seances;

        }


        @Override
        public int getCount() {
            return mData.size();
        }

        @Override
        public Comment getItem(int position) {
            return mData.get(position);
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }


        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            LayoutInflater layoutInflater = getLayoutInflater();
            @SuppressLint({"ViewHolder", "InflateParams"}) View holder = layoutInflater.inflate(R.layout.comments_layout,null);







            @SuppressLint("SimpleDateFormat") SimpleDateFormat inFormat = new SimpleDateFormat("yyyy-MM-dd");
            Date date;
            String goal = null;
            try {
                date = inFormat.parse(mData.get(position).getDate());
                @SuppressLint("SimpleDateFormat") SimpleDateFormat outFormat = new SimpleDateFormat("EEEE");
                assert date != null;
                goal = outFormat.format(date);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            titre =holder.findViewById(R.id.label_seance);
            From = holder.findViewById(R.id.seance_new_date);
            Time_Num = holder.findViewById(R.id.newDate);
            monitor = holder.findViewById(R.id.monitor_seance);
            comment = holder.findViewById(R.id.From_To);

            From.setText(goal);
            Time_Num.setText(mData.get(position).getDate().substring(0,10));
            titre.setText(mData.get(position).getLabel());
            monitor.setText(mData.get(position).getMonitor());
            comment.setText(mData.get(position).getComments());

            return holder;


        }




    }






}