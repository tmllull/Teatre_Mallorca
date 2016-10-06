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
public class MyCustomAdapterShows extends RecyclerView.Adapter<MyCustomAdapterShows.AdapterViewHolder> {

    ArrayList<Show> shows;
    MyCustomAdapterShows() {
        shows = new ArrayList<>();
    }

    @Override
    public MyCustomAdapterShows.AdapterViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        //Instancia un layout XML en la correspondiente vista.
        LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
        //Inflamos en la vista el layout para cada elemento
        View view = inflater.inflate(R.layout.row_layout_obres, viewGroup, false);
        return new AdapterViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MyCustomAdapterShows.AdapterViewHolder adapterViewHolder, int position) {
        if (shows != null) {
            adapterViewHolder.name.setText(shows.get(position).getTitle());
            adapterViewHolder.sessions.setText(shows.get(position).getSessions().toString());
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
        if (shows != null) return shows.size();
        return 0;
    }

    public class AdapterViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        /*
        *  Mantener una referencia a los elementos de nuestro ListView mientras el usuario realiza
        *  scrolling en nuestra aplicación. Así que cada vez que obtenemos la vista de un item,
        *  evitamos las frecuentes llamadas a findViewById, la cuál se realizaría únicamente la primera vez y el resto
        *  llamaríamos a la referencia en el ViewHolder, ahorrándonos procesamiento.
        */

        public TextView name;
        public TextView places;
        public TextView sessions;
        public View v;
        private String mTextView;
        private String mItem;

        public AdapterViewHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
            mTextView = itemView.toString();
            this.v = itemView;
            this.name = (TextView) itemView.findViewById(R.id.tv_nom_row);
            this.sessions = (TextView) itemView.findViewById(R.id.tv_sessions_row);
        }

        public void setItem (String item) {
            mItem = item;
            mTextView = item;
        }

        @Override
        public void onClick(View v) {

            Bundle bundle = new Bundle();
            bundle.putString("Title", shows.get(getAdapterPosition()).getTitle());
            Intent intent = new Intent (v.getContext(), DaysList.class);
            intent.putExtras(bundle);
            v.getContext().startActivity(intent);
            ((Activity)v.getContext()).finish();
        }
    }

    public void setDataSet (ArrayList<Show> shows) {
        this.shows = shows;
        notifyDataSetChanged();
    }
}
