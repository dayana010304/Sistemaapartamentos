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

    }


    public void singin (View view){
        String email = etEmail.getText().toString();
        String password = etPassword.getText().toString();
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(MainActivity.this, "Inisio de sesion correcto", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(MainActivity.this, ListApartmentActivity.class);
                            savePreferences();
                            startActivity(intent);
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
        String userState = "login";
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

    public void RegisterAparment (View view){
        Intent intent = new Intent(this, RegisterAparmentActivity.class);
        startActivity(intent);
    }
}
