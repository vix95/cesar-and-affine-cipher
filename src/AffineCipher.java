import java.io.*;
import java.util.Scanner;

class AffineCipher {
    private final int m = 26; // chars qty
    private Integer a; // key
    private Integer b; // factor

    AffineCipher(Integer a, Integer b) {
        this.a = a;
        this.b = b;
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
                    encrypted_line[i] = (char) (((a * (line[i] - 'A') + b) % m) + 'A');
                } else if (Character.isLowerCase(line[i])) {
                    encrypted_line[i] = (char) (((a * (line[i] - 'a') + b) % m) + 'a');
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

    // e(x) = a^(-1) * (x - b) mod m
    void decrypt(String path) throws IOException {
        BufferedWriter writer = new BufferedWriter(new FileWriter(path + "/decrypt.txt"));
        File file = new File(path + "/crypto.txt");
        Scanner scanner = new Scanner(file);

        while (scanner.hasNextLine()) {
            char[] line = scanner.nextLine().toCharArray();
            char[] decrypted_line = new char[line.length];

            int a_inv = 0;
            for (int i = 0; i < 26; i++)
            {
                int flag = (a * i) % 26;
                if (flag == 1)
                {
                    a_inv = i;
                    System.out.println(i);
                }
            }

            for (int i = 0; i < line.length; i++) {
                if (Character.isUpperCase(line[i])) {
                    decrypted_line[i] = (char) ((a_inv * ((line[i] - 'A') - b) % m) + 'A');
                } else if (Character.isLowerCase(line[i])) {
                    decrypted_line[i] = (char) ((a_inv * ((line[i] - 'a') - b) % m) + 'a');
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
