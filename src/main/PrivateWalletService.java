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

    public void add(String accessKey, String tokenType, int quantity){
        Wallet wallet = this.walletMap.get(accessKey);

        if(tokenType.equals("SToken")) {
            wallet.addSToken(quantity);
        } else if(tokenType.equals("LToken")) {
            wallet.addLToken(quantity);
        }
    }

    public void minus(String accessKey, String tokenType, int quantity) throws Exception {
        Wallet wallet = this.walletMap.get(accessKey);

        if(tokenType.equals("SToken")) {
            wallet.minusSToken(quantity);
        } else if(tokenType.equals("LToken")) {
            wallet.minusLToken(quantity);
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

        public void addSToken(int quantity) {
            this.SToken += quantity;
        }

        public void addLToken(int quantity) {
            this.LToken += quantity;
        }

        public void minusSToken(int quantity) throws Exception{
            if(quantity <= this.SToken) {
                this.SToken -= quantity;
            } else {
                throw new Exception("Your wallet balance of SToken is insufficient for the input quantity.");
            }
        }

        public void minusLToken(int quantity) throws Exception {
            if(quantity <= this.LToken) {
                this.LToken -= quantity;
            } else {
                throw new Exception("Your wallet balance of LToken is insufficient for the input quantity.");
            }
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
