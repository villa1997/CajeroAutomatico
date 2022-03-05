package com.example.cajeroautomatico;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

public class Transacciones extends AppCompatActivity {
    EditText etMonto;
    RadioButton rbConsignar, rbRetirar;
    Float saldoActual = 0.0f;
    TextView tvSaldoActual;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transacciones);
        etMonto = (EditText) findViewById(R.id.etMonto);
        rbRetirar = (RadioButton) findViewById(R.id.rbRetirar);
        rbConsignar = (RadioButton) findViewById(R.id.rbConsignar);
        tvSaldoActual = (TextView) findViewById(R.id.tvSaldoActual);
        ConsultaDatos(null);
    }

    public void RealizarTransaccion(View view) {
        AdminBd adminBD = new AdminBd(this, "BaseData", null, 1);
        SQLiteDatabase sqLiteDatabase = adminBD.getWritableDatabase();
        String tipoTransaccion = "";
        if (!TextUtils.isEmpty(etMonto.getText().toString())) {
            if (rbRetirar.isChecked()) {
                tipoTransaccion = "Retirar";
                if (Float.parseFloat(etMonto.getText().toString()) > saldoActual) {
                    Toast.makeText(this, "El monto a debitar es mayor al saldo disponible", Toast.LENGTH_SHORT).show();
                    return;
                } else
                    saldoActual -= Float.parseFloat(etMonto.getText().toString());
            }
            if (rbConsignar.isChecked()) {
                tipoTransaccion = "Consignacion";
                saldoActual += Float.parseFloat(etMonto.getText().toString());
            }

            ContentValues insertar = new ContentValues();
            insertar.put("SaldoActual", saldoActual);
            insertar.put("TipoTransaccion", tipoTransaccion);
            sqLiteDatabase.insert("BancoTransacciones", null, insertar);
            sqLiteDatabase.close();
            Toast.makeText(this, "Transaccion realizada correctamente", Toast.LENGTH_SHORT).show();
            LimpiarCampos();
            ConsultaDatos(null);
        }
        else
        {
            Toast.makeText(this, "El monto es obligatorio", Toast.LENGTH_SHORT).show();
            return;
        }
    }

    public void ConsultaDatos(View view) {
        AdminBd adminBD = new AdminBd(this, "BaseData", null, 1);
        SQLiteDatabase sqLiteDatabase = adminBD.getWritableDatabase();
        Cursor fila = sqLiteDatabase.rawQuery("select SaldoActual, TipoTransaccion from BancoTransacciones order by IdRows desc limit 1", null);

        if (fila.moveToFirst()) {
            saldoActual = Float.parseFloat(fila.getString((0)));
            Toast.makeText(this, "Su ultimo movimiento fue: " + fila.getString((1)) + " y su saldo actual es: " + Float.parseFloat(fila.getString((0))),
                    Toast.LENGTH_SHORT).show();

            tvSaldoActual.setText("Su saldo actual es: " + saldoActual.toString());
        }
    }

    public void LimpiarCampos()
    {
        etMonto.setText("");
    }
}