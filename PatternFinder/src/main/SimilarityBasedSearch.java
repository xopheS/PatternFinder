package main;

public class SimilarityBasedSearch {

    /**
     * Computes the mean value of a gray-scale image given as a 2D array 
     * @param image : a 2D double array, the gray-scale Image
     * @return a double value between 0 and 255 which is the mean value
     */
    
    public static double mean(double[][] image) {
        
        assert (image[0].length)*(image.length) >= 1;
        
        double sum = 0;
        
        int imgLength0 = image.length;
        int imgLength1 = image[0].length;
        
        for(int i = 0; i < imgLength0; i++)
        {
            for(int j = 0; j < imgLength1; j++)
            {
                sum += image[i][j];
            }
        }
        return sum/(imgLength0*imgLength1); 
    }
    
    //Finding the mean of a given submatrix starting at (row;col) with given length and height
    static double windowMean(double [][] matrix , int row , int col ,
            int width , int height) {
        
        assert (matrix[0].length)*(matrix.length) >= 1;
        assert row > 0;
        assert col > 0;
        assert width > 0;
        assert height > 0;
        
        double sum = 0;
        
        for(int i = row; i < row + width ; i++)
        {
            for(int j = col; j < col + height; j++)
            {
                sum += matrix[i][j];
            }
        }
        return sum/(width*height); 
    }

    
    /**
     * Computes the Normalized Cross Correlation of a gray-scale pattern if positioned
     * at the provided row, column-coordinate in a gray-scale image
     * @param row : a integer, the row-coordinate of the upper left corner of the pattern in the image.
     * @param column : a integer, the column-coordinate of the upper left corner of the pattern in the image.
     * @param pattern : an 2D array of doubles, the gray-scale pattern to find
     * @param image : an 2D array of double, the gray-scale image where to look for the pattern
     * @return a double, the Normalized Cross Correlation value at position (row, col) between the pattern and the part of
     * the base image that is covered by the pattern, if the pattern is shifted by x and y.
     * should return -1 if the denominator is 0
     */
    
    public static double normalizedCrossCorrelation(int row, int col, double[][] pattern, double[][] image) {
        
        assert (pattern[0].length)*(pattern.length) >= 1;
        assert (image[0].length)*(image.length) >= 1;
        assert row > 0;
        assert col > 0;
        
        double numeratorSum = 0;
        double denominatorSum1 = 0;
        double denominatorSum2 = 0;
        double denominator = 0;
        
        double M = mean(pattern); //Mean of the pattern
        
        int patLength0 = pattern.length;
        int patLength1 = pattern[0].length;
        
        double W = windowMean(image, row, col, patLength0, patLength1); //Mean of the submatrix (size of the pattern)
        
        for(int i = 0; i < patLength0; i++)
        {
            for(int j = 0; j < patLength1; j++)
            {
                
                //Calculating the normalized cross correlation
                numeratorSum += (image[row + i][col + j] - W)*(pattern[i][j] - M);
                denominatorSum1 += (image[row + i][col + j] - W)*(image[row + i][col + j] - W);
                denominatorSum2 += (pattern[i][j] - M)*(pattern[i][j] - M);
                
            }
        }
        
        
        denominator = Math.sqrt(denominatorSum1*denominatorSum2);
        
        if(denominator == 0)
        {
            return -1;
        }
        
        return numeratorSum/denominator; 
    }

    
    
    /**
     * Compute the similarityMatrix between a gray-scale image and a gray-scale pattern
     * @param pattern : an 2D array of doubles, the gray-scale pattern to find
     * @param image : an 2D array of doubles, the gray-scale image where to look for the pattern
     * @return a 2D array of doubles, containing for each pixel of a original gray-scale image, 
     * the similarity (normalized cross-correlation) between the image's window and the pattern
     * placed over this pixel (upper-left corner)
     */
    public static double[][] similarityMatrix(double[][] pattern, double[][] image) {
        
        assert (pattern[0].length)*(pattern.length) >= 1;
        assert (image[0].length)*(image.length) >= 1;
        
        int dim0 = image.length - pattern.length;
        int dim1 = image[0].length - pattern[0].length;
        
        double[][] similMatrix = new double[dim0][dim1];
        
        for(int i = 0; i < dim0; i++)
        {
            for(int j = 0; j < dim1; j++)
            {
                //Setting the cross correlation values in the similarity matrix
                similMatrix[i][j] = normalizedCrossCorrelation(i, j, pattern, image);
            }
        }
        
        return similMatrix; 
    }

}
