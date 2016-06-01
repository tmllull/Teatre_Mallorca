package com.example.tonimiquelllullamengual.teatre_mallorca;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by tonimiquelllullamengual on 17/5/16.
 */
public class MyCustomAdapterDies extends RecyclerView.Adapter<MyCustomAdapterDies.AdapterViewHolder> {

    ArrayList<Dia> dies;
    String data;
    MyCustomAdapterDies() {
        dies = new ArrayList<>();
    }

    @Override
    public MyCustomAdapterDies.AdapterViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        //Instancia un layout XML en la correspondiente vista.
        LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
        //Inflamos en la vista el layout para cada elemento
        View view = inflater.inflate(R.layout.row_layout_dies, viewGroup, false);
        return new AdapterViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MyCustomAdapterDies.AdapterViewHolder adapterViewHolder, int position) {
        if (dies != null) {
//            adapterViewHolder.nom.setText(dies.get(position).getNom());
            adapterViewHolder.places.setText(dies.get(position).getPlaces().toString());
            data = dies.get(position).getDia().toString();
            String dia_setmana = dies.get(position).getDiaSetmana().toString();
            if (dia_setmana.equals("Mon") || dia_setmana.equals("Lun.")) dia_setmana = "Dilluns";
            else if (dia_setmana.equals("Tue") || dia_setmana.equals("Mar."))
                dia_setmana = "Dimarts";
            else if (dia_setmana.equals("Wed") || dia_setmana.equals("Mie."))
                dia_setmana = "Dimecres";
            else if (dia_setmana.equals("Thu") || dia_setmana.equals("Jue."))
                dia_setmana = "Dijous";
            else if (dia_setmana.equals("Fri") || dia_setmana.equals("Vie."))
                dia_setmana = "Divendres";
            else if (dia_setmana.equals("Sat") || dia_setmana.equals("Sab."))
                dia_setmana = "Dissabte";
            else if (dia_setmana.equals("Sun") || dia_setmana.equals("Dom."))
                dia_setmana = "Diumenge";
            //else return;
            //adapterViewHolder.dia.setText("Hola");
            adapterViewHolder.dia.setText(dia_setmana+", "+data);
            if (position % 2 == 0) {
                adapterViewHolder.itemView.setBackgroundColor(0xFFFFFFFF);
            }
            else {
                adapterViewHolder.itemView.setBackgroundColor(0xFFECEFF1);
            }
        }
    }

    @Override
    public int getItemCount() {
        if (dies != null) return dies.size();
        return 0;
    }

    public class AdapterViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        /*
        *  Mantener una referencia a los elementos de nuestro ListView mientras el usuario realiza
        *  scrolling en nuestra aplicación. Así que cada vez que obtenemos la vista de un item,
        *  evitamos las frecuentes llamadas a findViewById, la cuál se realizaría únicamente la primera vez y el resto
        *  llamaríamos a la referencia en el ViewHolder, ahorrándonos procesamiento.
        */

        public TextView nom;
        public TextView places;
        public TextView dia;
        public View v;
        private String mTextView;
        private String mItem;

        public AdapterViewHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
            mTextView = itemView.toString();
            this.v = itemView;
            this.places = (TextView) itemView.findViewById(R.id.tv_places_llista_dies);
            this.dia = (TextView) itemView.findViewById(R.id.tv_dia_dies);
        }

        public void setItem (String item) {
            mItem = item;
            mTextView = item;
        }

        @Override
        public void onClick(View v) {

            Bundle bundle = new Bundle();
            bundle.putString("Titol", dies.get(getAdapterPosition()).getNom());
            bundle.putString("Data", data);
            bundle.putString("DiaSetmana", dia.getText().toString());
            Intent intent = new Intent (v.getContext(), InfoObra.class);
            intent.putExtras(bundle);
            v.getContext().startActivity(intent);
            ((Activity)v.getContext()).finish();
        }
    }

    public void setDataSet (ArrayList<Dia> dies) {
        this.dies = dies;
        notifyDataSetChanged();
    }
}
