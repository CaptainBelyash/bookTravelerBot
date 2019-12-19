import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;

class UserData {
    private State state = new State();
    private LinkedHashMap<String, BotPrimitive.MyFunc> currentCommands;
    private Boolean flChoose = false;
    private Boolean flNote = false;
    private Boolean flSearch = false;
    private Boolean flEcho = false;
    private int currentBook = 0;
    private ArrayList<String> currentParagraphsList = new ArrayList<>();
    private Quiz currentQuiz;
    private HashMap<Integer, HashMap<Integer, ArrayList<String>>> bookmarks = new HashMap<>();
    private HashMap<Integer, Integer> library = new HashMap<>();
    private ArrayList<String> searchResult = new ArrayList<>();
    private int countResult = 1;

    State getState() {
        return state;
    }

    HashMap<String, BotPrimitive.MyFunc> getCurrentCommands() {
        return currentCommands;
    }

    Boolean getFlChoose() {
        return flChoose;
    }

    Boolean getFlNote() {
        return flNote;
    }

    Boolean getFlSearch() {
        return flSearch;
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
        if(library.containsKey(currentBook))
            return library.get(currentBook);
        library.put(currentBook, 0);
        return 0;
    }

    Quiz getCurrentQuiz() {
        return currentQuiz;
    }

    HashMap<Integer, HashMap<Integer, ArrayList<String>>> getBookmarks() {
        return bookmarks;
    }

    ArrayList<String> getSearchResult() {
        return searchResult;
    }

    int getCountResult() {
        return countResult;
    }

    //getters

    void setCurrentCommands(LinkedHashMap<String, BotPrimitive.MyFunc> currentCommands) {
        this.currentCommands = currentCommands;
    }
    void setFlChoose(Boolean flChoose) {
        this.flChoose = flChoose;
    }

    void setFlNote(Boolean flNote) {
        this.flNote = flNote;
    }

    void setFlEcho(Boolean flEcho) {
        this.flEcho = flEcho;
    }

    void setCurrentBook(int currentBook) {
        this.currentBook = currentBook;
        if (!library.containsKey(currentBook))
            library.put(currentBook, 0);
    }

    void setCurrentParagraphsList(ArrayList<String> currentParagraphsList) {
        this.currentParagraphsList = currentParagraphsList;
    }

    void setCurrentPosition(int currentPosition) {
        library.put(currentBook, currentPosition);
    }

    void setFlSearch(Boolean flSearch) {
        this.flSearch = flSearch;
    }

    void setCurrentQuiz(Quiz quiz) {
        this.currentQuiz = quiz;
    }

    void setSearchResult(ArrayList<String> searchResult) {
        this.searchResult = searchResult;
    }

    //setters

    void addBookmark(String note, Integer par){
        if (!bookmarks.containsKey(currentBook)){
            var newArMarks = new ArrayList<String>();
            newArMarks.add(note);
            HashMap<Integer, ArrayList<String>> newBookMarks = new HashMap<>();
            newBookMarks.put(par, newArMarks);
            bookmarks.put(currentBook, newBookMarks);
        }
        else if (bookmarks.get(currentBook).containsKey(par)){
            bookmarks.get(currentBook).get(par).add(note);
        }
        else {
            var newArMarks = new ArrayList<String>();
            newArMarks.add(note);
            bookmarks.get(currentBook).put(par, newArMarks);
        }
    }

    void addCountResult(){
        countResult++;
    }

    void resetStartCountResult(){
        countResult = 1;
    }
}
