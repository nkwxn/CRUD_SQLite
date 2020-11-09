package com.nicholas.sqlitecrud;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Calendar;

public class EditActivity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener {
    SQLiteDBHelper helper;
    EditText txtNomor, txtName, txtPOB, txtAddress;
    TextView txtDOB;
    Spinner spGend;
    Button btnDatePick;
    long id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);

        helper = new SQLiteDBHelper(this);

        id = getIntent().getLongExtra(SQLiteDBHelper.row_id, 0);

        txtNomor = findViewById(R.id.txtNoEdit);

        btnDatePick = findViewById(R.id.btnSelectorEdit);
        txtDOB = findViewById(R.id.txtTanggalLahirEdit);
        txtName = findViewById(R.id.txtNameEdit);
        txtPOB = findViewById(R.id.txtTempatLahirEdit);
        txtAddress = findViewById(R.id.txtAlamatEdit);
        spGend = findViewById(R.id.spnGenderEdit);

        btnDatePick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDatePickDialog();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_edit, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.save_edit:
                String nomor = txtNomor.getText().toString().trim();
                String nama = txtName.getText().toString().trim();
                String tempatLahir = txtPOB.getText().toString().trim();
                String tanggalLahir = txtDOB.getText().toString().trim();
                String alamat = txtAddress.getText().toString().trim();
                String jk = spGend.getSelectedItem().toString().trim();

                ContentValues values = new ContentValues();
                values.put(SQLiteDBHelper.row_nomor, nomor);
                values.put(SQLiteDBHelper.row_nama, nama);
                values.put(SQLiteDBHelper.row_tempatLahir, tempatLahir);
                values.put(SQLiteDBHelper.row_tglLahir, tanggalLahir);
                values.put(SQLiteDBHelper.row_alamat, alamat);
                values.put(SQLiteDBHelper.row_jk, jk);

                if (nomor.equals("") || nama.equals("") || tempatLahir.equals("") || tanggalLahir.equals("") || alamat.equals("")) {
                    Toast.makeText(this, "Data tidak boleh kosong", Toast.LENGTH_SHORT).show();
                } else {
                    helper.updateData(values, id);
                    Toast.makeText(this, "Data tersimpan", Toast.LENGTH_SHORT).show();
                    finish();
                }
                break;
            case R.id.delete_edit:
                AlertDialog.Builder b2 = new AlertDialog.Builder(EditActivity.this);
                b2.setMessage("This data will be deleted");
                b2.setCancelable(true);
                b2.setPositiveButton("Delete", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        helper.deleteData(id);
                        Toast.makeText(EditActivity.this, "Data deleted", Toast.LENGTH_SHORT).show();
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
        return super.onOptionsItemSelected(item);
    }

    private void showDatePickDialog() {
        DatePickerDialog datePickerDialog = new DatePickerDialog(this, this,
                Calendar.getInstance().get(Calendar.YEAR),
                Calendar.getInstance().get(Calendar.MONTH),
                Calendar.getInstance().get(Calendar.DAY_OF_MONTH)
        );
        datePickerDialog.show();
    }

    @Override
    public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
        String date = i2 + "/" + i1 + "/" + i;
        txtDOB.setText(date);
    }
}