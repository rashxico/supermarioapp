package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

/**
 * La clase DescriptionActivity muestra una interfaz con los detalles de un personaje.
 * Recibe información de otra actividad mediante un Intent y la presenta en la pantalla.
 */
public class DescriptionActivity extends AppCompatActivity {

    /**
     * Método onCreate para inicializar la actividad y mostrar los datos del personaje.
     *
     * @param savedInstanceState Contiene el estado más reciente guardado de la actividad,
     *                           o null si es la primera vez que se crea.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_descripcion);

        // Configuración del botón para volver a la actividad principal
        Button boton = findViewById(R.id.button);

        // Obtiene el Intent con la información recibida
        Intent intent = getIntent();
        String descripcion = intent.getStringExtra("DESCRIPTION");
        String nombre = intent.getStringExtra("nombre");
        String caracteristicas = intent.getStringExtra("caracteristicas");
        int imageId = intent.getIntExtra("imagen", -1);

        // Establece la imagen en ImageView
        ImageView imageView = findViewById(R.id.imagePersonaje);
        imageView.setImageResource(imageId);

        // Muestra la descripción en el TextView correspondiente
        TextView textView = findViewById(R.id.textViewDescripcion);
        textView.setText(descripcion);

        // Muestra el nombre en su TextView
        TextView textView0 = findViewById(R.id.textViewNombre);
        textView0.setText(nombre);

        // Muestra las características en su TextView
        TextView textView1 = findViewById(R.id.textViewCaracteristicas);
        textView1.setText(caracteristicas);

        // Configura el evento click del botón para ir a la actividad principal
        boton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DescriptionActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }
}


