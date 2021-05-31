package com.sistema.apartamentos;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;

import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Map;

public class perfiluserActivity extends AppCompatActivity {

    FirebaseFirestore db = FirebaseFirestore.getInstance();
    private FirebaseAuth mAuth = FirebaseAuth.getInstance();
    private  String modelId;
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

        modelId = getIntent().getStringExtra("modelId");


    }
    public void obtenerDatosUser (View view){
        db.collection("users").document(modelId)
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
                        }
                    }
                });
    }

}