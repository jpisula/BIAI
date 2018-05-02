package Model;

import java.util.ArrayList;

public class Fitness {

    private int gameFields;
    private int rawFields;
    private int computerWin;
    private int playerWin;
    private int blockmove;
    public final int win;
    public final int draw;
    public final int lose;
    private AllBoards all;
    private boolean winner;
    private boolean looser;
    private boolean block;

    //zmienne dla sprawdzenie pierwszej planszy
    private int win3 = 2;
    private int win5 = 4;

    public Fitness(int game, int raw, AllBoards boards) {
        gameFields = game;
        rawFields = raw;
        computerWin = 3;
        playerWin = 6;
        blockmove = 7;
        win = 10;
        draw = 5;
        lose = 0;
        all = boards;
    }

    /**
     *
     * @param str
     * @return
     */
    public boolean checkFirstBoard(String str) {

        int suma = 0;
        int count = 0;
        //sparwdzenie rzedow
        for (int j = 0; j < gameFields; j += rawFields) {
            for (int i = 0; i < rawFields; i++) {
                suma += Integer.parseInt(String.valueOf(str.charAt(i + j)));
                if (Integer.parseInt(String.valueOf(str.charAt(i+j))) == 0)
                    count++;
            }
            if (count == 1 && (suma == win3 || suma == win5)) {
                //System.out.println("If przy rzedzie "+ j);
                return false;
            }
            count = 0;
            suma = 0;
        }

        //sprawdzenie kolumn
        for (int j = 0; j < rawFields; j++) {
            for (int i = 0; i < gameFields; i += rawFields) {
                suma += Integer.parseInt(String.valueOf(str.charAt(i + j)));
                if (Integer.parseInt(String.valueOf(str.charAt(i+j))) == 0)
                    count++;
            }

            if (count == 1 && (suma == win3 || suma == win5)) {
                //System.out.println("If przy kolumnie "+ j);
                return false;
            }
            count = 0;
            suma = 0;
        }

        //sprawdzenie pierwszego ukosu
        for (int i = 0; i < gameFields; i += (rawFields + 1)) {
            suma += Integer.parseInt(String.valueOf(str.charAt(i)));
            if (Integer.parseInt(String.valueOf(str.charAt(i))) == 0)
                count++;
        }

        if (count == 1 && (suma == win3 || suma == win5)) {
           // System.out.println("If przy ukosie w prawo");
            return false;
        }
        count = 0;
        suma = 0;
        //sprawdzenie drugiego ukosu
        for (int i = rawFields - 1; i <= (gameFields - rawFields); i += (rawFields - 1)) {
            suma += Integer.parseInt(String.valueOf(str.charAt(i)));
            if (Integer.parseInt(String.valueOf(str.charAt(i))) == 0)
                count++;
        }
        if (count == 1 && (suma == win3 || suma == win5)) {
            //System.out.println("If przy ukosie w lewo");
            return false;
        }
        System.out.println("Przeszlo wszystko");
        return true;
    }

    /**
     * Sprawdzenie czy dany stan gry nie jest juz koncowym. Komputer to '1', gracz to '2'. Komputer wygrywa dostaje 10 punktow
     * remisuje 5 punktow, przegrywa 0 punktow. Zwraca wynik punktowy.
     *
     * @param str
     * @return
     */
    public int checkGameState(String str) {
        winner = false;
        looser = false;
        block = false;
        int suma = 0;
        //sprawdzenie rzedow
        for (int j = 0; j < gameFields; j += rawFields) {
            for (int i = 0; i < rawFields; i++)
                suma += Integer.parseInt(String.valueOf(str.charAt(i + j)));
            if (suma == computerWin)
                winner = true;
            if (suma == playerWin)
                looser = true;
            if (suma == blockmove)
                block = true;
            suma = 0;
        }


        if (block && winner)
            return win;
        if (block && !winner)
            return draw;
        if (block && !looser)
            return draw;
        if (winner)
            return win;
        if (winner && !looser)
            return win;
        if (looser || (winner && looser))
            return lose;

/*
        if (block && winner)
            return win;
        if (block && !winner)
            return 5*win;
        if (winner && !looser)
            return win;
        if (looser || (winner && looser))
            return lose;
*/

        winner = false;
        looser = false;
        block = false;
        //sprawdzenie kolumn
        for (int j = 0; j < rawFields; j++) {
            for (int i = 0; i < gameFields; i += rawFields)
                suma += Integer.parseInt(String.valueOf(str.charAt(i + j)));

            if (suma == computerWin)
                winner = true;
            if (suma == playerWin)
                looser = true;
            if (suma == blockmove)
                block = true;
            suma = 0;
        }
        if (block && winner)
            return win;
        if (block && !winner)
            return draw;
        if (block && !looser)
            return draw;
        if (winner)
            return win;
        if (winner && !looser)
            return win;
        if (looser || (winner && looser))
            return lose;

        winner = false;
        looser = false;
        block = false;
        //sprawdzenie pierwszego ukosu
        for (int i = 0; i < gameFields; i += (rawFields + 1))
            suma += Integer.parseInt(String.valueOf(str.charAt(i)));

        if (suma == computerWin)
            winner = true;
        if (suma == playerWin)
            looser = true;
        if (suma == blockmove)
            block = true;

        if (block)
            return draw;
        if (winner)
            return win;
        if (looser)
            return lose;


        winner = false;
        looser = false;
        block = false;
        suma = 0;
        //sprawdzenie drugiego ukosu
        for (int i = rawFields - 1; i <= (gameFields - rawFields); i += (rawFields - 1))
            suma += Integer.parseInt(String.valueOf(str.charAt(i)));

        if (suma == computerWin)
            winner = true;
        if (suma == playerWin)
            looser = true;
        if (suma == blockmove)
            block = true;

        if (block)
            return draw;
        if (winner)
            return win;
        if (looser)
            return lose;

        return draw;
    }


    /**
     * Metoda sprawdza dany stan planszy pobrany z chromosomu. Zmniejsza populacje usuwajac najgorsze chromosomy.
     *
     * @param population - vector zawierajacy chromosomy - kolejne mozliwe posuniecia
     * @return
     */
    public ArrayList<String> modifyPopulation(ArrayList<String> population) {

        ArrayList<String> list = new ArrayList<>(); //lista zawierajaca kolejne strategie wraz z przyznana iloscia punktow
        int countLoses = 0, countDraws = 0, countWins = 0;

        for (int i = 0; i < population.size(); i++) {
            String chromosome = population.get(i); //pobranie chromosomu
            int result = checkGameState(all.getLastBoard(chromosome)); //pobranie ostatniej strategii z chromosomu i wywolanie funkcji zwracajacej wynik
            list.add(Integer.toString(result));
        }
        //zliczenie ile jest '0', ile '5', ile '10'
        for (int i = 0; i < list.size(); i++) {
            String result = list.get(i);
            if (result.equals("0"))
                countLoses++;
            if (result.equals("5"))
                countDraws++;
            if (result.equals("10"))
                countWins++;
        }

        int number = list.size() / 2;
        int count = 0;

        for (int i = 0; i < list.size(); i++) {
            if (count >= number)
                break;
            if (list.get(i).equals("0")) {
                list.remove(i);
                population.remove(i);
                count++;
                i--;
            }
        }

        if (count < number) {
            if (countDraws > 0) {
                for (int i = 0; i < list.size(); i++) {
                    if (count >= number)
                        break;
                    if (list.get(i).equals("5")) {
                        list.remove(i);
                        population.remove(i);
                        count++;
                        i--;
                    }
                }
            }
        }
        return population;
    }
}