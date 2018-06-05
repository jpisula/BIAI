package Model;

import java.util.ArrayList;
import java.util.Random;

public class Chromosome {

    private int gameFields;
    private int RADIX;
    private Mutation mutation;
    private AllBoards all;

    public Chromosome(int game, int rad, AllBoards boards) {
        gameFields = game;
        RADIX = rad;
        mutation = new Mutation();
        all = boards;
    }

    /**
     * Tworzy liste mozliwych ruchow i potem losuje jeden z nich. Zwraca dana strategie ruchu. Dla ruchu komputera wstawia '1'.
     *
     * @param stateNumber
     * @param allBoards
     * @return
     */
    private int getNextComputerMove(int stateNumber, ArrayList<String> allBoards) {
        int gameStrategy = 0;
        String actualStateString = allBoards.get(stateNumber);
        char[] actualState = actualStateString.toCharArray();
        int filledFields = Character.getNumericValue(actualStateString.charAt(gameFields));
        ArrayList<String> list = new ArrayList<>();

        for (int i = 0; i < gameFields; i++) {
            if (actualState[i] == '0') {
                actualState[i] = '1';
                actualState[gameFields] = Character.forDigit(filledFields + 1, RADIX);
                list.add(String.copyValueOf(actualState));
                actualState[i] = '0';
            }
        }
        int listSize = list.size();
        Random generator = new Random();
        int choice = generator.nextInt(listSize);

        if (mutation.mutation())
            choice = generator.nextInt(listSize);

        actualStateString = list.get(choice);
        gameStrategy = all.getStrategy(actualStateString);
        return gameStrategy;
    }

    /**
     * Tworzy liste mozliwych ruchow i potem losuje jeden z nich. Zwraca dana strategie ruchu. Dla ruchu gracza wstawia '2'.
     *
     * @param stateNumber
     * @param allBoards
     * @return
     */
    private int getNextPlayerMove(int stateNumber, ArrayList<String> allBoards) {
        int gameStrategy;

        String actualStateString = allBoards.get(stateNumber);
        char[] actualState = actualStateString.toCharArray();
        int filledFields = Character.getNumericValue(actualStateString.charAt(gameFields));
        ArrayList<String> list = new ArrayList<>();
        for (int i = 0; i < gameFields; i++) {
            if (actualState[i] == '0') {
                actualState[i] = '2';
                actualState[gameFields] = Character.forDigit(filledFields + 1, RADIX);
                list.add(String.copyValueOf(actualState));
                actualState[i] = '0';
            }
        }

        int listSize = list.size();
        Random generator = new Random();
        int choice = generator.nextInt(listSize);

        if (mutation.mutation())
            choice = generator.nextInt(listSize);

        actualStateString = list.get(choice);
        gameStrategy = all.getStrategy(actualStateString);

        return gameStrategy;
    }

    /**
     * Generuje chromosom.
     *
     * @param stateNumber
     * @return
     */
    public String createChromosome(int stateNumber, boolean mode, boolean mode2) {
        StringBuilder chromosome = new StringBuilder();
        char filledFields;
        ArrayList allBoards;
        allBoards = all.getList();
        String actualState = (String) allBoards.get(stateNumber);
        filledFields = actualState.charAt(gameFields);
        //w zaleznosci od tryby gry tworzony jest poprawny chromosom
        if (mode2) {
            for (int i = 0; i < (gameFields - Character.getNumericValue(filledFields)); i += 2) {
                int indexComputer = getNextComputerMove(stateNumber, allBoards);
                int indexPlayer = getNextPlayerMove(indexComputer, allBoards);
                chromosome.append(Integer.toString(indexComputer));
                chromosome.append(' ');
                chromosome.append(Integer.toString(indexPlayer));
                chromosome.append(' ');
                stateNumber = indexPlayer;
            }
        } else if (!mode) {
            for (int i = 0; i < (gameFields - Character.getNumericValue(filledFields)); i += 2) {
                int indexComputer = getNextComputerMove(stateNumber, allBoards);

                String temp = (String) allBoards.get(indexComputer);

                int indexPlayer = getNextPlayerMove(indexComputer, allBoards);

                String temp2 = (String) allBoards.get(indexPlayer);

                chromosome.append(Integer.toString(indexComputer));
                chromosome.append(' ');
                chromosome.append(Integer.toString(indexPlayer));
                chromosome.append(' ');
                stateNumber = indexPlayer;
                if ((gameFields - Character.getNumericValue(filledFields)) < (i+4)) { //potrzebne do trybu komputer vs komputer
                    indexComputer = getNextComputerMove(stateNumber, allBoards);
                    chromosome.append(Integer.toString(indexComputer));
                    chromosome.append(' ');
                    return (String.valueOf(chromosome).trim());
                }
            }
        } else {
            for (int i = 0; i < (gameFields - Character.getNumericValue(filledFields)); i += 2) {
                int indexPlayer = getNextPlayerMove(stateNumber, allBoards);
                int indexComputer = getNextComputerMove(indexPlayer, allBoards);
                chromosome.append(Integer.toString(indexPlayer));
                chromosome.append(' ');
                chromosome.append(Integer.toString(indexComputer));
                chromosome.append(' ');
                stateNumber = indexComputer;
            }
        }

        return (String.valueOf(chromosome).trim());
    }
}
