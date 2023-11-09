package com.example.geoApp;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class Adapter extends RecyclerView.Adapter<Adapter.ViewHolder> {

    LayoutInflater inflater;
    List<Coord> coordinates;
    private OnNoteDeleteListener deleteListener;



    Adapter(Context context, List<Coord> coordinates, OnNoteDeleteListener deleteListener){
        this.inflater = LayoutInflater.from(context);
        this.coordinates = coordinates;
        this.deleteListener = deleteListener;

    }

    public void updateN(List<Coord> newCoords) {
        coordinates = newCoords;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public Adapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.list_view, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Adapter.ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        String ADDR = coordinates.get(position).getADDR();
        String LONGITUDE = coordinates.get(position).getLONGITUDE();
        String LATITUDE = coordinates.get(position).getLATITUDE();

        holder.ADDR.setText(ADDR);
        holder.LONGITUDE.setText(LONGITUDE);
        holder.LATITUDE.setText(LATITUDE);


        holder.dCoord.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Coord coordToDelete = coordinates.get(position);
                deleteListener.onNoteDelete(coordToDelete);
            }
        });

    }

    @Override
    public int getItemCount() {
        return coordinates.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView ADDR, LONGITUDE, LATITUDE;
        ImageView dCoord;
        public ViewHolder(@NonNull View itemView){
            super(itemView);
            ADDR = itemView.findViewById(R.id.ADDRESS);
            LONGITUDE = itemView.findViewById(R.id.LONGITUDE);
            LATITUDE = itemView.findViewById(R.id.LATITUDE);
            dCoord = itemView.findViewById(R.id.remove);

        }
    }
    public interface OnNoteDeleteListener {
        void onNoteDelete(Coord coord);
    }

}
