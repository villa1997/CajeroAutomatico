package com.example.cajeroautomatico;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class RegisterUser extends AppCompatActivity {
    EditText etNombres, etDocumento, etEmail, etUsuario, etPassword;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_user);

        etNombres = (EditText) findViewById(R.id.etFullNameUser);
        etDocumento = (EditText) findViewById(R.id.etDocumentNumber);
        etEmail = (EditText) findViewById(R.id.etEmail);
        etUsuario = (EditText) findViewById(R.id.etUserName);
        etPassword = (EditText) findViewById(R.id.etPassword);

    }

    public void RegistrarUsuario(View view) {
        AdminBd adminBD = new AdminBd(this, "BaseData", null, 1);
        SQLiteDatabase sqLiteDatabase = adminBD.getWritableDatabase();

        if (TextUtils.isEmpty(etNombres.getText().toString()) || TextUtils.isEmpty(etDocumento.getText().toString()) || TextUtils.isEmpty(etEmail.getText().toString()) ||
                TextUtils.isEmpty(etUsuario.getText().toString())  || TextUtils.isEmpty(etPassword.getText().toString())) {
            Toast.makeText(this, "Por favor complete todos los campos", Toast.LENGTH_SHORT).show();
            return;
        }
        else{
            if(ConsultaUsuario(null))
            {
                Toast.makeText(this, "El usuario ya se encuentra registrado", Toast.LENGTH_SHORT).show();
                return;
            }
            else
            {

                ContentValues insertar = new ContentValues();
                insertar.put("Nombres", etNombres.getText().toString());
                insertar.put("Documento", etDocumento.getText().toString());
                insertar.put("Email", etEmail.getText().toString());
                insertar.put("Usuario", etUsuario.getText().toString());
                insertar.put("Password", etPassword.getText().toString());
                sqLiteDatabase.insert("Usuarios", null, insertar);
                sqLiteDatabase.close();
                Toast.makeText(this, "El usuario se insertó correctamente", Toast.LENGTH_SHORT).show();
                LimpiarCampos();
                Intent intent = new Intent(this, MainActivity.class);
                startActivity(intent);
            }
        }
    }
    public boolean ConsultaUsuario(View view) {
        AdminBd adminBD = new AdminBd(this, "BaseData", null, 1);
        SQLiteDatabase sqLiteDatabase = adminBD.getWritableDatabase();
        Cursor fila = sqLiteDatabase.rawQuery("select * from Usuarios where Documento = "+etDocumento.getText().toString()+" limit 1", null);

        if (fila.moveToFirst()) {
            return true;
        }else
        {
            return false;
        }
    }
    public void LimpiarCampos()
    {
        etNombres.setText("");
        etDocumento.setText("");
        etEmail.setText("");
        etUsuario.setText("");
        etPassword.setText("");
    }
}