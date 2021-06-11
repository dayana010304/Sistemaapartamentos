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

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Map;

public class editUserActivity extends AppCompatActivity {

    String rol, password, email;
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    EditText editName, editLastName, editCountry, editCity;
    TextView tvEmail;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_user);

        email = getIntent().getStringExtra("email");
        rol = getIntent().getStringExtra("rol");
        password = getIntent().getStringExtra("password");

        editName = findViewById(R.id.editName);
        editLastName = findViewById(R.id.editLastName);
        tvEmail = findViewById(R.id.tvEmail);
        editCountry = findViewById(R.id.editCountry);
        editCity = findViewById(R.id.editCity);



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
                            String city = documentSnapshot.getString("city");

                            editName.setText(name);
                            editLastName.setText(lastname);
                            tvEmail.setText(email);
                            editCountry.setText(country);
                            editCity.setText(city);

                        }
                    }
                });
    }


    public void updateData (View view) {
        rol=getIntent().getStringExtra("rol");
        password=getIntent().getStringExtra("password");

        String name = editName.getText().toString();
        String lastname = editLastName.getText().toString();
        String country = editCountry.getText().toString();
        String city = editCity.getText().toString();
        Map<String, Object> map = new HashMap<>();
        map.put("name", name);
        map.put("lastname", lastname);
        map.put("country", country);
        map.put("city", city);
        db.collection("users").document(email)
                .update(map)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Intent intent = new Intent(editUserActivity.this, ListApartmentActivity.class);
                        intent.putExtra("email",email);
                        intent.putExtra("rol",rol);
                        intent.putExtra("password",password);
                        startActivity(intent);
                        Toast.makeText(editUserActivity.this, "Los datos se actualizaron correctamente", Toast.LENGTH_SHORT).show();

                        onBackPressed();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(editUserActivity.this, "Error, los datos no se pudieron actualizar", Toast.LENGTH_SHORT).show();
                    }
                });
    }
    public void onBackPressed(){
        super.onBackPressed();
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
        return super.onOptionsItemSelected(item);
    }


}