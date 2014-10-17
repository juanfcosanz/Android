package app.sanz.juanfrancisco.registro;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;

import java.util.List;
import java.util.Objects;

import app.sanz.juanfrancisco.registro.dao.AlumnoDao;
import app.sanz.juanfrancisco.registro.modelo.Alumno;


public class ListaAlumnos extends Activity {

    ListView    listaAlumnos;
    int         layout = android.R.layout.simple_list_item_1;
    ImageButton btnAdd;
    private Alumno alumno;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_alumnos);

        btnAdd = (ImageButton) findViewById(R.id.btnAddUser);
        listaAlumnos = (ListView) findViewById(R.id.lista);

        //debemos registrar el menu y asociarla al componente
        registerForContextMenu(listaAlumnos);


       listaAlumnos.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapter, View v,
                                    int position, long id) {
                Alumno alumnoSeleccionado = (Alumno) adapter.getItemAtPosition(position);

                Intent irParaFormulario = new Intent(ListaAlumnos.this, Formulario.class);
                irParaFormulario.putExtra("alumnoSeleccionado", alumnoSeleccionado);  //mandamos el alumnoSeleccionado
                startActivity(irParaFormulario);
            }
        });

        listaAlumnos.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapter, View view,
                                           int position, long is) {
                alumno = (Alumno) adapter.getItemAtPosition(position);  //obtenemos el alumno seleccionado
//                Toast.makeText(ListaAlumnos.this, "Nombre seleccionado manteniendo presión larga:" + alumno,
//                        Toast.LENGTH_LONG).show();
                return false;
            }
        });

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Toast.makeText(ListaAlumnos.this,"Hola, haz hecho click en la imagen!!",Toast.LENGTH_SHORT).show();;
                Intent intent = new Intent("app.sanz.juanfrancisco.registro.modelo.FORMULARIO");
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();  //metodo que se inicia cuando podemos ver la vista
        cargarLista();
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v,
                                    ContextMenu.ContextMenuInfo menuInfo) {
        MenuItem llamar = menu.add("Llamar");
        llamar.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {

                Intent marcarNumero = new Intent(Intent.ACTION_CALL );
                Uri llamarAPersona = Uri.parse("tel:"+alumno.getTelefono());
                marcarNumero.setData(llamarAPersona);   //le damos datos
                startActivity(marcarNumero);

                return false;
            }
        });
        menu.add("Enviar un SMS");
        MenuItem visitarSite = menu.add("Visitar su Sitio Web");
        visitarSite.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                Intent irParaSite = new Intent(Intent.ACTION_VIEW);
                Uri localSite = Uri.parse("http://"+alumno.getSite());
                irParaSite.setData(localSite);
                startActivity(irParaSite);

                return false;
            }
        });

        MenuItem eliminar = menu.add("Eliminar");
        eliminar.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                AlumnoDao dao = new AlumnoDao(ListaAlumnos.this);
                dao.eliminar(alumno);
                //cargamos la lista nueva actualizada
                cargarLista();
                dao.close();
                return false;
            }
        });
        menu.add("Ver en el mapa");
        menu.add("Enviar email");

        super.onCreateContextMenu(menu, v, menuInfo);
    }

    protected void cargarLista() {
        AlumnoDao dao = new AlumnoDao(this);

        List<Alumno> alumnos = dao.getLista();
        dao.close();

        ArrayAdapter<Alumno> adapter = new ArrayAdapter<Alumno>(this, layout , alumnos);
        listaAlumnos.setAdapter(adapter);
    }
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.lista_alumnos, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
