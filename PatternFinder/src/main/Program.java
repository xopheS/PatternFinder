package main;

import java.io.File;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.stage.Stage;

public class Program extends Application{
    
    static String projectPath = System.getProperty("user.dir");
    
    public static void main(String[] args) {
        Application.launch(args);
    } 
    
    public void start(Stage primaryStage) {
        
        primaryStage.setTitle("Pattern Finder");
        primaryStage.getIcons().add(new Image("file:" + projectPath + "/images/icon.jpg"));
        
        Text t = new Text(10, 50, "Select a background image along with a pattern image to find in background \n "
                + "(pattern must be smaller than background)");
        t.setFont(new Font(20));
        
        FileChooser fileChooserBackground = new FileChooser();
        fileChooserBackground.setTitle("Open Background File");
        fileChooserBackground.setInitialDirectory(new File("images"));
        fileChooserBackground.getExtensionFilters().addAll(
                new ExtensionFilter("Image Files", "*.png", "*.jpg"));
        
        FileChooser fileChooserImage = new FileChooser();
        fileChooserImage.setTitle("Open Background File");
        fileChooserImage.setInitialDirectory(new File("images"));
        fileChooserImage.getExtensionFilters().addAll(
                new ExtensionFilter("Image Files", "*.png", "*.jpg"));

        Label pathBackground = new Label(null);
        Label pathImage = new Label(null);
        pathBackground.setMinWidth(250);
        pathImage.setMinWidth(250);
        
        Label choiceBox = new Label("Choose finding technique");
        ChoiceBox<String> cb = new ChoiceBox<>(FXCollections.observableArrayList(
                "SimilarityFind", "DistanceFind")
            );
        cb.getSelectionModel().selectFirst();
        cb.setMinWidth(120);
        
        Button button = new Button("Select Background");
        button.setMinWidth(120);
        button.setOnAction(e -> {
            File file = fileChooserBackground.showOpenDialog(primaryStage);
            if (file != null) {
                pathBackground.setText(file.getAbsolutePath());
            } else {
                pathBackground.setText(null);
            }
            
        });
        
        Button button2 = new Button("Select Image");
        button2.setMinWidth(120);
        button2.setOnAction(e -> {
            File file = fileChooserImage.showOpenDialog(primaryStage);
            if (file != null) {
                pathImage.setText(file.getAbsolutePath());
            } else {
                pathImage.setText(null);
            }
            
        });
                
        Label wait = new Label();
        Button go = new Button("Find!");
        go.setMinWidth(120);
        go.setOnAction(e -> {
           wait.setText("Please wait...");
          if(pathBackground.getText() == null  || pathImage.getText() == null) {
              Alert alert = new Alert(AlertType.WARNING);
              alert.setTitle("Information Dialog");
              alert.setHeaderText("File not Found");
              alert.setContentText("You need to upload a background file and an image file to find in background");
              alert.showAndWait();
          }
          else {
              int[][] background = Helper.read(pathBackground.getText()); 
              int[][] pattern = Helper.read(pathImage.getText()); 
              if(background.length <= pattern.length || background[0].length <= pattern[0].length) {
                  Alert alert = new Alert(AlertType.WARNING);
                  alert.setTitle("Information Dialog");
                  alert.setHeaderText("Pattern is not smaller than background");
                  alert.setContentText("Please upload appropriate images");
                  alert.showAndWait();
              }
              else {  
                  if(cb.getValue() ==  "SimilarityFind") similarityFind(pattern, background);
                  else distanceFind(pattern,background);
                  }
              }
          wait.setText(null);
        });
        
        GridPane root = new GridPane();
        root.add(button, 0, 0);
        root.add(button2, 0, 1);
        root.add(pathBackground, 1, 0);
        root.add(pathImage, 1, 1);
        root.add(cb, 0, 2);
        root.add(choiceBox, 1, 2);
        root.setHgap(10);
        root.setVgap(10);
        
        ImageView im = new ImageView(new Image("file:" + projectPath + "/images/icon.jpg"));
        im.setFitWidth(120);
        im.setFitHeight(100);
        
        GridPane find = new GridPane();
        find.add(wait, 1, 0);
        find.add(go, 0, 0);
        find.add(im, 0, 1);
        find.setVgap(20);
        find.setHgap(10);
        
        
        BorderPane mainscreen = new BorderPane();
        mainscreen.setTop(t);
        BorderPane.setMargin(t, new Insets(10, 20, 10, 20));
        mainscreen.setCenter(root);
        BorderPane.setMargin(root, new Insets(10, 20, 10, 20));
        mainscreen.setBottom(find);
        BorderPane.setMargin(find, new Insets(10, 20, 10, 20));
        mainscreen.setStyle("-fx-background-color: lightgray");
        Scene scene = new Scene(mainscreen);
        
            
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    
    
      
    public static void distanceFind(int[][] pattern, int[][] image)
    {
         double[][] distance = DistanceBasedSearch.distanceMatrix(pattern, image);          
         int[] p = Collector.findBest(distance, true);
         Helper.drawBox(p[0], p[1], pattern[0].length, pattern.length, image);
         Helper.show(image, "Found!");
    }
    
    public static void similarityFind(int[][] pattern, int[][] image)
    {
         double[][] correlation = SimilarityBasedSearch.similarityMatrix(ImageProcessing.toGray(pattern), ImageProcessing.toGray(image));           
         int[] p1 = Collector.findBest(correlation, false);
         Helper.drawBox(p1[0], p1[1], pattern[0].length, pattern.length, image);
         Helper.show(image, "Found!");
    }
    
}