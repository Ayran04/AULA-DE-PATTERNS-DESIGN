import java.util.ArrayList;
import java.util.List;

public class Character implements CharacterPrototype {

    private String name;
    private String type;
    private int health;
    private int attack;
    private int defense;
    private String weapon;
    private List<Skill> skills;

    public Character(
        String name,
        String type,
        int health,
        int attack,
        int defense,
        String weapon
    ) {
        this.name = name;
        this.type = type;
        this.health = health;
        this.attack = attack;
        this.defense = defense;
        this.weapon = weapon;
        this.skills = new ArrayList<>();
    }

    public void addSkill(Skill skill) {
        skills.add(skill);
    }

    public List<Skill> getSkills() {
        return skills;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setWeapon(String weapon) {
        this.weapon = weapon;
    }

    public void setHealth(int health) {
        this.health = health;
    }

    public void setAttack(int attack) {
        this.attack = attack;
    }

    public void setDefense(int defense) {
        this.defense = defense;
    }

    @Override
    public Character clone() {

        Character clone = new Character(name, type, health, attack, defense, weapon);

        // copia profunda: cada Skill eh copiada individualmente para que o
        // clone tenha sua propria lista e seus proprios objetos, evitando
        // que alteracoes no clone afetem o prototipo original.
        for (Skill skill : skills) {
            clone.addSkill(skill.copy());
        }

        return clone;
    }

    @Override
    public String toString() {
        return """
                Character
                --------------------------
                Nome: %s
                Tipo: %s
                Vida: %d
                Ataque: %d
                Defesa: %d
                Arma: %s
                Habilidades: %s
                """.formatted(
                name,
                type,
                health,
                attack,
                defense,
                weapon,
                skills
        );
    }
}
