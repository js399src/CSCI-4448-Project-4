/* NOTE BELOW
/* original code from Professor's Project 2 Source Code
/* modifications were made to add functionality overall for Project 3 requirements
/* and supplies were implemented
 */

public interface Random {
    // making this utility function that can be used by saying Random.rndFromRange(min,max)
    // https://www.baeldung.com/java-generating-random-numbers-in-range
    static int rndFromRange(int min, int max) {
        //returns a uniform inclusive random number from a given min and max range
        return (int) ((Math.random() * ((max+1) - min)) + min);
    }

    // just a quick random call for a random number between 0 and 1
    static double rnd() {
        return Math.random();
    }

    // a utility for getting a random enum value from any enum
    // https://stackoverflow.com/questions/1972392/pick-a-random-value-from-an-enum
    // call like randomEnum(MyEnum.class)
    static <T extends Enum<?>> T randomEnum(Class<T> clazz){
        int x = new java.util.Random().nextInt(clazz.getEnumConstants().length);
        return clazz.getEnumConstants()[x];
    }
}
