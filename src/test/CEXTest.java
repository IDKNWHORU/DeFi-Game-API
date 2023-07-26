package test;

import main.CEX;
import main.PrivateWalletService;

public class CEXTest {
    static CEX cex = new CEX();
    static CEX.Asset userA;

    static PrivateWalletService pws = new PrivateWalletService();

    static PrivateWalletService.Wallet walletA;


    public static void main(String... args) throws Exception {
        checkInitializePrivateWallet();
        checkInitializeCEX();
        aPurchase200SToken();
        aSale20SToken();
        aPurchase10LToken();
        aSale5LToken();
        aWithDraw100SToken();
        aWidthDraw1LToken();
        aWidthDraw1LTokenException();
    }

    // cex init will return 5 Assets = [a, b, c, d, e]
    static void checkInitializeCEX() {
        System.out.println("***** 게임 시작할 때 CEX 초기화 *****");
        System.out.println("");
        cex.setPrivateWalletService(pws);
        cex.init();
        System.out.println("CEX에 정의된 자산(사용자 자산) 카운트: "+ cex.getAssetMap().size());
        System.out.println("");
        System.out.println("CEX LToken 가격: " + cex.getLTokenPrice());
        System.out.println("");

        userA = cex.getAssetMap().get("a");

        System.out.println("사용자 A의 자산 정보");
        System.out.println("a의 현금 보유액: " + userA.getMoney());
        System.out.println("a의 보유 SToken: " + userA.getSToken());
        System.out.println("a의 보유 LToken: " + userA.getLToken());
        System.out.println("***** CEX 초기화 완료 *****");
        System.out.println("");
    }

    static void aPurchase200SToken() throws Exception {
        System.out.println("***** CEX에서 A지갑이 200개의 SToken을 구매 테스트 시작 *****");

        cex.purchase("a", "SToken", 200);

        System.out.println("a의 현금 보유액은 0원: " + (userA.getMoney() == 0));
        System.out.println("a의 보유 SToken은 200개: " + (userA.getSToken() == 200));
        System.out.println("***** 200 SToken 구매 테스트 완료 *****");
        System.out.println("");
    }

    static void aSale20SToken() throws Exception {
        System.out.println("***** CEX에서 A지갑이 20개의 SToken을 판매 테스트 시작 *****");

        cex.sale("a", "SToken", 20);

        System.out.println("a의 현금 보유액은 20000원: " + (userA.getMoney() == 20000));
        System.out.println("a의 보유 SToken은 180개: " + (userA.getSToken() == 180));
        System.out.println("***** 20 SToken 판매 테스트 완료 *****");
        System.out.println("");
    }

    static void aPurchase10LToken() throws Exception {
        System.out.println("***** CEX에서 A지갑이 10개의 LToken을 구매하는 테스트 시작 *****");

        cex.purchase("a", "LToken", 10);

        System.out.println("a의 현금 보유액은 15000원: " + (userA.getMoney() == 15000));
        System.out.println("a의 보유 LToken은 10개: " + (userA.getLToken() == 10));
        System.out.println("***** 10 LToken 구매 테스트 완료 *****");
        System.out.println("");
    }

    static void aSale5LToken() throws Exception {
        System.out.println("***** CEX에서 A지갑이 5개의 LToken을 판매하는 테스트 시작 *****");

        cex.sale("a", "LToken", 5);

        System.out.println("a의 현금 보유액은 17500원: " + (userA.getMoney() == 17500));
        System.out.println("a의 보유 LToken은 5개: " + (userA.getLToken() == 5));
        System.out.println("***** 5 LToken 판매 테스트 완료 *****");
        System.out.println("");
    }

    static void aWithDraw100SToken() throws Exception {
        System.out.println("***** CEX에서 A지갑이 100개의 SToken을 출금하는 테스트 시작 *****");

        System.out.println("출금 전 a의 보유 SToken: " + userA.getSToken());

        cex.withDraw("a", "SToken", 100);

        System.out.println("100개의 SToken 출금 후 a의 보유 SToken 78개: " + (userA.getSToken() == 78));
        System.out.println("100개의 Stoken 출금 후 a의 개인 지갑 보유 SToken 100개: " + (walletA.getSToken() == 100));
        System.out.println("***** 100 SToken 출금 테스트 완료 *****");
        System.out.println("");
    }

    static void aWidthDraw1LToken() throws Exception {
        System.out.println("***** CEX에서 A지갑이 1개의 LToken을 출금하는 테스트 시작 *****");

        System.out.println("출금 전 a의 보유 LToken: " + userA.getLToken());

        cex.withDraw("a", "LToken", 1);

        System.out.println("1개의 LToken 출금 후 a의 보유 LToken 0개: " + (userA.getLToken() == 0));
        System.out.println("1개의 LToken 출금 후 a의 개인 지갑 보유 LToken 1개: " + (walletA.getLToken() == 1));
        System.out.println("***** 1 LToken 출금 테스트 완료 *****");
        System.out.println("");
    }

    static void aWidthDraw1LTokenException() throws Exception {
        System.out.println("***** CEX에서 A지갑이 1개의 LToken을 출금했을 때 에러 발생하는 테스트 시작 *****");

        System.out.println("출금 전 a의 보유 LToken: " + userA.getLToken());

        try {
            cex.withDraw("a", "LToken", 1);
        }catch (Exception e) {
            System.out.println("1개의 LToken 출금 시 에러 메시지: " + e.getMessage());
        }

        System.out.println("***** 1 LToken 출금 에러 테스트 완료 *****");
        System.out.println("");
    }

    static void checkInitializePrivateWallet() {
        System.out.println("***** 게임 시작할 때 개인 지갑 서비스 초기화 시작 *****");
        System.out.println("");
        pws.initialize();

        System.out.println("개인 지갑 서지스에 정의된 자산(사용자 자산) 카운트 5개: "+ (pws.getWalletMap().size() == 5));
        System.out.println("");

        walletA = pws.getWalletMap().get("a");

        System.out.println("사용자 A의 개인 지갑 자산 정보");
        System.out.println("a의 보유 SToken: " + walletA.getSToken());
        System.out.println("a의 보유 LToken: " + walletA.getLToken());
        System.out.println("***** 개인 지갑 서비스 초기화 완료 *****");
        System.out.println("");
    }
}
