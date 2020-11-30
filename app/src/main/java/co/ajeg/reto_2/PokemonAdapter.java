package co.ajeg.reto_2;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.UUID;

import co.ajeg.reto_2.PokemonView.OnPokemonListener;

import co.ajeg.reto_2.model.Pokemon;


public class PokemonAdapter extends RecyclerView.Adapter<PokemonView> {

    private OnPokemonListener listener;
    private Context mContext;
    private ArrayList<Pokemon> pokemons;

    public ArrayList<Pokemon> getPokemons() {
        return pokemons;
    }

    public PokemonAdapter(Context mContext, OnPokemonListener listener) {
       this.pokemons = new ArrayList<>();
        this.mContext=mContext;
        this.listener=listener;
    }

    public void addPokemon(Pokemon pokemon){
        pokemons.add(pokemon);
        this.notifyDataSetChanged();
    }


    @NonNull
    @Override
    public PokemonView onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //XML -> View
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View row = inflater.inflate(R.layout.pokemonrow, null);
        ConstraintLayout rowroot = (ConstraintLayout) row;
        PokemonView pokemonView = new PokemonView(rowroot,listener);
        return pokemonView;
    }

    @Override
    public void onBindViewHolder(@NonNull PokemonView holder, int position) {
        if(pokemons.get(position)!=null){
            holder.getName().setText(pokemons.get(position).getName());
            String url = pokemons.get(position).getFront_sprite();
            Glide.with(holder.getRoot().getContext()).load(url).fitCenter().into(holder.getPokeImg());
        }

    }

    @Override
    public int getItemCount() {
        return pokemons.size();
    }

}
