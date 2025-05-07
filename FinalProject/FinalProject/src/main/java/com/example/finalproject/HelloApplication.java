package com.example.finalproject;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.ScrollPane;
import javafx.stage.Stage;

import java.io.IOException;

public class HelloApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        QuestionBank myBank = new QuestionBank();
        myBank.readMCQ("E:\\Champlain College\\Winter 2025 Semester\\Java 2\\FinalProject\\FinalProject\\src\\main\\resources\\mcq.txt");

        ScrollPane mainPane = new ScrollPane();
        Scene scene = new Scene(mainPane, 1080, 600);
        stage.setTitle("Champexamn: Champs Testing App");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}