public class Main {

    private static double learningRate = 0.001;			//współczynnik uczenia się
    private static int nots = 20;		//liczba danych testujacych
    private static int noi = 35;				//ilość wejść
    private static int nols = 20;		//liczba danych uczących
    private static int ll = 100;					//maksymalna ilosc epok uczenia
    private static int non = 2000;			//liczba neuronów
    private static double nr = 5.0;		//wartość promienia sąsiedztwa

    public static void main ( String[] args ) {

            WTM[] koh = new WTM[non];
        for (int i = 0; i < non; i++ )
            koh[i] = new WTM(noi);

        int ages = learn( koh );

        int[] winnerLearn = new int[nols], winnerTest = new int[nots];
        int percent = 0;


        for (int i = 0; i < nols; i++ )

            winnerLearn[i] = getWinner( koh, Letters.lettersLearn[i] );


        for (int i = 0; i < nols; i++ )
            winnerTest[i] = getWinner( koh, Letters.lettersTest[i] );

        for (int i = 0; i < nots; i++ )
            if ( winnerLearn[i] == winnerTest[i] )
                percent++;

        System.out.println( "Ilość epok = " + ages );
        System.out.println( "Poprawność testowania = " + ( ( percent * 100 ) / nots) + "%" );
    }


    private static int learn ( WTM[] kohonens ) {

        int counter = 0;
        int winner;

        int[] winners = new int[nols];
        for (int i = 0; i < nols; i++ )
            winners[i] = - 1;

        while ( ! isUnique( winners ) ) {

            for (int i = 0; i < nols; i++ ) {
                winner = getWinner( kohonens, Letters.lettersLearn[i] );
                kohonens[winner].learn( Letters.lettersLearn[i], learningRate );

                for (int j = 0; j < non; j++ )
                    if ( j != winner )
                        if ( distanceBetweenVectors( kohonens[winner].getW(), kohonens[j].getW() ) <= nr)
                            kohonens[j].learn( Letters.lettersLearn[i], learningRate );
            }

            for (int i = 0; i < nols; i++ )
                winners[i] = getWinner( kohonens, Letters.lettersLearn[i] );

            if ( ++ counter == ll)
                break;
        }

        return counter;
    }


    private static boolean isUnique ( int[] winners ) {

        for (int i = 0; i < nols; i++ )
            for (int j = i; j < nols; j++ )
                if ( i != j )
                    if ( winners[i] == winners[j] )
                        return false;

        return true;
    }


    private static int getWinner ( WTM[] kohonens, double[] vector ) {

        int winner = 0;
        double minDistance = distanceBetweenVectors( kohonens[0].getW(), vector );

        for (int i = 0; i < non; i++ ) {
            if ( distanceBetweenVectors( kohonens[i].getW(), vector ) < minDistance ) {
                winner = i;
                minDistance = distanceBetweenVectors( kohonens[i].getW(), vector );
            }
        }

        return winner;
    }


    private static double distanceBetweenVectors ( double[] vector1, double[] vector2 ) {

        double suma = 0.0;

        for ( int i = 0; i < vector1.length; i++ )
            suma += Math.abs( vector1[i] - vector2[i] ); //miara Manhattan

        return Math.sqrt( suma );
    }

}