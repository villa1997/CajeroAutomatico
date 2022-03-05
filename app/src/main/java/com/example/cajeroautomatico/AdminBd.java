package com.example.cajeroautomatico;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;


public class AdminBd  extends SQLiteOpenHelper {

    public AdminBd(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase BaseData) {
        BaseData.execSQL("create table BancoTransacciones(IdRows integer primary key asc, SaldoActual real default 300000, TipoTransaccion text default 'Consignacion')");
        BaseData.execSQL("create table Usuarios(IdRows integer primary key asc, Documento text, Nombres text, Email text, Usuario text, Password text)");

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
    }
}
