import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

class CaesarCipher {
    private final int m = 26; // chars qty
    private Integer key; // [1, 26]

    CaesarCipher() {
    }

    CaesarCipher(Integer key) {
        this.key = key;
    }

    int getM() {
        return m;
    }

    private char[] encrypt(char[] line) {
        char[] encrypted_line = new char[line.length];
        for (int i = 0; i < line.length; i++) {
            if (Character.isUpperCase(line[i])) {
                encrypted_line[i] = (char) ((line[i] + this.key - 'A') % this.m + 'A');
            } else if (Character.isLowerCase(line[i])) {
                encrypted_line[i] = (char) ((line[i] + this.key - 'a') % this.m + 'a');
            } else {
                encrypted_line[i] = line[i];
            }
        }

        return encrypted_line;
    }

    void encryptFile(String path) throws IOException {
        if (this.key != null) {
            BufferedWriter writer = new BufferedWriter(new FileWriter(path + "/crypto.txt"));
            Scanner scanner = new Scanner(new File(path + "/plain.txt"));

            while (scanner.hasNextLine()) {
                char[] line = scanner.nextLine().toCharArray();
                char[] encrypted_line = this.encrypt(line);

                System.out.println("plain line: " + String.valueOf(line));
                System.out.println("encrypted line: " + String.valueOf(encrypted_line) + "\n");

                writer.write(encrypted_line);
                writer.write('\n');
            }

            writer.close();
            scanner.close();
        }
    }

    private char[] decrypt(char[] line) {
        char[] decrypted_line = new char[line.length];
        for (int i = 0; i < line.length; i++) {
            if (Character.isUpperCase(line[i])) {
                decrypted_line[i] = (char) ((line[i] - this.key - 'A' + this.m) % this.m + 'A');
            } else if (Character.isLowerCase(line[i])) {
                decrypted_line[i] = (char) ((line[i] - this.key - 'a' + this.m) % this.m + 'a');
            } else {
                decrypted_line[i] = line[i];
            }
        }

        return decrypted_line;
    }

    void decryptFile(String path) throws IOException {
        if (this.key != null) {
            BufferedWriter writer = new BufferedWriter(new FileWriter(path + "/decrypt.txt"));
            Scanner scanner = new Scanner(new File(path + "/crypto.txt"));

            while (scanner.hasNextLine()) {
                char[] line = scanner.nextLine().toCharArray();
                char[] decrypted_line = this.decrypt(line);

                System.out.println("encrypted line: " + String.valueOf(line));
                System.out.println("decrypted line: " + String.valueOf(decrypted_line) + "\n");

                writer.write(decrypted_line);
                writer.write('\n');
            }

            writer.close();
            scanner.close();
        }
    }

    void decryptFileExtra(String path) throws IOException {
        BufferedWriter writer = new BufferedWriter(new FileWriter(path + "/decrypt.txt"));
        BufferedWriter writerKey = new BufferedWriter(new FileWriter(path + "/key-new.txt"));
        Scanner scanner = new Scanner(new File(path + "/crypto.txt"));
        Scanner scannerExtra = new Scanner(new File(path + "/extra.txt"));
        char[] template = scannerExtra.nextLine().toCharArray();

        if (template.length > 1) {
            boolean found_key = false;
            while (scanner.hasNextLine()) {
                char[] line = scanner.nextLine().toCharArray();
                char[] decrypted_line = new char[0];

                // find the key
                if (!found_key) {
                    for (int key = 1; key < this.m; key++) {
                        this.key = key;
                        decrypted_line = this.decrypt(line);

                        if (String.valueOf(decrypted_line).contains(String.valueOf(template))) {
                            found_key = true;
                            System.out.println("Found key: " + this.key);
                            writerKey.write(this.key.toString());
                            writerKey.close();
                            writer.write(decrypted_line);
                            writer.write('\n');
                            break;
                        }
                    }
                } else {
                    decrypted_line = this.decrypt(line);
                    writer.write(decrypted_line);
                    writer.write('\n');
                }

                if (found_key) {
                    System.out.println("encrypted line: " + String.valueOf(line));
                    System.out.println("decrypted line: " + String.valueOf(decrypted_line) + "\n");
                } else {
                    System.out.println("Error: finding the key is impossible");
                    break;
                }
            }

            writer.close();
            scanner.close();
        } else System.out.println("Error: finding the key is impossible");
    }

    void decryptBruteForce(String path) throws IOException {
        BufferedWriter writer = new BufferedWriter(new FileWriter(path + "/decrypt.txt"));

        // do brute force
        for (int key = 1; key < this.m; key++) {
            this.key = key;

            Scanner scanner = new Scanner(new File(path + "/crypto.txt"));
            while (scanner.hasNextLine()) {
                char[] line = scanner.nextLine().toCharArray();
                char[] decrypted_line = this.decrypt(line);

                writer.write(decrypted_line);
                writer.write('\n');
            }

            scanner.close();
        }

        writer.close();
        System.out.println(this.m - 1 + " combinations saved");
    }
}
