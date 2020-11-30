package co.ajeg.reto_2.model;

import java.io.Serializable;
import java.util.ArrayList;

public class Pokemon implements Serializable {

    private String uid;
    private int pokeId;
    private String name;
    private int health;
    private int defense;
    private int attack;
    private int speed;
    private ArrayList<String> types;
    private String front_sprite;
    private long timestamp;

    public Pokemon() {
    }

    public Pokemon(String uid, int pokeId, String name, int health, int defense, int attack, int speed, ArrayList<String> types, String front_sprite, long timestamp) {
        this.uid = uid;
        this.pokeId = pokeId;
        this.name = name;
        this.health = health;
        this.defense = defense;
        this.attack = attack;
        this.speed = speed;
        this.types = types;
        this.front_sprite = front_sprite;
        this.timestamp = timestamp;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public int getPokeId() {
        return pokeId;
    }

    public void setPokeId(int pokeId) {
        this.pokeId = pokeId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getHealth() {
        return health;
    }

    public void setHealth(int health) {
        this.health = health;
    }

    public int getDefense() {
        return defense;
    }

    public void setDefense(int defense) {
        this.defense = defense;
    }

    public int getAttack() {
        return attack;
    }

    public void setAttack(int attack) {
        this.attack = attack;
    }

    public int getSpeed() {
        return speed;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }

    public ArrayList<String> getTypes() {
        return types;
    }

    public void setTypes(ArrayList<String> types) {
        this.types = types;
    }

    public String getFront_sprite() {
        return front_sprite;
    }

    public void setFront_sprite(String front_sprite) {
        this.front_sprite = front_sprite;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }
}
