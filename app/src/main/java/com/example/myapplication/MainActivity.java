package com.example.myapplication;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.Arrays;
import java.util.List;
import java.util.Locale;

import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.google.android.material.navigation.NavigationView;

/**
 * Actividad principal de la aplicación que muestra un menú lateral y una lista de personajes.
 * Permite cambiar el idioma, mostrar detalles de personajes y un menú de opciones.
 */
public class MainActivity extends AppCompatActivity {

    private DrawerLayout drawerLayout; // Contenedor del menú lateral

    /**
     * Método onCreate que se ejecuta al iniciar la actividad.
     * Configura la interfaz, el menú lateral, el RecyclerView, y carga el idioma guardado.
     *
     * @param savedInstanceState Estado guardado de la actividad.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Configuración de la Toolbar
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("");

        // Configuración del RecyclerView con un GridLayoutManager
        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 2));

        // Muestra un Snackbar de bienvenida
        View view = findViewById(android.R.id.content);
        Snackbar.make(view, getString(R.string.mens_bienvenida), Snackbar.LENGTH_SHORT).show();

        // Carga el idioma guardado en las preferencias
        cargarIdiomaGuardado();

        // Inicialización de listas para las imágenes y descripciones de personajes
        List<Integer> imageList = Arrays.asList(
                R.drawable.mario,
                R.drawable.luigi,
                R.drawable.image3,
                R.drawable.toadd
        );
        List<String> descriptionList = Arrays.asList(
                getString(R.string.desc_mario),
                getString(R.string.desc_luigi),
                getString(R.string.desc_peach),
                getString(R.string.desc_toad)
        );
        List<String> nombreList = Arrays.asList(
                getString(R.string.mario),
                getString(R.string.luigi),
                getString(R.string.peach),
                getString(R.string.toad)
        );
        List<String> caracList = Arrays.asList(
                getString(R.string.carac_mario),
                getString(R.string.carac_luigi),
                getString(R.string.carac_peach),
                getString(R.string.carac_toad)
        );

        // Configuración del adaptador para el RecyclerView
        ImagenAdapter imageAdapter = new ImagenAdapter(this, imageList, nombreList, new ImagenAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                Intent intent = new Intent(MainActivity.this, DescriptionActivity.class);
                intent.putExtra("imagen", imageList.get(position));
                intent.putExtra("DESCRIPTION", descriptionList.get(position));
                intent.putExtra("nombre", nombreList.get(position));
                intent.putExtra("caracteristicas", caracList.get(position));
                Toast.makeText(MainActivity.this, getString(R.string.detalles) + " " + nombreList.get(position), Toast.LENGTH_SHORT).show();
                startActivity(intent);
            }
        });
        recyclerView.setAdapter(imageAdapter);

        // Configuración del menú lateral y NavigationView
        drawerLayout = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.navigation_view);

        // Configura el ActionBarDrawerToggle para el menú lateral
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.drawer_open, R.string.drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        // Configuración del switch en el menú para cambiar de idioma

        Switch mySwitch = (Switch) navigationView.getMenu().findItem(R.id.app_bar_switch).getActionView();
        mySwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    cambiarIdioma("en");
                    Toast.makeText(MainActivity.this, "Switch activado", Toast.LENGTH_SHORT).show();
                } else {
                    cambiarIdioma("es");
                    Toast.makeText(MainActivity.this, "Switch desactivado", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    /**
     * Infla el menú de opciones en la barra de la aplicación.
     *
     * @param menu Menú en el que se añadirán los elementos.
     * @return true para mostrar el menú, false para ocultarlo.
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return true;
    }

    /**
     * Maneja la selección de elementos en el menú de opciones.
     *
     * @param item Elemento seleccionado.
     * @return true si se maneja la selección, false si se delega.
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.menu1) {
            mostrarDialogoAlerta();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * Muestra un diálogo de alerta con la información de la aplicación.
     */
    private void mostrarDialogoAlerta() {
        new AlertDialog.Builder(this)
                .setIcon(R.drawable.ico_)
                .setTitle(getString(R.string.firma))
                .setMessage("Versión: 1.0")
                .show();
    }

    /**
     * Cambia el idioma de la aplicación y guarda la selección en SharedPreferences.
     *
     * @param idioma Código del idioma a establecer (por ejemplo, "es" o "en").
     */
    private void cambiarIdioma(String idioma) {
        if (!idioma.equals(getResources().getConfiguration().locale.getLanguage())) {
            Locale locale = new Locale(idioma);
            SharedPreferences prefs = getSharedPreferences("MisPreferencias", Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = prefs.edit();
            editor.putString("idioma", idioma);
            editor.apply();

            Locale.setDefault(locale);
            Configuration configuration = new Configuration();
            configuration.setLocale(locale);
            getResources().updateConfiguration(configuration, getResources().getDisplayMetrics());
            recreate();
        }
    }

    /**
     * Maneja la pulsación del botón "Atrás".
     * Si el menú lateral está abierto, lo cierra; de lo contrario, se comporta normalmente.
     */
    @Override
    public void onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    /**
     * Carga el idioma guardado en SharedPreferences y establece la aplicación en ese idioma.
     */
    private void cargarIdiomaGuardado() {
        SharedPreferences prefs = getSharedPreferences("MisPreferencias", Context.MODE_PRIVATE);
        String idiomaGuardado = prefs.getString("idioma", "es");
        cambiarIdioma(idiomaGuardado);
    }
}