package com.example.finalproject;

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
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
        MenuBar menu = buildMenu();

        HBox topBanner = buildTopBanner();
        topBanner.setAlignment(Pos.CENTER);

        Label gradeTxt = new Label("Grade: ");
        Label gradeAns = new Label("");
        HBox grade = new HBox(gradeTxt, gradeAns);
        grade.setAlignment(Pos.CENTER);

        QuestionBank myBank = new QuestionBank();
        myBank.readMCQ("src/main/resources/mcq.txt");
        myBank.readTFQ("src/main/resources/tfq.txt");

        myBank.printAllQuestions();

        ScrollPane questionPane = new ScrollPane();

        HBox navBar = buildNavigationBar();
        navBar.setAlignment(Pos.CENTER);

        root = new VBox(10, menu, topBanner, grade, questionPane, navBar);
        Scene scene = new Scene(root, 1080, 600);
        stage.setTitle("Champexamen: Champs Testing App");
        stage.setScene(scene);
        stage.show();
    }

    public static HBox buildTopBanner(){
        Image logoImg = new Image("logo.png");
        ImageView logo = new ImageView(logoImg);
        logo.setPreserveRatio(true);
        logo.setFitWidth(300);

        Image bannerImg = new Image("banner.png");
        ImageView banner = new ImageView(bannerImg);
        banner.setPreserveRatio(true);
        banner.setFitWidth(600);

        return new HBox(30, logo, banner);
    }
    public static HBox buildNavigationBar(){
        Button clear = new Button("Clear");
        Button save = new Button("Save");
        Button submit = new Button("Submit");

        return new HBox(10, clear, save, submit);
    }
    public static MenuBar buildMenu(){
        Menu file = new Menu("File");
        MenuItem open = new MenuItem("Open");
        MenuItem save = new MenuItem("Save");
        MenuItem exit = new MenuItem("Exit");
        file.getItems().add(open);
        file.getItems().add(save);
        file.getItems().add(exit);

        Menu edit = new Menu("Edit");
        MenuItem cut = new MenuItem("Cut");
        MenuItem copy = new MenuItem("Copy");
        MenuItem paste = new MenuItem("Paste");
        edit.getItems().add(cut);
        edit.getItems().add(copy);
        edit.getItems().add(paste);

        Menu quiz = new Menu("Quiz");
        MenuItem startQuiz = new MenuItem("Start Quiz");
        MenuItem viewResults = new MenuItem("View Results");
        quiz.getItems().add(startQuiz);
        quiz.getItems().add(viewResults);

        Menu extras = new Menu("Extras");
        MenuItem settings = new MenuItem("Settings");
        MenuItem about = new MenuItem("About");
        extras.getItems().add(settings);
        extras.getItems().add(about);

        Menu help = new Menu("Help");
        MenuItem helpContent = new MenuItem("Help Content");
        MenuItem aboutApp = new MenuItem("About App");
        help.getItems().add(helpContent);
        help.getItems().add(aboutApp);

        return new MenuBar(file, edit, quiz, extras, help);
    }

    public static void main(String[] args) {
        launch();
    }
}