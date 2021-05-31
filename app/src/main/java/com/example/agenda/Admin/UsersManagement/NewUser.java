package com.example.agenda.Admin.UsersManagement;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.agenda.R;

import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;

public class NewUser extends AppCompatActivity {

    private final List<String> Roles = new ArrayList<>();
    private EditText email,username,pass,phone;
    private Spinner role;
    private final String ipAddress = "http://192.168.1.121/RestAPI/";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_user);


        email = findViewById(R.id.email_new_user);
        username = findViewById(R.id.user_name_new_user);
        pass = findViewById(R.id.password_new_user);
        phone = findViewById(R.id.phone_new_user);
        role = findViewById(R.id.role_new_user);


        Spinner_Filling();

        TextView createUser = findViewById(R.id.CreateUser);
        createUser.setOnClickListener(v -> {
            newUser();
            SelectUser();
        });




    }


    public void Spinner_Filling(){
        // you need to have a list of data that you want the spinner to display

        Roles.add("Admin");
        Roles.add("client");
        Roles.add("monitor");

        ArrayAdapter<String> adapter = new ArrayAdapter<>(
                this, android.R.layout.simple_spinner_item, Roles);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        role.setAdapter(adapter);
    }


    public void newUser(){

        StringRequest stringRequest = new StringRequest(Request.Method.GET, ipAddress+"user/creationUser.php?email="+email.getText().toString()+"&pass="+pass.getText().toString()+"&phone="+phone.getText().toString()+"&user="+username.getText().toString(), response -> {

        }, error -> {

        });
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        requestQueue.add(stringRequest);

    }


    public void SelectUser(){
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(ipAddress+"user/userByEmail.php?email="+email.getText().toString(), response -> {
            for (int i =0;i< response.length(); i++){
                try {
                    AddRole(response.getJSONObject(i).getInt("id"));
                    Log.d("IS DONE :", ""+response.getJSONObject(i).getInt("id"));
                    Intent intent = new Intent(getApplicationContext(), UsersList.class);
                    startActivity(intent);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, error -> {

        });

        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        requestQueue.add(jsonArrayRequest);
    }


    public void AddRole(int id){
        StringRequest stringRequest = new StringRequest(Request.Method.GET, ipAddress+"user/relateUserWithRole.php?userID="+id+"&role="+role.getSelectedItem().toString(), response -> Log.d("IS DONE :", "YEP DONE"), error -> {

        });
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        requestQueue.add(stringRequest);
    }

}