package Model;

import java.util.ArrayList;
import java.util.Random;

/**
 * Klasa majaca na celu wykonanie krzyzowania.
 */
public class Crossover {

    /**
     * pobrana lista chromosomow
     */
    private ArrayList<String> population;
    /**
     * liste w ktorej sa same nowe dzieci lub rodzic dla ktorego nie znaleziono pasujacego chromosomu
     */
    private ArrayList<String> newPopulation;
    private Random generator;
    private float crossoverParam = 0.8f;
    /**
     * wielkosc listy, ktora nalezy utworzyc
     */
    private int populationCount;

    /**
     * Konstruktor - inicjalizacja zmiennych.
     *
     * @param pop
     */
    public Crossover(ArrayList<String> pop) {
        this.population = pop;
        this.generator = new Random();
        newPopulation = new ArrayList<>();
        populationCount = pop.size();
    }

    /**
     * Metoda odseparowuje jedna wylosowana strategie, przeszukuje liste w poszukiwaniu kolejnego wystapienia. Jesli znajdzie
     * wykona krzyzowanie. Z 2 rodzicow utworza sie 2 dzieci.
     *
     * @param elem
     */
    private void doCrossover(String elem, int tabIndex1) {

        int count = 0;
        int tabIndex2;
        String child1, child2;
        ArrayList<Integer> indexList1 = new ArrayList<>();
        ArrayList<Integer> indexList2 = new ArrayList<>();
        ArrayList<String> list = new ArrayList<>();

        for (Integer i = 0; i < elem.length(); i++) {
            if (elem.charAt(i) == ' ') {
                count++;
                indexList1.add(i);
            }
        }
        int rand = generator.nextInt(count - 2); //-2 aby nie wylosowac ostatniej spacji

        int parent1Start1 = indexList1.get(rand) + 1;
        int parent1Start2 = indexList1.get(rand + 1);

        String parent1 = elem.substring(parent1Start1, parent1Start2); //wyodrebniona strategia
        String parentToSearch = ' ' + parent1 + ' ';
        String strategy2;

        //wyszukanie chromosomu ktory zawiera pobrana strategie
        for (int i = 0; i < population.size(); i++) {
            if (i != tabIndex1) {
                if (population.get(i).contains(parentToSearch)) {
                    list.add(population.get(i)); //lista zawiera kolejnego rodzica
                    indexList2.add(i); //lista zawierajaca indeksy kolejnych rodzicow
                }
            }
        }
        if (!list.isEmpty() && list.size() > 1) {
            if (list.size() > 1)
                rand = generator.nextInt(list.size() - 1);
            else
                rand = 0;
            strategy2 = list.get(rand);
            tabIndex2 = indexList2.get(rand);
            int parent2Start1 = strategy2.indexOf(parentToSearch); //indeks od ktorego zaczyna sie parent1 w 2 rodzicu
            parent2Start1++;
            child1 = elem.substring(0, parent1Start1) + strategy2.substring(parent2Start1, strategy2.length());
            child2 = strategy2.substring(0, parent2Start1 - 1) + elem.substring(parent1Start2 - parent1.length() - 1, elem.length());

            //usuniecie z populacji rodzicow i zastapienie nimi dziecmi
            population.remove(tabIndex1);
            population.remove(tabIndex2);
            newPopulation.add(child1);
            newPopulation.add(child2);
            populationCount -= 2;
        } else {
            newPopulation.add(elem);
            population.remove(tabIndex1);
            populationCount--;
        }
    }

    /**
     * Rozpoczecie krzyzowania
     */
    public void startCrossover() {
        float random = generator.nextFloat();
        if (random <= crossoverParam) {
            String elem = population.get(0);
            int count = 0;
            for (int i = 0; i < elem.length(); i++) {
                if (elem.charAt(i) == ' ')
                    count++;
            }
            int rand;
            if (count > 1) {
                while (populationCount > 0) {
                    if (populationCount > 1)
                        rand = generator.nextInt(populationCount - 1);
                    else
                        rand = 0;
                    doCrossover(population.get(rand), rand);
                }
            } else
                newPopulation = new ArrayList<String>(population);
        } else
            newPopulation = new ArrayList<String>(population);
    }

    /**
     * Metoda zwracajaca zmieniona liste populacji
     *
     * @return
     */
    public ArrayList<String> getPopulation() {
        return this.newPopulation;
    }
}
