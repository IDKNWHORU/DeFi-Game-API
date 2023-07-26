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
        aDeposit10SToken();
        aDeposit1LToken();
        aStake20();
        aStake21Exception();
    }

    // cex init will return 5 Assets = [a, b, c, d, e]
    static void checkInitializeCEX() {
        System.out.println(highlightTitle(highlightTitle("***** 게임 시작할 때 CEX 초기화 *****")));
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
        System.out.println(highlightTitle("***** CEX 초기화 완료 *****"));
        System.out.println("");
    }

    static void aPurchase200SToken() throws Exception {
        System.out.println(highlightTitle("***** CEX에서 사용자 A의 200개의 SToken 구매 테스트 시작 *****"));

        cex.purchase("a", "SToken", 200);

        System.out.println("a의 현금 보유액은 0원: " + booleanValue(userA.getMoney() == 0));
        System.out.println("a의 보유 SToken 200개: " + booleanValue(userA.getSToken() == 200));
        System.out.println(highlightTitle("***** 200 SToken 구매 테스트 완료 *****"));
        System.out.println("");
    }

    static void aSale20SToken() throws Exception {
        System.out.println(highlightTitle("***** CEX에서 사용자 A의 20개의 SToken 판매 테스트 시작 *****"));

        cex.sale("a", "SToken", 20);

        System.out.println("a의 현금 보유액은 20000원: " + booleanValue(userA.getMoney() == 20000));
        System.out.println("a의 보유 SToken 180개: " + booleanValue(userA.getSToken() == 180));
        System.out.println(highlightTitle("***** 20 SToken 판매 테스트 완료 *****"));
        System.out.println("");
    }

    static void aPurchase10LToken() throws Exception {
        System.out.println(highlightTitle("***** CEX에서 사용자 A의 10개의 LToken 구매 테스트 시작 *****"));

        cex.purchase("a", "LToken", 10);

        System.out.println("a의 현금 보유액은 15000원: " + booleanValue(userA.getMoney() == 15000));
        System.out.println("a의 보유 LToken 10개: " + booleanValue(userA.getLToken() == 10));
        System.out.println(highlightTitle("***** 10 LToken 구매 테스트 완료 *****"));
        System.out.println("");
    }

    static void aSale5LToken() throws Exception {
        System.out.println(highlightTitle("***** CEX에서 사용자 A의 5개의 LToken 판매 테스트 시작 *****"));

        cex.sale("a", "LToken", 5);

        System.out.println("a의 현금 보유액은 17500원: " + booleanValue(userA.getMoney() == 17500));
        System.out.println("a의 보유 LToken 5개: " + booleanValue(userA.getLToken() == 5));
        System.out.println(highlightTitle("***** 5 LToken 판매 테스트 완료 *****"));
        System.out.println("");
    }

    static void aWithDraw100SToken() throws Exception {
        System.out.println(highlightTitle("***** CEX에서 사용자 A의 100개의 SToken 출금 테스트 시작 *****"));

        System.out.println("출금 전 a의 보유 SToken: " + userA.getSToken());

        cex.withDraw("a", "SToken", 100);

        System.out.println("100개의 SToken 출금 후 a의 보유 SToken 78개: " + booleanValue(userA.getSToken() == 78));
        System.out.println("100개의 SToken 출금 후 a의 개인 지갑 보유 SToken 100개: " + booleanValue(walletA.getSToken() == 100));
        System.out.println(highlightTitle("***** 100 SToken 출금 테스트 완료 *****"));
        System.out.println("");
    }

    static void aWidthDraw1LToken() throws Exception {
        System.out.println(highlightTitle("***** CEX에서 사용자 A의 1개의 LToken 출금 테스트 시작 *****"));

        System.out.println("출금 전 a의 보유 LToken: " + userA.getLToken());

        cex.withDraw("a", "LToken", 1);

        System.out.println("1개의 LToken 출금 후 a의 보유 LToken 0개: " + booleanValue(userA.getLToken() == 0));
        System.out.println("1개의 LToken 출금 후 a의 개인 지갑 보유 LToken 1개: " + booleanValue(walletA.getLToken() == 1));
        System.out.println(highlightTitle("***** 1 LToken 출금 테스트 완료 *****"));
        System.out.println("");
    }

    static void aWidthDraw1LTokenException() throws Exception {
        System.out.println(highlightTitle("***** CEX에서 사용자 A의 LToken 출금 초과 에러 발생 테스트 시작 *****"));

        System.out.println("출금 전 a의 보유 LToken: " + userA.getLToken());

        try {
            cex.withDraw("a", "LToken", 1);
        }catch (Exception e) {
            System.out.println("1개의 LToken 출금 시 에러 메시지: " + errorMessage(e.getMessage()));
        }

        System.out.println(highlightTitle("***** 1 LToken 출금 에러 테스트 완료 *****"));
        System.out.println("");
    }

    static void checkInitializePrivateWallet() {
        System.out.println(highlightTitle("***** 게임 시작할 때 개인 지갑 서비스 초기화 시작 *****"));
        System.out.println("");
        pws.initialize();

        System.out.println("개인 지갑 카운트 5개: "+ (pws.getWalletMap().size() == 5));
        System.out.println("");

        walletA = pws.getWalletMap().get("a");

        System.out.println("사용자 A의 개인 지갑 자산 정보");
        System.out.println("a의 보유 SToken: " + walletA.getSToken());
        System.out.println("a의 보유 LToken: " + walletA.getLToken());
        System.out.println(highlightTitle("***** 개인 지갑 서비스 초기화 완료 *****"));
        System.out.println("");
    }

    static void aDeposit10SToken() throws Exception {
        System.out.println(highlightTitle("***** CEX에서 사용자 A의 10개의 SToken 입금 테스트 시작 *****"));

        System.out.println("입금 전 a의 보유 SToken: " + userA.getSToken());
        System.out.println("입금 전 a의 개인 지갑 보유 SToken: " + walletA.getSToken());
        cex.deposit("a", "SToken", 10);
        System.out.println(highlightTitle("***** 10 SToken 입금 *****"));
        System.out.println("10개의 SToken 입금 후 a의 보유 SToken 88개: " + booleanValue(userA.getSToken() == 88));
        System.out.println("10개의 SToken 입금 후 a의 개인 지갑 보유 SToken 90개: " + booleanValue(walletA.getSToken() == 90));
        System.out.println(highlightTitle("***** 10 SToken 입금 테스트 완료 *****"));
    }

    static void aDeposit1LToken() throws Exception {
        System.out.println(highlightTitle("***** CEX에서 사용자 A의 1개의 LToken 입금 테스트 시작 *****"));

        System.out.println("입금 전 a의 보유 LToken: " + userA.getLToken());
        System.out.println("입금 전 a의 개인 지갑 보유 LToken: " + walletA.getLToken());
        cex.deposit("a", "LToken", 1);
        System.out.println(highlightTitle("***** 10 SToken 입금 *****"));
        System.out.println("10개의 LToken 입금 후 a의 보유 LToken 1개: " + booleanValue(userA.getLToken() == 1));
        System.out.println("10개의 LToken 입금 후 a의 개인 지갑 보유 LToken 0개: " + booleanValue(walletA.getLToken() == 0));
        System.out.println(highlightTitle("***** 1 LToken 입금 테스트 완료 *****"));
        System.out.println("");
    }

    static void aStake20() throws Exception{
        System.out.println(highlightTitle("***** CEX에서 사용자 A의 20개의 SToken 스테이크 테스트 시작 *****"));

        System.out.println("입금 전 a의 보유 SToken: " + userA.getSToken());
        cex.stake("a", 20);

        CEX.StakeManager userAStake = userA.getStakeList().get(0);
        System.out.println("20개의 SToken 스테이크 후 a의 보유 SToken 68개: " + booleanValue(userA.getSToken() == 68));
        System.out.println("스테이크된 SToken 20개: " + booleanValue(userAStake.getStakeAmount() == 20));
        System.out.println("스테이크된 SToken Fee 0개: " + booleanValue(userAStake.getFeeAmount() == 0));
        System.out.println(highlightTitle("***** 20 SToken 스테이크 테스트 완료 *****"));
        System.out.println("");
    }

    static void aStake21Exception() throws Exception{
        System.out.println(highlightTitle("***** CEX에서 사용자 A의 21개의 SToken 스테이크 에러 발생 테스트 시작 *****"));

        System.out.println("입금 전 a의 보유 SToken: " + userA.getSToken());

        try {
            cex.stake("a", 21);
        }catch (Exception e) {
            System.out.println("21개의 SToken 스테이크 에러 메시지: " + errorMessage(e.getMessage()));
        }

        System.out.println("20개의 SToken 스테이크 후 a의 보유 SToken 68개: " + booleanValue(userA.getSToken() == 68));
        System.out.println(highlightTitle("***** 20 SToken 스테이크 에러 테스트 완료 *****"));
        System.out.println("");
    }

    static String errorMessage(String msg) {
        return "\u001B[31m" + msg + "\u001B[0m";
    }

    static String booleanValue(boolean value) {
        if(value) {
            return "\u001B[32m"+ true +"\u001B[0m";
        } else {
            return "\u001B[31m" + false + "\u001B[0m";
        }
    }

    static String highlightTitle(String title) {
        return "\u001B[42m" + "\u001B[30m" +title + "\u001B[0m";
    }
}
