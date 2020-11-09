package com.nicholas.sqlitecrud;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.ContentValues;
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

public class AddActivity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener {
    SQLiteDBHelper helper;
    EditText txtNomor, txtName, txtPOB, txtAddress;
    TextView txtDOB;
    Spinner spGend;
    Button btnDatePick;
    long id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);

        helper = new SQLiteDBHelper(this);

        id = getIntent().getLongExtra(SQLiteDBHelper.row_id, 0);

        txtNomor = findViewById(R.id.txtNoAdd);

        btnDatePick = findViewById(R.id.btnSelector);
        txtDOB = findViewById(R.id.txtTanggalLahir);
        txtName = findViewById(R.id.txtName);
        txtPOB = findViewById(R.id.txtTempatLahiradd);
        txtAddress = findViewById(R.id.txtAlamatadd);
        spGend = findViewById(R.id.spnGender);

        btnDatePick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDatePickDialog();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_add, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.save_add:
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
                    helper.insertData(values);
                    Toast.makeText(this, "Data tersimpan", Toast.LENGTH_SHORT).show();
                    finish();
                }
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
        String date = i2 + "/" + (i1+1) + "/" + i;
        txtDOB.setText(date);
    }
}