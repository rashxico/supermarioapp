package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import androidx.appcompat.app.AppCompatActivity;

/**
 * Actividad de splash que muestra una pantalla de bienvenida al inicio de la aplicación.
 * Después de un breve periodo, inicia la actividad principal.
 */
public class SplashActivity extends AppCompatActivity {

    private static final int duracion = 3000; // Duración del splash en milisegundos (3 segundos)

    /**
     * Método onCreate que se ejecuta al crear la actividad.
     * Configura la pantalla de splash y establece un retraso antes de iniciar la actividad principal.
     *
     * @param savedInstanceState Estado guardado de la actividad.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        // Handler que ejecuta un retraso antes de iniciar la actividad principal
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                // Inicia la actividad principal
                Intent intent = new Intent(SplashActivity.this, MainActivity.class);
                startActivity(intent); // Inicia MainActivity
                finish(); // Cierra SplashActivity para evitar volver a ella al presionar 'Atrás'
            }
        }, duracion); // Tiempo de espera antes de ejecutar el Runnable
    }
}
