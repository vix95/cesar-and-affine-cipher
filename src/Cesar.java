import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Cesar {
    private static String path = System.getProperty("user.dir") + "/test_files";
    private static String key_file = "key.txt";

    public static void main(String[] args) {
        CesarMenu cesarMenu = new CesarMenu();
        System.out.println(cesarMenu.toString());
        CesarCipher cesarCipher;

        try {
            cesarCipher = new CesarCipher(readKey(path, key_file));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    private static int readKey(String path, String filename) throws FileNotFoundException {
        int key = 1;
        File file = new File(path + '/' + filename);
        Scanner scanner = new Scanner(file);
        String string = scanner.nextLine();
        System.out.println(string);

        return key;
    }
}
