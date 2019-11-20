package com.myapplication.made.firebaseoktoberprojeksendiri;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class UpdateDeleteActivity extends AppCompatActivity {

    public static final String KEY_PERSON = "key_person";
    @BindView(R.id.edt_nama)
    EditText edtNama;
    @BindView(R.id.edt_asal)
    EditText edtAsal;
    @BindView(R.id.btn_simpan)
    Button btnSimpan;
    @BindView(R.id.btn_delete)
    Button btnDelete;

    private DatabaseReference databaseReference;
    private String id, nama, asal;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_delete);
        ButterKnife.bind(this);

        databaseReference = FirebaseDatabase.getInstance().getReference();

        Peserta peserta = getIntent().getParcelableExtra(KEY_PERSON);
        id = peserta.getId();
        nama = peserta.getNama();
        asal = peserta.getAsal();

        edtNama.setText(nama);
        edtAsal.setText(asal);

        Toast.makeText(this, id +" "+ nama +" "+ asal, Toast.LENGTH_SHORT).show();
    }

    @OnClick({R.id.btn_simpan, R.id.btn_delete})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_simpan:
                String nama = edtNama.getText().toString().trim();
                String asal = edtAsal.getText().toString().trim();
                if (TextUtils.isEmpty(asal) || TextUtils.isEmpty(nama)) {
                    Toast.makeText(UpdateDeleteActivity.this, "Jangan Kosong", Toast.LENGTH_SHORT).show();
                } else {
                    updateData(nama, asal);
                }
                break;
            case R.id.btn_delete:
                deleteData();
                break;
        }
    }

    private void updateData(String nama, String asal) {
        Peserta peserta = new Peserta();
        peserta.setNama(nama);
        peserta.setAsal(asal);

        databaseReference.child("peserta").child(id).setValue(peserta).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                Toast.makeText(UpdateDeleteActivity.this, "Berhasil dirubah cuy", Toast.LENGTH_SHORT).show();
                finish();
            }
        });
    }

    private void deleteData() {
        databaseReference.child("peserta").child(id).removeValue();
        Toast.makeText(UpdateDeleteActivity.this, "Dihapus cuy", Toast.LENGTH_SHORT).show();
        finish();
    }
}
