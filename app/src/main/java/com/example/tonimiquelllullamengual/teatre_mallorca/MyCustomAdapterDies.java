package com.example.tonimiquelllullamengual.teatre_mallorca;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 * Created by tonimiquelllullamengual on 17/5/16.
 */
public class MyCustomAdapterDies extends RecyclerView.Adapter<MyCustomAdapterDies.AdapterViewHolder> {

    ArrayList<Dia> dies;
    String data, dia_setmana;

    MyCustomAdapterDies() {
        dies = new ArrayList<>();
    }

    DbHelper dbHelper;
    int disp = 1;

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
            adapterViewHolder.places.setText(dies.get(position).getPlaces().toString());
            data = dies.get(position).getDia().toString();
            dia_setmana = dies.get(position).getDiaSetmana().toString();
            adapterViewHolder.dia.setText(dia_setmana + ", " + data);
            if (position % 2 == 0) {
                adapterViewHolder.itemView.setBackgroundColor(0xFFFFFFFF);
            } else {
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

        public void setItem(String item) {
            mItem = item;
            mTextView = item;
        }

        @Override
        public void onClick(final View v) {
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yy");
            String data_actual = sdf.format(new Date());
            Date d = null;
            try {
                d = sdf.parse(data_actual);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            final long milis = d.getTime();
            dbHelper = new DbHelper(v.getContext());
            String nom = dies.get(getAdapterPosition()).getNom();
            String diia = dies.get(getAdapterPosition()).getDia();
            final Cursor c = dbHelper.getObraData(nom, diia);
            if (c.moveToFirst()) {
                if (Long.valueOf(c.getString(c.getColumnIndex(dbHelper.CN_MILIS))) < milis) {
                    new AlertDialog.Builder(v.getContext())
                            .setTitle("La sessió ha expirat")
                            .setMessage("Aquesta sessió ja no està disponible. " +
                                    "Pots consultar la seva informació i usuaris, però no " +
                                    "podràs comprar entrades")
                            .setPositiveButton("D'acord", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    disp = 0;
                                    Bundle bundle = new Bundle();
                                    bundle.putString("Titol", dies.get(getAdapterPosition()).getNom());
                                    bundle.putString("Data", dies.get(getAdapterPosition()).getDia());
                                    bundle.putString("DiaSetmana", dies.get(getAdapterPosition()).getDiaSetmana());
                                    bundle.putInt("Disponible", disp);
                                    Intent intent = new Intent(v.getContext(), InfoObra.class);
                                    intent.putExtras(bundle);
                                    v.getContext().startActivity(intent);
                                    ((Activity) v.getContext()).finish();

                                }
                            })
                            .setNegativeButton("Tornar", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                }
                            })
                            .setIcon(R.drawable.info)
                            .show();

                }
                else {
                    Bundle bundle = new Bundle();
                    bundle.putString("Titol", dies.get(getAdapterPosition()).getNom());
                    bundle.putString("Data", dies.get(getAdapterPosition()).getDia());
                    bundle.putString("DiaSetmana", dies.get(getAdapterPosition()).getDiaSetmana());
                    bundle.putInt("Disponible", disp);
                    Intent intent = new Intent(v.getContext(), InfoObra.class);
                    intent.putExtras(bundle);
                    v.getContext().startActivity(intent);
                    ((Activity) v.getContext()).finish();
                }
            }
        }
    }

    public void setDataSet(ArrayList<Dia> dies) {
        this.dies = dies;
        notifyDataSetChanged();
    }
}
