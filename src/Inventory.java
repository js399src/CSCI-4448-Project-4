import java.util.ArrayList;

/* NOTE BELOW
/* original code from Professor's Project 2 Source Code
/* modifications were made to add functionality overall for Project 3 requirements
/* and supplies were implemented
 */
public class Inventory implements Log_output {

    // I specifically wanted to try to do this for now with single level ArrayLists
    // there may be better collections or nested lists that might make some
    // operations more efficient...

    // I'm also just dealing with Pets in inventory, keeping a bunch of Supplies and deciding whether to use
    // Items or Pets/Supplies in your ArrayLists is for you to do...

    public ArrayList<Pet> pets;
    public ArrayList<Pet> sickPets;
    public ArrayList<Pet> arrivingPets;
    public ArrayList<Pet> soldPets;

    public ArrayList<Supplies> supplies;

    public ArrayList<Supplies> arrivingSupplies;
    public ArrayList<Supplies> soldSupplies;

    // I was too lazy to do a nice naming scheme for pets, so I just give them numbers.  Sad...
    static int petNumber;

    static int suppliesNumber;

    Inventory() {
        pets = new ArrayList<>();
        arrivingPets = new ArrayList<>();
        soldPets = new ArrayList<>();
        sickPets = new ArrayList<>();

        supplies = new ArrayList<>();
        arrivingSupplies = new ArrayList<>();
        soldSupplies = new ArrayList<>();

        petNumber = 1;
        suppliesNumber = 1;
        initializeInventory(pets, supplies);
    }

    void initializeInventory(ArrayList<Pet> pet_list, ArrayList<Supplies> supplies_list) { // sets the count of every item in the store to 3
        for (int i = 0; i < 3; i++) {
            for (AnimalType type: AnimalType.values()) {
                Pet pet = makeNewPetByType(type);
                pet_list.add(pet);
            }
            for (SuppliesType type: SuppliesType.values()) {
                Supplies supplyItem = makeNewSuppliesByType(type);
                supplies_list.add(supplyItem);
            }
        }
    }

    // There may be fancier ways to do this with things like Java newInstance
    // and the reflection framework, this has the advantage of being pretty clean and readable
    // we're not applying patterns here yet, otherwise this really wants to use a factory
    Pet makeNewPetByType(AnimalType type) {
        Pet pet;
        switch (type) {
            case CAT -> {pet = new Cat(); pet.name = "Cat "+petNumber;}
            case DOG -> {pet = new Dog();pet.name = "Dog "+petNumber;}
            case BIRD -> {pet = new Bird();pet.name = "Bird "+petNumber;}
            case FERRET -> {pet = new Ferret();pet.name = "Ferret "+petNumber;}
            case SNAKE -> {pet = new Snake();pet.name = "Snake "+petNumber;}
            default -> {
                out("Error in makeNewItemByType - unexpected type enum");
                pet = null;
            }
        }
        petNumber += 1;
        return pet;
    }

    Supplies makeNewSuppliesByType(SuppliesType type){
        Supplies supplyItem;
        switch (type) {
            case FOOD -> supplyItem = new Food();
            case LEASH -> supplyItem = new Leash();
            case TOYS -> supplyItem = new Toys();
            case CATLITTER -> supplyItem = new CatLitter();
            case TREATS -> supplyItem = new Treats();
            default -> {
                out("Error in makeNewSuppliesByType - unexpected type enum");
                supplyItem = null;
            }
        }
        supplyItem.name = "SuppliesItem: "+ suppliesNumber;
        suppliesNumber+=1;
        return supplyItem;
    }

    // add(), remove() can be done directly to the list
    // overall count can come from size()
    // count of specific item types
    int countPetsByType(ArrayList<Pet> list, AnimalType type) {
        int count = 0;
        for (Pet pet:list) if (pet.animalType == type) count += 1;
        return count;
    }
    int countSuppliesByType(ArrayList<Supplies> list, SuppliesType type) {
        int count = 0;
        for (Supplies suppliesItem:list) if (suppliesItem.suppliesType == type) count += 1;
        return count;
    }

    // helper to get value of items in a list
    double getValueOfPets(ArrayList<Pet> list) {
        double value = 0;
        for (Pet pet:list) value = value + pet.purchasePrice;
        return value;
    }

    double getValueOfSupplies(ArrayList<Supplies> list ){
        double value = 0;
        for (Supplies suppliesItem:list) value = value + suppliesItem.purchasePrice;
        return value;
    }
}