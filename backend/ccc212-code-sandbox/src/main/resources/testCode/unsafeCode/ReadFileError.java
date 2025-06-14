import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
public class Main {

    public static void main(String[] args) throws InterruptedException, IOException {
        String userDir = System.getProperty("user.dir");
        List<String> allLines = Files.readAllLines(Paths.get(userDir));
        System.out.println(String.join("\n", allLines));
    }
}