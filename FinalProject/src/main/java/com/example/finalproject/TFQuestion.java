package com.example.finalproject;

public class TFQuestion extends Question{
    // Constructors
    public TFQuestion() {
        super();
    }
    public TFQuestion(String questionText, String correctAnswer, QuestionType questionType) {
        super(questionText, correctAnswer, questionType);
    }

    // Methods
    @Override
    public String toString() {
        return "TFQuestion{" + '\n' +
                "questionText = " + super.getQuestionText() + '\n' +
                "correctAnswer = " + super.getCorrectAnswer() + '\n' +
                "questionType = " + super.getQuestionType() + '\n' +
                "}";
    }
}
