package com.trex.parkirBDL.view;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;
import com.trex.parkirbandarlampung.R;
import com.trex.parkirBDL.model.FirebaseViewHolder;
import com.trex.parkirBDL.model.datasetfire;

import java.util.ArrayList;

public class kedaton extends AppCompatActivity {


    private RecyclerView recyclerView;

    private ArrayList<datasetfire> arrayList;

    private FirebaseRecyclerOptions<datasetfire> options;

    private FirebaseRecyclerAdapter<datasetfire, FirebaseViewHolder> adapter;

    private DatabaseReference databasereference,daa;





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





    @Override

        protected void onCreate(Bundle savedInstanceState) {

            super.onCreate(savedInstanceState);

            setContentView(R.layout.activity_kedaton);



            recyclerView = findViewById(R.id.recyclerview_id);

            recyclerView.setHasFixedSize(true);

            recyclerView.setLayoutManager(new LinearLayoutManager(this));

            arrayList = new ArrayList<datasetfire>();

            databasereference = FirebaseDatabase.getInstance().getReference("Parkir");

            databasereference.keepSynced(true);

            options = new FirebaseRecyclerOptions.Builder<datasetfire>().setQuery(databasereference, datasetfire.class).build();



            adapter = new FirebaseRecyclerAdapter<datasetfire, FirebaseViewHolder>(options) {

                @Override

                protected void onBindViewHolder(@NonNull FirebaseViewHolder holder, int position, @NonNull final datasetfire model) {



                    holder.namab.setText(model.getNama_area());

                    holder.dateb.setText(model.getDate());

                    holder.catb.setText(model.getProfilename());

                    Picasso.get().load(model.getImage()).into(holder.image);


//
//                    holder.itemView.setOnClickListener(new View.OnClickListener() {
//
//                        @Override
//
//                        public void onClick(View v) {
//
//                            Intent Intent = new Intent(kedaton.this, detailbarang.class);
//
//                            Intent.putExtra("pname",model.getNama());
//
//                            Intent.putExtra("date",model.getDate());
//
//                            Intent.putExtra("category", model.getCategory());
//
//                            Intent.putExtra("image",model.getImage());
//
//                            Intent.putExtra("prace",model.getPrice());
//
//                            Intent.putExtra("time",model.getTime());
//
//                            Intent.putExtra("deskripsi",model.getDescription());
//
//                            Intent.putExtra("profilemail",model.getNama2());
//
//                            Intent.putExtra("pid",model.getPid());
//
//                            startActivity(Intent);
//
//                        }
//
//                    });



                }



                @NonNull

                @Override

                public FirebaseViewHolder onCreateViewHolder(@NonNull ViewGroup ViewGroup, int  i) {

                    return new FirebaseViewHolder(LayoutInflater.from(kedaton.this).inflate(R.layout.row1,ViewGroup,false));

                }

            };





            recyclerView.setAdapter(adapter);






        }
}
