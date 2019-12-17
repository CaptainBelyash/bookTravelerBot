import java.nio.file.FileSystems;
import java.nio.file.Path;

public class AbsPath {
    private Path path = FileSystems.getDefault().getPath("").toAbsolutePath();

    public String getAbsPath(String path){
        return this.path.toString() + path;
    }
}
