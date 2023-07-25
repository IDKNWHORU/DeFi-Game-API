package test;

import main.CEX;

public class CEXTest {
    static CEX cex = new CEX();

    public static void main(String... args) throws Exception {
        checkInitialize();
        aPurchase200SToken();
        aSale20SToken();
    }

    // cex init will return 5 Assets = [a, b, c, d, e]
    public static void checkInitialize() {
        System.out.println("***** 게임 시작할 때 CEX 초기화 *****");
        cex.init();
        System.out.println("cex에 정의된 자산(사용자 자산) 카운트 5개: "+ (cex.getAssetMap().size() == 5));

        CEX.Asset a = cex.getAssetMap().get("a");

        System.out.println("사용자 A의 자산 정보");
        System.out.println(a);
        System.out.println("a의 현금 보유액 200000원: " + (a.getMoney() == 200000));
        System.out.println("a의 보유 SToken 0개: " + (a.getSToken() == 0));
        System.out.println("a의 보유 LToken 0개: " + (a.getLToken() == 0));
        System.out.println("***** CEX 초기화 끝 값 확인 완료 *****");
    }

    public static void aPurchase200SToken() throws Exception {
        System.out.println("***** CEX에서 A지갑이 200 SToken을 구매 테스트 시작 *****");
        CEX.Asset a = cex.getAssetMap().get("a");

        cex.purchase("a", "SToken", 200);

        System.out.println("a의 현금 보유액은 0원: " + (a.getMoney() == 0));
        System.out.println("a의 보유 SToken은 200개: " + (a.getSToken() == 200));
        System.out.println("***** CEX에서 A지갑이 200 SToken을 구매 테스트 완료 *****");
    }

    public static void aSale20SToken() throws Exception {
        System.out.println("***** CEX에서 A지갑이 20 SToken을 판매 테스트 시작 *****");

        CEX.Asset a = cex.getAssetMap().get("a");

        cex.sale("a", "SToken", 20);

        System.out.println("a의 현금 보유액은 20000원: " + (a.getMoney() == 20000));
        System.out.println("a의 보유 SToken은 180개: " + (a.getSToken() == 180));
        System.out.println("***** CEX에서 A지갑이 20 SToken을 판매 테스트 완료 *****");
    }
}
