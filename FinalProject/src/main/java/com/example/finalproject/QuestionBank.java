package com.example.finalproject;

import java.io.File;
import java.io.FileNotFoundException;
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
        ListIterator<Question> iterator = this.getQuestions().listIterator();
        while(iterator.hasNext()){
            Question q = (Question) iterator.next();
            System.out.println(q);;
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
                this.getQuestions().add(myQuestion);
            }

        }catch(FileNotFoundException ex){
            System.out.println("ERROR - File " + fname + " Not Found");
        }
    }
    public void readTFQ(String fname){
        try{
            File file = new File(fname);
            Scanner reader = new Scanner(file);


            while(reader.hasNext()){
                String line = reader.nextLine().trim();
                String questionText = line.substring(3).trim();

                line = reader.nextLine().trim();
                String correctAnswer = line.substring(4).trim();

                TFQuestion myQuestion = new TFQuestion(questionText, correctAnswer, QuestionType.TFQ);
                this.getQuestions().add(myQuestion);
            }

        }catch(FileNotFoundException ex){
            System.out.println("ERROR - File " + fname + " Not Found");
        }
    }

    public LinkedList<Question> selectRandQuestions(int[] indices){
        LinkedList<Question> list = new LinkedList<>();
        for(int i = 0; i < indices.length; i++){
            list.add(this.getQuestions().get(indices[i]));
        }

        return list;
    }
}
