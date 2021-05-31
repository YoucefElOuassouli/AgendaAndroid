package com.example.agenda.Admin.SeanceManagement;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CalendarView;
import android.widget.DatePicker;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.example.agenda.Adapters.MailAdapter;
import com.example.agenda.Adapters.UserAdapter;
import com.example.agenda.Models.Mail;
import com.example.agenda.Models.Seance;
import com.example.agenda.Models.User;
import com.example.agenda.R;

import org.json.JSONArray;
import org.json.JSONException;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Random;

public class Calendar_Admin extends AppCompatActivity {

    private final String ipAddress = "http://192.168.1.121/RestAPI/";
    Random r = new Random();
    private TextView Day_counter, Day;
    CalendarView calendarView;
    private RecyclerView SeanceList;
    List<Seance> seanceList = new ArrayList<>();
    String SelectedDate;
    Bundle d = new Bundle();


    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendar_admin);

        Day = findViewById(R.id.day);
        Day_counter = findViewById(R.id.Day_Num);
        SeanceList = findViewById(R.id.seancesInfo);
        calendarView = findViewById(R.id.calendarView);


        calendarView.setOnDateChangeListener((view, year, month, dayOfMonth) -> {
                    SelectedDate = year+"-"+month+"-"+dayOfMonth+" 00:00:00";


            Day_counter.setText(""+ dayOfMonth);
                    Day.setText("/ "+ month);

                    ListSeanceBySelectedDay(SelectedDate);


        });

    }

    public void NewSeance(View view){

        Intent intent = new Intent(getApplicationContext(),NewSeance.class);
        d.putString("SelectedDate",SelectedDate);
        intent.putExtras(d);
        startActivity(intent);

    }


    public void ListSeanceBySelectedDay(String SelectedDate){

        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(ipAddress+"seance/seances.php?DateStart="+SelectedDate, response -> {

                for (int i=0; i<response.length(); i++){
                    try {
                            Seance seance = new Seance();
                            seance.setId(response.getJSONObject(i).getInt("id"));
                            seance.setLabel(response.getJSONObject(i).getString("label"));
                            seance.setDateStart(response.getJSONObject(i).getString("DateStart"));
                            seance.setStartAt(response.getJSONObject(i).getString("startAt"));
                            seance.setEndAt(response.getJSONObject(i).getString("EndAt"));
                            seance.setDescription(response.getJSONObject(i).getString("description"));
                            seance.setMonitor_id(response.getJSONObject(i).getInt("monitor_id"));
                            seance.setPayee(response.getJSONObject(i).getInt("payee"));

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



    public void AdapterConfiguration(List<Seance> lst){

        SeanceAdapter myAdapter = new SeanceAdapter(this,lst);

        SeanceList.setLayoutManager(new LinearLayoutManager(this));
        SeanceList.setAdapter(myAdapter);

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu,menu);

        return super.onCreateOptionsMenu(menu);
    }



    public class SeanceAdapter extends RecyclerView.Adapter<SeanceAdapter.MailViewHolder> {


        private Context mContext ;
        private List<Seance> mData ;

        public SeanceAdapter(Context mContext, List<Seance> lst) {
            this.mContext = mContext;
            this.mData = lst;
        }



        @NonNull
        @Override
        public SeanceAdapter.MailViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

            View view ;
            LayoutInflater mInflater = LayoutInflater.from(mContext);
            view = mInflater.inflate(R.layout.seance_card_view,parent,false);
            // click listener here
            return new SeanceAdapter.MailViewHolder(view);
        }

        @SuppressLint("SetTextI18n")
        @Override
        public void onBindViewHolder(SeanceAdapter.MailViewHolder holder, final int position) {

                List<Integer> Colors = new ArrayList<>();
                Colors.add(getResources().getColor(R.color.FirstSeance));
                Colors.add(getResources().getColor(R.color.SecondSeance));
                Colors.add(getResources().getColor(R.color.FifthSeance));
                Colors.add(getResources().getColor(R.color.ThirdSeance));


                holder.From.setText(mData.get(position).getStartAt());
                holder.From_To.setText(mData.get(position).getStartAt()+ " " + mData.get(position).getEndAt());
                holder.desc.setText(mData.get(position).getDescription());
                holder.titre.setText(mData.get(position).getLabel());
                holder.SeanceHolder.setBackgroundColor(Colors.get(r.nextInt(4)));
        }





        @Override
        public int getItemCount() {
            return mData.size();
        }


        public class MailViewHolder extends RecyclerView.ViewHolder {


            TextView titre, desc,From_To, From;
            RelativeLayout SeanceHolder;


            public MailViewHolder(View itemView) {
                super(itemView);
                titre = itemView.findViewById(R.id.Seance_title);
                desc = itemView.findViewById(R.id.seance_desc);
                From_To = itemView.findViewById(R.id.From_To);
                From = itemView.findViewById(R.id.Time);
                SeanceHolder = itemView.findViewById(R.id.SeanceHolder);
            }
        }

    }



}