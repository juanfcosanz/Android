package www.androiddev.com.memorydev;

import android.app.Activity;
import android.app.Dialog;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.Message;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


public class MyActivity extends Activity implements View.OnClickListener{

    ImageView   img1, img2, img3, img4, img5, img6, img7, img8, img9,
                img10, img11, img12, imgCapturada;
    Button      jugar, salir;
    TextView    idMensaje;
    int         contador = 0, capImg1, capImg2, capCtrl1, capCtrl2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my);

        img1 = (ImageView) findViewById(R.id.imagen1);
        img2 = (ImageView) findViewById(R.id.imagen2);
        img3 = (ImageView) findViewById(R.id.imagen3);
        img4 = (ImageView) findViewById(R.id.imagen4);
        img5 = (ImageView) findViewById(R.id.imagen5);
        img6 = (ImageView) findViewById(R.id.imagen6);
        img7 = (ImageView) findViewById(R.id.imagen7);
        img8 = (ImageView) findViewById(R.id.imagen8);
        img9 = (ImageView) findViewById(R.id.imagen9);
        img10 = (ImageView) findViewById(R.id.imagen10);
        img11 = (ImageView) findViewById(R.id.imagen11);
        img12 = (ImageView) findViewById(R.id.imagen12);

        jugar = (Button) findViewById(R.id.BtnJugar);
        salir = (Button) findViewById(R.id.BtnSalir);

        idMensaje = (TextView) findViewById(R.id.TxtContador);

        img1.setOnClickListener(this);
        img2.setOnClickListener(this);
        img3.setOnClickListener(this);
        img4.setOnClickListener(this);
        img5.setOnClickListener(this);
        img6.setOnClickListener(this);
        img7.setOnClickListener(this);
        img8.setOnClickListener(this);
        img9.setOnClickListener(this);
        img10.setOnClickListener(this);
        img11.setOnClickListener(this);
        img12.setOnClickListener(this);

        jugar.setOnClickListener(this);
        salir.setOnClickListener(this);

        desactivar();

    }

    public void desactivar(){
        img1.setEnabled(false);
        img2.setEnabled(false);
        img3.setEnabled(false);
        img4.setEnabled(false);
        img5.setEnabled(false);
        img6.setEnabled(false);
        img7.setEnabled(false);
        img8.setEnabled(false);
        img9.setEnabled(false);
        img10.setEnabled(false);
        img11.setEnabled(false);
        img12.setEnabled(false);
    }

    public void activar(){
        img1.setEnabled(true);
        img2.setEnabled(true);
        img3.setEnabled(true);
        img4.setEnabled(true);
        img5.setEnabled(true);
        img6.setEnabled(true);
        img7.setEnabled(true);
        img8.setEnabled(true);
        img9.setEnabled(true);
        img10.setEnabled(true);
        img11.setEnabled(true);
        img12.setEnabled(true);
    }

    public void comparar(int idImagen, int idControl, int rControl,final ImageView imagen){

        if( capImg1 == 0){
            capImg1 = idImagen;
            capCtrl1 = idControl;
            imgCapturada = (ImageView) findViewById(capCtrl1);
//            Toast d= Toast.makeText(this, "valor: "+capCtrl1, Toast.LENGTH_SHORT);
//            d.show();
        }else{
            capCtrl2 = rControl;
//            Toast d= Toast.makeText(this, "valor: "+capCtrl1, Toast.LENGTH_SHORT);
//            d.show();
            if(capCtrl2 != capCtrl1){
                capImg2 = idImagen;
                if(capImg1 != capImg2){
                    Handler handler = new Handler();
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            imgCapturada.setImageResource(R.drawable.droid_logo);
                            imagen.setImageResource(R.drawable.droid_logo);
                        }
                    }, 500);
                    capImg1 = 0;
                    capImg2 = 0;
                }
                else{
                    //si las imagenes son iguales
                    imgCapturada.setEnabled(false);
                    imagen.setEnabled(false);
                    contador++;
                    capImg1 = 0;
                    capImg2 = 0;
                }
            }
            else{
                //si lo controles llegan a ser iguales
                capCtrl2 = 0;
            }
        }

    }

    public void cronometro(){
        new CountDownTimer(60000, 1000){

            @Override
            public void onTick(long miliSegundos) {
                //este evento se invoca en cada intervalo de decrementación (cada 1seg)
                jugar.setEnabled(false);
                idMensaje.setText(""+miliSegundos/1000);
                if( contador == 6){
                    idMensaje.setText("Ganaste!!");
                    onFinish(); //finaliza el conteo
                }
            }

            @Override
            public void onFinish() {
                //se activa cuando la cuenta atras a acabado
                desactivar();
                jugar.setEnabled(true);
                if( contador != 6){
                    idMensaje.setText("Perdiste!!!");
                }
            }
        }.start();  //comienza el conteo regresivo

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.my, menu);
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

    @Override
    public void onClick(View v) {

        switch (v.getId()){
            case R.id.imagen1:
                img1.setImageResource(R.drawable.icono1);
                comparar(R.drawable.icono1, R.id.imagen1, v.getId(), img1);
                break;
            case R.id.imagen2:
                img2.setImageResource(R.drawable.icono2);
                comparar(R.drawable.icono2, R.id.imagen2, v.getId(), img2);
                break;
            case R.id.imagen3:
                img3.setImageResource(R.drawable.icono3);
                comparar(R.drawable.icono3, R.id.imagen3, v.getId(), img3);
                break;
            case R.id.imagen4:
                img4.setImageResource(R.drawable.icono4);
                comparar(R.drawable.icono4, R.id.imagen4, v.getId(), img4);
                break;
            case R.id.imagen5:
                img5.setImageResource(R.drawable.icono5);
                comparar(R.drawable.icono5, R.id.imagen5, v.getId(), img5);
                break;
            case R.id.imagen6:
                img6.setImageResource(R.drawable.icono6);
                comparar(R.drawable.icono6, R.id.imagen6, v.getId(), img6);
                break;
            case R.id.imagen7:
                img7.setImageResource(R.drawable.icono1);
                comparar(R.drawable.icono1, R.id.imagen7, v.getId(), img7);
                break;
            case R.id.imagen8:
                img8.setImageResource(R.drawable.icono2);
                comparar(R.drawable.icono2, R.id.imagen8, v.getId(), img8);
                break;
            case R.id.imagen9:
                img9.setImageResource(R.drawable.icono3);
                comparar(R.drawable.icono3, R.id.imagen9, v.getId(), img9);
                break;
            case R.id.imagen10:
                img10.setImageResource(R.drawable.icono4);
                comparar(R.drawable.icono4, R.id.imagen10, v.getId(), img10);
                break;
            case R.id.imagen11:
                img11.setImageResource(R.drawable.icono5);
                comparar(R.drawable.icono5, R.id.imagen11, v.getId(), img11);
                break;
            case R.id.imagen12:
                img12.setImageResource(R.drawable.icono6);
                comparar(R.drawable.icono6, R.id.imagen12, v.getId(), img12);
                break;
            case R.id.BtnJugar:
                activar();
                cronometro();
                break;
            case R.id.BtnSalir:
                finish();
                break;

        }

    }
}
