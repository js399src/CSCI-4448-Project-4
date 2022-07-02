// I keep the Item-y kind of information in this abstract class.
// I'm not instantiating Items directly

/* NOTE BELOW
/* original code from Professor's Project 2 Source Code
/* modifications were made to add functionality overall for Project 3 requirements
/* and supplies were implemented
 */

public abstract class Item {
    String name;
    double purchasePrice;
    double listPrice;
    double salePrice;
    int dayArrived;
    int daySold;

    Item() {
        name = "";
        purchasePrice = Random.rndFromRange(1,100);
        listPrice = 2*purchasePrice;
        salePrice = 0;
        dayArrived = 0;
        daySold = 0;
    }
}
