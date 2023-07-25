package main;

import java.util.HashMap;
import java.util.Map;

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

    public void purchase(String assetKey, String tokenType, int quantity) {
        Asset target = this.getAssetMap().get(assetKey);

        if(tokenType.equals("SToken")) {
            System.out.println("tokenType : " + tokenType);
            target.purchaseSToken(quantity);
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

        public boolean purchaseSToken(int quantity) {
            int price = quantity * 1000;
            if(price <= this.money) {
                this.SToken = quantity;
                this.money = this.money - price;
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
