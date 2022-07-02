// We've got two subclasses of employees, and they do the actual work during simulation runs
// The top level Employee class isn't abstract because I have one routine that is usable by any employee
// Might want to consider how to code that so Employee could be abstract again...
// I need to know who employees are and what store they work at

/* NOTE BELOW
/* original code from Professor's Project 2 Source Code
/* modifications were made to add functionality overall for Project 3 requirements
/* and supplies were implemented
 */
import Decorator.AddOn;
import Decorator.InsuranceAddOnDecorator;
import Decorator.MicrochipAddOnDecorator;
import Decorator.VetCheckupAddOnDecorator;
import Observer.*;
import Strategy.Haphazard;
import Strategy.NegativeReinforcement;
import Strategy.PositiveReinforcement;
import Strategy.Training_Strategy;

// this is the employee class it handles the operations of the clerk and trainer
// it also pushes some signals to the observers
public class Employee implements Log_output, Poisson{
    String name;
    Store store;
    int daysWorked;

    // initial inventory of toys is ordered in inventory class
    static boolean toysOrdered = true;

    Employee(String name, Store store) {
        this.name = name;
        this.store = store;
        daysWorked = 0;
    }

    // both types of Employees do this
    void arriveAtStore() {
        out(this.name + " arrives at store.");
        new ArriveAtStoreObserver(store.logger, this.name + " arrives at store.");
    }

}

// Clerks doing their things...
class Clerk extends Employee {
    Clerk(String name, Store store) {
        super(name, store);
    }

    void processDeliveries() {
        int total_deliveries = 0;
        out(this.name + " is checking for deliveries.");
        boolean petArrivals = false;
        for (Pet pet:store.inventory.arrivingPets) {
            if (pet.dayArrived == store.today) {
                store.inventory.pets.add(pet);
                out(pet.name + " added to inventory.");
                total_deliveries++;
            }
        }
        store.inventory.arrivingPets.removeIf(pet -> pet.dayArrived == store.today);
        boolean supplyArrivals = false;
        for (Supplies suppliesItem:store.inventory.arrivingSupplies) {
            if (suppliesItem.dayArrived == store.today) {
                store.inventory.supplies.add(suppliesItem);
                out(suppliesItem.name + " added to inventory.");
                total_deliveries++;
            }
        }
        store.inventory.arrivingSupplies.removeIf(supplies -> supplies.dayArrived == store.today);
        new ProcessDeliveriesObserver(store.logger, total_deliveries+ " items were processed");
    }

    void checkRegister() {
        out(this.name + " checks: "+Format.asDollar(store.cashInRegister)+" in register.");
        if (store.cashInRegister<200) {
            out("Cash register is low on funds.");
            this.goToBank();
        }
        new CheckRegisterObserver(store.logger, "$" + store.cashInRegister + " is in the cash register");
    }

    void goToBank() {
        out(this.name + " gets money from the bank.");
        store.cashInRegister += 1000;
        store.cashFromBank += 1000;
        this.checkRegister();
        new GoToBankObserver(store.logger, "$" + store.cashInRegister + " is in the cash register after going to the bank");
    }

    void doInventory() {
        out(this.name + " is doing inventory.");
        for (AnimalType type: AnimalType.values()) {
            int numItems = store.inventory.countPetsByType(store.inventory.pets,type);
            out(this.name + " counts "+numItems+" "+type.toString().toLowerCase()+"s");
            if (numItems == 0) {
                this.placeAPetOrder(type);
            }
        }
        for (SuppliesType type: SuppliesType.values()) {
            int numItems = store.inventory.countSuppliesByType(store.inventory.supplies,type);
            out(this.name + " counts "+numItems+" "+type.toString().toLowerCase()+"s");
            if (numItems == 0) {
                this.placeASuppliesOrder(type);
            }
        }


        int petCount = store.inventory.pets.size();
        double petWorth = store.inventory.getValueOfPets(store.inventory.pets);
        int suppliesCount = store.inventory.supplies.size();
        double suppliesWorth = store.inventory.getValueOfSupplies(store.inventory.supplies);
        out(this.name + " finds " + petCount + " pets in store, worth "+Format.asDollar(petWorth));
        out(this.name + " finds " + suppliesCount + " supplies in store, worth "+Format.asDollar(suppliesWorth));
        out("The total worth of all items is: " + Format.asDollar(petWorth + suppliesWorth));
        new DoInventoryObserver(store.logger, "There are " + (petCount + suppliesCount) + " items in the inventory.");
        new DoInventoryObserver(store.logger, "There is $" + (petWorth + suppliesWorth) + " worth of value in the inventory.");
    }

    void placeAPetOrder(AnimalType type) {
        out(this.name + " needs to order "+type.toString().toLowerCase()+"s");
        int total_pets_ordered = 0;
        // order 3 more of this item type
        // they arrive in 1 to 3 days
        int arrivalDay = store.today + Random.rndFromRange(1,3);
        for (int i = 0; i < 3; i++) {
            Pet pet = store.inventory.makeNewPetByType(type);
            String price = Format.asDollar(pet.purchasePrice);
            if (store.cashInRegister >= pet.purchasePrice) {
                store.cashInRegister -= pet.purchasePrice;
                out(this.name + " ordered a pet for " + price);
                pet.dayArrived = arrivalDay;
                store.inventory.arrivingPets.add(pet);
                total_pets_ordered++;
            }
            else {
                out(this.name + "can't pay for the pet they want to order!  Pet price is "+price);
            }
        }
        new PlaceOrderObserver(store.logger, "There were " + total_pets_ordered + " total pets ordered");
    }


    void placeASuppliesOrder(SuppliesType type) {
        int total_supplies_ordered = 0;
        if (toysOrdered && type.equals(SuppliesType.TOYS)){
            out("Stock of TOYS is out, not ordering more.");
            return;
        }
        out(this.name + " needs to order "+type.toString().toLowerCase()+"s");
        // order 3 more of this item type
        // they arrive in 1 to 3 days
        int arrivalDay = store.today + Random.rndFromRange(1,3);
        for (int i = 0; i < 3; i++) {
            Supplies suppliesItem = store.inventory.makeNewSuppliesByType(type);
            String price = Format.asDollar(suppliesItem.purchasePrice);
            if (store.cashInRegister >= suppliesItem.purchasePrice) {
                store.cashInRegister -= suppliesItem.purchasePrice;
                out(this.name + " ordered a pet for " + price);
                suppliesItem.dayArrived = arrivalDay;
                store.inventory.arrivingSupplies.add(suppliesItem);
                total_supplies_ordered++;
            }
            else {
                out(this.name + "can't pay for the pet they want to order!  Pet price is "+price);
            }
        }
        // this pushes an update to the observer
        new PlaceOrderObserver(store.logger, "There were " + total_supplies_ordered + " total supplies ordered");

    }



    // Little bit of a code smell here - some duplicate code in the two stages of selling a thing.
    // It's pretty simple and it's right here, so I'll leave it as is for now...
    void openTheStore() {
        // 3 - 10 customers
        int customer = 0;
        int numberOfCustomers = 2 + getPoissonRandom(3);

        int items_sold_by_trainer = 0;
        int items_sold_by_clerk = 0;
        double value_sold_by_trainer = 0.0;
        double value_sold_by_clerk = 0.0;

        for (int i = 0; i < numberOfCustomers; i++) {
            customer += 1;
            Pet soldPet = null;
            for (Pet pet:store.inventory.pets) {
                // 10% chance of buying attempt
                if (Random.rnd() < .1) {
                    out("Customer " + customer + " is looking at " + pet.name);
                    // 50% will buy from clerk
                    if (Random.rnd() > .5) {
                        String price = Format.asDollar(pet.listPrice);
                        out("Customer is buying " + pet.name + " for " + price + " from clerk " + this.name);
                        soldPet = pet;
                        pet.salePrice = pet.listPrice;
                        addOnToPet(pet);
                        pet.daySold = store.today;
                        items_sold_by_clerk++;
                        value_sold_by_clerk += pet.salePrice;
                        break;
                    } else {
                        // trainer offers 10% discount if not
                        // 75% chance to buy from trainer
                        if (Random.rnd() > .25) {
                            double salePrice = pet.listPrice * .9;
                            String price = Format.asDollar(salePrice);
                            String name = store.activeTrainer.name;
                            out("Customer is buying " + pet.name + " for " + price + " from trainer " + name);
                            soldPet = pet;
                            pet.salePrice = salePrice;
                            addOnToPet(pet);
                            pet.daySold = store.today;
                            items_sold_by_trainer++;
                            value_sold_by_trainer += pet.salePrice;
                            break;
                        } else {
                            // otherwise keep looking
                            out("Customer isn't buying " + pet.name);
                        }
                    }
                }
            }
            if (soldPet != null) {
                store.cashInRegister += soldPet.salePrice;
                // add sold goods to soldPets
                store.inventory.soldPets.add(soldPet);
                store.inventory.pets.remove(soldPet);
            }
            else {
                out("Customer "+customer+" didn't buy a pet!");
            }

            // Looking through supplies inventory now
            Supplies soldSuppliesItem = null;
            for (Supplies suppliesItem :store.inventory.supplies) {
                // 10% chance of buying attempt
                if (Random.rnd() < .1) {
                    out("Customer " + customer + " is looking at " + suppliesItem.name);
                    // 50% will buy from clerk
                    if (Random.rnd() > .5) {
                        String price = Format.asDollar(suppliesItem.listPrice);
                        out("Customer is buying " + suppliesItem.name + " for " + price + " from clerk " + this.name);
                        soldSuppliesItem = suppliesItem;
                        suppliesItem.salePrice = suppliesItem.listPrice;
                        suppliesItem.daySold = store.today;
                        items_sold_by_clerk++;
                        value_sold_by_clerk += suppliesItem.salePrice;
                        break;
                    } else {
                        // trainer offers 10% discount if not
                        // 75% chance to buy from trainer
                        if (Random.rnd() > .25) {
                            double salePrice = suppliesItem.listPrice * .9;
                            String price = Format.asDollar(salePrice);
                            String name = store.activeTrainer.name;
                            out("Customer is buying " + suppliesItem.name + " for " + price + " from trainer " + name);
                            soldSuppliesItem = suppliesItem;
                            suppliesItem.salePrice = salePrice;
                            suppliesItem.daySold = store.today;
                            items_sold_by_trainer++;
                            value_sold_by_trainer += suppliesItem.salePrice;
                            break;
                        } else {
                            // otherwise keep looking
                            out("Customer isn't buying " + suppliesItem.name);
                        }
                    }
                }
            }
            if (soldSuppliesItem != null) {
                store.cashInRegister += soldSuppliesItem.salePrice;
                // add sold goods to soldPets
                store.inventory.soldSupplies.add(soldSuppliesItem);
                store.inventory.supplies.remove(soldSuppliesItem);
            }
            else {
                out("Customer "+customer+" didn't buy a item from supplies!");
            }
        }
        // pushes name and items to the loger through the observer
        new OpenStoreObserver(store.logger, "Clerk " + store.activeClerk.name + " sold " + items_sold_by_clerk + " items today");
        new OpenStoreObserver(store.logger, "Trainer " + store.activeTrainer.name + " sold " + items_sold_by_trainer + " items today");
        new OpenStoreObserver(store.logger, "Clerk " + store.activeClerk.name + " sold " + value_sold_by_clerk + "$ worth of items today");
        new OpenStoreObserver(store.logger, "Trainer " + store.activeTrainer.name + " sold " + value_sold_by_trainer + "$ worth of items today");
        new OpenStoreObserver(store.logger, "There were " + numberOfCustomers +" customers that visited the store today");

        if (store.activeClerk.name.equals("Dante")){
            new ItemsSoldObserver(store.tracker, 0, items_sold_by_clerk);
            new PurchasePriceTotalObserver(store.tracker, 0, value_sold_by_clerk);
        }
        if (store.activeClerk.name.equals("Randal")){
            new ItemsSoldObserver(store.tracker, 1, items_sold_by_clerk);
            new PurchasePriceTotalObserver(store.tracker, 1, value_sold_by_clerk);
        }
        if (store.activeClerk.name.equals("Jason")){
            new ItemsSoldObserver(store.tracker, 2, items_sold_by_clerk);
            new PurchasePriceTotalObserver(store.tracker, 2, value_sold_by_clerk);
        }
        if (store.activeTrainer.name.equals("Alpa")){
            new ItemsSoldObserver(store.tracker, 3, items_sold_by_trainer);
            new PurchasePriceTotalObserver(store.tracker, 3, value_sold_by_trainer);
        }
        if (store.activeTrainer.name.equals("Kirk")){
            new ItemsSoldObserver(store.tracker, 4, items_sold_by_trainer);
            new PurchasePriceTotalObserver(store.tracker, 4, value_sold_by_trainer);
        }
        if (store.activeTrainer.name.equals("Ricky")){
            new ItemsSoldObserver(store.tracker, 5, items_sold_by_trainer);
            new PurchasePriceTotalObserver(store.tracker, 5, value_sold_by_trainer);
        }

    }

    // add ons to pets
    // this is called above in the openTheStore function when a pet is bought
    void addOnToPet(Pet pet){
        //microchip
        if (Random.rnd() >= .5){
            AddOn microchip = new MicrochipAddOnDecorator(pet);
            microchip.getAddOnPrice();
            pet.salePrice+=50;
        }
        //pet insurance
        if (Random.rnd() <= .25){
            AddOn pet_insurance = new InsuranceAddOnDecorator(pet);
            pet_insurance.getAddOnPrice();
            pet.salePrice+=50;
        }
        // vet visits
        if (Random.rnd() >= .5){
            AddOn vet_visit = new VetCheckupAddOnDecorator(pet);
            // random number between 1 and 4 bought
            int num_visits = Random.rndFromRange(1, 4);
            for (int i = 0; i < num_visits; i++){
                vet_visit.getAddOnPrice();
                pet.salePrice += 25;
            }
        }
        out("Total price for " + pet.name + " is now " + Format.asDollar(pet.salePrice));
    }

    void cleanTheStore(){
        out(this.name + " vacuums the store.");
    }

    void leaveTheStore(){
        out(this.name + " locks up the store and leaves.");
        new LeaveStoreObserver(store.logger, this.name + " has left the store");
    }
}

class Trainer extends Employee implements Random{

    //Trainer strategy
    Training_Strategy strategy;
    Trainer(String name, Store store, int training_strategy) {
        super(name, store);
        // assign haphazard strategy
        if (training_strategy == 1){
            strategy = new Training_Strategy(new Haphazard());
        }
        // assign neg reinforcement strategy
        else if (training_strategy == 2) {
            strategy = new Training_Strategy(new NegativeReinforcement());
        }
        // assign pos reinforcement strategy
        else if (training_strategy == 3) {
            strategy = new Training_Strategy(new PositiveReinforcement());
        } else {
            out("No training strategy was selected for Trainer " + this.name);
        }
    }

    // There's some Java pickiness around moving things off a collection while you're looping through it
    // I also used the newer removeIf method that handles a bulk removal

    void feedAnimals() {
        out(this.name + " is feeding the animals...");
        // Check to see if any animals get sick during feeding (5% chance)
        for (Pet pet: store.inventory.pets) {
            if (pet.healthy) {
                pet.healthy = Random.rnd() > .05;
                if (!pet.healthy) out(pet.name + " just got sick!");
            }
        }
        // Check the sick pets to see if they get better (25% chance)
        for (Pet pet: store.inventory.sickPets) {
            if (!pet.healthy) {
                pet.healthy = Random.rnd() > .75;
                if (pet.healthy) out(pet.name + " just got healthy!");
            }
        }
        // Move sick pets from pets to sickPets
        // Java is a little picky about how things are moved between lists in for loops
        for (Pet pet: store.inventory.pets) {
            if (!pet.healthy) store.inventory.sickPets.add(pet);
        }
        store.inventory.pets.removeIf(pet -> !pet.healthy);
        // Move healthy pets from sickPets to pets
        for (Pet pet: store.inventory.sickPets) {
            if (pet.healthy) store.inventory.pets.add(pet);
        }
        store.inventory.sickPets.removeIf(pet -> pet.healthy);
    }

    void trainAnimals(){
        out(this.name + " is training the animals...");
        // trainer trys to change housebroken attribute
        for (Pet pet: store.inventory.pets) {
            if (pet.animalType.equals(AnimalType.DOG)){
                out(this.name + " is trying to train Dog " + pet.name);
                // execute whatever strategy is assigned to the trainer
                ((Dog) pet).housebroken = this.strategy.executeStrategy(((Dog) pet).housebroken);

            }
            if (pet.animalType.equals(AnimalType.CAT)){
                out(this.name + " is trying to train Cat " + pet.name);
                // execute whatever strategy is assigned to the trainer
                ((Cat) pet).housebroken = this.strategy.executeStrategy(((Cat) pet).housebroken);
            }
            if (pet.animalType.equals(AnimalType.FERRET)) {
                out(this.name + " is trying to train Ferret " + pet.name);
                // execute whatever strategy is assigned to the trainer
                ((Ferret) pet).housebroken = this.strategy.executeStrategy(((Ferret) pet).housebroken);
            }
        }
    }

    void cleanTheStore() {

        out(this.name + " cleans the cages.");
        // I'm assuming here that only the healthy animals want to escape...
        if (store.inventory.pets.size()>0)
            if (Random.rnd()<.05) {
                int escapee = Random.rndFromRange(0,store.inventory.pets.size()-1);
                Pet escapedPet = store.inventory.pets.get(escapee);
                out(escapedPet.name + " has escaped during cage cleaning!");
                String catcher;
                if (Random.rnd()<.5) catcher = this.name;
                else catcher = store.activeClerk.name;
                out(catcher + " caught the escaped pet!");
                new CleanStoreObserver(store.logger, escapedPet.name + " has escaped it's cage.");
            }
    }

    void leaveTheStore() {
        out(this.name + " locks up the store and leaves.");
        new LeaveStoreObserver(store.logger, this.name + " has left the store");
    }
}
