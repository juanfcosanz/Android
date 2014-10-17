package app.sanz.juanfrancisco.registro;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.File;
import java.io.Serializable;

import app.sanz.juanfrancisco.registro.dao.AlumnoDao;
import app.sanz.juanfrancisco.registro.modelo.Alumno;
import app.sanz.juanfrancisco.registro.modelo.FormularioHelper;

/**
 * Created by Juan Francisco on 06/10/2014.
 */
public class Formulario extends Activity{

    private         FormularioHelper formularioHelper;
    Button          grabarAlumno;
    ImageView       foto;
    private         String rutaArchivo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.formulario);

        grabarAlumno = (Button) findViewById(R.id.btnGrabarAlumno);
        formularioHelper = new FormularioHelper(this);

        //recibimos los datos al formulario
        Intent intent = getIntent();
        final Alumno alumnoSeleccionadoAModificar = (Alumno) intent.getSerializableExtra("alumnoSeleccionado");

        if( alumnoSeleccionadoAModificar != null ){
            grabarAlumno.setText("Modificar Datos");
            formularioHelper.cargarDatosModificar(alumnoSeleccionadoAModificar);
        }

        grabarAlumno.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Alumno alumno = formularioHelper.guardarAlumno();

                AlumnoDao dao = new AlumnoDao(Formulario.this);

                if ( alumnoSeleccionadoAModificar == null ){
                    dao.guardar(alumno);
                }else{
                    alumno.setId(alumnoSeleccionadoAModificar.getId());
                    dao.modificar(alumno);
                }

                dao.close();  //cerramos los recursos

                finish();  //volvemos al listado
                //Toast.makeText(Formulario.this,"Registro guardado!!",Toast.LENGTH_SHORT).show();
            }
        });

        foto = formularioHelper.getFoto();
        foto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //nos da el control de la camara (lanzar)
                Intent irParaCamara = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

                //ruta donde guardamos la camara, con el tiempo en miliseg del system y extencion png
                rutaArchivo = Environment.getExternalStorageDirectory().toString()
                        +"/"+System.currentTimeMillis()+".png";

                //Obtenemos la refencias
                File archivo = new File(rutaArchivo);
                Uri localImagen = Uri.fromFile(archivo);

                //lo enviamos como un put Extra(Extra_UoTPUT) -> salida de la imagen
                irParaCamara.putExtra(MediaStore.EXTRA_OUTPUT, localImagen);

                startActivityForResult(irParaCamara, 123);

            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        //metodo para sobreescribir

        if( requestCode == 123){
            if( resultCode == Activity.RESULT_OK){
                //si paso el 123, y si hice ok se carga la imagen
                formularioHelper.cargarImagen(rutaArchivo);
            }else{
                rutaArchivo = null;
            }
        }
        //super.onActivityResult(requestCode, resultCode, data);
    }
}
