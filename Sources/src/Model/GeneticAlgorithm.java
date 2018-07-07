package Model;

import java.util.ArrayList;
import java.util.Collections;

public class GeneticAlgorithm {

    private int rawFields;
    private int gameFields;
    private int RADIX;
    private String actualBoard;
    private AllBoards all;
    private Chromosome chrom;
    private Fitness fitness;
    private int populationSize = 500;

    /**
     * Class constructor
     */
    public GeneticAlgorithm(String board) {
        gameFields = 9; //---------------------------------------------------KUBA
        rawFields = 3;
        RADIX = 10;
        actualBoard = board;
        all = new AllBoards(gameFields, RADIX);
        chrom = new Chromosome(gameFields, RADIX, all);
        fitness = new Fitness(gameFields, rawFields, all);
    }

    /**
     * Metoda zarzadzajaca calym algorytmem genetycznym.
     * @return
     */
    public String start(boolean mode, boolean mode2) throws Exception {
        //--------------------------------------------------------------------------------TIME KUBA
        long startTime = System.currentTimeMillis();
        all.getAllBoards(); // tworzenie tablicy zawierajacej niecale 9000 kombinacji
        long estimatedTime = System.currentTimeMillis() - startTime;
        System.out.println("TIME: " + estimatedTime);
        //--------------------------------------------------------------------------------TIME KUBA
        ArrayList<String> population = new ArrayList<>();
        //pobranie stringu, w ktorym jest aktualny stan planszy
        //pierwsze 9 cyfr to stan planszy, ostatnia ilosc wypelnionych pol
        int stateNumber = all.getStrategy(actualBoard); //aktualny stan planszy wyrazony poprzez numer strategii

        //petla tworzaca populacje chromosomow
        for (int i = 0; i < populationSize; i++) {
            String chromosome = chrom.createChromosome(stateNumber, mode); //metoda generujaca chromosom
            population.add(chromosome);//vector chromosomow
        }
        //zmniejszenie populacji o te najgorsze chromosomy
        population = fitness.modifyPopulation(population);

        //ostateczny wynik - pierwsze 9 cyfr to stan planszy, ostatnia ilosc juz wypelnionych pol. Potrzebne do algorytmu.
        //w wywolanej metodzie nastepuje krzyzowanie, ocena chromosomow i wybor najlepszej opcji
        return all.getBestBoard(population, fitness, mode);
    }
}
