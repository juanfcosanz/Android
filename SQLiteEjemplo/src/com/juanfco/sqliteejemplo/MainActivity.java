package com.juanfco.sqliteejemplo;

import android.app.Activity;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;

public class MainActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		// abrir la conexion a la BD
		SQLiteDatabase bd = openOrCreateDatabase("MiBD", Context.MODE_PRIVATE,
				null);
		// como no hay tablas en la Bd , creamo una
		bd.execSQL("CREATE TABLE IF NOT EXISTS PERSONA(nombre VARCHAR, ape_paterno VARCHAR);");

		/*
		 * CODIGO USADO PARA INSERTA DATOS // escribimos informacion en la BD
		 * String nombre = "Juan"; String apellido = "Perez"; String Sql =
		 * "INSERT INTO PERSONA VALUES('" + nombre + "','" + apellido + "');";
		 * // ejecutamos la sentecia bd.execSQL(Sql);
		 */

		// el cursor nos va a permitir movernos entre cada uno de los registros
		// de la tabla
		Cursor c = bd.rawQuery("SELECT * FROM PERSONA", null);
		c.moveToFirst();
		Log.d("Valor 1", c.getString(c.getColumnIndex("nombre")));
		Log.d("Valor 2", c.getString(c.getColumnIndex("ape_paterno")));

		bd.close();
	}
}
