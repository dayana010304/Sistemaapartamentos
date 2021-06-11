package com.sistema.apartamentos;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
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
    TextView tvNombre, tvName, tvApellido, tvLastName, tvCorreo, tvEmail, tvPais, tvCountry, tvCiudad, tvCity, tvRole, tvRol;
    String email, rol, password;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_perfiluser);
        tvNombre = findViewById(R.id.tvNombre);
        tvName = findViewById(R.id.tvName);
        tvApellido = findViewById(R.id.tvApellido);
        tvLastName = findViewById(R.id.tvLastName);
        tvCorreo = findViewById(R.id.tvCorreo);
        tvEmail = findViewById(R.id.tvEmail);
        tvPais = findViewById(R.id.tvPais);
        tvCountry = findViewById(R.id.tvCountry);
        tvCiudad = findViewById(R.id.tvCiudad);
        tvCity = findViewById(R.id.tvCity);
        tvRole = findViewById(R.id.tvRole);
        tvRol = findViewById(R.id.tvRol);


        email=getIntent().getStringExtra("email");
        rol=getIntent().getStringExtra("rol");
        password=getIntent().getStringExtra("password");




        db.collection("users").document(email)
                .get()
                .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                        if (documentSnapshot.exists()) {
                            String name = documentSnapshot.getString("name");
                            String lastname = documentSnapshot.getString("lastname");
                            String email = documentSnapshot.getString("email");
                            String country = documentSnapshot.getString("country");
                            String  city = documentSnapshot.getString("city");
                            String rol = documentSnapshot.getString("rol");

                            tvName.setText(name);
                            tvLastName.setText(lastname);
                            tvEmail.setText(email);
                            tvCountry.setText(country);
                            tvCity.setText(city);
                            tvRol.setText(rol);

                        } else {
                            Toast.makeText(perfiluserActivity.this, "No tienes conexion a internet", Toast.LENGTH_SHORT).show();
                            //Log.d(TAG, "get failed with ", task.getException());
                        }
                    }

                });
    }

    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.overflow2, menu);
        return true;
    }
    public boolean onOptionsItemSelected(MenuItem item){
        int id = item.getItemId();
        if ( id == R.id.item1){
            Intent intent1 = new Intent(this,MainActivity.class);
            this.startActivity(intent1);
            return true;
        }

        else if (id == R.id.item2){
            Intent intent2 = new Intent(this, perfiluserActivity.class);
            intent2.putExtra("email", email);
            this.startActivity(intent2);
            return true;
        }

        else if (id == R.id.item3){
            Intent intent3 = new Intent(this, editUserActivity.class);
            intent3.putExtra("email",email);
            intent3.putExtra("rol",rol);
            intent3.putExtra("password",password);
            this.startActivity(intent3);
            return true;
        }
        else if (id == R.id.item4) {
            Intent intent4 = new Intent(this, MainActivity.class);
            intent4.putExtra("email", email);
            intent4.putExtra("rol", rol);
            intent4.putExtra("password", password);
            this.startActivity(intent4);
            return true;
        }


        return super.onOptionsItemSelected(item);
    }

}