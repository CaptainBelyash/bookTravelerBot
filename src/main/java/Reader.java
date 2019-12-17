import org.checkerframework.checker.units.qual.A;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

class Reader {
    private AbsPath absPath = new AbsPath();

    String readFile(String nameFile) {
        try {
            File file = new java.io.File(absPath.getAbsPath(nameFile));
            FileReader fileReader = new FileReader(file);
            BufferedReader reader = new BufferedReader(fileReader);
            String line = reader.readLine();
            StringBuilder text = new StringBuilder();
            while (line != null) {
                text.append(line).append("\n");
                line = reader.readLine();
            }
            return text.toString();
        } catch (IOException e) {
            e.printStackTrace();
            return "aaaa";
        }
    }

    private int countLines(String str) {
        String[] lines = str.split("\r\n|\r|\n");
        return lines.length;
    }

    int getCountLinesInFile() {
        return countLines(readFile("src\\main\\resources\\library.txt"));
    }

    String readFileLine(String nameFile, int n) {
        try {
            File file = new java.io.File(absPath.getAbsPath(nameFile));
            FileReader fileReader = new FileReader(file);
            BufferedReader reader = new BufferedReader(fileReader);
            int count = 1;
            String line = reader.readLine();
            while ((line != null) && (count < n)) {
                line = reader.readLine();
                count++;
            }
            reader.close();
            return line;
        } catch (IOException e) {
            e.printStackTrace();
            return "aaaa";
        }
    }

    String getCurrentBookName(int currentBook) {
        StringBuilder result = new StringBuilder();
        var name = readFileLine("src\\main\\resources\\library.txt", currentBook).toCharArray();
        var fl = false;
        for (char c : name) {
            if (fl)
                result.append(c);
            if (c == '"')
                fl = !fl;
        }
        return result.deleteCharAt(result.length() - 1).toString();
    }
}
