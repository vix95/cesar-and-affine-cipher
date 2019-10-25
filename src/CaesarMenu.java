import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class CaesarMenu {
    CaesarMenu() {
    }

    String readInput() throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
        String cmd = bufferedReader.readLine();
        if (cmd.equals("-q")) return cmd;
        else if (cmd.split(" ").length != 2) {
            System.out.println("Wrong number of args, need only 2.");
            return null;
        }

        return cmd;
    }

    void printWrongKey() {
        System.out.println("Error: unrecognized key, the key must be a number");
    }

    void printWrongFactor() {
        System.out.println("Error: unrecognized factor, the factor must be a number");
    }

    @Override
    public String toString() {
        return "-c (szyfr Cezara)\n" +
                "-a (szyfr afiniczny)\n" +
                "-e (szyfrowanie)\n" +
                "-d (odszyfrowywanie)\n" +
                "-j (kryptoanaliza z tekstem jawnym)\n" +
                "-k (kryptoanaliza wylacznie w oparciu o kryptogram)";
    }
}
