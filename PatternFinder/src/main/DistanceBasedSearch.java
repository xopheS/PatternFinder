package main;

public class DistanceBasedSearch {

    /**
     * Computes the mean absolute error between two RGB pixels, channel by channel.
     * @param patternPixel : a integer, the second RGB pixel.
     * @param imagePixel : a integer, the first RGB pixel.
     * @return a double, the value of the error for the RGB pixel pair. (an integer in [0, 255])
     */
    public static double pixelAbsoluteError(int patternPixel, int imagePixel) {
        double absRed = Math.abs(ImageProcessing.getRed(patternPixel) - ImageProcessing.getRed(imagePixel));
        double absGreen = Math.abs(ImageProcessing.getGreen(patternPixel) - ImageProcessing.getGreen(imagePixel));
        double absBlue = Math.abs(ImageProcessing.getBlue(patternPixel) - ImageProcessing.getBlue(imagePixel));
        
        double absoluteError = (absRed + absGreen + absBlue)/3;
        
        return absoluteError;
    }

    /**
     * Computes the mean absolute error loss of a RGB pattern if positioned
     * at the provided row, column-coordinates in a RGB image
     * @param row : a integer, the row-coordinate of the upper left corner of the pattern in the image.
     * @param column : a integer, the column-coordinate of the upper left corner of the pattern in the image.
     * @param pattern : an 2D array of integers, the RGB pattern to find
     * @param image : an 2D array of integers, the RGB image where to look for the pattern
     * @return a double, the mean absolute error
     * @return a double, mean absolute error value at position (row, col) between the pattern and the part of
     * the base image that is covered by the pattern, if the pattern is shifted by x and y.
     */
    public static double meanAbsoluteError(int row, int col, int[][] pattern, int[][] image) {
        
        assert (image[0].length)*(image.length) >= 1;
        assert (pattern[0].length)*(pattern.length) >= 1;
        assert col >=0;
        assert row >=0;
        
        double sum = 0;
        int patternLength0 = pattern.length;
        int patternLength1 = pattern[0].length;
   
        for(int i=0; i < patternLength0; i++) {
            
            for(int j=0; j < patternLength1; j++) {
               
                sum += pixelAbsoluteError(pattern[i][j], image[row+i][col+j]);
                
            }
        }
        
        return sum/(patternLength0 * patternLength1); 
    }

    /**
     * Compute the distanceMatrix between a RGB image and a RGB pattern
     * @param pattern : an 2D array of integers, the RGB pattern to find
     * @param image : an 2D array of integers, the RGB image where to look for the pattern
     * @return a 2D array of doubles, containing for each pixel of a original RGB image, 
     * the distance (meanAbsoluteError) between the image's window and the pattern
     * placed over this pixel (upper-left corner) 
     */
    public static double[][] distanceMatrix(int[][] pattern, int[][] image) {
        
        assert (image[0].length)*(image.length) >= 1;
        assert (pattern[0].length)*(pattern.length) >= 1;
        
        int imgLength0 = image.length;
        int imgLength1 = image[0].length;
        int patternLength0 = pattern.length;
        int patternLength1 = pattern[0].length;
        int iMax = imgLength0 - patternLength0;
        int jMax = imgLength1 - patternLength1;
        double[][] distMatrix = new double[imgLength0 - patternLength0][imgLength1 - patternLength1];
        for(int i = 0; i < iMax; i++)
        {
            for(int j = 0; j < jMax; j++)
            {
                distMatrix[i][j] = meanAbsoluteError(i, j, pattern, image);
            }
        }
        return distMatrix; 
    }

   
    
    public static double[][] distanceMatrix(int[][] pattern, int[][] image, String strategy) {
    
    int iMax = image.length - pattern.length;
    int jMax = image[0].length - pattern[0].length;
    
    double[][] distMatrix = new double[image.length][image[0].length];
    
    if (strategy == "wrapping") {
    
        for(int i = 0; i < image.length; i++)
        {
            for(int j = 0; j < image[0].length; j++)
            {
                if (j>jMax && i>iMax)      {distMatrix[i][j] = meanAbsoluteError(i%iMax, j%jMax, pattern, image);}
                else if (i>iMax && j<jMax) {distMatrix[i][j] = meanAbsoluteError(i%iMax, j, pattern, image);}
                else if (j>jMax && i<iMax) {distMatrix[i][j] = meanAbsoluteError(i, j%jMax, pattern, image);}
                else if (j<jMax && i<iMax) {distMatrix[i][j] = meanAbsoluteError(i, j, pattern, image);}
            }
        }
        
        
    }
    if (strategy == "mirroring") {
        
        for(int i = 0; i < image.length; i++)
        {
            for(int j = 0; j < image[0].length; j++)
            {
                if (j>jMax && i>iMax)      {distMatrix[i][j] = meanAbsoluteError((iMax-2-i%iMax),(jMax-2-j%jMax), pattern, image); continue;}
                else if (i>iMax && j<jMax) {distMatrix[i][j] = meanAbsoluteError((iMax-2-i%iMax), j, pattern, image); continue;}
                else if (j>jMax && i<iMax) {distMatrix[i][j] = meanAbsoluteError(i,(jMax-2-j%jMax), pattern, image); continue;}
                else if (j<jMax && i<iMax) {distMatrix[i][j] = meanAbsoluteError(i, j, pattern, image); continue;}
            }
        }
        
        
    }
    return distMatrix;
    
    }
    
    
}