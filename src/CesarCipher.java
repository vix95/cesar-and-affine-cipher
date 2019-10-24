import java.io.*;
import java.util.Scanner;

class CesarCipher {
    private Integer key;

    CesarCipher(Integer key) {
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
                    encrypted_line[i] = (char) ((line[i] + key - 65) % 26 + 65);
                } else if (Character.isLowerCase(line[i])) {
                    encrypted_line[i] = (char) ((line[i] + key - 97) % 26 + 97);
                } else {
                    encrypted_line[i] = line[i];
                }
            }

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
            char[] encrypted_line = new char[line.length];

            for (int i = 0; i < line.length; i++) {
                if (Character.isUpperCase(line[i])) {
                    encrypted_line[i] = (char) ((line[i] - key - 65) % 26 + 65);
                } else if (Character.isLowerCase(line[i])) {
                    encrypted_line[i] = (char) ((line[i] - key - 97) % 26 + 97);
                } else {
                    encrypted_line[i] = line[i];
                }
            }

            writer.write(encrypted_line);
            writer.close();
        }

        scanner.close();
    }
}
