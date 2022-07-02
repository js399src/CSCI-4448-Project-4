/* NOTE BELOW
/* original code from Professor's Project 2 Source Code
/* modifications were made to add functionality overall for Project 3 requirements
/* and supplies were implemented
 */

public class Supplies extends Item{

    SuppliesType suppliesType;
    Supplies(){
        super();
    }
}
class Food extends Supplies {

    Size size;

    AnimalType animal;

    String type;

    Food(){
        super();
        suppliesType = SuppliesType.FOOD;
        size = Random.randomEnum(Size.class);
        animal = Random.randomEnum(AnimalType.class);
        type = "Dry";
    }

}
class Leash extends Supplies{

    AnimalType animal;

    Leash(){
        super();
        suppliesType = SuppliesType.LEASH;
        animal = Random.randomEnum(AnimalType.class);
    }

}
class CatLitter extends Supplies{

    Size size;

    CatLitter(){
        super();
        suppliesType = SuppliesType.CATLITTER;
        size = Random.randomEnum(Size.class);
    }

}
class Toys extends Supplies{

    AnimalType animal;

    Toys(){
        super();
        suppliesType = SuppliesType.TOYS;
        animal = Random.randomEnum(AnimalType.class);
    }

}
class Treats extends Supplies{

    AnimalType animal;

    Treats(){
        super();
        suppliesType = SuppliesType.TREATS;
        animal = Random.randomEnum(AnimalType.class);
    }

}
