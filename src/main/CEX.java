package main;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutionException;

public class CEX {
    Map<String, Asset> assetMap;
    int LTokenPrice;
    public CEX() {}

    public void init() {
        this.assetMap = new HashMap();

        this.assetMap.put("a", new Asset());
        this.assetMap.put("b", new Asset());
        this.assetMap.put("c", new Asset());
        this.assetMap.put("d", new Asset());
        this.assetMap.put("e", new Asset());
        this.LTokenPrice = 500;
    }

    public void purchase(String assetKey, String tokenType, int quantity) throws Exception {
        Asset target = this.getAssetMap().get(assetKey);

        if(tokenType.equals("SToken")) {
            target.purchaseSToken(quantity);
        } else if(tokenType.equals("LToken")) {
            target.purchaseLToken(quantity, this.LTokenPrice);
        }
    }

    public void sale(String assetKey, String tokenType, int quantity) throws Exception {
        Asset target = this.getAssetMap().get(assetKey);

        if(tokenType.equals("SToken")) {
            target.saleSToken(quantity);
        } else if(tokenType.equals("LToken")) {
            target.saleLToken(quantity, this.LTokenPrice);
        }
    }

    public void withDraw(String accessKey, String tokenType, int quantity) throws Exception {
        Asset target = this.getAssetMap().get(accessKey);

        if(tokenType.equals("SToken")) {
            target.withDrawSToken(quantity);
        } else if(tokenType.equals("LToken")) {
            target.withDrawLToken(quantity);
        }
    }

    public Map<String, Asset> getAssetMap() { return this.assetMap; }

    public int getLTokenPrice () { return this.LTokenPrice; }

    public String toString() {
        return """
                CEX {
                    LTokenPrice: %s,
                    assetMap: %s
                }
                """.formatted(this.LTokenPrice, this.assetMap);
    }

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

        public boolean purchaseLToken(int quantity, int LTokenPrice) throws Exception {
            int price = quantity * LTokenPrice;
            if(price <= this.money) {
                this.LToken = this.LToken + quantity;
                this.money = this.money - price;
            }else {
                throw new Exception("money is loser then price.");
            }
            return true;
        }

        public boolean saleLToken(int quantity, int LTokenPrice) throws Exception {
            int price = quantity * LTokenPrice;
            if(quantity <= this.LToken) {
                this.LToken = this.LToken - quantity;
                this.money = this.money + price;
            } else {
                throw new Exception("you have lower LToken then input quantity");
            }

            return true;
        }

        public boolean withDrawSToken(int quantity) throws Exception {
            if(quantity + 2 <= this.SToken) {
                this.SToken = this.SToken - (quantity + 2);
            } else {
                throw new Exception("you have lower SToken than input quantity");
            }

            return true;
        }

        public boolean withDrawLToken(int quantity) throws Exception {
            if(quantity + 4 <= this.LToken) {
                this.LToken = this.LToken - (quantity + 4);
            } else {
                throw new Exception("you have lower LToken than input quantity");
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
