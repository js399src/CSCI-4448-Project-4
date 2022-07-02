// Pretty much mirrored the project requirements in this class hierarchy

/* NOTE BELOW
/* original code from Professor's Project 2 Source Code
/* modifications were made to add functionality overall for Project 3 requirements
/* and supplies were implemented
 */

import Decorator.AddOn;

public class Pet extends Item implements AddOn {
    String breed;
    int age;
    boolean healthy;

    AnimalType animalType;

    @Override
    public void getAddOnPrice() {
        System.out.println("Customer purchased the Add-On listed below for the pet:");
    }

    Pet() {
        super();
        breed = "Unknown";
        age = Random.rndFromRange(1,10);
        healthy = true;
    }
}

class Dog extends Pet {
    Size size;
    Color color;
    boolean housebroken;
    boolean purebred;
    // I did do a little naming in each subclass for breeds
    String[] breeds = new String[] {"Mutt","Labrador","Shepard","Great Dane","Chihuahua","Poodle"};

    Dog() {
        super();
        breed = breeds[Random.rndFromRange(0,breeds.length - 1)];
        size = Random.randomEnum(Size.class);
        color = Random.randomEnum(Color.class);
        housebroken = false;
        purebred = Random.rnd() > .5;
        animalType = AnimalType.DOG;
    }
}

class Cat extends Pet {
    Size size;
    Color color;
    boolean housebroken;
    boolean purebred;
    String[] breeds = new String[] {"Moggie","Maine Coon","Persian","Siamese","Shorthair","Abyssinian"};

    Cat() {
        super();
        breed = breeds[Random.rndFromRange(0,breeds.length - 1)];
        color = Random.randomEnum(Color.class);
        housebroken = false;
        purebred = Random.rnd() > .5;
        animalType = AnimalType.CAT;
    }
}

class Bird extends Pet {
    Size size;
    boolean mimicry;
    boolean exotic;
    boolean papers;
    String[] breeds = new String[] {"Norwegian Blue","LBJ","King Parrot","Budgie","Cockatiel"};

    Bird() {
        super();
        breed = breeds[Random.rndFromRange(0,breeds.length - 1)];
        size = Random.randomEnum(Size.class);
        mimicry = Random.rnd() > .8;
        exotic = Random.rnd() > .8;
        if (exotic) {
            papers = Random.rnd() > .2;
        }
        else {
            papers = false;
        }
        animalType = AnimalType.BIRD;
    }
}

class Ferret extends Pet{
    Color color;
    boolean housebroken;

    Ferret() {
        super();
        color = Random.randomEnum(Color.class);
        housebroken = false;
        animalType = AnimalType.FERRET;
    }

}

class Snake extends Pet{
    Size size;

    Snake() {
        super();
        size = Random.randomEnum(Size.class);
        animalType = AnimalType.SNAKE;
    }

}
