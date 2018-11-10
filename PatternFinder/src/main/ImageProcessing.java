package main;
public final class ImageProcessing {
    
    
    // Functions getRed & getBlue & getGreen : Applying the mask on the RGB color & bit-shift
    
    
    /**
     * Returns red component from given packed color.
     * @param rgb : a 32-bits RGB color
     * @return an integer,  between 0 and 255
     * @see #getGreen
     * @see #getBlue
     * @see #getRGB(int, int, int)
     */
    public static int getRed(int rgb) {
        int redMask= 0xFF0000;
        return ((rgb & redMask)>>16); 
    }

    /**
     * Returns green component from given packed color.
     * @param rgb : a 32-bits RGB color
     * @return an integer between 0 and 255
     * @see #getRed
     * @see #getBlue
     * @see #getRGB(int, int, int)
     */
    public static int getGreen(int rgb) {
        int greenMask= 0xFF00;
        return ((rgb & greenMask)>>8); 
    }

    /**
     * Returns blue component from given packed color.
     * @param rgb : a 32-bits RGB color
     * @return an integer between 0 and 255
     * @see #getRed
     * @see #getGreen
     * @see #getRGB(int, int, int)
     */
    public static int getBlue(int rgb) {
        int blueMask= 0xFF;
        return ((rgb & blueMask));
    }

   
    /**
     * Returns the average of red, green and blue components from given packed color.
     * @param rgb : 32-bits RGB color
     * @return a double between 0 and 255
     * @see #getRed
     * @see #getGreen
     * @see #getBlue
     * @see #getRGB(int)
     */
    public static double getGray(int rgb) {
        
        double gray = (getRed(rgb) + getBlue(rgb) + getGreen(rgb))/3;       
        return gray;
    }

    /**
     * Returns packed RGB components from given red, green and blue components.
     * @param red : an integer 
     * @param green : an integer 
     * @param blue : an integer
     * @return a 32-bits RGB color
     * @see #getRed
     * @see #getGreen
     * @see #getBlue
     */
   
    // getRGB : Components < 0 & > 255 set between 0 and 255 and building the RBG
    public static int getRGB(int red, int green, int blue) {
        if(red < 0)
        {
            red = 0;
        }
        if(red > 255)
        {
            red = 255;
        }
        if(green < 0)
        {
            green = 0;
        }
        if(green > 255)
        {
            green = 255;
        }
        if(blue < 0)
        {
            blue = 0;
        }
        if(blue > 255)
        {
            blue = 255;
        }
        int rgb = (blue << 16) + (green << 8) + red;
        return rgb; 
    }

    /**
     * Returns packed RGB components from given gray-scale value.
     * @param gray : an integer 
     * @return a 32-bits RGB color
     * @see #getGray
     */
    
    public static int getRGB(double gray) {
        int rgb = getRGB((int)(gray),(int)(gray),(int)(gray));
        return rgb; 
    }

    /**
     * Converts packed RGB image to gray-scale image.
     * @param image : a HxW integer array
     * @return a HxW double array
     * @see #encode
     * @see #getGray
     */
    
    //setting all the pixels of the image to shades of gray
    public static double[][] toGray(int[][] image) {
        assert image != null;
        double[][] imageGray = new double[image.length][image[0].length];
        int imgLength0 = image.length;
        int imgLength1 = image[0].length;
        for(int i = 0; i < imgLength0; i++)
        {
            for(int j = 0; j < imgLength1; j++)
            {
                imageGray[i][j] = getGray(image[i][j]);
            }
        }
        return imageGray;
    }

    /**
     * Converts gray-scale image to packed RGB image.
     * @param channels : a HxW double array
     * @return a HxW integer array
     * @see #decode
     * @see #getRGB(double)
     */
    
    //Inverse function of toGray : setting the pixels of the image from grayscale to RGB
    public static int[][] toRGB(double[][] gray) {
        assert gray != null;
        int[][] imageRGB = new int[gray.length][gray[0].length];
        int grayLength0 = gray.length;
        int grayLength1 = gray[0].length;
        for(int i = 0; i < grayLength0; i++)
        {
            for(int j = 0; j < grayLength1; j++)
            {
                imageRGB[i][j] = getRGB(gray[i][j]);
            }
        }
        return imageRGB;
    }

    
    /**
     * Convert an arbitrary 2D double matrix into a 2D integer matrix 
     * which can be used as RGB image
     * @param matrix : the arbitrary 2D double array to convert into integer
     * @param min : a double, the minimum value the matrix could theoretically contains
     * @param max : a double, the maximum value the matrix could theoretically contains
     * @return an 2D integer array, containing a RGB mapping of the matrix 
     */
    public static int[][] matrixToRGBImage(double[][] matrix, double min, double max) {
        
        assert matrix.length > 1;
        assert matrix[0].length > 1;
        
        if (min > max) {
            
            double temp = min;
            min = max;
            max = temp;
            
        }
        
        int[][] matrix1 = new int[matrix.length][matrix[0].length];
        int matrixLength0 = matrix.length;
        int matrixLength1 = matrix[0].length;
        for(int i = 0; i < matrixLength0; i++)
        {
            for(int j = 0; j < matrixLength1 ; j++)
            {
                double g = ((matrix[i][j] - min)/(max - min))*255;
                matrix1[i][j] = getRGB(g);
            }
        }
        return matrix1;
    }
}
