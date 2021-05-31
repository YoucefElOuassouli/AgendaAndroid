package com.example.agenda.Client;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
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
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.example.agenda.Admin.SeanceManagement.Calendar_Admin;
import com.example.agenda.Models.Seance;
import com.example.agenda.R;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Calendar extends AppCompatActivity {

    Adapter myAdapter;
    Random r;
    private String ipAddress = "http://192.168.1.121/RestAPI/";
    List<Seance> seanceList = new ArrayList<>();
    RecyclerView SeanceList,getSeanceListBySearching;
    EditText search;
    Bundle d;
    Button ff;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendar);

        SeanceList = findViewById(R.id.rv);
        getSeanceListBySearching = findViewById(R.id.rv2);
        d = getIntent().getExtras();

        Fill_Seance_Client();

        SeanceList.setAlpha(1);
        getSeanceListBySearching.setAlpha(0);

        search = findViewById(R.id.search_for_seance_or_day);

        search.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                myAdapter.filter(s);
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






    public void AdapterConfiguration(List<Seance> lst){

        Adapter myAdapter = new Adapter(this,lst);

        SeanceList.setLayoutManager(new LinearLayoutManager(this));
        SeanceList.setAdapter(myAdapter);

    }




    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu,menu);

        return super.onCreateOptionsMenu(menu);
    }

    public class Adapter extends RecyclerView.Adapter<Adapter.ViewHolder> {


        private Context mContext ;
        private List<Seance> mData ;
        private List<Seance> mDataCopy ;

        public Adapter(Context mContext, List<Seance> lst) {
            this.mContext = mContext;
            this.mData = lst;
            this.mDataCopy = new ArrayList<>();
            mDataCopy.addAll(mData);
        }



        @NonNull
        @Override
        public Adapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

            View view ;
            LayoutInflater mInflater = LayoutInflater.from(mContext);
            view = mInflater.inflate(R.layout.seance_view_with_progress,parent,false);
            // click listener here
            return new Adapter.ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(Adapter.ViewHolder holder, final int position) {

            List<Integer> Colors = new ArrayList<>();
            Colors.add(getResources().getColor(R.color.FirstSeance));
            Colors.add(getResources().getColor(R.color.SecondSeance));
            Colors.add(getResources().getColor(R.color.FifthSeance));
            Colors.add(getResources().getColor(R.color.ThirdSeance));

            String From = mData.get(position).getStartAt().substring(0,5);
           String From_To = ""+mData.get(position).getStartAt()+ " " + mData.get(position).getEndAt();
            holder.From.setText(From);
            holder.From_To.setText(From_To);
            holder.desc.setText(mData.get(position).getDescription());
            holder.titre.setText(mData.get(position).getLabel());
//            holder.SeanceHolder.setBackgroundColor(Colors.get(r.nextInt(4)));
        }




        public void filter(CharSequence charSequence){

            List<Seance> seances = new ArrayList<>();
            if(!TextUtils.isEmpty(charSequence)){
                for (Seance seance:mData){
                    if(seance.getLabel().toLowerCase().contains(charSequence)){
                        seances.add(seance);
                    }
                }
            }else{
                mData.addAll(mDataCopy);
            }


            mData.clear();
            mData.addAll(seances);

            for (Seance seance:mData){
                Log.d("result :",seance.getLabel());
            }
            notifyDataSetChanged();
            seances.clear();
        }


        @Override
        public int getItemCount() {
            return mData.size();
        }


        public class ViewHolder extends RecyclerView.ViewHolder {


            TextView titre, desc,From_To, From;
            RelativeLayout SeanceHolder;


            public ViewHolder(View itemView) {
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