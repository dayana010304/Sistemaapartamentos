package com.sistema.apartamentos;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class RegisterActivity extends AppCompatActivity {

    private EditText etIdentification, etName, etLastName, etEmail1, etCountry, etCity, etPassword1, etPassword2;
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
    }

    public void saveUser(View view) {
        Map<String, Object> user = new HashMap<>();
        String identification = etIdentification.getText().toString();
        String name = etName.getText().toString();
        String lastname = etLastName.getText().toString();
        String email1 = etEmail1.getText().toString();
        String country = etCountry.getText().toString();
        String city = etCity.getText().toString();
        String password1 = etPassword1.getText().toString();
        String password2 = etPassword2.getText().toString();
        user.put("identification", identification);
        user.put("name", name);
        user.put("lastname", lastname);
        user.put("email", email1);
        user.put("country", country);
        user.put("city", city);
        user.put("password1", password1);
        user.put("password2", password2);
        db.collection("users").document(identification)
                .set(user)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        if (etName.getText().toString().isEmpty()) {
                            Toast.makeText(RegisterActivity.this, "Campo Nombre vacio", Toast.LENGTH_SHORT).show();
                        } else {
                            if (etLastName.getText().toString().isEmpty()) {
                                Toast.makeText(RegisterActivity.this, "Campo Apellido vacio", Toast.LENGTH_SHORT).show();
                            } else {
                                if (etEmail1.getText().toString().isEmpty()) {
                                    Toast.makeText(RegisterActivity.this, "Campo Email vacio", Toast.LENGTH_SHORT).show();
                                } else {
                                    if (etCountry.getText().toString().isEmpty()) {
                                        Toast.makeText(RegisterActivity.this, "Campo pais vacio", Toast.LENGTH_SHORT).show();
                                    } else {
                                        if (etCity.getText().toString().isEmpty()) {
                                            Toast.makeText(RegisterActivity.this, "Campo ciudad vacio", Toast.LENGTH_SHORT).show();
                                        } else {
                                            if (etPassword1.getText().toString().isEmpty()) {
                                                Toast.makeText(RegisterActivity.this, "Campo contraseña vacio", Toast.LENGTH_SHORT).show();
                                            } else {
                                                if (etPassword2.getText().toString().isEmpty()) {
                                                    Toast.makeText(RegisterActivity.this, "Campo confirmar contraseña vacio", Toast.LENGTH_SHORT).show();
                                                } else {
                                                    Toast.makeText(RegisterActivity.this, "Usuario registrado con exito", Toast.LENGTH_SHORT).show();
                                                    Intent intent = new Intent(RegisterActivity.this, MainActivity.class);
                                                    startActivity(intent);
                                                    registerUser(email1, password1);
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }

                })

                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(RegisterActivity.this, "Hubo un error, no se pudo registrar", Toast.LENGTH_SHORT).show();
                    }
                });

    }

    public  void registerUser(String email1, String password1  ) {
        mAuth.createUserWithEmailAndPassword(email1, password1)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(RegisterActivity.this, "Usuario registrado con exito", Toast.LENGTH_SHORT).show();

                        } else {
                            Toast.makeText(RegisterActivity.this, "No se pudo registrar, el correo ya existe", Toast.LENGTH_SHORT).show();
                        }
                    }

                });
    }


}

