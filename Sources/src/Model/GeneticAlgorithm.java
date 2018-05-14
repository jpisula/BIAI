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
    private int populationSize = 1000;
    private Crossover cross;
    private int crossOverNumber = 1;

    /**
     * Class constructor
     */
    public GeneticAlgorithm(String board) {
        gameFields = 9;
        rawFields = 3;
        RADIX = 10;
        actualBoard = board;
        all = new AllBoards(gameFields, RADIX);
        chrom = new Chromosome(gameFields, RADIX, all);
        fitness = new Fitness(gameFields, rawFields, all);
    }

    public String start() {
        all.getAllBoards(); // tworzenie tablicy zawierajacej niecale 9000 kombinacji
        ArrayList<String> population = new ArrayList<>();
        //pobranie stringu, w ktorym jest aktualny stan planszy
        //pierwsze 9 cyfr to stan planszy, ostatnia ilosc wypelnionych pol
        int stateNumber = all.getStrategy(actualBoard); //aktualny stan planszy wyrazony poprzez numer strategii

        //petla tworzaca populacje chromosomow
        for (int i = 0; i < populationSize; i++) {
            String chromosome = chrom.createChromosome(stateNumber); //metoda generujaca chromosom
            population.add(chromosome);//vector chromosomow
        }
        //zmniejszenie populacji o te najgorsze chromosomy
        population = fitness.modifyPopulation(population);

        //*********************************************************************************
        //zainicjowanie i rozpoczecie krzyzowania
        //TODO - dorobic warunek sprawdzajacy kiedy nalezy przestac robic krzyzowanie, czyli znaleziono juz idealny chromosom
        //TODO - np przeniesc to do getBestBoard i tam to robic az funkcja fitnessowa nie zwroci jakiego fajnego wyniku
        for (int i=0; i<crossOverNumber;i++) {
            cross = new Crossover(population);
            cross.startCrossover();
            population = new ArrayList<String>(cross.getPopulation());
        }

        //*********************************************************************************

        //ostateczny wynik - pierwsze 9 cyfr to stan planszy, ostatnia ilosc juz wypelnionych pol. Potrzebne do algorytmu.
        String resultBoard = all.getBestBoard(population, fitness);
        return resultBoard;
    }
}
