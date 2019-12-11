class Quiz {
    private String[] questions;
    private String[] answers;
    private String currentAnswer = "0";
    private int currentQuestion = 0;
    private int correctAnswers = 0;

    Quiz(String[] questions, String[] answers) {
        this.questions = questions;
        this.answers = answers;
    }

    String[] getQuestions() {
        return questions;
    }

    String[] getAnswers() {
        return answers;
    }

    String getCurrentAnswer() {
        return currentAnswer;
    }

    int getCurrentQuestion() {
        return currentQuestion;
    }

    int getCorrectAnswers() {
        return correctAnswers;
    } //getters

    void setCurrentAnswer(String currentAnswer) {
        this.currentAnswer = currentAnswer;
    }

    void nextQuestion() {
        currentQuestion++;
    }

    void addCorrectAnswers() {
        correctAnswers++;
    }
}