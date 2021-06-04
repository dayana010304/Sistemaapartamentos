package com.sistema.apartamentos;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class perfiluserActivity extends AppCompatActivity {

    FirebaseFirestore db = FirebaseFirestore.getInstance();
    private FirebaseAuth mAuth = FirebaseAuth.getInstance();
    TextView tvIdentificacion, tvIdentification, tvNombre, tvName, tvApellido, tvLastName, tvCorreo, tvEmail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_perfiluser);
        tvIdentificacion = findViewById(R.id.tvIdentificacion);
        tvIdentification = findViewById(R.id.tvIdentification);
        tvNombre = findViewById(R.id.tvNombre);
        tvName = findViewById(R.id.tvName);
        tvApellido = findViewById(R.id.tvApellido);
        tvLastName = findViewById(R.id.tvLastName);
        tvCorreo = findViewById(R.id.tvCorreo);
        tvEmail = findViewById(R.id.tvEmail);



    }

    public void readUser(View view) {
        db.collection("users").document("11111")
                .get()
                .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                        if (documentSnapshot.exists()) {
                            String identification = documentSnapshot.getString("identification");
                            String name = documentSnapshot.getString("name");
                            String lastname = documentSnapshot.getString("lastname");
                            String email = documentSnapshot.getString("email");

                            tvIdentification.setText(identification);
                            tvName.setText(name);
                            tvLastName.setText(lastname);
                            tvEmail.setText(email);
                        } else {
                            Toast.makeText(perfiluserActivity.this, "No tienes conexion a internet", Toast.LENGTH_SHORT).show();
                            //Log.d(TAG, "get failed with ", task.getException());
                        }
                    }

                });

    }

}