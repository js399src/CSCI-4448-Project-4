import java.util.Random;

/* NOTE BELOW
/* original code from Professor's Project 2 Source Code
/* modifications were made to add functionality overall for Project 3 requirements
/* and supplies were implemented
 */

public interface Poisson {

    // code snippet from https://stackoverflow.com/questions/9832919/generate-poisson-arrival-in-java
    default int getPoissonRandom(double mean) {
        Random r = new Random();
        double L = Math.exp(-mean);
        int k = 0;
        double p = 1.0;
        do {
            p = p * r.nextDouble();
            k++;
        } while (p > L);
        return k - 1;
    }
}
