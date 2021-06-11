package com.sistema.apartamentos;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class MainActivity extends AppCompatActivity {

    EditText etEmail, etPassword;
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    private FirebaseAuth mAuth = FirebaseAuth.getInstance();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        String userState= loadPreferences();
        if (userState.equals("Inicio de sesion guardado")){
            Intent intent = new Intent(MainActivity.this, ListApartmentActivity.class);
            startActivity(intent);
        }
        else{
            setContentView(R.layout.activity_main);
        }
        //setContentView(R.layout.activity_main);
        etEmail = findViewById(R.id.etEmail);
        etPassword = findViewById(R.id.etPassword);

        String email1Retorno=getIntent().getStringExtra("email");
        String passwordRetorno=getIntent().getStringExtra("password");

        if( email1Retorno == null) {

            etEmail.setText("");
            etPassword.setText("");
        }
        else{
            etEmail.setText(email1Retorno);
            etPassword.setText(passwordRetorno);
        }

    }

    public void validarRol (View view){
        final String email, password;
        email = etEmail.getText().toString();
        password = etPassword.getText().toString();
        if (email.isEmpty()) {
            Toast.makeText(getApplicationContext(), "Usuario no ingresado.", Toast.LENGTH_SHORT).show();
            etEmail.requestFocus();
        } else if (password.isEmpty()) {
            Toast.makeText(getApplicationContext(), "Debe ingresar la contraseña.", Toast.LENGTH_SHORT).show();
            etPassword.requestFocus();
        }
        else {
            DocumentReference docRef = db.collection("users").document(email);
            docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                @Override
                public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                    if (task.isSuccessful()) {
                        DocumentSnapshot document = task.getResult();
                        if (document.exists()) {
                            String password = document.getString("password");
                            if (password.equals(password)) {
                                String rol = document.getString("rol");
                                if (rol.equals("Anfitrion")) {
                                    Intent intent = new Intent(getApplicationContext(), ListApartmentActivity.class);
                                    intent.putExtra("email", email);
                                    intent.putExtra("rol", rol);
                                    intent.putExtra("password", password);
                                    singin(email, password);
                                    savePreferences();
                                    startActivity(intent);
                                    onBackPressed();
                                } else if (rol.equals("Invitado")) {
                                    Intent intent = new Intent(getApplicationContext(), InvitedActivity.class);
                                    intent.putExtra("email", email);
                                    intent.putExtra("rol", rol);
                                    intent.putExtra("password", password);
                                    startActivity(intent);
                                    savePreferences();
                                    singin(email,password);
                                    onBackPressed();
                                }
                                etPassword.setText("");
                            } else {
                                Toast.makeText(getApplicationContext(), "Contraseña incorrecta", Toast.LENGTH_SHORT).show();
                                etPassword.setText("");
                                etPassword.requestFocus();
                            }

                        } else {
                            Toast.makeText(getApplicationContext(), "Usuario no extiste, por favor confirmar", Toast.LENGTH_LONG).show();
                            etEmail.requestFocus();
                            etPassword.setText("");
                        }
                    }
                }
            });
        }
    }

    public void singin (String email,  String password){
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(MainActivity.this, "Inisio de sesion correcto", Toast.LENGTH_SHORT).show();
                            onBackPressed();

                        } else {
                            Toast.makeText(MainActivity.this, "No se pudo iniciar sesion", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    public void onBackPressed(){
        super.onBackPressed();
    }

    public void RegistrarActivity(View view) {
        Intent intent = new Intent(this, RegisterActivity.class);
        startActivity(intent);

    }
   public String savePreferences(){
        SharedPreferences preferences = getSharedPreferences("user", Context.MODE_PRIVATE);
        String userState = "Inisio de sesion guardado";
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("state", userState);
        editor.commit();
        return userState;
    }

    public String loadPreferences(){
        SharedPreferences preferences = getSharedPreferences("user", Context.MODE_PRIVATE);
        String userState = preferences.getString("state", "error");
        return userState;
    }
}
