package test;

import main.CEX;

public class CEXTest {
    // cex init will return 5 Assets = [a, b, c, d, e]
    public static void main(String... args) {
        CEX cex = new CEX();

        cex.init();

        System.out.println(cex.getAssetMap().size() == 5);

        Object a = cex.getAssetMap().get("a");

        System.out.println(a);
    }
}
