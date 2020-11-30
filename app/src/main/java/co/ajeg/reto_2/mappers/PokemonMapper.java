package co.ajeg.reto_2.mappers;

import java.util.ArrayList;
import java.util.Date;
import java.util.UUID;

import co.ajeg.reto_2.model.Pokemon;
import co.ajeg.reto_2.model.PokemonDTO;

public class PokemonMapper {

    public PokemonMapper() {
    }

    public Pokemon asPokemon(PokemonDTO pokemonDTO){

        if(pokemonDTO==null) {
            return null;
        }

        Pokemon pokemon = new Pokemon();
        pokemon.setUid(UUID.randomUUID().toString());
        pokemon.setPokeId(pokemonDTO.getId());
        pokemon.setName(pokemonDTO.getName());
        pokemon.setFront_sprite(pokemonDTO.getSprites().getFront_default());

        for (int i=0;i<pokemonDTO.getStats().length;i++){
            if(pokemonDTO.getStats()[i].getStat().getName().equals("hp")){
                pokemon.setHealth(pokemonDTO.getStats()[i].getBase_stat());
            }if(pokemonDTO.getStats()[i].getStat().getName().equals("attack")){
                pokemon.setAttack(pokemonDTO.getStats()[i].getBase_stat());
            }if(pokemonDTO.getStats()[i].getStat().getName().equals("defense")){
                pokemon.setDefense(pokemonDTO.getStats()[i].getBase_stat());
            }if(pokemonDTO.getStats()[i].getStat().getName().equals("speed")){
                pokemon.setSpeed(pokemonDTO.getStats()[i].getBase_stat());
            }
        }

        ArrayList<String> types = new ArrayList<>();

        for (int j=0;j<pokemonDTO.getTypes().length;j++){
            types.add(pokemonDTO.getTypes()[j].getType().getName());
        }

        pokemon.setTypes(types);
        pokemon.setTimestamp(new Date().getTime());

        return pokemon;
    }

}
