package main;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutionException;

public class CEX {
    Map<String, Asset> assetMap;
    public CEX() {}

    public void init() {
        this.assetMap = new HashMap();

        this.assetMap.put("a", new Asset());
        this.assetMap.put("b", new Asset());
        this.assetMap.put("c", new Asset());
        this.assetMap.put("d", new Asset());
        this.assetMap.put("e", new Asset());
    }

    public void purchase(String assetKey, String tokenType, int quantity) throws Exception {
        Asset target = this.getAssetMap().get(assetKey);

        if(tokenType.equals("SToken")) {
            target.purchaseSToken(quantity);
        }
    }

    public void sale(String assetKey, String tokenType, int quantity) throws Exception {
        Asset target = this.getAssetMap().get(assetKey);

        if(tokenType.equals("SToken")) {
            target.saleSToken(quantity);
        }
    }

    public Map<String, Asset> getAssetMap() { return this.assetMap; }

    public class Asset {
        private int money;
        private int SToken;
        private int LToken;

        Asset() {
            this.money = 200000;
            this.SToken = 0;
            this.LToken = 0;
        }

        public int getMoney() {
            return this.money;
        }

        public int getSToken() {
            return this.SToken;
        }

        public int getLToken() {
            return this.LToken;
        }

        public boolean purchaseSToken(int quantity) throws Exception {
            int price = quantity * 1000;
            if(price <= this.money) {
                this.SToken = this.SToken + quantity;
                this.money = this.money - price;
            } else {
                throw new Exception("money is lower then price");
            }
            return true;
        }

        public boolean saleSToken(int quantity) throws Exception{
            int price = quantity * 1000;
            if(quantity <= this.SToken) {
                this.SToken = this.SToken - quantity;
                this.money = this.money + price;
            } else {
                throw new Exception("you have lower SToken then input quantity");
            }
            return true;
        }

        public String toString() {
            return """
                    Asset {
                        money: %s,
                        SToken: %s,
                        LToken: %s
                    }
                    """.formatted(this.money, this.SToken, this.LToken);
        }
    }
}
