package co.ajeg.reto_2;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.firebase.firestore.FirebaseFirestore;

import co.ajeg.reto_2.model.Pokemon;

public class PokemonActivity extends AppCompatActivity implements View.OnClickListener {

    private TextView nameET;
    private TextView typeET;
    private TextView hpET;
    private TextView attackET;
    private TextView defenseET;
    private TextView speedET;
    private ImageView sprite;
    private Button leaveBtn;

    private Pokemon actualPokemon;

    private FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pokemon);

        nameET = findViewById(R.id.nameET);
        typeET = findViewById(R.id.typeET);
        hpET = findViewById(R.id.hpET);
        attackET = findViewById(R.id.attackET);
        defenseET = findViewById(R.id.defenseET);
        speedET = findViewById(R.id.speedET);
        sprite = findViewById(R.id.sprite);
        leaveBtn = findViewById(R.id.leaveBtn);

        leaveBtn.setOnClickListener(this);

        actualPokemon = (Pokemon) getIntent().getExtras().getSerializable("pokemon");

        db = FirebaseFirestore.getInstance();

        runOnUiThread(()->{
            nameET.setText(""+actualPokemon.getName());

            if(actualPokemon.getTypes().size()==1){
                typeET.setText("("+actualPokemon.getTypes().get(0)+")");
            }if(actualPokemon.getTypes().size() > 1){
                typeET.setText("("+actualPokemon.getTypes().get(0)+","+actualPokemon.getTypes().get(1)+")");
            }

            hpET.setText(""+actualPokemon.getHealth());
            attackET.setText(""+actualPokemon.getAttack());
            defenseET.setText(""+actualPokemon.getDefense());
            speedET.setText(""+actualPokemon.getSpeed());

            String url = actualPokemon.getFront_sprite();
            Glide.with(this).load(url).fitCenter().into(sprite);
        });

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.leaveBtn:
                String uid = getIntent().getExtras().getString("currentPokemons");
                db.collection("pokemons").document(uid).collection("myPokemons").document(actualPokemon.getUid()).delete();
                finish();
                break;
        }
    }
}