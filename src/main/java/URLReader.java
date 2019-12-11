import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;


public class URLReader {
    enum InfoAbout {
        Author,
        ThumbnailSketchBook,
    }

    public static void main(String[] args) {
        //System.out.println(GetInfo("https://ru.wikipedia.org/wiki/Java", InfoAbout.Author));
        //System.out.println(GetInfo("https://ru.wikipedia.org/wiki/Виноваты_звёзды_(роман)", InfoAbout.ThumbnailSketchBook));
        //System.out.println(GetThumbnailSketch("https://litlife.club/books/174926/read?page=7", "</div>", "<script src", "</script>", "<a id="));
    }

    static String GetThumbnailSketch(String site, String preBegin, String begin, String end) throws Exception {
        URL website = new URL(site);
        BufferedReader in = new BufferedReader(
                new InputStreamReader(website.openStream()));
        String inputLine;
        StringBuilder text = new StringBuilder();
        boolean fl = false;
        boolean flEnd = false;
        boolean flBegin = false;
        while ((inputLine = in.readLine()) != null) {
            if ((flEnd) && (inputLine.contains(end)))
                break;
            else
                flEnd = false;
            if ((inputLine.contains("</p")) && fl) {
                flEnd = true;
            }
            if (fl) {
                text.append(inputLine);
            }
            if (flBegin && inputLine.contains(begin)) {
                text.append(inputLine);
                fl = true;
                flBegin = false;
                continue;
            } else
                flBegin = false;
            if (inputLine.contains(preBegin))
                flBegin = true;
        }
        in.close();
        return text.toString();
    } // необработанный абзац с javascript конструкцией

    static String ProcessText(String text) {
        if (text.length() == 0)
            return "";
        StringBuilder newText = new StringBuilder();
        boolean flSkip = false;
        boolean flJ = false; //флаг на символ &(случай &#91; = '[')
        int flDiv = 0;
        var textArray = text.toCharArray();
        for (int i = 0; i < text.length(); i++) {
            if ((textArray[i] == ' ') && (textArray[i + 1] == '<') && (textArray[i + 2] == '/'))
                continue;
            if (textArray[i] == '>') {
                flSkip = false;
                continue;
            }
            if (textArray[i] == '<') {
                if ((textArray[i + 1] == '/') && (textArray[i + 2] == 'p'))
                    newText.append('\n');
                flSkip = true;
                continue;
            }
            if ((textArray[i] == 'd') && (textArray[i + 1] == 'i') && (textArray[i + 2] == 'v')) {
                if (textArray[i - 1] == '/')
                    flDiv--;
                else
                    flDiv++;
                i += 3;
                continue;
            }
            if ((textArray[i] == '&') && (textArray[i + 1] == '#')) {
                if (flJ) {
                    i += 4;
                    flJ = false;
                    continue;
                }
                if (textArray[i + 3] == ';') {
                    i += 3;
                    continue;
                }
                if (textArray[i + 4] == ';') {
                    i += 4;
                    flJ = true;
                    continue;
                }
                if (textArray[i + 5] == ';') {
                    newText.append(' ');
                    i += 5;
                    continue;
                }
                continue;
            }
            if ((flJ) || (flDiv > 0))
                continue;
            if (!(flSkip))
                newText.append(textArray[i]);
        }
        return newText.toString();
    }

    static String GetInfo(String site, InfoAbout info) throws Exception {
        if (site == null)
            site = "https://ru.wikipedia.org/wiki/Java";
        if (info == InfoAbout.ThumbnailSketchBook) {
            return ProcessText(GetThumbnailSketch(site, "</h2", "<p", "<h2"));
        }
        if (info == InfoAbout.Author) {
            return ProcessText(GetThumbnailSketch(site, "</tabl", "<p>", "<div"));
        }
        return ""; // читать книгу
    }
}