package com.example.agenda.Home;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.example.agenda.Admin.AdminHome;
import com.example.agenda.Client.ClientHome;
import com.example.agenda.Monitor.MonitorHome;
import com.example.agenda.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class LoginPage extends AppCompatActivity {

    private String ipAddress = "http://192.168.1.121/RestAPI/";
    private JsonArrayRequest jsonArrayRequest;
    private RequestQueue requestQueue;
    private Bundle bundle = new Bundle();
    private EditText email,pass;
    private Button Login;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_page);

        email = findViewById(R.id.email_login);
        pass = findViewById(R.id.pass_login);
        Login = findViewById(R.id.Login_btn);

        Login.setOnClickListener(v -> l());




    }



    public void l (){
        jsonArrayRequest = new JsonArrayRequest(ipAddress+"user/Login.php?email="+email.getText().toString()+"&pass="+pass.getText().toString()+"", response -> {
            JSONObject jsonObject;
            for (int i = 0; i<response.length(); i++){

                try {
                    jsonObject = response.getJSONObject(i);

                    if(jsonObject.getString("role").equals("Admin")){
                        Intent intent = new Intent(getApplicationContext(), AdminHome.class);
                        bundle.putString("username", jsonObject.getString("full_name"));
                        intent.putExtras(bundle);
                        startActivity(intent);
                    }
                    else if(jsonObject.getString("role").equals("client")){
                        Intent intent = new Intent(getApplicationContext(), ClientHome.class);
                        bundle.putString("username", jsonObject.getString("full_name"));
                        bundle.putInt("id",jsonObject.getInt("id"));
                        intent.putExtras(bundle);
                        startActivity(intent);

                    }
                    else if(jsonObject.getString("role").equals("monitor")){
                        Intent intent = new Intent(getApplicationContext(), MonitorHome.class);
                        bundle.putString("username", jsonObject.getString("full_name"));
                        intent.putExtras(bundle);
                        startActivity(intent);

                    }else{
                        Toast.makeText(getApplicationContext(),"Account does not exist",Toast.LENGTH_LONG).show();

                    }


                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, error -> {

        });

        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        requestQueue.add(jsonArrayRequest);
    }

}