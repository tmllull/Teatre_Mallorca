package com.example.tonimiquelllullamengual.teatre_mallorca;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 * Created by tonimiquelllullamengual on 17/5/16.
 */
public class MyCustomAdapterDays extends RecyclerView.Adapter<MyCustomAdapterDays.AdapterViewHolder> {

    ArrayList<Day> days;
    String date, dayOfTheWeek;

    MyCustomAdapterDays() {
        days = new ArrayList<>();
    }

    DbHelper dbHelper;
    int disp = 1;

    @Override
    public MyCustomAdapterDays.AdapterViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        //Instancia un layout XML en la correspondiente vista.
        LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
        //Inflamos en la vista el layout para cada elemento
        View view = inflater.inflate(R.layout.row_layout_days, viewGroup, false);
        return new AdapterViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MyCustomAdapterDays.AdapterViewHolder adapterViewHolder, int position) {
        if (days != null) {
            adapterViewHolder.places.setText(days.get(position).getPlaces().toString());
            date = days.get(position).getDate().toString();
            dayOfTheWeek = days.get(position).getDayOfTheWeek().toString();
            adapterViewHolder.day.setText(dayOfTheWeek + ", " + date);
            if (position % 2 == 0) {
                adapterViewHolder.itemView.setBackgroundColor(0xFFFFFFFF);
            } else {
                adapterViewHolder.itemView.setBackgroundColor(0xFFECEFF1);
            }
        }
    }

    @Override
    public int getItemCount() {
        if (days != null) return days.size();
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
        public TextView day;
        public View v;
        private String mTextView;
        private String mItem;

        public AdapterViewHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
            mTextView = itemView.toString();
            this.v = itemView;
            this.places = (TextView) itemView.findViewById(R.id.tvDaysListPlaces);
            this.day = (TextView) itemView.findViewById(R.id.tvDaysListDay);
        }

        public void setItem(String item) {
            mItem = item;
            mTextView = item;
        }

        @Override
        public void onClick(final View v) {
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yy");
            String actualDate = sdf.format(new Date());
            Date d = null;
            try {
                d = sdf.parse(actualDate);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            final long milis = d.getTime();
            dbHelper = new DbHelper(v.getContext());
            String name = days.get(getAdapterPosition()).getName();
            String daay = days.get(getAdapterPosition()).getDate();
            final Cursor c = dbHelper.getShowDate(name, daay);
            if (c.moveToFirst()) {
                if (Long.valueOf(c.getString(c.getColumnIndex(dbHelper.CN_MILIS))) < milis) {
                    new AlertDialog.Builder(v.getContext())
                            .setTitle("La sessió ha expirat")
                            .setMessage("Aquesta sessió ja no està available. " +
                                    "Pots consultar la seva informació i users, però no " +
                                    "podràs comprar tickets")
                            .setPositiveButton("D'acord", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    disp = 0;
                                    Bundle bundle = new Bundle();
                                    bundle.putString(String.valueOf(R.string.bundleTitle),
                                            days.get(getAdapterPosition()).getName());
                                    bundle.putString(String.valueOf(R.string.bundleDate),
                                            days.get(getAdapterPosition()).getDate());
                                    bundle.putString(String.valueOf(R.string.bundleDayOfTheWeek),
                                            days.get(getAdapterPosition()).getDayOfTheWeek());
                                    bundle.putInt(String.valueOf(R.string.bundleAvailable), disp);
                                    Intent intent = new Intent(v.getContext(), InfoShow.class);
                                    intent.putExtras(bundle);
                                    v.getContext().startActivity(intent);
                                    ((Activity) v.getContext()).finish();

                                }
                            })
                            .setNegativeButton(R.string.back, new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                }
                            })
                            .setIcon(R.drawable.info)
                            .show();

                }
                else {
                    Bundle bundle = new Bundle();
                    bundle.putString(String.valueOf(R.string.bundleTitle),
                            days.get(getAdapterPosition()).getName());
                    bundle.putString(String.valueOf(R.string.bundleDate),
                            days.get(getAdapterPosition()).getDate());
                    bundle.putString(String.valueOf(R.string.bundleDayOfTheWeek),
                            days.get(getAdapterPosition()).getDayOfTheWeek());
                    bundle.putInt(String.valueOf(R.string.bundleAvailable), disp);
                    Intent intent = new Intent(v.getContext(), InfoShow.class);
                    intent.putExtras(bundle);
                    v.getContext().startActivity(intent);
                    ((Activity) v.getContext()).finish();
                }
            }
        }
    }

    public void setDataSet(ArrayList<Day> days) {
        this.days = days;
        notifyDataSetChanged();
    }
}
