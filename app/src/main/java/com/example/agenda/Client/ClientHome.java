package com.example.agenda.Client;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.agenda.R;

public class ClientHome extends AppCompatActivity {

    Bundle d;
    TextView name;
    int id;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_client_home);



        d = getIntent().getExtras();

        name = findViewById(R.id.user_name);
        name.setText(d.getString("username"));
        id = d.getInt("id");
    }


    public void GoToEmploie(View view){
        Intent  intent = new Intent(getApplicationContext(), Test.class);
        d.putInt("client",id);
        intent.putExtras(d);
        startActivity(intent);
    }

    public void GoToAbsence(View view){
        Intent  intent = new Intent(getApplicationContext(), Absence.class);
        d.putInt("client",id);
        intent.putExtras(d);
        startActivity(intent);

    }

    public void GoToComments(View view){
        Intent  intent = new Intent(getApplicationContext(), Comments.class);
        d.putInt("client",id);
        intent.putExtras(d);
        startActivity(intent);

    }

}