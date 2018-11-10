package main;

import java.util.ArrayList;

public class Collector {

    /**
     * Find the row, column coordinates of the best element (biggest or smallest) for the given matrix
     * @param matrix : an 2D array of doubles
     * @param smallestFirst : a boolean, indicates if the smallest element is the best or not (biggest is then the best)
     * @return an array of two integer coordinates, row first and then column
     */
    
    public static int[] findBest(double[][] matrix, boolean smallestFirst) {
        
        assert (matrix.length)*(matrix[0].length) >= 1;
        
        //Finding the coordinates of the minimum vales of the array
        if(smallestFirst)
        {
            int[] minPos = new int[] {0, 0};
            double min = matrix[0][0];
            for(int i = 0; i < matrix.length; i++)
            {
                for(int j = 0; j < matrix[0].length; j++)
                {
                    if(matrix[i][j] < min)
                    {
                        min = matrix[i][j];
                        minPos[0] = i;
                        minPos[1] = j;
                    }
                }
            }
            return minPos;
        }
        
        //Finding the coordinates of the maximum value in the array
        else
        {
            int[] maxPos = new int[] {0, 0};
            double max = matrix[0][0];
            for(int i = 0; i < matrix.length; i++)
            {
                for(int j = 0; j < matrix[0].length; j++)
                {
                    if(matrix[i][j] > max)
                    {
                        max = matrix[i][j];
                        maxPos[0] = i;
                        maxPos[1] = j;
                    }
                }
            }
            return maxPos;
        }
    }

    
    /**
     * Find the row, column coordinate-pairs of the n best (biggest or smallest) elements of the given matrix
     * @param n : an integer, the number of best elements we want to find 
     * @param matrix : an 2D array of doubles
     * @param smallestFirst : a boolean,  indicates if the smallest element is the best or not (biggest is the best)
     * @return an array of size n containing row, column-coordinate pairs
     */
    
    public static int[][] findNBest(int n, double[][] matrix, boolean smallestFirst) {
        
        assert (matrix.length)*(matrix[0].length) >= 1;
        
        double[][] matrixClone = new double[matrix.length][matrix[0].length];
        
        //Creating the array of coordinates of size n
        int[][] nBest = new int[n][2];
        
        for(int i = 0; i < matrix.length; i++)
        {
            matrixClone[i] = matrix[i].clone();
        }
        
        for (int i=0; i<n; i++) {
            
            //Copy the coordinates of the smallest element to the array of coordinates
            nBest[i][0] = findBest(matrixClone,smallestFirst)[0];
            nBest[i][1] = findBest(matrixClone,smallestFirst)[1];
            //Setting the value of the point to + or - infinity in order to take the second smallest/biggest in the next iteration
            if (smallestFirst) matrixClone[findBest(matrixClone,smallestFirst)[0]][findBest(matrixClone,smallestFirst)[1]] = Double.POSITIVE_INFINITY;
            else matrixClone[findBest(matrixClone,smallestFirst)[0]][findBest(matrixClone,smallestFirst)[1]] = Double.NEGATIVE_INFINITY;
        }
        return nBest;
    }
    
    

    /** 
     * 
     * Sorts all the row, column coordinates based on their pixel value
     * @param matrix : an 2D array of doubles
     * @return A list of points, each point is an array of length 2.
     */
    
     public static ArrayList<int[]> quicksortPixelCoordinates(double[][] matrix) {
        
        assert (matrix.length)*(matrix[0].length) >= 1;
        
        ArrayList<int[]> points = new ArrayList<int[]>(); 
        
        for (int i=0; i < matrix.length; i++) {
            
            for(int j=0; j < matrix[0].length; j++) {
                
                points.add(new int[] {i,j});
            }
        }
        
        return quicksortPixelCoordinates(points,matrix);
    }
     
     
     public static ArrayList<int[]> quicksortPixelCoordinates(ArrayList<int[]> points , double[][] matrix) {
         
        if(points.isEmpty())  return points;
         
        ArrayList<int[]> inferieur = new ArrayList<int[]>();
        ArrayList<int[]> egal = new ArrayList<int[]>();
        ArrayList<int[]> superieur = new ArrayList<int[]>();
        
        int[] pivot = points.get(0);
        int[] point = {0,0};
        
        for (int i=0; i < points.size(); i++) {
            
                point = points.get(i);
                
                if (matrix[point[0]][point[1]] == matrix[pivot[0]][pivot[1]]) egal.add(point);
                if (matrix[point[0]][point[1]] >  matrix[pivot[0]][pivot[1]]) superieur.add(point);
                if (matrix[point[0]][point[1]] <  matrix[pivot[0]][pivot[1]]) inferieur.add(point);
        }
        
        inferieur = quicksortPixelCoordinates(inferieur,matrix);
        superieur = quicksortPixelCoordinates(superieur,matrix);
        
        superieur.addAll(egal);
        superieur.addAll(inferieur);
 
        return superieur;
     }

    
    /**
     * 
     * Use a quick sort to find the row, column coordinate-pairs of the n best (biggest or smallest) elements of the given matrix
     * @param n : an integer, the number of best elements we want to find 
     * @param matrix : an 2D array of doubles
     * @param smallestFirst : a boolean, indicate if the smallest element is the best or not (biggest is the best)
     * @return an array of size n containing row, column-coordinate pairs
     */
    public static int[][] findNBestQuickSort(int n, double[][] matrix, boolean smallestFirst) {
        
        assert (matrix.length)*(matrix[0].length) >= 1;
        
        int[][] nBest = new int[n][2];
        
        ArrayList<int[]> points = new ArrayList<int[]>();
        points = quicksortPixelCoordinates(matrix);
    
        if (!smallestFirst) {
            
            for (int i=0; i<n; i++) {
                
                nBest[i][0] = points.get(i)[0];
                nBest[i][1] = points.get(i)[1];
            }
            
        }
        
        else {
            
            for (int i=n-1; i>=0; i--) {
                
                nBest[i][0] = points.get(i)[0];
                nBest[i][1] = points.get(i)[1];
            }
            
        }
    
        return nBest;
    
    }
}
