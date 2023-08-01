package com.example.projekakhir191103624nickyazriel;

import android.database.Cursor;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

public class PemainActivity extends AppCompatActivity {
    EditText nomor, nama, jeniskelamin, posisi;

    Button simpan, tampil, edit, hapus;

    DBHelper DB;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pemain);

        nomor = findViewById(R.id.nomor);
        nama = findViewById(R.id.nama);
        jeniskelamin = findViewById(R.id.jk);
        posisi = findViewById(R.id.posisi);

        simpan = findViewById(R.id.btnsimpan);
        tampil = findViewById(R.id.btntampil);
        edit = findViewById(R.id.btnedit);
        hapus = findViewById(R.id.btnhapus);

        DB = new DBHelper(this);

        simpan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String no = nomor.getText().toString();
                String nm = nama.getText().toString();
                String jk = jeniskelamin.getText().toString();
                String pos = posisi.getText().toString();

                if (TextUtils.isEmpty(no) || TextUtils.isEmpty(nm) || TextUtils.isEmpty(jk) || TextUtils.isEmpty(pos))
                    Toast.makeText(PemainActivity.this, "Semua Field Wajib diIsi", Toast.LENGTH_LONG).show();
                else {
                    Boolean ceknoPMN= DB.cekno(no);
                    if (ceknoPMN == false){
                        Boolean insert = DB.insertDataPMN(no,nm,jk,pos);
                        if (insert == true){
                            Toast.makeText(PemainActivity.this, "Data berhasil disimpan", Toast.LENGTH_SHORT).show();
                        }else {
                            Toast.makeText(PemainActivity.this,"Data gagal disimpan", Toast.LENGTH_SHORT).show();
                        }
                    }else {
                        Toast.makeText(PemainActivity.this,"Data Mahasiswa Sudah Ada", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

        tampil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Cursor res = DB.tampilDataPMN();
                if (res.getCount()==0){
                    Toast.makeText(PemainActivity.this, "Tidak ada Data", Toast.LENGTH_SHORT).show();
                    return;
                }
                StringBuffer buffer =  new StringBuffer();
                while (res.moveToNext()){
                    buffer.append("Nomor Pemain  : "+res.getString(0)+"\n");
                    buffer.append("Nama Pemain   : "+res.getString(1)+"\n");
                    buffer.append("Jenis Kelamin : "+res.getString(2)+"\n");
                    buffer.append("Posisi Pemain : "+res.getString(3)+"\n\n");
                }
                AlertDialog.Builder builder = new AlertDialog.Builder(PemainActivity.this);
                builder.setCancelable(true);
                builder.setTitle("Biodata Pemain");
                builder.setMessage(buffer.toString());
                builder.show();
            }
        });

        hapus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String kb = nomor.getText().toString();
                Boolean cekHapusData = DB.hapusDataPMN(kb);
                if (cekHapusData == true)
                    Toast.makeText(PemainActivity.this, "Data Berhasil Terhapus", Toast.LENGTH_SHORT).show();
                else
                    Toast.makeText(PemainActivity.this, "Data Tidak Ada", Toast.LENGTH_SHORT).show();
            }
        });

        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String no_ = nomor.getText().toString();
                String nm_ = nama.getText().toString();
                String jk_ = jeniskelamin.getText().toString();
                String pos_ = posisi.getText().toString();

                if (TextUtils.isEmpty(no_) || TextUtils.isEmpty(nm_) || TextUtils.isEmpty(jk_) || TextUtils.isEmpty(jk_) || TextUtils.isEmpty(pos_))
                    Toast.makeText(PemainActivity.this, "Semua Field Wajib diIsi", Toast.LENGTH_LONG).show();
                else {
                    Boolean edit = DB.editDataPMN(no_,nm_,jk_,pos_);
                    if (edit == false){
                        Toast.makeText(PemainActivity.this, "Data berhasil diedit", Toast.LENGTH_SHORT).show();
                    }else {
                        Toast.makeText(PemainActivity.this,"Data gagal diedit", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

    }
}