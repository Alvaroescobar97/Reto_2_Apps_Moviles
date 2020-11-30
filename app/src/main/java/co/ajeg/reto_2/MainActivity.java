package co.ajeg.reto_2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.Collection;
import java.util.UUID;

import co.ajeg.reto_2.model.Trainer;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText usernameET;
    private Button logBtn;

    private FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        usernameET = findViewById(R.id.usernameET);
        logBtn = findViewById(R.id.logBtn);

        logBtn.setOnClickListener(this);

        db = FirebaseFirestore.getInstance();

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.logBtn:

                String username = usernameET.getText().toString();
                Trainer trainer = new Trainer(UUID.randomUUID().toString(),username);

                CollectionReference trainerRef = db.collection("trainers");
                Query query = trainerRef.whereEqualTo("userName",username);
                query.get().addOnCompleteListener(task -> {
                    if(task.isSuccessful()){

                        if(task.getResult().size() > 0){
                            for(QueryDocumentSnapshot doc : task.getResult()){
                                Trainer dbTrainer = doc.toObject(Trainer.class);
                                launchPokedex(dbTrainer);
                                break;
                            }
                        }else{
                            db.collection("trainers").document(trainer.getId()).set(trainer);
                            launchPokedex(trainer);
                        }

                    }
                });

                break;
        }
    }
    public void launchPokedex(Trainer trainer){
        Intent i = new Intent(this, Pokedex.class);
        i.putExtra("myTrainer",trainer);
        startActivity(i);
    }
}