package com.myapplication.made.firebaseoktoberprojeksendiri;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class PesertaAdapter extends RecyclerView.Adapter<PesertaAdapter.ViewHolder> {

    List<Peserta> listPeserta;

    public PesertaAdapter(List<Peserta> listPerson) {
        this.listPeserta = listPerson;
    }

    @NonNull
    @Override
    public PesertaAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_peserta, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final PesertaAdapter.ViewHolder holder, final int position) {

        holder.txtAsal.setText(listPeserta.get(position).getAsal());
        holder.txtNama.setText(listPeserta.get(position).getNama());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // todo siapkan data untuk dikirim
                Peserta peserta = new Peserta();
                peserta.setId(listPeserta.get(position).getId());
                peserta.setNama(listPeserta.get(position).getNama());
                peserta.setAsal(listPeserta.get(position).getAsal());

                // todo kirim data dengan parcelable
                Intent intent = new Intent(holder.itemView.getContext(), UpdateDeleteActivity.class);
                intent.putExtra(UpdateDeleteActivity.KEY_PERSON, peserta);
                holder.itemView.getContext().startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return listPeserta.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView txtNama, txtAsal;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            txtNama = itemView.findViewById(R.id.tv_nama);
            txtAsal = itemView.findViewById(R.id.tv_asal);
        }
    }
}