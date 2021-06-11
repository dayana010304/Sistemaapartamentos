package com.sistema.apartamentos;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

import Models.ApartmentModel;

public class InvitedActivity extends AppCompatActivity {

    FirebaseFirestore db = FirebaseFirestore.getInstance();
    RecyclerView rvFirestoreApartmentList;
    FirestoreRecyclerAdapter adapter;
    private FirebaseAuth mAuth = FirebaseAuth.getInstance();
    TextView tvRol1, tvNombre1;
    String rol, password, email;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_invited);
        rvFirestoreApartmentList = findViewById(R.id.rvFirestoreApartmentList);
        loadPreferences();

        tvNombre1 = findViewById(R.id.tvNombre1);
        tvRol1 = findViewById(R.id.tvRol1);
        email = getIntent().getStringExtra("email");
        rol = getIntent().getStringExtra("rol");
        password = getIntent().getStringExtra("password");

        DocumentReference docRef = db.collection("users").document(getIntent().getStringExtra("email"));
        docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        Log.d("Mensaje1", "DocumentSnapshot data: " + document.getData());
                        String name=document.getString("name");
                        String rol=document.getString("rol");

                        tvNombre1.setText(name);
                        tvRol1.setText(rol);

                    } else {
                        Log.d("no encuentra", "No such document");
                    }
                } else {
                    Log.d("Mensaje3", "get failed with ", task.getException());
                }
            }
        });

        Query query = db.collection("apartments");

        FirestoreRecyclerOptions<ApartmentModel> options = new FirestoreRecyclerOptions.Builder<ApartmentModel>()
                .setQuery(query, ApartmentModel.class)
                .build();
        adapter = new FirestoreRecyclerAdapter<ApartmentModel, ListApartmentActivity.ApartmentsViewHolder>(options) {
            @Override
            protected void onBindViewHolder(@NonNull ListApartmentActivity.ApartmentsViewHolder holder, int position, @NonNull ApartmentModel model) {

                DocumentSnapshot modelDocument = getSnapshots().getSnapshot(holder.getAdapterPosition());
                final String id = getSnapshots().getSnapshot(position).getId();

                holder.tvOwner.setText(model.getOwner());
                holder.tvCountry.setText(model.getCountry());
                holder.tvCity.setText(model.getCity());
                holder.tvAddress.setText(model.getAddress());
                holder.tvNumberHab.setText(model.getNumberhab());
                holder.tvValueNight.setText(model.getValuenight());
                holder.tvReviewApar.setText(model.getReviewapar());

            }

            @NonNull
            @Override
            public ListApartmentActivity.ApartmentsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_sigle_invited, parent, false);
                return new ListApartmentActivity.ApartmentsViewHolder(view);
            }
        };

        rvFirestoreApartmentList.setHasFixedSize(true);
        rvFirestoreApartmentList.setLayoutManager(new LinearLayoutManager(this));
        rvFirestoreApartmentList.setAdapter(adapter);
    }

    private class ApartmentsViewHolder extends RecyclerView.ViewHolder {
        TextView tvOwner, tvCountry, tvCity, tvAddress, tvNumberHab, tvValueNight, tvReviewApar;

        public ApartmentsViewHolder(@NonNull View itemView) {
            super(itemView);

            tvOwner = itemView.findViewById(R.id.tvOwner);
            tvCountry = itemView.findViewById(R.id.tvCountry);
            tvCity = itemView.findViewById(R.id.tvCity);
            tvAddress = itemView.findViewById(R.id.tvAddress);
            tvNumberHab = itemView.findViewById(R.id.tvNumberHab);
            tvValueNight = itemView.findViewById(R.id.tvValueNight);
            tvReviewApar = itemView.findViewById(R.id.tvReviewApar);
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        adapter.startListening();
    }

    @Override
    protected void onStop() {
        super.onStop();
        adapter.stopListening();
    }
    public void loadPreferences() {
        SharedPreferences preferences = getSharedPreferences("user", Context.MODE_PRIVATE);
        String userState = preferences.getString("state", "error");
        Toast.makeText(this, userState, Toast.LENGTH_SHORT).show();
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
            intent2.putExtra("email",email);
            intent2.putExtra("rol",rol);
            intent2.putExtra("password",password);
            this.startActivity(intent2);
            return true;
        }
        else if (id == R.id.item3){
            Intent intent3 = new Intent(this, editUserInvitedActivity.class);
            intent3.putExtra("email",email);
            intent3.putExtra("rol",rol);
            intent3.putExtra("password",password);
            this.startActivity(intent3);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}