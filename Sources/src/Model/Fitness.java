package Model;
import java.util.ArrayList;

public class Fitness {

    private int gameFields;
    private int rawFields;
    private int computerWin;
    private int playerWin;
    private int win;
    private int draw;
    private int lose;
    private AllBoards all;

    public Fitness(int game, int raw, AllBoards boards) {
        gameFields = game;
        rawFields = raw;
        computerWin = 3;
        playerWin = 6;
        win = 10;
        draw = 5;
        lose = 0;
        all = boards;
    }

    /**
     * Sprawdzenie czy dany stan gry nie jest juz koncowym. Komputer to '1', gracz to '2'. Komputer wygrywa dostaje 10 punktow
     * remisuje 5 punktow, przegrywa 0 punktow. Zwraca wynik punktowy.
     * @param str
     * @return
     */
    public int checkGameState(String str) {
        int suma = 0;
        int koniec;
        //sprawdzenie rzedow
        for (int j=0;j<gameFields;j+=rawFields) {
            for (int i = 0; i < rawFields; i++) {
                suma += Integer.parseInt(String.valueOf(str.charAt(i + j)));
            }
            if (suma == computerWin) {
                koniec = win;
                return koniec;
            }
            if (suma == playerWin) {
                koniec = lose;
                return koniec;
            }
            suma = 0;
        }
        //sprawdzenie kolumn
        for (int j=0;j<rawFields;j++) {
            for (int i = 0; i < rawFields; i+=rawFields) {
                suma += Integer.parseInt(String.valueOf(str.charAt(i + j)));
            }
            if (suma == computerWin) {
                koniec = win;
                return koniec;
            }
            if (suma == playerWin) {
                koniec = lose;
                return koniec;
            }
            suma = 0;
        }
        //sprawdzenie na ukos
        for (int i=0;i<gameFields;i+=(rawFields+1)) {
            suma += Integer.parseInt(String.valueOf(str.charAt(i)));
            if (suma == computerWin) {
                koniec = win;
                return koniec;
            }
            if (suma == playerWin) {
                koniec = lose;
                return koniec;
            }
        }
        suma = 0;
        //sparwdzenie drugiego ukosu
        for (int i=rawFields-1;i<=(gameFields - rawFields);i+=(rawFields-1)) {
            suma += Integer.parseInt(String.valueOf(str.charAt(i)));
            if (suma == computerWin) {
                koniec = win;
                return koniec;
            }
            if (suma == playerWin) {
                koniec = lose;
                return koniec;
            }
        }
        koniec = draw;
        return koniec;
    }



    /**
     * Metoda sprawdza dany stan planszy pobrany z chromosomu. Zmniejsza populacje usuwajac najgorsze chromosomy.
     * @param population - vector zawierajacy chromosomy - kolejne mozliwe posuniecia
     * @return
     */
    public ArrayList<String> modifyPopulation(ArrayList<String>population) {

        ArrayList<String>list = new ArrayList<>(); //lista zawierajaca kolejne strategie wraz z przyznana iloscia punktow
        int countLoses = 0, countDraws = 0, countWins=0;

        for (int i=0;i<population.size();i++) {
            String chromosome = population.get(i); //pobranie chromosomu
            int result = checkGameState(all.getLastBoard(chromosome)); //pobranie ostatniej strategii z chromosomu i wywolanie funkcji zwracajacej wynik
            list.add(Integer.toString(result));
        }
        //zliczenie ile jest '0', ile '5', ile '10'
        for (int i=0;i<list.size();i++) {
            String result = list.get(i);
            if (result.equals("0"))
                countLoses++;
            if (result.equals("5"))
                countDraws++;
            if (result.equals("10"))
                countWins++;
        }

        int number = list.size()/2;
        int count = 0;

        for (int i=0;i<list.size();i++) {
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
            if (countDraws > 0 ) {
                for (int i=0;i<list.size();i++) {
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
