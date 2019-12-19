import org.o7planning.googledrive.quickstart.GoogleDrive;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.ArrayList;
import java.util.HashMap;

class BotLogic {
    private HashMap<String, UserData> users = new HashMap<>();
    private Reader reader = new Reader();
    private GoogleDrive googleDrive;

    {
        try {
            googleDrive = new GoogleDrive();
        } catch (IOException | GeneralSecurityException e) {
            e.printStackTrace();
        }
    }

    HashMap<String, UserData> getUsers() {
        return users;
    }

    Reader getReader() {
        return reader;
    }

    void setCurrentParagraphsList(UserData userData) {
        try {
            var bookName = reader.getCurrentBookName(userData.getCurrentBook());
            System.out.println(bookName);
            var out = googleDrive.getParagraphsList(googleDrive.getTextByGoogleDisk(googleDrive.getDrive(), bookName));
            userData.setCurrentParagraphsList(out);
            //System.out.println(out);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    State getUserState(String chatId, Bot bot) {
        return users.computeIfAbsent(chatId, chatId1 -> bot.createUser()).getState();
    }

    HashMap<String, BotPrimitive.MyFunc> getUserCommands(String chatId, Bot bot) {
        return users.computeIfAbsent(chatId, chatId1 -> bot.createUser()).getCurrentCommands();
    }

    UserData getUserData(String chatId, Bot bot) {
        return users.computeIfAbsent(chatId, chatId1 -> bot.createUser());
    }

    void createQuiz(String chatId) throws IOException {
        var userData = users.get(chatId);
        var name = reader.getCurrentBookName(userData.getCurrentBook());
        var answer = reader.readFile("src\\main\\resources\\quizs\\Answers\\" + name + ".Answers.txt").split(";\n");
        //var arAnswers = googleDrive.getTextByGoogleDisk(googleDrive.getDrive(), name + ".Answers").split("\n");
        var arQuestions = googleDrive.getTextByGoogleDisk(googleDrive.getDrive(), name + ".Questions").split(";");
        userData.setCurrentQuiz(new Quiz(arQuestions, answer));
    }
}
