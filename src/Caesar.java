import java.io.*;
import java.util.Scanner;

// unrecognized key and factor doesn't have impact on files
// files will keep previous values
public class Caesar {
    private static String path = System.getProperty("user.dir") + "/test_files";
    private static CaesarMenu caesarMenu;

    public static void main(String[] args) {
        caesarMenu = new CaesarMenu();
        System.out.println(caesarMenu.toString());

        try {
            String cmd;
            do {
                cmd = caesarMenu.readInput();
                if (cmd != null && !cmd.equals("-q")) {
                    String arg1 = cmd.split(" ")[0];
                    String arg2 = cmd.split(" ")[1];

                    if (arg1.equals("-e") || arg2.equals("-e")) {
                        if (arg1.equals("-c") || arg2.equals("-c")) {
                            CaesarCipher caesarCipher = new CaesarCipher(readKey(path, "-c"));
                            caesarCipher.encryptFile(path);
                        } else if (arg1.equals("-a") || arg2.equals("-a")) {
                            AffineCipher affineCipher = new AffineCipher(readKey(path, "-a"), readFactor(path));
                            affineCipher.encryptFile(path);
                        }
                    } else if (arg1.equals("-d") || arg2.equals("-d")) {
                        if (arg1.equals("-c") || arg2.equals("-c")) {
                            CaesarCipher caesarCipher = new CaesarCipher(readKey(path, "-c"));
                            caesarCipher.decryptFile(path);
                        } else if (arg1.equals("-a") || arg2.equals("-a")) {
                            AffineCipher affineCipher = new AffineCipher(readKey(path, "-a"), readFactor(path));
                            affineCipher.decryptFile(path);
                        }
                    } else if (arg1.equals("-j") || arg2.equals("-j")) {
                        if (arg1.equals("-c") || arg2.equals("-c")) {
                            CaesarCipher caesarCipher = new CaesarCipher();
                            caesarCipher.decryptFileExtra(path);
                        } else if (arg1.equals("-a") || arg2.equals("-a")) {
                            AffineCipher affineCipher = new AffineCipher();
                            affineCipher.decryptFileExtra(path);
                        }
                    } else if (arg1.equals("-k") || arg2.equals("-k")) {
                        if (arg1.equals("-c") || arg2.equals("-c")) {
                            CaesarCipher caesarCipher = new CaesarCipher();
                            caesarCipher.decryptBruteForce(path);
                        } else if (arg1.equals("-a") || arg2.equals("-a")) {
                            AffineCipher affineCipher = new AffineCipher();
                            affineCipher.decryptBruteForce(path);
                        }
                    }
                }
            } while (cmd == null || !cmd.equals("-q"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static Integer readKey(String path, String cipher) throws FileNotFoundException {
        Scanner scanner = new Scanner(new File(path + "/key.txt"));

        try {
            int key = Integer.parseInt(scanner.nextLine().split(" ")[0]);
            if (key < 1) {
                caesarMenu.printWrongKey();
                return null;
            } else if (cipher.equals("-c") && key > new CaesarCipher().getM() - 1) {
                caesarMenu.printWrongKey();
                return null;
            } else if (cipher.equals("-a") && key > new AffineCipher().getM() - 1) {
                caesarMenu.printWrongKey();
                return null;
            } else return key; // only positive integer
        } catch (Exception e) {
            caesarMenu.printWrongKey();
            return null;
        }
    }

    private static Integer readFactor(String path) throws FileNotFoundException {
        Scanner scanner = new Scanner(new File(path + "/key.txt"));

        try {
            int factor = Integer.parseInt(scanner.nextLine().split(" ")[1]);
            if (factor < 0) {
                caesarMenu.printWrongFactor();
                return null;
            } else return factor; // only positive integer
        } catch (Exception e) {
            caesarMenu.printWrongFactor();
            return null;
        }
    }
}
