package Model;
import java.util.ArrayList;

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
        for (int i = 0; i< populationSize; i++) {
            String chromosome = chrom.createChromosome(stateNumber); //metoda generujaca chromosom
            population.add(chromosome);//vector chromosomow
        }
        //zmniejszenie populacji o te najgorsze chromosomy
        population = fitness.modifyPopulation(population);
        //wywolanie krzyzowania

        //kolejne wywolanie funkcji fitness i tak iles tam razy powtorzenie krzyzowania (albo moze jeden?)

        //zwrocenie pierwszego elementu vectora populacji - przekazanie pierwszego stanu planszy z chromosomu
        //wywolac na samym koncu

        //ostateczny wynik - pierwsze 9 cyfr to stan planszy, ostatnia ilosc juz wypelnionych pol. Potrzebne do algorytmu.
        String resultBoard = all.getBestBoard(population, fitness);
        return resultBoard;
    }
}
