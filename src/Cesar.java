import java.io.*;
import java.util.Scanner;

public class Cesar {
    private static String path = System.getProperty("user.dir") + "/test_files";

    public static void main(String[] args) {
        CesarMenu cesarMenu = new CesarMenu();
        System.out.println(cesarMenu.toString());

        try {
            String cmd;
            do {
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
                cmd = bufferedReader.readLine();
                String arg1 = cmd.split(" ")[0];
                String arg2 = cmd.split(" ")[1];

                if (arg1.equals("-e") || arg2.equals("-e")) {
                    if (arg1.equals("-c") || arg2.equals("-c")) {
                        CesarCipher cesarCipher = new CesarCipher(readKey(path));
                        cesarCipher.encrypt(path);
                    } else if (arg1.equals("-a") || arg2.equals("-a")) {
                        AffineCipher affineCipher = new AffineCipher(readKey(path), readFactor(path));
                        affineCipher.encrypt(path);
                    }
                } else if (arg1.equals("-d") || arg2.equals("-d")) {

                } else if (arg1.equals("j") || arg2.equals("-j")) {

                } else if (arg1.equals("-k") || arg2.equals("-k")) {

                }
            } while (!cmd.equals("-q"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static Integer readKey(String path) throws FileNotFoundException {
        File file = new File(path + "/key.txt");
        Scanner scanner = new Scanner(file);

        try {
            return Integer.parseInt(scanner.nextLine().split(" ")[0]); // only integer
        } catch (Exception e) {
            System.out.println("Error: unrecognized key, the key must be a number");
            return null;
        }
    }

    private static Integer readFactor(String path) throws FileNotFoundException {
        File file = new File(path + "/key.txt");
        Scanner scanner = new Scanner(file);

        try {
            return Integer.parseInt(scanner.nextLine().split(" ")[1]); // only integer
        } catch (Exception e) {
            System.out.println("Error: unrecognized factor, the factor must be a number");
            return null;
        }
    }
}
