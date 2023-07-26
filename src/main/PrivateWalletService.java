package main;

import java.util.HashMap;
import java.util.Map;

public class PrivateWalletService {
    Map<String, Wallet> walletMap;

    public PrivateWalletService() {

    }

    public void initialize() {
        this.walletMap = new HashMap<>();

        this.walletMap.put("a", new Wallet());
        this.walletMap.put("b", new Wallet());
        this.walletMap.put("c", new Wallet());
        this.walletMap.put("d", new Wallet());
        this.walletMap.put("e", new Wallet());
    }

    public Map<String, Wallet> getWalletMap() { return this.walletMap; }

    public void put(String accessKey, String tokenType, int quantity){
        Wallet wallet = this.walletMap.get(accessKey);

        if(tokenType.equals("SToken")) {
            wallet.putSToken(quantity);
        } else if(tokenType.equals("LToken")) {
            wallet.putLToken(quantity);
        }
    }

    public String toString() {
        return """
                PrivateWalletService {
                    walletMap: %s
                }
                """.formatted(walletMap);
    }

    public class Wallet {
        private int SToken;
        private int LToken;

        Wallet() {
            this.SToken = 0;
            this.LToken = 0;
        }

        public int getSToken() { return this.SToken; }
        public int getLToken() { return this.LToken; }

        public void putSToken(int quantity) {
            this.SToken += quantity;
        }

        public void putLToken(int quantity) {
            this.LToken += quantity;
        }

        public String toString() {
            return """
                    Wallet {
                        SToken: %s,
                        LToken: %s
                    }
                    """.formatted(this.SToken, this.LToken);
        }
    }
}
