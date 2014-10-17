package app.sanz.juanfrancisco.registro.modelo;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RatingBar;

import app.sanz.juanfrancisco.registro.Formulario;
import app.sanz.juanfrancisco.registro.R;

/**
 * Created by Juan Francisco on 06/10/2014.
 */
public class FormularioHelper {

    private final EditText nombre;
    private final EditText sitio;
    private final EditText direccion;
    private final EditText telefono;
    private final RatingBar barraNota;
    private final ImageView foto;
    private final Alumno alumno;

    public FormularioHelper(Formulario formulario) {
        nombre = (EditText) formulario.findViewById(R.id.Name);
        sitio = (EditText) formulario.findViewById(R.id.Site);
        direccion = (EditText) formulario.findViewById(R.id.Direccion);
        telefono = (EditText) formulario.findViewById(R.id.Phone);
        barraNota = (RatingBar) formulario.findViewById(R.id.Note);
        foto = (ImageView) formulario.findViewById(R.id.Foto);
        alumno = new Alumno();
    }

    public Alumno guardarAlumno() {
        Alumno newAlumno = new Alumno();
        newAlumno.setNombre(nombre.getText().toString());
        newAlumno.setSite(sitio.getText().toString());
        newAlumno.setDireccion(direccion.getText().toString());
        newAlumno.setTelefono(telefono.getText().toString());
        newAlumno.setNota(Double.valueOf(barraNota.getRating()));
        return newAlumno;
    }

    public ImageView getFoto(){
        return foto;
    }

    public void cargarDatosModificar(Alumno alumnoSeleccionadoAModificar) {
        //colocamos los datos del alumno
        nombre.setText(alumnoSeleccionadoAModificar.getNombre());
        sitio.setText(alumnoSeleccionadoAModificar.getSite());
        direccion.setText(alumnoSeleccionadoAModificar.getDireccion());
        telefono.setText(alumnoSeleccionadoAModificar.getTelefono());
        barraNota.setRating(alumnoSeleccionadoAModificar.getNota().floatValue());
    }

    public void cargarImagen(String rutaArchivo) {
        alumno.setFoto(rutaArchivo);

        Bitmap imagen = BitmapFactory.decodeFile(rutaArchivo);
        Bitmap imagenReducida = Bitmap.createScaledBitmap(imagen, 100, 100,  true);

        foto.setImageBitmap(imagenReducida);
    }
}
