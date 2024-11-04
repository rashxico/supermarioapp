package com.example.myapplication;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.Glide;
import java.util.List;

/**
 * Adaptador personalizado para RecyclerView que muestra una lista de imágenes y nombres.
 * Cada elemento incluye una imagen y un nombre, y permite detectar clics en cada elemento.
 */
public class ImagenAdapter extends RecyclerView.Adapter<ImagenAdapter.ImageViewHolder> {

    // Contexto de la aplicación para cargar recursos
    private Context context;
    private List<Integer> listaImagenes; // Lista de recursos de imágenes
    private List<String> listaNombres; // Lista de nombres de las imágenes
    private OnItemClickListener listener; // Listener para manejar clics en elementos

    /**
     * Constructor del adaptador.
     *
     * @param context       Contexto de la aplicación
     * @param listaImagenes Lista de identificadores de recursos de imágenes
     * @param listaNombres  Lista de nombres correspondientes a las imágenes
     * @param listener      Listener para manejar eventos de clic en los elementos
     */
    public ImagenAdapter(Context context, List<Integer> listaImagenes, List<String> listaNombres, OnItemClickListener listener) {
        this.context = context;
        this.listaImagenes = listaImagenes;
        this.listaNombres = listaNombres;
        this.listener = listener;
    }

    /**
     * Crea una nueva instancia de ViewHolder cuando RecyclerView necesita un nuevo elemento.
     *
     * @param par   ViewGroup al que se añadirá la vista creada
     * @param vieww Tipo de vista del nuevo elemento
     * @return Un nuevo ViewHolder que contiene la vista del elemento
     */
    @NonNull
    @Override
    public ImageViewHolder onCreateViewHolder(@NonNull ViewGroup par, int vieww) {
        View view = LayoutInflater.from(context).inflate(R.layout.imagenes, par, false);
        return new ImageViewHolder(view);
    }

    /**
     * Enlaza los datos de una posición en particular a un ViewHolder.
     *
     * @param holder   ViewHolder donde se colocarán los datos
     * @param posicion Posición del elemento en el conjunto de datos
     */
    @Override
    public void onBindViewHolder(@NonNull ImageViewHolder holder, int posicion) {
        int numeroImagen = listaImagenes.get(posicion);
        String name = listaNombres.get(posicion);
        Glide.with(context).load(numeroImagen).into(holder.imageView);
        holder.nombreTextView.setText(name);
    }

    /**
     * Devuelve el número total de elementos en el conjunto de datos.
     *
     * @return Tamaño de la lista de imágenes
     */
    @Override
    public int getItemCount() {
        return listaImagenes.size();
    }

    /**
     * ViewHolder que representa cada elemento de la lista de RecyclerView.
     */
    public class ImageViewHolder extends RecyclerView.ViewHolder {

        ImageView imageView; // ImageView para mostrar la imagen
        TextView nombreTextView; // TextView para mostrar el nombre

        /**
         * Constructor del ViewHolder que configura la vista del elemento.
         *
         * @param itemView Vista del elemento
         */
        public ImageViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.imageView);
            nombreTextView = itemView.findViewById(R.id.nameTextView);

            // Configura el clic para el elemento
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    if (position != RecyclerView.NO_POSITION) {
                        listener.onItemClick(position);
                    }
                }
            });
        }
    }

    /**
     * Interfaz para manejar los eventos de clic en elementos de la lista.
     */
    public interface OnItemClickListener {
        /**
         * Método llamado al hacer clic en un elemento.
         *
         * @param position Posición del elemento en la lista
         */
        void onItemClick(int position);
    }
}
