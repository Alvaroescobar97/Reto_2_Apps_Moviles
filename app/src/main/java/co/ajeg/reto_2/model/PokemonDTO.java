package co.ajeg.reto_2.model;

public class PokemonDTO {

    private int id;
    private String name;
    private StatWrapper[] stats;
    private Sprite sprites;
    private TypeWrapper[] types;

    public PokemonDTO(int id, String name, StatWrapper[] stats, Sprite sprites, TypeWrapper[] types) {
        this.id = id;
        this.name = name;
        this.stats = stats;
        this.sprites = sprites;
        this.types = types;
    }



    public PokemonDTO() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public StatWrapper[] getStats() {
        return stats;
    }

    public void setStats(StatWrapper[] stats) {
        this.stats = stats;
    }

    public Sprite getSprites() {
        return sprites;
    }

    public void setSprites(Sprite sprites) {
        this.sprites = sprites;
    }

    public TypeWrapper[] getTypes() {
        return types;
    }

    public void setTypes(TypeWrapper[] types) {
        this.types = types;
    }
}
