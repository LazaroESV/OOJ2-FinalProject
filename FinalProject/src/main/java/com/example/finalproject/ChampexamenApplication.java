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

        Label ShowingGrade = new Label("Grade: Not submitted yet");
        Label gradeAns = new Label("");
        HBox gradeBox = new HBox(labelShowingGrade);
        grade.setAlignment(Pos.CENTER);

        QuestionBank myBank = new QuestionBank();
        myBank.readMCQ("src/main/resources/mcq.txt");
        myBank.readTFQ("src/main/resources/tfq.txt");

        //Question 13
        int[] indices = {0, 1, 2, 3, 4, 5, 6, 7, 8, 9};
        exam = new Exam(bank.selectRandQuestions(indices));

        VBox[] pages = createExamPage(exam);
        VBox allQuestions = new VBox(15);

        for(VBox qBox : pages){
            allQuestions.getChildren().add(qBox);
        }

        ScrollPane scrollPane = new ScrollPane(allQuestions);
        scrollPane.setFitToWidht(true);

        HBox navBar = buildNavigationBar();
        navBar.setAlignment(Pos.CENTER);

        root = new VBox(10, menu, topBanner, gradeBox, scrollPane, navBar);
        Scene scene = new Scene(root, 1080, 600);
        stage.setTitle("Champexamen: Champs Testing App");
        stage.setScene(scene);
        stage.show();
    }

    //Question 11
    public VBox[] createExamPage(Exam exam){
        int size = exam.getQuestions().size();
        VBox[] questionsBoxes = new VBox[size];

        for(int i = 0; i<size; i++){
            Question q = exam.getQuestion(i);
            if(q instanceof TFQuestion){
                questionBoxes[i] = buildTrueFalseQ(i, (TFQuestion) q);
            } else if(q instanceof MCQuestion){
                questionsBoxes[i] = buildMCQ(i, (MCQuestion) q);
            } else{
                Label error = new Label("Unsupported question type");
                questionsBoxes[i] = new VBox(error);
            }
        }
        return questionBoxes;
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

        //Grade Submission Logic
        submit.setOnAction(new SubmitButtonHandler());

        return new HBox(10, clear, save, submit);
    }
    
    public static MenuBar buildMenu(){
        Menu file = new Menu("File");
        MenuItem open = new MenuItem("Open");
        MenuItem save = new MenuItem("Save");
        MenuItem exit = new MenuItem("Exit");
        file.getItems().addAll(open, save, exit);

        Menu edit = new Menu("Edit");
        MenuItem cut = new MenuItem("Cut");
        MenuItem copy = new MenuItem("Copy");
        MenuItem paste = new MenuItem("Paste");
        edit.getItems().addAll(cut, copy, paste);

        Menu quiz = new Menu("Quiz");
        MenuItem startQuiz = new MenuItem("Start Quiz");
        MenuItem viewResults = new MenuItem("View Results");
        quiz.getItems().addAll(startQuiz, viewResults);

        Menu extras = new Menu("Extras");
        MenuItem settings = new MenuItem("Settings");
        MenuItem about = new MenuItem("About");
        extras.getItems().addAll(settings, about);

        Menu help = new Menu("Help");
        MenuItem helpContent = new MenuItem("Help Content");
        MenuItem aboutApp = new MenuItem("About App");
        help.getItems().addAll(helpContent, aboutApp);

        return new MenuBar(file, edit, quiz, extras, help);
    }

    public void setQuestionAnswer(int questionNumber, String answer){
        exam.getSubmittedAnswers().put(questionNumber,answer);
    }

    //Question 12
    class SubmitButtonHandler implements EventHandler<ActionEvent>{
        @Override
        public void handle(ActionEvent e){
            int correct = 0;
            int total = exam.getQuestions().size();

            for(int i = 0; i<total; i++){
                Question q = exam.getQuestion(i);
                String submitted = exam.getSubmittedAnswer(i);
                if(submitted != null && submitted.equalsIgnoreCase(q.getCorrectAnswer())){
                    correct++;
                }
            }
            double grade = (correct / (double) total) * 100;
            labelShowingGrade.setText("Grade: " + String.format("%.2f", grade) + "%"); 
        }
    }

    public static void main(String[] args) {
        launch();
    }
}
