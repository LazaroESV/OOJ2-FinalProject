package com.example.finalproject;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.ListIterator;
import java.util.Scanner;

public class QuestionBank {
    // Data Fields
    protected LinkedList<Question> questions;

    // Constructors
    public QuestionBank() {
        this.questions = new LinkedList<>();
    }
    public QuestionBank(LinkedList<Question> questions) {
        this.questions = new LinkedList<>();
        this.questions.addAll(questions);
    }

    // Mutators & Accessors
    public LinkedList<Question> getQuestions() {
        return questions;
    }
    public void setQuestions(LinkedList<Question> questions) {
        this.questions = new LinkedList<>();
        this.questions.addAll(questions);
    }

    // Methods
    public void clearQuestions(){
        this.getQuestions().clear();
    }

    public Question getQuestion(int i){
        return this.getQuestions().get(i);
    }

    public void printAllQuestions(){
        ListIterator iterator = this.getQuestions().listIterator();
        while(iterator.hasNext()){
            Question q = (Question) iterator.next();
            q.toString();
        }
    }

    public void readMCQ(String fname){
        try{
            File file = new File(fname);
            Scanner reader = new Scanner(file);


            while(reader.hasNext()){
                String line = reader.nextLine().trim();
                String questionText = line.substring(3).trim();

                LinkedList<String> options = new LinkedList<>();
                String option = "";
                String opt = "";
                do{
                    line = reader.nextLine().trim();
                    opt = line.substring(0,3);
                    if(!opt.equals("ANS")){
                        option = line.substring(2).trim();
                        options.add(option);
                    }
                }while(!opt.equals("ANS"));
                String correctAnswer = line.substring(4).trim();

                MCQuestion myQuestion = new MCQuestion(questionText, correctAnswer, QuestionType.MCQ, options);
                System.out.println(myQuestion);
                //this.getQuestions().add(myQuestion);
            }

        }catch(FileNotFoundException ex){
            System.out.println("ERROR - File " + fname + " Not Found");
        }
    }
}
