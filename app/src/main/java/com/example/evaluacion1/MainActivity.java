package com.example.evaluacion1;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import java.text.SimpleDateFormat;
import java.util.Date;
import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import android.widget.Button;
import android.content.Intent;

public class MainActivity extends AppCompatActivity {

    Button bEnviar, bSalir;

    EditText etNombre, etRut, etDescripcion;

    boolean isAllFieldsCheked = false;

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

                isAllFieldsCheked = CheckAllFields();

                if (isAllFieldsCheked) {
                    Intent i = new Intent(MainActivity.this, Activity2.class);
                    startActivity(i);
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
    }

    // Validacion de inputs
    private boolean CheckAllFields(){
        if (etNombre.length() == 0) {
            etNombre.setError("Ingrese su nombre");
            return false;
        }

        if (etRut.length() == 0) {
            etRut.setError("Rut invalido");
            return false;
        }

        if (etDescripcion.length() == 0) {
            etDescripcion.setError("Se necesita descripcion");
            return false;
        }

        return true;
    }
}