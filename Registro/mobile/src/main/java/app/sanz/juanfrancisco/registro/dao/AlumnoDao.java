package app.sanz.juanfrancisco.registro.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

import app.sanz.juanfrancisco.registro.modelo.Alumno;

/**
 * Created by Juan Francisco on 06/10/2014.
 */
public class AlumnoDao extends SQLiteOpenHelper {

    private static final String DATABASE = "RegistroMitorix";  //nombre de la base
    private static final int VERSION = 1; //version

    public AlumnoDao(Context context) {
        super(context, DATABASE, null, VERSION);
    }

    public void guardar(Alumno alumno) {
        //Guarda en la BD, motor SQLite
        ContentValues values = new ContentValues();
        values.put("nombre", alumno.getNombre());
        values.put("sitio", alumno.getSite());
        values.put("direccion", alumno.getDireccion());
        values.put("telefono", alumno.getTelefono());
        values.put("nota", alumno.getNota());
        values.put("foto", alumno.getFoto());
        getWritableDatabase().insert("Alumnos", null, values);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        //Metodo que nos va a permitir crear tablas
        String ddl = "CREATE TABLE ALUMNOS (" +
                "id INTEGER PRIMARY KEY," +
                "nombre TEXT UNIQUE NOT NULL," +
                "sitio TEXT," +
                "direccion TEXT," +
                "telefono TEXT," +
                "nota REAL," +
                "foto TEXT);";
        //ejecuta la sentencia SQL
        db.execSQL(ddl);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        //Metodo, aqui ya sabe que existe una BD
        String ddl = "DROP TABLE IF EXISTS Alumnos";   //verificamos que exista la tabla Alumnos
        db.execSQL(ddl);

        this.onCreate(db);  //nos vuelve a recrear la BD
    }

    public List<Alumno> getLista() {

        String table = "Alumnos";
        String[] columns = {"id", "nombre", "sitio", "direccion", "telefono", "nota", "foto"};
        String selection = null;
        String[] selectionArgs = null;
        String groupBy = null;
        String having = null;
        String orderBy = null;

        Cursor cursor = getWritableDatabase().query(table, columns, selection, selectionArgs,
                groupBy, having, orderBy);

        ArrayList<Alumno> alumnos = new ArrayList<Alumno>();

        while (cursor.moveToNext()){
            Alumno alumno = new Alumno();
            alumno.setId(cursor.getLong(0));
            alumno.setNombre(cursor.getString(1));
            alumno.setSite(cursor.getString(2));
            alumno.setDireccion(cursor.getString(3));
            alumno.setTelefono(cursor.getString(4));
            alumno.setNota(cursor.getDouble(5));
            alumno.setFoto(cursor.getString(6));

            alumnos.add(alumno);
        }

        return alumnos;
    }

    public void eliminar(Alumno alumno) {

        String table = "Alumnos";
        String whereClause = "id=?"; //id es espacial
        String[] whereArgs = {alumno.getId().toString()};
        getWritableDatabase().delete(table, whereClause, whereArgs);
    }

    public void modificar(Alumno alumno) {

        ContentValues values = new ContentValues();
        values.put("nombre", alumno.getNombre());
        values.put("sitio", alumno.getSite());
        values.put("direccion", alumno.getDireccion());
        values.put("telefono", alumno.getTelefono());
        values.put("nota", alumno.getNota());
        values.put("foto", alumno.getFoto());

        String table = "Alumnos";
        String whereClause = "id=?";
        String[] whereArgs = {alumno.getId().toString()};

        getWritableDatabase().update(table, values, whereClause, whereArgs );
    }
}
