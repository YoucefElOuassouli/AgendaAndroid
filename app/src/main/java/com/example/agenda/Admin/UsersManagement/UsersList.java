package com.example.agenda.Admin.UsersManagement;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.example.agenda.Adapters.MailAdapter;
import com.example.agenda.Adapters.UserAdapter;
import com.example.agenda.Admin.AdminHome;
import com.example.agenda.Models.Mail;
import com.example.agenda.Models.User;
import com.example.agenda.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class UsersList extends AppCompatActivity {

    private String ipAddress = "http://192.168.1.121/RestAPI/";
    private JsonArrayRequest ArrayRequest;
    private RequestQueue requestQueue;
    private final List<User> userList = new ArrayList<>();
    private RecyclerView UserRecycler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_users_list);


        UserRecycler = findViewById(R.id.uses_recycler);
        USERS_LIST();

    }

    public  void NewUser(View view){
        Intent intent = new Intent(getApplicationContext(), NewUser.class);
        startActivity(intent);
    }

    private void USERS_LIST() {

        ArrayRequest = new JsonArrayRequest(ipAddress+"user/listUsers.php", response -> {


            JSONObject jsonObject = null;

            for (int i =0; i<response.length(); i++){
                try {

                    User user = new User();
                        user.setId(response.getJSONObject(i).getInt("id"));
                        user.setEmail(response.getJSONObject(i).getString("email"));
                        user.setFull_name(response.getJSONObject(i).getString("full_name"));
                        user.setPhone(response.getJSONObject(i).getString("phone"));
                        user.setRole(response.getJSONObject(i).getString("role"));
                        Log.d("testing ",user.getEmail());

                    userList.add(user);

                }catch (JSONException e)
                {
                    e.printStackTrace();
                }
            }

            AdapterConfiguration(userList);

        }, error -> {

        });

        requestQueue = Volley.newRequestQueue(UsersList.this);
        requestQueue.add(ArrayRequest);
    }

    public void AdapterConfiguration(List<User> lst){

        UserAdapter myAdapter = new UserAdapter(this,lst);

        UserRecycler.setLayoutManager(new LinearLayoutManager(this));
        UserRecycler.setAdapter(myAdapter);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu,menu);

        return super.onCreateOptionsMenu(menu);
    }

}