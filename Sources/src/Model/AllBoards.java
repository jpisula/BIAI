package Model;

import java.util.ArrayList;

import static java.lang.StrictMath.abs;
import static java.lang.StrictMath.pow;

public class AllBoards {
    private int gameFields;
    private int RADIX;
    private ArrayList<String> allBoards;
    private int combinations;

    /**
     * Class constructor
     *
     * @param game
     * @param rad
     */
    public AllBoards(int game, int rad) {
        gameFields = game;
        RADIX = rad;
        allBoards = new ArrayList<>();
        combinations = (int) pow(3, gameFields);
    }

    /**
     * Tworzy tablice strategii.
     *
     * @return
     */
    public void getAllBoards() { //metoda tworzaca vector stanow gry - wszystkie mozliwe kombinacje
        String board;

        for (int i = 0; i < combinations; ++i) {
            board = ConvertPermutationToString(i);
            if (!board.isEmpty())
                allBoards.add(ConvertPermutationToString(i));
        }
    }

    /**
     * Metoda zwracajaca liste mozliwych kombinacji
     *
     * @return
     */
    public ArrayList<String> getList() {
        return allBoards;
    }

    /**
     * Generuje kolejna kombinacje mozliwych ruchow.
     *
     * @param permutation
     * @return
     */
    private String ConvertPermutationToString(int permutation) //metoda zwracajaca kolejna kombinacje - kolejny mozliwy stan planszy
    {
        String board = "0000000000"; //pierwsze 9 cyfr to stan planszy, 10 to ilosc wypelnionych pol
        char[] boardC = board.toCharArray();
        int counter = 0;
        int xCount = 0;
        int oCount = 0;
        int filledFields;

        while (permutation > 0) {
            switch (permutation % 3) {
                case 0:
                    break;
                case 1:
                    boardC[counter] = '1';
                    oCount++;
                    break;
                case 2:
                    boardC[counter] = '2';
                    xCount++;
                    break;
            }
            counter++;
            permutation = permutation / 3;
        }
        filledFields = oCount + xCount; //dodanie 10 cyfry oznaczajcej liczbe wypelnionych pol
        boardC[gameFields] = Character.forDigit(filledFields, RADIX);
        board = String.valueOf(boardC);

        if (abs(xCount - oCount) > 1)
            board = "";

        return board;
    }

    /**
     * Zwraca numer strategii.
     *
     * @param board
     * @return
     */
    public int getStrategy(String board) {
        int index = allBoards.indexOf(board);
        return index;
    }

    /**
     * Metoda zwracajaca stan gry, ktory nalezy wprowadzic na plansze.
     *
     * @param strategy
     * @return
     */
    public String getFirstBoard(String strategy) {

        int index = strategy.indexOf(' ');
        String temp = "";
        for (int i = 0; i < index; i++) {
            temp += strategy.charAt(i);
        }
        return allBoards.get(Integer.parseInt(temp));
    }

    /**
     * Metoda zwracajaca stan gry, ktory nalezy przeanalizowac pod katem wygranej.
     *
     * @param strategy
     * @return
     */
    public String getLastBoard(String strategy) {

        int index = strategy.lastIndexOf(' ');
        String temp = "";
        for (int i = index + 1; i < strategy.length(); i++) {
            temp += strategy.charAt(i);
        }
        return allBoards.get(Integer.parseInt(temp));
    }

    /**
     * Metoda wybierajaca strategie, ktora uzyskala najwieksza ilosc punktow. Metoda wywoluje takze kolejna funkcje
     * fitnessowa sparwdzajaca czy nie nastapila wygrana lub blokada. Zwraca numer strategii.
     *
     * @param list
     * @return
     */
    public String getBestResult(ArrayList<String> list, Fitness fit) {

        String nameS, numberS, nameMax = "", nameWinner = "", nameBlockade = "", filled;
        Integer spaceIndex, numberMax = 0, fields;
        boolean winner = false, blockade = false;

        for (int i = 0; i < list.size(); i++) {

            nameS = "";
            numberS = "";
            String data = list.get(i);
            spaceIndex = data.indexOf(" ");

            for (int j = 0; j < spaceIndex; j++) //wyodrebnienie "kolumn" stringa pobranego z listy
                nameS += data.charAt(j); //strategia
            for (int j = spaceIndex + 1; j < data.length(); j++)
                numberS += data.charAt(j); //liczba punktow

            //pobranie liczby zajetych pol
            String temp = String.valueOf(getFirstBoard(nameS += ' '));
            filled = String.valueOf(temp.charAt(9));
            fields = Integer.parseInt(filled);

            if (!winner) {
                if ((Integer.parseInt(numberS) > numberMax)) {
                    numberMax = Integer.parseInt(numberS);
                    nameMax = nameS;
                    nameMax += " ";
                }
            }
            //sprawdzenie czy nie ma sytuacji wygranej dla komputera
            if (list.size() > 3 && !winner && fields > 2 && fields < 7) {
                boolean test = fit.checkWinner(String.valueOf(getFirstBoard(nameS += ' ')));
                if (test) {
                    nameWinner = nameS;
                    nameMax = nameS;
                    winner = true;
                    //System.out.println("Wybranie wygranej: " + nameS + " - " + String.valueOf(getFirstBoard(nameS += ' ')) + ": " + numberS);
                }
            }
            if (list.size() > 3 && !winner && !blockade && fields > 2 && fields < 7) {
                boolean test = fit.checkBlockade(String.valueOf(getFirstBoard(nameS += ' ')));
                if (test) {
                    nameBlockade = nameS;
                    nameMax = nameS;
                    blockade = true;
                    //System.out.println("Wybranie blokady: " + nameS + " - " + String.valueOf(getFirstBoard(nameS += ' ')) + ": " + numberS);
                }
            }
            //System.out.println(nameS + " - " + String.valueOf(getFirstBoard(nameS+=' ')) + ": " + numberS);
        }

        if (winner)
            nameMax = nameWinner;
        else if (blockade)
            nameMax = nameBlockade;

        return String.valueOf(getFirstBoard(nameMax));
    }

    /**
     * Metoda zlicza punkty za plansze dla kazdego ruchu (pierwszy gen w chromosomie) i zwraca ten z najwieksza iloscia punktow.
     * Zwraca plansze, ktora nalezy przekazac do gry.
     *
     * @param population
     * @return
     */
    public String getBestBoard(ArrayList<String> population, Fitness fit) {
        ArrayList<String> list = new ArrayList<>(); //lista zawierajaca kolejne strategie wraz z przyznana iloscia punktow
        ArrayList<String> endingList = new ArrayList<>(); //skrocona wersja wczesniejszej listy. Zawiera strategie z juz zsumowanymi punktami
        int spaceIndex, index = -1;

        for (int i = 0; i < population.size(); i++) {
            String chromosome = population.get(i); //pobranie chromosomu
            int result = fit.checkGameState(getLastBoard(chromosome)); //pobranie ostatniej strategii z chromosomu i wywolanie funkcji zwracajacej wynik
            list.add(getStrategy(getFirstBoard(chromosome)) + " " + Integer.toString(result)); //lista zawirajaca strategie i ocene ostatecznego wyniku
        }

        for (int i = 0; i < list.size(); i++) {
            String data = list.get(i);
            spaceIndex = data.indexOf(" ");
            String name = "", numberNew = "", numberOld = "", nameTest = "";
            for (int j = 0; j < spaceIndex; j++) //wyodrebnienie "kolumn" stringa pobranego z listy
                name += data.charAt(j);
            for (int j = spaceIndex + 1; j < data.length(); j++)
                numberNew += data.charAt(j);

            //pobranie indeksu elementu, jesli juz jest w liscie
            if (endingList.isEmpty())
                index = -1;
            else {
                for (int l = 0; l < endingList.size(); l++) {
                    nameTest = "";
                    String dataTest = endingList.get(l);
                    spaceIndex = dataTest.indexOf(" ");
                    for (int k = 0; k < spaceIndex; k++) //wyodrebnienie "kolumn" stringa pobranego z listy
                        nameTest += dataTest.charAt(k);
                    if (nameTest.equals(name)) {
                        index = l;
                        break;
                    } else
                        index = -1;
                }
            }
            //odpowiednie dodanie nowych danych do uproszczonej listy lub "zmodyfikowanie" elementów
            if (index >= 0) {
                data = endingList.get(index);
                spaceIndex = data.indexOf(" ");
                for (int j = spaceIndex + 1; j < data.length(); j++) //pobranie starej liczby
                    numberOld += data.charAt(j);

                Integer number = Integer.parseInt(numberOld);
                number += Integer.parseInt(numberNew);
                numberNew = number.toString();
                data = name + " " + numberNew;
                endingList.remove(index);
                endingList.add(data);
            } else
                endingList.add(data);
        }
        //pobranie z listy endingList numeru strategi, ktora uzyskala najwiecej punktow
        return getBestResult(endingList, fit);
    }
}
