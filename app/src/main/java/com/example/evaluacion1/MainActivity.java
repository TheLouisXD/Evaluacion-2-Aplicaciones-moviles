package com.example.evaluacion1;

import android.app.AlertDialog;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import android.widget.Button;
import android.content.Intent;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements SensorEventListener{

    // Variables de los botones

    Button bEnviar, bSalir;

    EditText etNombre, etRut, etDescripcion;

    boolean Completado = false;

    private SensorEvent event;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        // Botones
        bEnviar = findViewById(R.id.btnEnviar);
        bSalir = findViewById(R.id.btnSalir);

        // Inputs
        etNombre = findViewById(R.id.nombre);
        etRut = findViewById(R.id.rut);
        etDescripcion = findViewById(R.id.incidente);

        // Funcion de validación
        bEnviar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Completado = Validacion();

                if (Completado) {
                    Confirmacion();
                }
            }
        });

        bSalir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MainActivity.this.finish();
                System.exit(0);
            }
        });

        // Obtenemos la fecha y hora del dispositivo
        TextView currentFecha = findViewById(R.id.fecha);

        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");

        String currentDateAndTime = sdf.format(new Date());

        currentFecha.setText(currentDateAndTime);

        EdgeToEdge.enable(this);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        super.onResume();
        SensorManager sm = (SensorManager) getSystemService(SENSOR_SERVICE);
        List<Sensor> sensors = sm.getSensorList(Sensor.TYPE_ACCELEROMETER);
        if (sensors.size() > 0){
            sm.registerListener(this, sensors.get(0), SensorManager.SENSOR_DELAY_GAME);
        }
    }

    @Override
    public void onSensorChanged(SensorEvent event){

        float y = event.values[1];

        if (y > 9 && y < 20) {

            Completado = Validacion();

            if (Completado) {
                Intent i = new Intent(MainActivity.this, Activity2.class);
                startActivity(i);
            }
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy){

    }


    private void Confirmacion() {
        new AlertDialog.Builder(this)
                .setTitle("Confirmación")
                .setMessage("¿Deseas enviar el reporte?")
                .setPositiveButton("Si", (dialog, which) -> {
                    Intent i = new Intent(MainActivity.this, Activity2.class);
                    startActivity(i);
                })
                .setNegativeButton("No", null)
                .show();
    }

    // Validacion de inputs
    private boolean Validacion(){
        if (etNombre.length() == 0) {
            etNombre.setError("Ingrese su nombre");
            return false;
        }

        if (etRut.length() == 0) {

            etRut.setError("Debe ingresar su rut");
            return false;

        } else {

            String inputText = etRut.getText().toString();

            // Verficamos que los numeros ingresados en el rut sigan el fomato requerido
            // el ^ indica el inicio del texto
            // el \\d{8} indica que deben haber 8 digitos al principio
            // el - quiere decir que debe haber un guion
            // y \\d$ quiere decir que debe haber un digito y el $ indica el fin del texto
            if (!inputText.matches("^\\d{8}-\\d$")) {
                etRut.setError("Debe seguir el formato 12345678-9");
                return false;
            }
        }

        if (etDescripcion.length() == 0) {
            etDescripcion.setError("Se necesita descripcion");
            return false;
        }

        return true;
    }
}