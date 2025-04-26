package com.example.finalproject;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;

public class ChampexamenApplication extends Application {
    // Data Fields
    private Exam exam;
    private Label labelShowingGrade;
    private VBox[] questionVBoxes;
    private VBox root;

    @Override
    public void start(Stage stage) throws IOException {
        QuestionBank myBank = new QuestionBank();
        myBank.readMCQ("src/main/resources/mcq.txt");
        myBank.readTFQ("src/main/resources/tfq.txt");

        myBank.printAllQuestions();

        ScrollPane mainPane = new ScrollPane();
        Scene scene = new Scene(mainPane, 1080, 600);
        stage.setTitle("Champexamen: Champs Testing App");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}