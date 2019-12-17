import java.util.ArrayList;
import java.util.HashMap;

class UserData {
    private State state = new State();
    private HashMap<String, BotPrimitive.MyFunc> currentCommands;
    private Boolean flChoose = false;
    private Boolean flEcho = false;
    private int currentBook = 0;
    private ArrayList<String> currentParagraphsList = new ArrayList<>();
    private int currentPosition = 0;
    private Quiz currentQuiz;
    private HashMap<Integer, Integer> libraryUser;

    State getState() {
        return state;
    }

    HashMap<String, BotPrimitive.MyFunc> getCurrentCommands() {
        return currentCommands;
    }

    Boolean getFlChoose() {
        return flChoose;
    }

    Boolean getFlEcho() {
        return flEcho;
    }

    int getCurrentBook() {
        return currentBook;
    }

    ArrayList<String> getCurrentParagraphsList() {
        return currentParagraphsList;
    }

    int getCurrentPosition() {
        return currentPosition;
    }

    Quiz getCurrentQuiz() {
        return currentQuiz;
    }

    public HashMap<Integer, Integer> getLibraryUser() {
        return libraryUser;
    } //getters

    void setCurrentCommands(HashMap<String, BotPrimitive.MyFunc> currentCommands) {
        this.currentCommands = currentCommands;
    }
    void setFlChoose(Boolean flChoose) {
        this.flChoose = flChoose;
    }

    void setFlEcho(Boolean flEcho) {
        this.flEcho = flEcho;
    }

    void setCurrentBook(int currentBook) {
        this.currentBook = currentBook;
    }

    void setCurrentParagraphsList(ArrayList<String> currentParagraphsList) {
        this.currentParagraphsList = currentParagraphsList;
    }

    void setCurrentPosition(int currentPosition) {
        this.currentPosition = currentPosition;
    }

    void setCurrentQuiz(Quiz quiz) {
        this.currentQuiz = quiz;
    }

    public void setLibraryUser(HashMap<Integer, Integer> libraryUser) {
        this.libraryUser = libraryUser;
    }//setters

    public void addBook(int book, int paragraph){
        libraryUser.put(book, paragraph);
    }
}
