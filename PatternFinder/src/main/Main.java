package main;

import java.util.ArrayList;


/**
* 
* @author Christophe SAAD & David CIAN
*
*   Where is Charlie Project 
*
*/
public final class Main {

  static String projectPath = System.getProperty("user.dir");
    
  public static void main(String[] args) {
    testGetRed();
    testGetGreen();
    testGetBlue();
    testGetGray();
    testGetRGB();
    testGrayscale();
    testFindBest();
    testQuickSort();
    testNBestQuickSort();
    testFindNBest();
    testDistanceBasedSearch();
    //testDistanceBasedSearchMirroring();
    //testDistanceBasedSearchWrapping();
    testSimilarityBasedSearch();   
    findCharlie();
    
  }
  
  /*
   * Tests for Class ImageProcessing
   */
  public static void testGetRed() { 
    int color = 0b11110000_00001111_01010101;
    int ref = 0b11110000;
    int red = ImageProcessing.getRed(color);
    if (red == ref) {
        System.out.println("Test passed");
    } else {
        System.out.println("Test failed. Returned value = " + red + " Expected value = " + ref);
    }
  }
  
  public static void testGetGreen() { 
    int color = 0b11110000_00001111_01010101;
    int ref = 0b00001111;
    int green = ImageProcessing.getGreen(color);
    if (green == ref) {
        System.out.println("Test passed");
    } else {
        System.out.println("Test failed. Returned value = " + green + " Expected value = " + ref);
    }
  }
  
  public static void testGetBlue() { 
    int color = 0b11110000_00001111_01010101;
    int ref = 0b01010101;
    int blue = ImageProcessing.getBlue(color);
    if (blue == ref) {
        System.out.println("Test passed");
    } else {
        System.out.println("Test failed. Returned value = " + blue + " Expected value = " + ref);
    }
  }
  
  public static void testGetGray() {
    int color = 0b11110000_00001111_01010101;
    double ref = (85 + 15 + 240)/3;
    if (ImageProcessing.getGray(color) == ref)
    {
        System.out.println("Test passed");
        
    }
    else
    {
        System.out.println("Test failed");
    }
    
  }
  
  public static void testGetRGB() {
    int color = 5574640; //int value of color
    int blue = 0b01010101;
    int green = 0b00001111;
    int red = 0b11110000;
    if (ImageProcessing.getRGB(red,green,blue) == color)
    {
        System.out.println("Test passed");
        
    }
    else
    {
        System.out.println("Test failed");
    }
    
  }
    
  
  public static void testGrayscale() {
    System.out.println("Test Grayscale");
    int[][] image = Helper.read(projectPath + "/images/food.png");
    double[][] gray = ImageProcessing.toGray(image);
    Helper.show(ImageProcessing.toRGB(gray), "test bw");
  }
  
  //TODO: complete
  
      
  /*
   * Tests for Class Collector
   */
  
  public static void testFindBest() {
    System.out.println("Test findNBest");
    double[][] t = new double[][] {{20, 30, 10, 50, 32}, {28, 39, 51, 78, 91}};
    int[] coords = Collector.findBest(t, true); 
    if (coords[0] == 0 && coords[1] == 2) {
        System.out.println("Test passed");
    }
    else {
        
        System.out.println("Test failed");
    }
  }
  
  
  public static void testFindNBest() {
    System.out.println("Test findNBest");
    double[][] t = new double[][] {{20, 30, 10, 50, 32}, {28, 39, 51, 78, 91}};
    int[][] coords = Collector.findNBest(10, t, true);              
    for (int[] a : coords) {
        int r = a[0];
        int c = a[1];
        System.out.println("Row=" + r + " Col=" + c + " Val=" + t[r][c]);
    }    
  }
  
  public static void testQuickSort() {
    System.out.println("Test findBest Quicksort");
    double[][] t = new double[][] {{20, 30, 10, 50, 32}, {28, 39, 51, 78, 91}};
    ArrayList<int[]> coords = Collector.quicksortPixelCoordinates(t); 
    if (coords.get(0)[0] == 1 && coords.get(0)[1] == 4) {
        System.out.println("Test passed");
    }
    else {
        
        System.out.println("Test failed");
    }
  }
  
  public static void testNBestQuickSort() {
    System.out.println("Test findNBest Quicksort");
    double[][] t = new double[][] {{20, 30, 10, 50, 32}, {28, 39, 51, 78, 91}};
    int[][] coords = Collector.findNBestQuickSort(10, t, true);             
    for (int[] a : coords) {
        int r = a[0];
        int c = a[1];
        System.out.println("Row=" + r + " Col=" + c + " Val=" + t[r][c]);
    }    
  }

  //TODO: complete

  /*
   * Tests for Class DistanceBasedSearch
   */
  
  public static void testDistanceBasedSearch() {
    System.out.println("Test DistanceBasedSearch");
    int[][] food = Helper.read(projectPath + "/images/food.png");
    int[][] onions = Helper.read(projectPath + "/images/onions.png");
    double[][] distance = DistanceBasedSearch.distanceMatrix(onions, food);             
    int[] p = Collector.findBest(distance, true);
    Helper.drawBox(p[0], p[1], onions[0].length, onions.length, food);
    Helper.show(food, "Found!");
    
    Helper.show(ImageProcessing.matrixToRGBImage(distance , 0, 255),
            "Distance") ;
    
  
  }
  
  public static void testDistanceBasedSearchMirroring() {
    System.out.println("Test DistanceBasedSearch");
    int[][] beach = Helper.read(projectPath + "/images/charlie_beach.png");
    int[][] charlie = Helper.read(projectPath + "/images/charlie.png");
    double[][] distance = DistanceBasedSearch.distanceMatrix(charlie, beach, "mirroring");          
    int[] p = Collector.findBest(distance, true);
    Helper.drawBox(p[0], p[1], charlie[0].length, charlie.length, beach);
    Helper.show(beach, "Found!");
    
    Helper.show(ImageProcessing.matrixToRGBImage(distance , 0, 255),
            "Distance") ;
    
  
  }
  
  public static void testDistanceBasedSearchWrapping() {
    System.out.println("Test DistanceBasedSearch");
    int[][] beach = Helper.read(projectPath + "/images/charlie_beach.png");
    int[][] charlie = Helper.read(projectPath + "/images/charlie.png");
    double[][] distance = DistanceBasedSearch.distanceMatrix(charlie, beach, "wrapping");           
    int[] p = Collector.findBest(distance, true);
    Helper.drawBox(p[0], p[1], charlie[0].length, charlie.length, beach);
    Helper.show(beach, "Found!");
    
    Helper.show(ImageProcessing.matrixToRGBImage(distance , 0, 255),
            "Distance") ;
    
  
  }
  
  //TODO: complete
  
  /*
   * Tests for Class SimilarityBasedSearch
   */

  public static void testSimilarityBasedSearch() {
    System.out.println("Test SimilarityBasedSearch");
    int[][] food = Helper.read(projectPath + "/images/food.png");
    int[][] onions = Helper.read(projectPath + "/images/onions.png");
    double[][] foodGray = ImageProcessing.toGray(food);
    double[][] onionsGray = ImageProcessing.toGray(onions);     
    double[][] similarity = SimilarityBasedSearch.similarityMatrix(onionsGray, foodGray);
    int[][] best = Collector.findNBest(8, similarity, false);               
    for (int[] a : best) {
        int r = a[0];
        int c = a[1];
        Helper.drawBox(r, c, onions[0].length, onions.length, food);
    }
    Helper.show(food, "Found again!");      
  }
  
  public static void findCharlie() {
    System.out.println("Find Charlie");
    int[][] beach = Helper.read(projectPath + "/images/charlie_beach.png");
    int[][] charlie = Helper.read(projectPath + "/images/charlie.png");
    double[][] beachGray = ImageProcessing.toGray(beach);
    double[][] charlieGray = ImageProcessing.toGray(charlie);       

    System.out.println("Compute Similarity Matrix: expected time about 2 min");
    double[][] similarity = SimilarityBasedSearch.similarityMatrix(charlieGray, beachGray);

    System.out.println("Find N Best");
    int[] best = Collector.findBest(similarity, false);   
    double max = similarity[best[0]][best[1]];
    
    Helper.show(ImageProcessing.matrixToRGBImage(similarity, -1, max), "Similarity");
    
    Helper.drawBox(best[0], best[1], charlie[0].length, charlie.length, beach);
    System.out.println("drawBox at (" + best[0] + "," + best[1] + ")");
    Helper.show(beach, "Found again!");     
  }
  
}
