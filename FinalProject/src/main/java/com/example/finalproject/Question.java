package com.example.finalproject;

public class Question {
    // Data Fields
    private String questionText;
    private String correctAnswer;
    private QuestionType questionType;

    // Constructors
    public Question() {
        this.questionText = "";
        this.correctAnswer = "";
        this.questionType = QuestionType.MCQ;
    }
    public Question(String questionText, String correctAnswer, QuestionType questionType) {
        this.questionText = questionText;
        this.correctAnswer = correctAnswer;
        this.questionType = questionType;
    }

    // Mutators & Accessors
    public String getQuestionText() {
        return questionText;
    }
    public void setQuestionText(String questionText) {
        this.questionText = questionText;
    }

    public String getCorrectAnswer() {
        return correctAnswer;
    }
    public void setCorrectAnswer(String correctAnswer) {
        this.correctAnswer = correctAnswer;
    }

    public QuestionType getQuestionType() {
        return questionType;
    }
    public void setQuestionType(QuestionType questionType) {
        this.questionType = questionType;
    }

    // Methods
    @Override
    public String toString() {
        return "Question{" + '\n' +
                "questionText = " + questionText + '\n' +
                "correctAnswer = " + correctAnswer + '\n' +
                "questionType = " + questionType + '\n' +
                '}';
    }
}
