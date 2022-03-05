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
            if(ConsultaUsuario(null))
            {
                Intent intent = new Intent(this, Transacciones.class);
                startActivity(intent);
                Limpiar();
                Toast.makeText(this, "Inicio de sesión exitoso", Toast.LENGTH_SHORT).show();
            }else
            {
                Toast.makeText(this, "el usuario o la contraseña son incorrectos", Toast.LENGTH_SHORT).show();
            }
        }
    }

    //Event action to register user
    public void RegisterUser(View view)
    {
        Intent intent = new Intent(this, RegisterUser.class);
        startActivity(intent);
    }

    //event clear all elements
    private void Limpiar()
    {
        userName.setText("");
        passwordUser.setText("");
    }

    // event clear search user in database
    public boolean ConsultaUsuario(View view) {
        AdminBd adminBD = new AdminBd(this, "BaseData", null, 1);
        SQLiteDatabase sqLiteDatabase = adminBD.getWritableDatabase();
        Cursor fila = sqLiteDatabase.rawQuery("select * from Usuarios where Usuario = '"+userName.getText().toString()+"' and Password = '"+passwordUser.getText().toString()+"' limit 1", null);

        if (fila.moveToFirst()) {
            return true;
        }else
        {
            return false;
        }
    }
}