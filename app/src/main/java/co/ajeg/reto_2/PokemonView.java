package co.ajeg.reto_2;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

public class PokemonView extends RecyclerView.ViewHolder implements View.OnClickListener{

    private ConstraintLayout root;
    private ImageView pokeImg;
    private TextView name;
    private OnPokemonListener listener;

    public PokemonView(ConstraintLayout root,OnPokemonListener listener) {
        super(root);
        this.root = root;
        name = root.findViewById(R.id.pokeName);
        pokeImg = root.findViewById(R.id.pokeImg);
        this.listener=listener;

        root.setOnClickListener(this);
    }

    public ConstraintLayout getRoot() {
        return root;
    }

    public ImageView getPokeImg() {
        return pokeImg;
    }

    public TextView getName() {
        return name;
    }

    @Override
    public void onClick(View v) {
        listener.onPokemonClick(getAdapterPosition());
    }

    public interface OnPokemonListener{
        public void onPokemonClick(int position);
    }


}
