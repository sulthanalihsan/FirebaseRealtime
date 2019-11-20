package com.myapplication.made.firebaseoktoberprojeksendiri;

import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Adapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class RealtimeDatabaseActivity extends AppCompatActivity {

    @BindView(R.id.edt_nama)
    EditText edtNama;
    @BindView(R.id.edt_asal)
    EditText edtAsal;
    @BindView(R.id.btn_simpan)
    Button btnSimpan;

    private FirebaseDatabase database;
    private DatabaseReference databaseReference;
    private RecyclerView recyclerView;
    private PesertaAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_realtime_database);
        ButterKnife.bind(this);

        recyclerView = findViewById(R.id.rv_peserta);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(RealtimeDatabaseActivity.this));

        showPeserta();

    }

    private void showPeserta() {
        databaseReference = FirebaseDatabase.getInstance().getReference("peserta");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                ArrayList<Peserta> listPeserta = new ArrayList<>();

                for (DataSnapshot pesertaDataSnapshot : dataSnapshot.getChildren()) {
                    Peserta peserta = pesertaDataSnapshot.getValue(Peserta.class);
                    peserta.setId(pesertaDataSnapshot.getKey());
                    listPeserta.add(peserta);
                }
                adapter = new PesertaAdapter(listPeserta);
                adapter.notifyDataSetChanged();
                recyclerView.setAdapter(adapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    @OnClick(R.id.btn_simpan)
    public void onViewClicked() {
        String nama = edtNama.getText().toString().trim();
        String asal = edtAsal.getText().toString().trim();
        if (TextUtils.isEmpty(nama) || TextUtils.isEmpty(asal)) {
            Toast.makeText(this, "Ga Boleh Kosong", Toast.LENGTH_SHORT).show();
        } else {
            Peserta peserta = new Peserta();
            peserta.setNama(nama);
            peserta.setAsal(asal);

            String childID = databaseReference.push().getKey();

            databaseReference.child(childID).setValue(peserta).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    edtNama.setText("");
                    edtAsal.setText("");
                    Toast.makeText(RealtimeDatabaseActivity.this, "berhasil disimpan", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }
}
