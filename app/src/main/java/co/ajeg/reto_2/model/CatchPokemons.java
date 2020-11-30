package co.ajeg.reto_2.model;

public class CatchPokemons {

    private String trainerId;
    private String pokemonsId;

    public CatchPokemons(String trainerId, String pokemonsId) {
        this.trainerId = trainerId;
        this.pokemonsId = pokemonsId;
    }

    public CatchPokemons() {
    }

    public String getTrainerId() {
        return trainerId;
    }

    public void setTrainerId(String trainerId) {
        this.trainerId = trainerId;
    }

    public String getPokemonsId() {
        return pokemonsId;
    }

    public void setPokemonsId(String pokemonsId) {
        this.pokemonsId = pokemonsId;
    }
}
