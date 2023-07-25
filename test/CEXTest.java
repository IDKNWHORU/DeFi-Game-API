package test;

import main.CEX;

public class CEXTest {
    static CEX cex = new CEX();

    public static void main(String... args) {
        checkInitialize();
        aPurchase200SToken();
    }

    // cex init will return 5 Assets = [a, b, c, d, e]
    public static void checkInitialize() {
        System.out.println("***** 게임 시작할 때 CEX 초기화 *****");
        cex.init();
        System.out.println("cex size will be 5: "+ (cex.getAssetMap().size() == 5));

        CEX.Asset a = cex.getAssetMap().get("a");

        System.out.println("show Asset instance");
        System.out.println(a);
        System.out.println("a have 2000 money: " + (a.getMoney() == 200000));
        System.out.println("a have 0 SToken: " + (a.getSToken() == 0));
        System.out.println("a have 0 LToken: " + (a.getLToken() == 0));
        System.out.println("***** CEX 초기화 끝 값 확인 완료 *****");
    }

    public static void aPurchase200SToken() {
        System.out.println("***** CEX에서 A지갑이 200 SToken을 구매 테스트 시작 *****");
        CEX.Asset a = cex.getAssetMap().get("a");

        cex.purchase("a", "SToken", 200);

        System.out.println(a);
        System.out.println("a have 0 money: " + (a.getMoney() == 0));
        System.out.println("a have 200 SToken: " + (a.getSToken() == 200));
        System.out.println("***** CEX에서 A지갑이 200 SToken을 구매 테스트 종료 *****");
    }
}
