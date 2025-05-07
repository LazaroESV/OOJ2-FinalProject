package com.example.finalproject;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;

public class ChampexamenApplication extends Application {
    // Data Fields
    private Exam exam;
    private Label labelShowingGrade;
    private VBox[] questionVBoxes;
    private VBox root;
    private VBox allQuestions;
    private ArrayList<String> savedState = new ArrayList<>();
    private ArrayList<String> attempts = new ArrayList<>();


    // Settings
    private int width = 1080;
    private int height = 600;

    // Menu Items
    MenuItem open;
    MenuItem save;
    MenuItem exit;
    MenuItem cut;
    MenuItem copy;
    MenuItem paste;
    MenuItem startQuiz;
    MenuItem viewResults;
    MenuItem settings;
    MenuItem about;
    MenuItem helpContent;
    MenuItem aboutApp;

    @Override
    public void start(Stage stage) throws IOException {
        MenuBar menu = buildMenu();

        HBox topBanner = buildTopBanner();
        topBanner.setAlignment(Pos.CENTER);

        Label gradeTxt = new Label("Grade:");
        labelShowingGrade = new Label("");
        HBox gradeBox = new HBox(5, gradeTxt, labelShowingGrade);
        gradeBox.setAlignment(Pos.CENTER);

        QuestionBank myBank = new QuestionBank();
        myBank.readMCQ("src/main/resources/mcq.txt");
        myBank.readTFQ("src/main/resources/tfq.txt");

        int[] indices = {0, 1, 2, 3, 4, 5, 6, 7, 8, 9};
        exam = new Exam(myBank.selectRandQuestions(indices));

        questionVBoxes = createExamPage(exam);
        allQuestions = new VBox(30);

        for(VBox qBox : questionVBoxes){
            allQuestions.getChildren().add(qBox);
        }

        ScrollPane scrollPane = new ScrollPane(allQuestions);
        scrollPane.setFitToWidth(true);
        scrollPane.setStyle("-fx-padding: 20px;");


        // Set the menu actions
        exit.setOnAction(e -> {
            stage.close();
        });

        aboutApp.setOnAction(e -> {
            Alert alert = new Alert(Alert.AlertType.INFORMATION, """
                    Authors: \
                    
                    - José Santiago Arevalo Morales\
                    
                    - Lazaro Ernesto Sagarra Valdes\
                    
                    Version: 3.2""");
            alert.setTitle("About This App");
            alert.setHeaderText("Champexamen Quiz App©");
            alert.show();
        });

        save.setOnAction(e -> {
            saveExamAnswers();
        });
        open.setOnAction(e -> {
            clearExamAnswers();
        });

        startQuiz.setOnAction(e -> {
            ChampexamenApplication app = new ChampexamenApplication();
            try {
                app.start(stage);
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        });

        cut.setOnAction(e -> {
            saveExamAnswers();
            clearExamAnswers();
        });
        copy.setOnAction(e -> {
            saveExamAnswers();
        });
        paste.setOnAction(e -> {
            System.out.println("These are the copied answers:");
            for (String s : savedState) {
                if(s != null){
                    System.out.println(s);
                }else{
                    System.out.println("Not answered");
                }
            }
        });

        viewResults.setOnAction(e -> {
            for (String attempt : attempts) {
                System.out.println(attempt);
            }
        });

        settings.setOnAction(e -> {
            Stage window = new Stage();

            Label wTxt = new Label("Width: ");
            wTxt.setAlignment(Pos.CENTER);
            Slider wSlide = new Slider(100.0, 600.0, 600.0);
            VBox width = new VBox(10, wTxt, wSlide);

            Label hTxt = new Label("Height: ");
            hTxt.setAlignment(Pos.CENTER);
            Slider hSlide = new Slider(500.0, 1080.0, 1080.0);
            VBox height = new VBox(10, hTxt, hSlide);

            Button btn = new Button("Save");
            btn.setAlignment(Pos.CENTER);

            VBox mainBox = new VBox(30, width, height, btn);
            mainBox.setAlignment(Pos.CENTER);

            Scene scene = new Scene(mainBox, 300, 300);
            window.setTitle("Settings");
            window.setScene(scene);

            window.show();
        });
        //

        HBox navBar = buildNavigationBar();
        navBar.setAlignment(Pos.CENTER);

        root = new VBox(10, menu, topBanner, gradeBox, scrollPane, navBar);
        Scene scene = new Scene(root, width, height);
        stage.setTitle("Champexamen: Champs Testing App");
        stage.setScene(scene);
        stage.show();
    }

    public VBox buildTFQ(int questionNumber, TFQuestion q){
        Label prompt = new Label((questionNumber+1) + ") (True/False) " + q.getQuestionText());

        CheckBox[] answers = new CheckBox[2];

        CheckBox t = new CheckBox("True");
        answers[0] = t;

        CheckBox f = new CheckBox("False");
        answers[1] = f;

        t.setOnAction(e -> {
            answers[1].setSelected(false);
            setQuestionAnswer(questionNumber, "T");
        });
        f.setOnAction(e -> {
            answers[0].setSelected(false);
            setQuestionAnswer(questionNumber, "F");
        });

        HBox buttons = new HBox(10, t, f);

        return new VBox(10, prompt, buttons);
    }
    public VBox buildMCQ(int questionNumber, MCQuestion q){
        Label prompt = new Label((questionNumber+1) + ") " + q.getQuestionText());
        VBox mcq = new VBox(10, prompt);

        LinkedList<String> opts = ((MCQuestion) q).getOptions();
        ToggleGroup group = new ToggleGroup();

        for(int i = 0; i < opts.size(); i++) {
            RadioButton option = new RadioButton(opts.get(i));
            option.setToggleGroup(group);

            String txt = "";
            if(i == 0){
                txt = "A";
            }else if(i == 1){
                txt = "B";
            }else if(i == 2){
                txt = "C";
            }else if(i == 3){
                txt = "D";
            }else{
                txt = "E";
            }

            String finalTxt = txt;
            option.setOnAction(e -> setQuestionAnswer(questionNumber, finalTxt));
            mcq.getChildren().add(option);
        }

        return mcq;
    }

    public VBox[] createExamPage(Exam exam){
        int size = exam.getQuestions().size();
        VBox[] questionBoxes = new VBox[size];

        for(int i = 0; i<size; i++){
            Question q = exam.getQuestion(i);
            if(q.getQuestionType() == QuestionType.TFQ){
                questionBoxes[i] = buildTFQ(i, (TFQuestion) q);

            } else if(q.getQuestionType() == QuestionType.MCQ){
                questionBoxes[i] = buildMCQ(i, (MCQuestion) q);

            } else{
                Label error = new Label("Unsupported question type");
                questionBoxes[i] = new VBox(error);
            }
        }
        return questionBoxes;
    }

    public HBox buildTopBanner(){
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
    
    public HBox buildNavigationBar(){
        Button clear = new Button("Clear");
        Button save = new Button("Save");
        Button submit = new Button("Submit");

        clear.setOnAction(e -> clearExamAnswers());
        save.setOnAction(e -> saveExamAnswers());
        submit.setOnAction(new SubmitButtonHandler());

        return new HBox(10, clear, save, submit);
    }

    private void clearExamAnswers() {
        exam.getSubmittedAnswers().clear();
        for (VBox qBox : questionVBoxes) {
            qBox.getChildren().forEach(e -> {
                if (e instanceof RadioButton rb){
                    rb.setSelected(false);
                }
                if (e instanceof CheckBox cb){
                    cb.setSelected(false);
                }
            });
        }
        labelShowingGrade.setText("");
    }

    private void saveExamAnswers() {
        System.out.println("Saved Answers:");

        for (int i = 0; i < exam.getQuestions().size(); i++) {
            String ans = exam.getSubmittedAnswer(i);
            savedState.add(ans);
            System.out.println((i + 1) + ": " + (ans != null ? ans : "Not answered"));
        }
    }




    public MenuBar buildMenu(){
        Menu file = new Menu("File");
        open = new MenuItem("Open");
        save = new MenuItem("Save");
        exit = new MenuItem("Exit");

        file.getItems().addAll(open, save, exit);

        Menu edit = new Menu("Edit");
        cut = new MenuItem("Cut");
        copy = new MenuItem("Copy");
        paste = new MenuItem("Paste");
        edit.getItems().addAll(cut, copy, paste);

        Menu quiz = new Menu("Quiz");
        startQuiz = new MenuItem("Start Quiz");
        viewResults = new MenuItem("View Results");
        quiz.getItems().addAll(startQuiz, viewResults);

        Menu extras = new Menu("Extras");
        settings = new MenuItem("Settings");
        about = new MenuItem("About");
        extras.getItems().addAll(settings, about);

        Menu help = new Menu("Help");
        helpContent = new MenuItem("Help Content");
        aboutApp = new MenuItem("About App");
        help.getItems().addAll(helpContent, aboutApp);

        return new MenuBar(file, edit, quiz, extras, help);
    }

    public void setQuestionAnswer(int questionNumber, String answer){
        exam.getSubmittedAnswers().put(questionNumber,answer);
    }

    class SubmitButtonHandler implements EventHandler<ActionEvent> {
        @Override
        public void handle(ActionEvent e){
            System.out.println(exam.getSubmittedAnswers());

            int correct = 0;
            int total = exam.getQuestions().size();

            for(int i = 0; i < total; i++){
                Question q = exam.getQuestion(i);
                String submitted = exam.getSubmittedAnswer(i);
                if(submitted != null && submitted.equalsIgnoreCase(q.getCorrectAnswer())){
                    correct++;
                }
            }
            double grade = (correct / (double) total) * 100;
            String attemptNumber = String.valueOf(attempts.size() + 1);
            attempts.add("Attempt #" + attemptNumber + ", Grade: " + grade + "%");
            labelShowingGrade.setText(String.format("%.2f", grade) + "%");
        }
    }

    public static void main(String[] args) {
        launch();
    }
}
