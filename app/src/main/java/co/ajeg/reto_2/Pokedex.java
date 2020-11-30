package co.ajeg.reto_2;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.ListenerRegistration;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.UUID;

import co.ajeg.reto_2.comm.HTTPSWebUtilDomi;
import co.ajeg.reto_2.mappers.PokemonMapper;
import co.ajeg.reto_2.model.CatchPokemons;
import co.ajeg.reto_2.model.Pokemon;
import co.ajeg.reto_2.model.PokemonDTO;
import co.ajeg.reto_2.model.Trainer;

public class Pokedex extends AppCompatActivity implements View.OnClickListener, PokemonView.OnPokemonListener {

    private EditText catchET;
    private EditText searchET;
    private TextView badTV;
    private Button catchBtn;
    private Button searchBtn;
    private Button reload;

    private RecyclerView myPokemons;
    private LinearLayoutManager layoutManager;
    private PokemonAdapter adapter;

    private ArrayList<Pokemon> catchPokemons;
    private Trainer myTrainer;

    private CatchPokemons currentPokemons;

    private ListenerRegistration listener;

    private FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pokedex);

        catchET = findViewById(R.id.catchET);
        searchET = findViewById(R.id.searchET);
        catchBtn = findViewById(R.id.catchBtn);
        searchBtn = findViewById(R.id.searchBtn);
        reload = findViewById(R.id.reload);
        badTV = findViewById(R.id.badTV);
        myPokemons= findViewById(R.id.myPokemons);

        catchPokemons = new ArrayList<>();

        //La lista siempre va a tener el mismo tamaño
        myPokemons.setHasFixedSize(true);


        layoutManager = new LinearLayoutManager(this);
        myPokemons.setLayoutManager(layoutManager);

        adapter = new PokemonAdapter(this,this);
        myPokemons.setAdapter(adapter);

        catchBtn.setOnClickListener(this);
        reload.setOnClickListener(this);
        searchBtn.setOnClickListener(this);

        myTrainer = (Trainer) getIntent().getExtras().getSerializable("myTrainer");
        Log.e(">>",myTrainer.getId());

        db = FirebaseFirestore.getInstance();

        CollectionReference pokemonsRef = db.collection("trainers").document(myTrainer.getId()).collection("myPokemons");
        Query query = pokemonsRef.whereEqualTo("trainerId", myTrainer.getId());

        query.get().addOnCompleteListener(task -> {
            if(task.isSuccessful()){
                if(task.getResult().size() > 0){
                    for(QueryDocumentSnapshot doc : task.getResult()){
                        currentPokemons = doc.toObject(CatchPokemons.class);
                    }
                }else{
                    String pokemonsId =  UUID.randomUUID().toString();
                    CatchPokemons myPoke = new CatchPokemons(myTrainer.getId(),pokemonsId);
                    db.collection("trainers").document(myTrainer.getId()).collection("myPokemons").document(pokemonsId).set(myPoke);
                    currentPokemons = myPoke;

                }
                suscribeToMyPokemons();
            }
        });



    }

    public void suscribeToMyPokemons(){

        CollectionReference myPokemonsRef = db.collection("pokemons").document(currentPokemons.getPokemonsId()).collection("myPokemons");

        Query query = myPokemonsRef.orderBy("timestamp",Query.Direction.DESCENDING);

        listener = query.addSnapshotListener((data,error)->{
            adapter.getPokemons().clear();

            for(DocumentSnapshot doc : data.getDocuments()){
                Pokemon p = doc.toObject(Pokemon.class);
                adapter.addPokemon(p);
                Log.e(">>>>", doc.toString());
            }

        });

    }

    @Override
    protected void onDestroy() {
        listener.remove();
        super.onDestroy();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.catchBtn:

                if(!catchET.getText().toString().equals("")){
                    new Thread(
                            ()->{
                                Gson gson = new Gson();
                                HTTPSWebUtilDomi httpsWebUtilDomi = new HTTPSWebUtilDomi();
                                String json = httpsWebUtilDomi.GETrequest("https://pokeapi.co/api/v2/pokemon/"+catchET.getText().toString().toLowerCase());

                                if (json==null){
                                    runOnUiThread(()->badTV.setVisibility(View.VISIBLE));
                                }else{
                                    PokemonDTO poke = gson.fromJson(json, PokemonDTO.class);

                                    Log.e(">>>",poke.getName());
                                    Log.e(">>>",poke.getStats()[0].getStat().getName());
                                    Log.e(">>>",poke.getTypes()[0].getType().getName());
                                    Log.e(">>>",poke.getSprites().getFront_default());
                                    Log.e(">>>", String.valueOf(poke));

                                    Pokemon lastCatch = new PokemonMapper().asPokemon(poke);

                                    db.collection("pokemons").document(currentPokemons.getPokemonsId()).collection("myPokemons").document(lastCatch.getUid()).set(lastCatch);

                                    //catchPokemons.add(lastCatch);

                                    Log.e(">>>>>>>>",lastCatch.getName());
                                    Log.e(">>>>>>>>",""+lastCatch.getPokeId());
                                    Log.e(">>>>>>>>",""+lastCatch.getFront_sprite());

                                    runOnUiThread(()->badTV.setVisibility(View.INVISIBLE));
                                }


                            }
                    ).start();
                }

                break;
            case R.id.searchBtn:

                String searchName = searchET.getText().toString().toLowerCase();
                if(!searchName.equals("")){
                    CollectionReference myPokemonsRef = db.collection("pokemons").document(currentPokemons.getPokemonsId()).collection("myPokemons");
                    Query query = myPokemonsRef.whereEqualTo("name",searchName);
                    query.get().addOnCompleteListener(task -> {
                        if(task.isSuccessful()){
                            adapter.getPokemons().clear();
                            for(QueryDocumentSnapshot doc : task.getResult()){
                                Pokemon p = doc.toObject(Pokemon.class);
                                adapter.addPokemon(p);
                                Log.e(">>>>", doc.toString());
                            }

                        }
                    });
                }

                break;
            case R.id.reload:
                Log.e(">>>>", "Realoading...");
                suscribeToMyPokemons();
                break;
        }
    }

    @Override
    public void onPokemonClick(int position) {
       Intent intent = new Intent(this,PokemonActivity.class);
       intent.putExtra("currentPokemons",currentPokemons.getPokemonsId());
       intent.putExtra("pokemon",adapter.getPokemons().get(position));
       startActivity(intent);
    }
}