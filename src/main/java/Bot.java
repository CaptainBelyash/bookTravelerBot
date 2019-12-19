import org.telegram.telegrambots.ApiContextInitializer;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.meta.exceptions.TelegramApiRequestException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Bot extends BotPrimitive {
    private BotLogic botLogic = new BotLogic();
    private BotCommands botCommands = new BotCommands();

    public static void main(String[] args) {
        ApiContextInitializer.init();
        TelegramBotsApi telegramBotApi = new TelegramBotsApi();
        try {
            Bot bot = new Bot();
            telegramBotApi.registerBot(bot);
        } catch (TelegramApiRequestException e) {
            e.printStackTrace();
        }
    }

    public void onUpdateReceived(Update update) { //прием сообщений, получение обновлений, реализованный на лонгпул - запрос
        Message message = update.getMessage();
        if (message != null && message.hasText()) {
            try {
                processingMessage(message);
            } catch (IOException e) {
                e.printStackTrace();
                System.out.println("Ошибка в processingMessage");
            }
        }
    }

    private void processingMessage(Message message) throws IOException {
        var chatId = message.getChatId().toString();
        if (!botLogic.getUsers().containsKey(chatId)) {
            botLogic.getUsers().put(chatId, createUser());
        }
        var currentCommands = botLogic.getUserCommands(chatId, this);
        var userData = botLogic.getUserData(chatId, this);
        if (userData.getFlEcho()) {
            echo(message);
            userData.setFlEcho(false);
        } else if (userData.getFlChoose()) {
            chooseBook(message);
            userData.setFlChoose(false);
        } else if(userData.getFlNote()) {
            addNote(message);
            userData.setFlNote(false);
        } else if (userData.getFlSearch()){
            search(message);
            userData.setFlSearch(false);
        } else if (currentCommands.containsKey(message.getText())) {
            MyFunc func = currentCommands.get(message.getText());
            func.func(message);
        } else
            sendMsg(message, "Неверная команда, попробуй еще раз.");
    }

    private void sendMsg(Message message, String text) {
        if (text.equals(""))
            return;
        SendMessage sendMessage = getSendMessage(message, text);
        try {
            addButtons(sendMessage, message);
            execute(sendMessage);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    private void addButtons(SendMessage sendMessage, Message message) {
        var currentCommands = botLogic.getUserCommands(message.getChatId().toString(), this);
        var replyKeyboardMarkup = getReplyKeyboardMarkup();//инициализация клавиатуры
        sendMessage.setReplyMarkup(replyKeyboardMarkup);
        List<KeyboardRow> keyboardRowList = new ArrayList<>();
        KeyboardRow keyboardFirstRow = new KeyboardRow();
        int count = 0;
        for (String command : currentCommands.keySet()) {
            keyboardFirstRow.add(new KeyboardButton(command));
            count++;
            if (count == 3) {
                keyboardRowList.add(keyboardFirstRow);
                keyboardFirstRow = new KeyboardRow();
                count = 0;
            }
        }
        keyboardRowList.add(keyboardFirstRow);
        replyKeyboardMarkup.setKeyboard(keyboardRowList);
    }

    private void printFile(String nameFile, Message message) {
        sendMsg(message, botLogic.getReader().readFile(nameFile));
    }

    UserData createUser() {
        var data = new UserData();
        data.setCurrentCommands(createPrimitiveCommands());
        return data;
    }

    ////////////////
    private HashMap<String, MyFunc> createPrimitiveCommands() {
        HashMap<String, MyFunc> commands = new HashMap<>();
        commands.put("?", (this::help));
        commands.put("эхо", (this::echo));
        commands.put("авторы", (this::authors));
        commands.put("дата", (this::printDate));
        commands.put("библиотека", (this::library));
        return commands;
    }

    private HashMap<String, MyFunc> createLibraryCommands() {
        HashMap<String, MyFunc> commands = new HashMap<>();
        commands.put("?", (this::help));
        commands.put("главное меню", (this::exitToMain));
        commands.put("выбрать книгу", (message -> {
            sendMsg(message, "Введите номер книги");
            chooseBook(message);
        }));
        return commands;
    }

    private HashMap<String, MyFunc> createCurrentBookCommands() {
        HashMap<String, MyFunc> commands = new HashMap<>();
        commands.put("?", (this::help));
        commands.put("библиотека", (this::library));
        commands.put("автор", (message -> {
            try {
                getInfoAbAuthor(message);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }));
        commands.put("краткое", (message -> {
            try {
                getThumbnailSketch(message);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }));
        commands.put("ᐅ", (this::readNext));
        commands.put("викторина", (this::runQuiz));
        commands.put("поиск", (this::search));
        return commands;
    }

    private HashMap<String, MyFunc> createQuizCommands() {
        HashMap<String, MyFunc> commands = new HashMap<>();
        commands.put("?", (this::help));
        commands.put("книга", (this::returnBook));
        commands.put("1", (message -> checkAnswer(message, "1")));
        commands.put("2", (message -> checkAnswer(message, "2")));
        commands.put("3", (message -> checkAnswer(message, "3")));
        return commands;
    }

    private HashMap<String, MyFunc> createReadCommand() {
        HashMap<String, MyFunc> commands = new HashMap<>();
        commands.put("?", (this::help));
        commands.put("книга", (this::returnBook));
        commands.put("ᐅ", (this::readNext));
        commands.put("добавить закладку", (this::note));
        commands.put("посмотреть текущие закладки", (this::getMarks));
        return commands;
    }
    ///////////////
    private void help(Message message) {
        sendMsg(message, botCommands.help(message, botLogic, this));
    } // сделать функции перехода. не сейчас

    private void authors(Message message) {
        printFile("src\\main\\resources\\authors.txt", message);
    }

    private void echo(Message message) {
        var userData = botLogic.getUserData(message.getChatId().toString(), this);
        if (userData.getFlEcho())
            sendMsg(message, message.getText());
        userData.setFlEcho(true);
    }

    private void printDate(Message message) {
        sendMsg(message, botCommands.getDate());
    }

    private void library(Message message) {
        sendMsg(message, botCommands.library(message, botLogic, this));
        botLogic.getUserData(message.getChatId().toString(), this).setCurrentCommands(createLibraryCommands());
        sendMsg(message, "Вы в библиотеке.");
        printFile("src\\main\\resources\\library.txt", message);
    }

    private void checkAnswer(Message message, String answer) throws IOException {
        var userData = botLogic.getUserData(message.getChatId().toString(), this);
        userData.getCurrentQuiz().setCurrentAnswer(answer);
        runQuiz(message);
    }

    private void runQuiz(Message message) throws IOException {
        var userData = botLogic.getUserData(message.getChatId().toString(), this);
        if (userData.getState().getCurrentState() == State.state.Quiz) {
            var quantity = userData.getCurrentQuiz().getAnswers().length;
            var n = userData.getCurrentQuiz().getCurrentQuestion();
            var correctAnswer = userData.getCurrentQuiz().getAnswers()[n];
            if (userData.getCurrentQuiz().getCurrentAnswer().equals(correctAnswer)) {
                sendMsg(message, "Верно");
                userData.getCurrentQuiz().addCorrectAnswers();
            } else
                sendMsg(message, "Неверно");
            userData.getCurrentQuiz().nextQuestion();
            if (userData.getCurrentQuiz().getCurrentQuestion() < userData.getCurrentQuiz().getAnswers().length)
                sendMsg(message, botCommands.getNextQuestion(userData, quantity));
            else
                library(message);
        } else {
            userData.setCurrentCommands(createQuizCommands());
            sendMsg(message, botCommands.handleFirstEntry(userData, botLogic, message.getChatId().toString()));
        }
    }

    private void exitToMain(Message message) {
        var userData = botLogic.getUserData(message.getChatId().toString(), this);
        userData.getState().setCurrentState(State.state.Main);
        userData.setCurrentCommands(createPrimitiveCommands());
        sendMsg(message, "Вы вышли в главное меню.");
    }

    private void chooseBook(Message message) {
        var userData = botLogic.getUserData(message.getChatId().toString(), this);
        var text = botCommands.chooseBook(message, botLogic, userData);
        if (text.equals("Неправильно выбран номер книги"))
            userData.setCurrentCommands(createLibraryCommands());
        else
            userData.setCurrentCommands(createCurrentBookCommands());
        sendMsg(message, text);
    }

    private void getInfoAbAuthor(Message message) throws Exception {
        sendMsg(message, botCommands.getInfoAbAuthor(message.getChatId().toString(), botLogic, this));
    }

    private void getThumbnailSketch(Message message) throws Exception {
        String[] lines = botCommands.getThumbnailSketch(message.getChatId().toString(), botLogic, this);
        for (String line : lines) {
            sendMsg(message, line);
        }
    }//commandsWithSendMessage

    private void readNext(Message message) {
        var userData = botLogic.getUserData(message.getChatId().toString(), this);
        var pos = userData.getCurrentPosition();
        botLogic.getUserData(message.getChatId().toString(), this).setCurrentCommands(createReadCommand());
        sendMsg(message, botCommands.nextRead(userData, pos));
        userData.setCurrentPosition(pos + 1);
    }

    private void returnBook(Message message) {
        var userData = botLogic.getUserData(message.getChatId().toString(), this);
        userData.getState().setCurrentState(State.state.Book);
        userData.setCurrentCommands(createCurrentBookCommands());
        sendMsg(message, "Вы вернулись в раздел книги");
    }

    private void note(Message message) {
        var userData = botLogic.getUserData(message.getChatId().toString(), this);
        userData.getState().setCurrentState(State.state.Note);
        sendMsg(message, "Вы в режиме создания закладки. Ввод в формате: {Число} {закладка}. Главное оставьте пробел между ними");
        addNote(message);
    }

    private void addNote(Message message) {
        var userData = botLogic.getUserData(message.getChatId().toString(), this);
        var text = botCommands.addNote(message, userData);
        sendMsg(message, text);
    }

    private void getMarks(Message message) {
        var userData = botLogic.getUserData(message.getChatId().toString(), this);
        sendMsg(message, botCommands.getMarks(userData));
    }

    private void search(Message message){
        var userData = botLogic.getUserData(message.getChatId().toString(), this);
        userData.getState().setCurrentState(State.state.Search);
        sendMsg(message, "Вы в режиме поиска. Введите фразу");
        searchText(message, userData);
    }

    private void searchText(Message message, UserData userData) {
        var search_answer = botCommands.search(message.getText(), userData);
        for (var answer:search_answer) {
            sendMsg(message, answer);
        }
    }
}