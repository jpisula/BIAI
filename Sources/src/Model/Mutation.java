package Model;

import java.util.Random;

public class Mutation {

    private float mutationParm;
    private Random generator;

    public Mutation() {
        mutationParm = 0.01f;
        generator = new Random();
    }

    /**
     * Metoda sprawdzajaca czy ma nastapic mutacja genu.
     *
     * @return - wartosc bool - czy mutacja ma sie wydarzyc
     */
    public boolean mutation() {
        float random = generator.nextFloat();
        if (random <= mutationParm)
            return true;
        else
            return false;
    }

}
