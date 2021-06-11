package com.sistema.apartamentos;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class RegisterActivity extends AppCompatActivity {

    private EditText etIdentification, etName, etLastName, etEmail1, etCountry, etCity, etPassword1, etPassword2;
    RadioButton rbAnfitrion, rbInvitado;
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    private FirebaseAuth mAuth = FirebaseAuth.getInstance();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        etIdentification = (EditText) findViewById(R.id.etIdentification);
        etName = (EditText) findViewById(R.id.etName);
        etLastName = (EditText) findViewById(R.id.etLastName);
        etEmail1 = (EditText) findViewById(R.id.etEmail1);
        etCountry = (EditText) findViewById(R.id.etCountry);
        etCity = (EditText) findViewById(R.id.etCity);
        etPassword1 = (EditText) findViewById(R.id.etPassword1);
        etPassword2 = (EditText) findViewById(R.id.etPassword2);
        rbAnfitrion= findViewById(R.id.rbAnfitrion);
        rbInvitado= findViewById(R.id.rbInvitado);
    }

    public void saveUser(View view) {
        String name = etName.getText().toString();
        String lastname = etLastName.getText().toString();
        String email1 = etEmail1.getText().toString();
        String country = etCountry.getText().toString();
        String city = etCity.getText().toString();
        String password1 = etPassword1.getText().toString();
        String password2 = etPassword2.getText().toString();
        if (name.isEmpty() ){
            Toast.makeText(getApplicationContext(),"Todos los campos son obligatorios",Toast.LENGTH_LONG).show();
            etName.requestFocus();
        }
        else if (lastname.isEmpty() ){
            Toast.makeText(getApplicationContext(),"Todos los campos son obligatorios",Toast.LENGTH_LONG).show();
            etLastName.requestFocus();
        }
        else if (email1.isEmpty()){
            Toast.makeText(getApplicationContext(),"Todos los campos son obligatorios",Toast.LENGTH_LONG).show();
            etEmail1.requestFocus();
        }
        else if (country.isEmpty()){
            Toast.makeText(getApplicationContext(),"Todos los campos son obligatorios",Toast.LENGTH_LONG).show();
            etCountry.requestFocus();
        }
        else if (city.isEmpty()){
            Toast.makeText(getApplicationContext(),"Todos los campos son obligatorios",Toast.LENGTH_LONG).show();
            etCity.requestFocus();
        }
        else if (password1.isEmpty()){
            Toast.makeText(getApplicationContext(),"Todos los campos son obligatorios",Toast.LENGTH_LONG).show();
            etPassword1.requestFocus();
        }
        else if (password1.length()<6){

            Toast.makeText(getApplicationContext(),"La contraseña debe contener  6 o más caracteres",Toast.LENGTH_LONG).show();
            etPassword1.requestFocus();
            etPassword1.setText("");
            etPassword2.setText("");
        }
        else if (password2.isEmpty()){
            Toast.makeText(getApplicationContext(),"Se debe confirmar su clave",Toast.LENGTH_LONG).show();
            etPassword2.requestFocus();
        }
        else if(!password1.equals(password2)){
            Toast.makeText(this,"Contraseñas no coinciden",Toast.LENGTH_SHORT).show();
            etPassword1.setText("");
            etPassword2.setText("");
            etPassword1.requestFocus();
        }
        else {
            DocumentReference docRef = db.collection("users").document(email1);
            docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                @Override
                public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                    if (task.isSuccessful()) {
                        DocumentSnapshot document = task.getResult();
                        if (document.exists()) {
                            AlertDialog.Builder builder = new AlertDialog.Builder(RegisterActivity.this);
                            builder.setTitle("Usuario ya existe.");
                            builder.setMessage("Deseas iniciar Sesion con:\n "+email1+"?")
                                    .setPositiveButton("Sí", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                                            intent.putExtra("email", email1);
                                            startActivity(intent);
                                        }
                                    })
                                    .setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            Toast.makeText(getApplicationContext(),"Por favor crear cuenta con otro nombre de usuario",Toast
                                                    .LENGTH_LONG).show();
                                            dialog.dismiss();
                                        }
                                    }).show();
                        }
                        else {
                            Map<String, Object> user = new HashMap<>();
                            user.put("password", password1);
                            if (rbAnfitrion.isChecked()) {
                                user.put("rol", "Anfitrion");
                            } else if (rbInvitado.isChecked()) {
                                user.put("rol", "Invitado");
                            }
                            user.put("name", name);
                            user.put("lastname", lastname);
                            user.put("email", email1);
                            user.put("country", country);
                            user.put("city", city);
                            db.collection("users").document(email1)
                                    .set(user)
                                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                                        @Override
                                        public void onSuccess(Void aVoid) {
                                            Toast.makeText(getApplicationContext(),"Usuario resgistrado correctamente",Toast.LENGTH_LONG).show();
                                            Intent intent= new Intent(getApplicationContext(),MainActivity.class);
                                            startActivity(intent);
                                            intent.putExtra("email", email1);
                                            intent.putExtra("password1", password1);
                                            registerUser(email1, password1);
                                        }
                                    })
                                    .addOnFailureListener(new OnFailureListener() {
                                        @Override
                                        public void onFailure(@NonNull Exception e) {
                                            Toast.makeText(getApplicationContext(),"No se pudo registrar",Toast.LENGTH_LONG).show();
                                        }
                                    });
                        }
                    }
                }
            });
        }
    }
    public void registerUser (String email1, String password1){
        mAuth.createUserWithEmailAndPassword(email1, password1)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(getApplicationContext(), "Usuario Registrado Correctamente", Toast.LENGTH_SHORT).show();

                        } else {
                            Toast.makeText(RegisterActivity.this,"Error, no se pudo registrar",Toast.LENGTH_SHORT).show();

                        }
                    }
                });
    }
}

