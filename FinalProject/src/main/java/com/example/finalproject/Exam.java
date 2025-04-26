package com.example.finalproject;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.ListIterator;

public class Exam {
    // Data Fields
    protected HashMap<Integer, Question> questions;
    protected HashMap<Integer, String> submittedAnswers;

    // Constructors
    public Exam(){
        this.questions = new HashMap<>();
        this.submittedAnswers = new HashMap<>();
    }
    public Exam(HashMap<Integer, Question> questions, HashMap<Integer, String> submittedAnswers) {
        this.questions = new HashMap<>();
        this.questions.putAll(questions);

        this.submittedAnswers = new HashMap<>();
        this.submittedAnswers.putAll(submittedAnswers);
    }
    public Exam(LinkedList<Question> qList){
        this.questions = new HashMap<>();
        this.submittedAnswers = new HashMap<>();

        ListIterator<Question> iterator = qList.listIterator();
        int i = 0;
        while(iterator.hasNext()){
            this.questions.put(i, iterator.next());
        }
    }

    // Mutators & Accessors
    public HashMap<Integer, Question> getQuestions() {
        return questions;
    }
    public void setQuestions(HashMap<Integer, Question> questions) {
        this.questions = new HashMap<>();
        this.questions.putAll(questions);
    }

    public HashMap<Integer, String> getSubmittedAnswers() {
        return submittedAnswers;
    }
    public void setSubmittedAnswers(HashMap<Integer, String> submittedAnswers) {
        this.submittedAnswers = new HashMap<>();
        this.submittedAnswers.putAll(submittedAnswers);
    }

    // Methods
    public void clearQuestions(){
        this.getQuestions().clear();
    }

    public Question getQuestion(int i){
        return this.getQuestions().get(i);
    }
    public String getSubmittedAnswer(int i){
        return this.getSubmittedAnswers().get(i);
    }

    public void printAllQuestions(){
        for(int i = 0; i < this.getQuestions().size();i++){
            Question q = this.getQuestions().get(i);
            System.out.println(q);
        }
    }
}
