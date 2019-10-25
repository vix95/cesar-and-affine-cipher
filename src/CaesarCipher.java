import java.io.*;
import java.util.Arrays;
import java.util.Scanner;

class CaesarCipher {
    private final int m = 26; // chars qty
    private Integer key;

    CaesarCipher(Integer key) {
        this.key = key;
    }

    void encrypt(String path) throws IOException {
        BufferedWriter writer = new BufferedWriter(new FileWriter(path + "/crypto.txt"));
        File file = new File(path + "/plain.txt");
        Scanner scanner = new Scanner(file);

        while (scanner.hasNextLine()) {
            char[] line = scanner.nextLine().toCharArray();
            char[] encrypted_line = new char[line.length];

            for (int i = 0; i < line.length; i++) {
                if (Character.isUpperCase(line[i])) {
                    encrypted_line[i] = (char) ((line[i] + key - 'A') % m + 'A');
                } else if (Character.isLowerCase(line[i])) {
                    encrypted_line[i] = (char) ((line[i] + key - 'a') % m + 'a');
                } else {
                    encrypted_line[i] = line[i];
                }
            }

            System.out.println("plain line: " + String.valueOf(line));
            System.out.println("encrypted line: " + String.valueOf(encrypted_line));

            writer.write(encrypted_line);
            writer.close();
        }

        scanner.close();
    }

    void decrypt(String path) throws IOException {
        BufferedWriter writer = new BufferedWriter(new FileWriter(path + "/decrypt.txt"));
        File file = new File(path + "/crypto.txt");
        Scanner scanner = new Scanner(file);

        while (scanner.hasNextLine()) {
            char[] line = scanner.nextLine().toCharArray();
            char[] decrypted_line = new char[line.length];

            for (int i = 0; i < line.length; i++) {
                if (Character.isUpperCase(line[i])) {
                    decrypted_line[i] = (char) ((line[i] - key - 'A' + m) % 26 + 'A');
                } else if (Character.isLowerCase(line[i])) {
                    decrypted_line[i] = (char) ((line[i] - key - 'a' + m) % 26 + 'a');
                } else {
                    decrypted_line[i] = line[i];
                }
            }

            System.out.println("encrypted line: " + String.valueOf(line));
            System.out.println("decrypted line: " + String.valueOf(decrypted_line));

            writer.write(decrypted_line);
            writer.close();
        }

        scanner.close();
    }
}
