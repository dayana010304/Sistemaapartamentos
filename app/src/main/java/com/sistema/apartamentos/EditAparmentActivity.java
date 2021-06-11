package com.sistema.apartamentos;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;


public class EditAparmentActivity extends AppCompatActivity {

    String rol, password, email;
    private String modelId;
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    EditText edittCountry, edittCity, edittNumberHab, edittValueNight, edittReviewApar;
    TextView tvOwner1, tvAdress1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_aparment);

        email = getIntent().getStringExtra("email");
        rol = getIntent().getStringExtra("rol");
        password = getIntent().getStringExtra("password");

        modelId = getIntent().getStringExtra("modelId");
        edittCountry = findViewById(R.id.edittCountry);
        edittCity = findViewById(R.id.edittCity);
        tvAdress1 = findViewById(R.id.tvAddress1);
        tvOwner1 = findViewById(R.id.tvOwner1);
        edittNumberHab = findViewById(R.id.edittNumberHab);
        edittValueNight = findViewById(R.id.edittValueNight);
        edittReviewApar = findViewById(R.id.edittReviewApart);


        db.collection("apartments").document(modelId)
                .get()
                .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                        if (documentSnapshot.exists()) {
                            String country = documentSnapshot.getString("country");
                            String city = documentSnapshot.getString("city");
                            String owner = documentSnapshot.getString("owner");
                            String address = documentSnapshot.getString("address");
                            String numberhab = documentSnapshot.getString("numberhab");
                            String valuenight = documentSnapshot.getString("valuenight");
                            String reviewapar = documentSnapshot.getString("reviewapar");

                            edittCountry.setText(country);
                            edittCity.setText(city);
                            tvAdress1.setText(address);
                            tvOwner1.setText(owner);
                            edittNumberHab.setText(numberhab);
                            edittValueNight.setText(valuenight);
                            edittReviewApar.setText(reviewapar);
                        }
                    }
                });
    }


    public void updateDatos (View view) {
        String country = edittCountry.getText().toString();
        String city = edittCity.getText().toString();
        //String address = edittAddress.getText().toString();
        String numberhab = edittNumberHab.getText().toString();
        String valuenight = edittValueNight.getText().toString();
        String reviewapar = edittReviewApar.getText().toString();
        Map<String, Object> map = new HashMap<>();
        map.put("country", country);
        map.put("city", city);
        //map.put("address", address);
        map.put("numberhab", numberhab);
        map.put("valuenight", valuenight);
        map.put("reviewapar", reviewapar);
        db.collection("apartments").document(modelId)
                .update(map)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Intent intent = new Intent(EditAparmentActivity.this, ListApartmentActivity.class);
                        startActivity(intent);
                        Toast.makeText(EditAparmentActivity.this, "Los datos se actualizaron correctamente", Toast.LENGTH_SHORT).show();
                        onBackPressed();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(EditAparmentActivity.this, "Error, los datos no se pudieron actualizar", Toast.LENGTH_SHORT).show();
                    }
                });
    }
    public void onBackPressed(){
        super.onBackPressed();
    }

}