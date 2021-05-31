package com.example.agenda.Admin;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.example.agenda.Adapters.MailAdapter;
import com.example.agenda.Admin.SeanceManagement.Calendar_Admin;
import com.example.agenda.Admin.UsersManagement.UsersList;
import com.example.agenda.Models.Mail;
import com.example.agenda.Models.User;
import com.example.agenda.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class AdminHome extends AppCompatActivity {


    RecyclerView mail_receiver;
    private String ipAddress = "http://192.168.1.121/RestAPI/";
    TextView countClients,countAdmins,countMonitors;
    int counter = 0;
    final List<Mail> mailList = new ArrayList<>();
    private JsonArrayRequest ArrayRequest;
    private RequestQueue requestQueue;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_home);


        mail_receiver = findViewById(R.id.mail_receiver);
        countClients = findViewById(R.id.client_count);
        countAdmins = findViewById(R.id.admin_count);
        countMonitors = findViewById(R.id.monitor_count);

        CountClients();
        CountAdmins();
        CountMonitors();



        MAIL_LIST();


    }




    private void CountClients(){


        ArrayRequest = new JsonArrayRequest(ipAddress+"user/countClients.php", response -> {


            JSONObject jsonObject = null;

                for (int i =0; i<response.length(); i++){
                    try {
                        counter = response.getJSONObject(i).getInt("clients");
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }

            countClients.setText(String.valueOf(counter));
        }, error -> {

        });

        requestQueue = Volley.newRequestQueue(AdminHome.this);
        requestQueue.add(ArrayRequest);

    }

    private void CountMonitors(){


        ArrayRequest = new JsonArrayRequest(ipAddress+"user/countMonitors.php", response -> {


            JSONObject jsonObject = null;

            for (int i =0; i<response.length(); i++){
                try {
                    counter = response.getJSONObject(i).getInt("monitor");
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            countMonitors.setText(String.valueOf(counter));
        }, error -> {

        });

        requestQueue = Volley.newRequestQueue(AdminHome.this);
        requestQueue.add(ArrayRequest);

    }

    private void CountAdmins(){


        ArrayRequest = new JsonArrayRequest(ipAddress+"user/countAdmins.php", response -> {


            JSONObject jsonObject = null;

            for (int i =0; i<response.length(); i++){
                try {
                    counter = response.getJSONObject(i).getInt("admin");
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            countAdmins.setText(String.valueOf(counter));
        }, error -> {

        });

        requestQueue = Volley.newRequestQueue(AdminHome.this);
        requestQueue.add(ArrayRequest);

    }



    public void goToUserManagement(View view){
        Intent intent = new Intent(getApplicationContext(), UsersList.class);
        startActivity(intent);
    }
    public void goToSeanceManagement(View view){
        Intent intent = new Intent(getApplicationContext(), Calendar_Admin.class);
        startActivity(intent);
    }


    private void MAIL_LIST() {

        ArrayRequest = new JsonArrayRequest(ipAddress+"mail/mails.php", response -> {


            JSONObject jsonObject = null;

            for (int i =0; i<response.length(); i++){
                try {

                    Mail mail = new Mail();

                    mail.setId(response.getJSONObject(i).getInt("id"));
                    mail.setFrom(response.getJSONObject(i).getString("_from"));
                    mail.setClient_id_from(response.getJSONObject(i).getInt("client_id_from"));
                    mail.setContent(response.getJSONObject(i).getString("content"));


                    mailList.add(mail);

                }catch (JSONException e)
                {
                    e.printStackTrace();
                }
            }

            AdapterConfiguration(mailList);

        }, error -> {

        });

        requestQueue = Volley.newRequestQueue(AdminHome.this);
        requestQueue.add(ArrayRequest);
    }


    public void AdapterConfiguration(List<Mail> lst){

        MailAdapter myAdapter = new MailAdapter(this,lst);

        mail_receiver.setHasFixedSize(true);
        mail_receiver.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false ));
        mail_receiver.setAdapter(myAdapter);

    }




    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu,menu);

        return super.onCreateOptionsMenu(menu);
    }


}