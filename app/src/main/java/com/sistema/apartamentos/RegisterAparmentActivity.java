package com.sistema.apartamentos;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class RegisterAparmentActivity extends AppCompatActivity {

    EditText etOwner, etConuntrya, etCitya, etAddressa, etNumberHab, etValueNight, etReviewApar;
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_aparment);
        etOwner = findViewById(R.id.etOwner);
        etConuntrya = findViewById(R.id.etCountrya);
        etCitya = findViewById(R.id.etCitya);
        etAddressa = findViewById(R.id.etAddressa);
        etNumberHab = findViewById(R.id.etNumberHab);
        etValueNight = findViewById(R.id.etValueNight);
        etReviewApar = findViewById(R.id.etReviewApar);

    }

    public void addApartment (View view){
        Map<String, Object> apartment = new HashMap<>();
        String owner = etOwner.getText().toString();
        String country = etConuntrya.getText().toString();
        String city = etCitya.getText().toString();
        String address = etAddressa.getText().toString();
        String numberhab = etNumberHab.getText().toString();
        String valuenight = etValueNight.getText().toString();
        String reviewapar = etReviewApar.getText().toString();

        apartment.put("owner", owner);
        apartment.put("country", country);
        apartment.put("city", city);
        apartment.put("address", address);
        apartment.put("numberhab", numberhab);
        apartment.put("valuenight", valuenight);
        apartment.put("reviewapar", reviewapar);
        db.collection("apartments").document(address)
                .set(apartment)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Toast.makeText(RegisterAparmentActivity.this, "Apartemnto registrado con exito", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(RegisterAparmentActivity.this, ListApartmentActivity.class);
                        startActivity(intent);
                        onBackPressed();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(RegisterAparmentActivity.this, "Hubo un error, no se pudo registrar", Toast.LENGTH_SHORT).show();
                    }
                });

    }

    public void onBackPressed(){
        super.onBackPressed();
    }

}