package com.example.cajeroautomatico;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    EditText userName, passwordUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        userName = (EditText) findViewById(R.id.etUserName);
        passwordUser = (EditText) findViewById(R.id.etPassword);
    }

    //Event action to login user
    public void Login(View view)
    {
        String user =  userName.getText().toString();
        String password =  passwordUser.getText().toString();
        if(user.isEmpty() || password.isEmpty())
        {
            Toast.makeText(this, "Todos los campos son obligatorios", Toast.LENGTH_SHORT).show();
        }
        else
        {

            Intent intent = new Intent(this, Transacciones.class);
            startActivity(intent);
            Limpiar();
            Toast.makeText(this, "Inicio de sesión exitoso", Toast.LENGTH_SHORT).show();



        }
    }

    //Event action to register user
    public void RegisterUser(View view)
    {
        Intent intent = new Intent(this, RegisterUser.class);
        startActivity(intent);
    }

    private void Limpiar()
    {
        userName.setText("");
        passwordUser.setText("");
    }
}