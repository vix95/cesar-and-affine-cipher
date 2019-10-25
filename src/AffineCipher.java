import java.io.*;
import java.util.Scanner;

class AffineCipher {
    private final int m = 26; // chars qty
    private Integer a; // key [1, 26]
    private Integer b; // factor
    private int a_inverse = 0; // inverse of a

    AffineCipher() {

    }

    AffineCipher(Integer a, Integer b) {
        this.a = a;
        this.b = b;
    }

    // e(x) = (ax + b) mod m
    private char[] encrypt(char[] line) {
        char[] encrypted_line = new char[line.length];
        for (int i = 0; i < line.length; i++) {
            if (Character.isUpperCase(line[i])) {
                encrypted_line[i] = (char) ((((this.a * (line[i] - 'A')) + this.b) % this.m) + 'A');
            } else if (Character.isLowerCase(line[i])) {
                encrypted_line[i] = (char) ((((this.a * (line[i] - 'a')) + this.b) % this.m) + 'a');
            } else {
                encrypted_line[i] = line[i];
            }
        }

        return encrypted_line;
    }

    void encryptFile(String path) throws IOException {
        if (this.a != null && this.b != null) {
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

    // e(x) = a^(-1) * (x - b) mod m
    private char[] decrypt(char[] line) {
        char[] decrypted_line = new char[line.length];
        for (int i = 0; i < this.m; i++) {
            if ((this.a * i) % this.m == 1) this.a_inverse = i;
        }

        for (int i = 0; i < line.length; i++) {
            if (Character.isUpperCase(line[i])) {
                decrypted_line[i] = (char) (((this.a_inverse * ((line[i] + 'A' - this.b)) % this.m)) + 'A');
            } else if (Character.isLowerCase(line[i])) {
                decrypted_line[i] = Character.toLowerCase((char)
                        (((this.a_inverse * ((Character.toUpperCase(line[i]) + 'A' - this.b)) % this.m)) + 'A'));
            } else {
                decrypted_line[i] = line[i];
            }
        }

        return decrypted_line;
    }

    void decryptFile(String path) throws IOException {
        if (this.a != null && this.b != null) {
            BufferedWriter writer = new BufferedWriter(new FileWriter(path + "/decrypt.txt"));
            Scanner scanner = new Scanner(new File(path + "/crypto.txt"));

            while (scanner.hasNextLine()) {
                char[] line = scanner.nextLine().toCharArray();
                char[] decrypted_line = decrypt(line);

                System.out.println("encrypted line: " + String.valueOf(line));
                System.out.println("decrypted line: " + String.valueOf(decrypted_line) + "\n");

                writer.write(decrypted_line);
                writer.write('\n');
            }

            writer.close();
            scanner.close();
        }
    }

    // greatest common factor
    private int gcf(int a, int b) {
        if (b == 0) return a;
        else return gcf(b, a % b);
    }

    void decryptFileExtra(String path) throws IOException {
        BufferedWriter writer = new BufferedWriter(new FileWriter(path + "/decrypt.txt"));
        BufferedWriter writerKey = new BufferedWriter(new FileWriter(path + "/key-new.txt"));
        Scanner scanner = new Scanner(new File(path + "/crypto.txt"));
        Scanner scannerExtra = new Scanner(new File(path + "/extra.txt"));
        char[] template = scannerExtra.nextLine().toCharArray();
        boolean found_key = false;

        if (template.length > 2) {
            while (scanner.hasNextLine()) {
                char[] line = scanner.nextLine().toCharArray();
                char[] decrypted_line = new char[0];

                // find the key
                if (!found_key) {
                    for (int a = 1; a < this.m; a++) {
                        if (gcf(a, this.m) == 1) { // check coprime between a factor and m
                            this.a = a;
                            for (int b = 0; b < this.m; b++) {
                                this.b = b;
                                decrypted_line = this.decrypt(line);

                                if (String.valueOf(decrypted_line).contains(String.valueOf(template))) {
                                    found_key = true;
                                    System.out.println("Found key: a = " + this.a + ", b = " + this.b);
                                    writerKey.write(this.a.toString() + " " + this.b.toString());
                                    writerKey.close();
                                    writer.write(decrypted_line);
                                    writer.write('\n');
                                    break;
                                }
                            }
                        }

                        if (found_key) break;
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
        } else System.out.println("Error: finding the key is impossible");

        writer.close();
        scanner.close();
    }

    void decryptBruteForce(String path) throws IOException {
        BufferedWriter writer = new BufferedWriter(new FileWriter(path + "/decrypt.txt"));
        int times = 0;

        // do brute force
        for (int a = 1; a < this.m; a++) {
            if (gcf(a, this.m) == 1) { // check coprime between a factor and m
                this.a = a;
                for (int b = 0; b < this.m; b++) {
                    this.b = b;
                    times++;

                    Scanner scanner = new Scanner(new File(path + "/crypto.txt"));
                    while (scanner.hasNextLine()) {
                        char[] line = scanner.nextLine().toCharArray();
                        char[] decrypted_line = this.decrypt(line);

                        writer.write(decrypted_line);
                        writer.write('\n');
                    }

                    scanner.close();
                }
            }
        }

        writer.close();
        System.out.println(times + " combinations saved");
    }
}
