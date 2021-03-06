package de.tobiyas.util.math;

public class Levenshtein {

	
	/**
    * returns the levenshtein distance of two strings<br>
    * Der Levenshtein-Algorithmus (auch Edit-Distanz genannt) errechnet die
    * Mindestanzahl von Editierungsoperationen, die notwendig sind, um eine
    * bestimmte Zeichenkette soweit abzudern, um eine andere bestimmte
    * Zeichenkette zu erhalten.<br>
    * Die wohl bekannteste Weise die Edit-Distanz zu berechnen erfolgt durch
    * den sogenannten Dynamic-Programming-Ansatz. Dabei wird eine Matrix
    * initialisiert, die fr jede (m, N)-Zelle die Levenshtein-Distanz
    * (levenshtein distance) zwischen dem m-Buchstabenprfix des einen Wortes
    * und des n-Prfix des anderen Wortes enthlt.<br>
    * Die Tabelle kann z.B. von der oberen linken Ecke zur untereren rechten
    * Ecke gefllt werden. Jeder Sprung horizontal oder vertikal entspricht
    * einer Editieroperation (Einfgen bzw. Lschen eines Zeichens) und
    * "kostet" einen bestimmte virtuellen Betrag.<br>
    * Die Kosten werden normalerweise auf 1 fr jede der Editieroperationen
    * eingestellt. Der diagonale Sprung kostet 1, wenn die zwei Buchstaben in
    * die Reihe und Spalte nicht bereinstimmen, oder im Falle einer
    * bereinstimmung 0.<br>
    * Jede Zelle minimiert jeweils die lokalen Kosten. Daher entspricht die
    * Zahl in der untereren rechten Ecke dem Levenshtein-Abstand zwischen den
    * beiden Wrtern.
    * 
    * @param s
    * @param t
    * @return the levenshtein dinstance
    */
   public static int levenshteinDistance(String s, String t) {
       final int sLen = s.length(), tLen = t.length();

       if (sLen == 0)
           return tLen;
       if (tLen == 0)
           return sLen;

       int[] costsPrev = new int[sLen + 1]; // previous cost array, horiz.
       int[] costs = new int[sLen + 1];     // cost array, horizontally
       int[] tmpArr;                        // helper to swap arrays
       int sIndex, tIndex;                  // current s and t index
       int cost;                            // current cost value
       char tIndexChar;                     // char of t at tIndexth pos.

       for (sIndex = 0; sIndex <= sLen; sIndex++)
           costsPrev[sIndex] = sIndex;

       for (tIndex = 1; tIndex <= tLen; tIndex++) {
           tIndexChar = t.charAt(tIndex - 1);
           costs[0] = tIndex;

           for (sIndex = 1; sIndex <= sLen; sIndex++) {
               cost = (s.charAt(sIndex - 1) == tIndexChar) ? 0 : 1;
               // minimum of cell to the left+1, to the top+1, to the
               // diagonally left and to the up +cost
               costs[sIndex] = Math.min(Math.min(costs[sIndex - 1] + 1,
                                                 costsPrev[sIndex] + 1),
                                        costsPrev[sIndex - 1] + cost);
           }

           // copy current distance counts to 'previous row' distance counts
           tmpArr = costsPrev;
           costsPrev = costs;
           costs = tmpArr;
       }

       // we just switched costArr and prevCostArr, so prevCostArr now actually
       // has the most recent cost counts
       return costsPrev[sLen];
   }
   
   /**
    * Gets the nearest object with the Levenshtein Distance.
    * <br>toString() is called on all objects to determine their distance.
    * 
    * @param test the string to test against.
    * @param objects to use.
    * @return the nearest Object. 
    */
   public static <T> T getNearest(String test, T[] objects){
	   T nearest = null;
	   int best = 100000;
	   
	   for(T obj : objects) {
		   int dist = levenshteinDistance(test, obj.toString());
		   if(dist < best){
			   nearest = obj;
			   best = dist;
		   }
	   }
	   
	   return nearest;
   }
   
   /**
    * Gets the nearest object with the Levenshtein Distance.
    * <br>toString() is called on all objects to determine their distance.
    * 
    * @param test the string to test against.
    * @param objects to use.
    * @return the nearest Object. 
    */
   public static <T> T getNearestIgnoreCase(String test, T[] objects){
	   test = test.toLowerCase();
	   
	   T nearest = null;
	   int best = 100000;
	   
	   for(T obj : objects) {
		   int dist = levenshteinDistance(test, obj.toString().toLowerCase());
		   if(dist < best){
			   nearest = obj;
			   best = dist;
		   }
	   }
	   
	   return nearest;
   }

	
}
