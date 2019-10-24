public class CesarMenu {
    CesarMenu() {
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
