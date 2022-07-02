// This guy handles driving the simulation cycle
// If I wanted to run multiple stores, I could probably make the change here (mostly)
// All it really knows is what day it is and the store instance

/* NOTE BELOW
/* original code from Professor's Project 2 Source Code
/* modifications were made to add functionality overall for Project 3 requirements
/* and supplies were implemented
 */

public class Simulation implements Log_output {
    Store store;
    int dayCounter;

    Simulation() {
        dayCounter = 0;
        store = new Store();
    }

    void startSim(int days) {
        int day;
        for (day = 1; day <= days; day++) {
            out(" ");
            out("*** Simulation day "+day+" ***");
            store.openToday(day);
        }
    }

    // Summary is left as an exercise to the reader...
    void summary() {
        out("");
        out("***Summary of Simulation***");

        // print out items left in inventory + total value (purchase price)
        double remainingValue = 0.0;
        out("Items left in inventory: ");
        for (Supplies suppliesItem: store.inventory.supplies){
            remainingValue+= suppliesItem.purchasePrice;
            out(suppliesItem.name);
        }
        for(Pet pet: store.inventory.pets){
            remainingValue+= pet.purchasePrice;
            out(pet.name);
        }
        out("Value of items left in inventory, worth " + Format.asDollar(remainingValue));
        out("");

        // print out the items sold, including the daySold and the salePrice, with a total of the salePrice
        double totalSalePrice = 0.0;
        out("Items sold: ");
        for (Supplies suppliesItem: store.inventory.soldSupplies){
            totalSalePrice+= suppliesItem.salePrice;
            out(suppliesItem.name + " was sold on day " + suppliesItem.daySold + " for " + Format.asDollar(suppliesItem.salePrice));
        }
        for(Pet pet: store.inventory.soldPets){
            totalSalePrice+= pet.salePrice;
            out(pet.name + " was sold on day " + pet.daySold + " for " + Format.asDollar(pet.salePrice));
        }
        out("Value of all items sold, worth " + Format.asDollar(totalSalePrice));
        out("");

        // print out the pets remaining in the sick pets collection
        out("Pets left in sick inventory: ");
        for(Pet pet: store.inventory.sickPets){
            out(pet.name);
        }
        out("");

        // print out the final count of money in the Cash Register
        out("Amount of money left in the Cash Register: " + Format.asDollar(store.cashInRegister));
        out("");

        // print out how much money was added to the register from the GoToBank Action
        out("Amount of money withdrawn from the Bank: " + Format.asDollar(store.cashFromBank));
        out("");

    }
}
