import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

class AffineCipher {
    private Integer key;
    private Integer factor;

    AffineCipher(Integer key, Integer factor) {
        this.key = key;
        this.factor = factor;
    }

    // e(x) = (ax + b) mod m
    void encrypt(String path) throws IOException {
        BufferedWriter writer = new BufferedWriter(new FileWriter(path + "/crypto.txt"));
        File file = new File(path + "/plain.txt");
        Scanner scanner = new Scanner(file);

        while (scanner.hasNextLine()) {
            char[] line = scanner.nextLine().toCharArray();
            char[] encrypted_line = new char[line.length];

            for (int i = 0; i < line.length; i++) {
                if (Character.isUpperCase(line[i])) {
                    encrypted_line[i] = (char) ((key * (line[i] - 65) + factor) % 26 + 65);
                } else if (Character.isLowerCase(line[i])) {
                    encrypted_line[i] = (char) ((key * (line[i] - 97) + factor) % 26 + 97);
                } else {
                    encrypted_line[i] = line[i];
                }
            }

            writer.write(encrypted_line);
            writer.close();
        }

        scanner.close();
    }

    // e(x) = a^(-1) * (x - b) mod m
    void decrypt(String path) {

    }
}
