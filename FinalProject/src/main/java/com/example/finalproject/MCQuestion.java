package com.example.finalproject;

import java.util.LinkedList;

public class MCQuestion extends Question{
    // Data Fields
    private LinkedList<String> options;

    // Constructors
    public MCQuestion() {
        super();
    }
    public MCQuestion(String questionText, String correctAnswer, QuestionType questionType, LinkedList<String> options) {
        super(questionText, correctAnswer, questionType);
        this.options = new LinkedList<>();
        this.options.addAll(options);
    }

    // Mutators & Accessors
    public LinkedList<String> getOptions() {
        return options;
    }
    public void setOptions(LinkedList<String> options) {
        this.options = new LinkedList<>();
        this.options.addAll(options);
    }

    // Methods
    @Override
    public String toString() {
        return "MCQuestion{" + '\n' +
                "questionText = " + super.getQuestionText() + '\n' +
                "correctAnswer = " + super.getCorrectAnswer() + '\n' +
                "questionType = " + super.getQuestionType() + '\n' +
                "options = " + options +
                '}';
    }
}
