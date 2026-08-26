public class Main {

    public static void main(String[] args) {

        // protótipos base de cada tipo de personagem
        Character warriorPrototype = new Character("Warrior Base", "Warrior", 100, 30, 20, "Sword");
        warriorPrototype.addSkill(new Skill("Slash", 15));
        warriorPrototype.addSkill(new Skill("Shield Block", 0));

        Character magePrototype = new Character("Mage Base", "Mage", 70, 40, 10, "Staff");
        magePrototype.addSkill(new Skill("Fireball", 25));
        magePrototype.addSkill(new Skill("Ice Nova", 20));

        Character archerPrototype = new Character("Archer Base", "Archer", 80, 35, 15, "Bow");
        archerPrototype.addSkill(new Skill("Precise Shot", 20));
        archerPrototype.addSkill(new Skill("Multi Arrow", 18));

        // novos personagens criados a partir dos protótipos, alterando
        // apenas o que é necessário para cada instância
        Character arthos = warriorPrototype.clone();
        arthos.setName("Arthos");

        Character brakus = warriorPrototype.clone();
        brakus.setName("Brakus");
        brakus.setWeapon("Axe");

        Character elowen = magePrototype.clone();
        elowen.setName("Elowen");

        Character sylas = archerPrototype.clone();
        sylas.setName("Sylas");
        sylas.getSkills().add(new Skill("Explosive Arrow", 30));

        System.out.println(warriorPrototype);
        System.out.println(arthos);
        System.out.println(brakus);
        System.out.println(magePrototype);
        System.out.println(elowen);
        System.out.println(archerPrototype);
        System.out.println(sylas);

        // prova de que a lista de habilidades do clone é independente
        // da lista do protótipo (cópia profunda)
        System.out.println("Prototipo Archer permaneceu com 2 habilidades: " + (archerPrototype.getSkills().size() == 2));
    }
}
