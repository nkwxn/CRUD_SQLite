package com.nicholas.sqlitecrud;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {
    Toolbar toolbar;
    FloatingActionButton fab;
    ListView lv;
    SQLiteDBHelper helper;
    LayoutInflater inflater;
    TextView tvNomor, tvName, tvPOB, tvJK, tvTgl, tvAlamat;
    View dialogView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        fab = findViewById(R.id.fabAdd);
        lv = findViewById(R.id.list_data);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), AddActivity.class));
            }
        });

        helper = new SQLiteDBHelper(this);
        lv.setOnItemClickListener(this);
    }

    public void setListView() {
        Cursor c = helper.allData();
        CustomCursorAdapter customCursorAdapter = new CustomCursorAdapter(this, c, 1);
        lv.setAdapter(customCursorAdapter);
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        TextView getId = view.findViewById(R.id.listID);
        final long id = Long.parseLong(getId.getText().toString());
        final Cursor c = helper.oneData(id);
        c.moveToFirst();

        final AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
        builder.setTitle("Choose");

        String[] opt = {"View Data", "Edit Data", "Delete Data"};

        builder.setItems(opt, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                switch (i) {
                    case 0:
                        final AlertDialog.Builder viewData = new AlertDialog.Builder(MainActivity.this);
                        inflater = getLayoutInflater();
                        dialogView = inflater.inflate(R.layout.view_data, null);
                        viewData.setView(dialogView);
                        viewData.setTitle("Lihat Data");

                        tvNomor = dialogView.findViewById(R.id.tvNo);
                        tvName = dialogView.findViewById(R.id.tvName);
                        tvPOB = dialogView.findViewById(R.id.tvTempatLahir);
                        tvTgl = dialogView.findViewById(R.id.tvTanggalLahir);
                        tvJK = dialogView.findViewById(R.id.tvJK);
                        tvAlamat = dialogView.findViewById(R.id.tvAlamat);

                        tvNomor.setText("Number: " + c.getString(c.getColumnIndex(SQLiteDBHelper.row_nomor)));
                        tvName.setText("Name: " + c.getString(c.getColumnIndex(SQLiteDBHelper.row_nama)));
                        tvPOB.setText("Place of Birth: " + c.getString(c.getColumnIndex(SQLiteDBHelper.row_tempatLahir)));
                        tvTgl.setText("Date of Birth: " + c.getString(c.getColumnIndex(SQLiteDBHelper.row_tglLahir)));
                        tvJK.setText("Gender: " + c.getString(c.getColumnIndex(SQLiteDBHelper.row_jk)));
                        tvAlamat.setText("Address: " + c.getString(c.getColumnIndex(SQLiteDBHelper.row_alamat)));

                        viewData.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                dialogInterface.dismiss();
                            }
                        });
                        viewData.show();
                        break;
                    case 1:
                        Intent idata = new Intent(getApplicationContext(), EditActivity.class);
                        idata.putExtra(SQLiteDBHelper.row_id, id);
                        startActivity(idata);
                        break;
                    case 2:
                        AlertDialog.Builder b2 = new AlertDialog.Builder(MainActivity.this);
                        b2.setMessage("This data will be deleted");
                        b2.setCancelable(true);
                        b2.setPositiveButton("Delete", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                helper.deleteData(id);
                                Toast.makeText(MainActivity.this, "Data deleted", Toast.LENGTH_SHORT).show();
                                setListView();
                            }
                        });
                        b2.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                dialogInterface.cancel();
                            }
                        });
                        AlertDialog ad = b2.create();
                        ad.show();
                        break;
                }
            }
        });
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    @Override
    protected void onResume() {
        super.onResume();
        setListView();
    }
}