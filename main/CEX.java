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

    public Map<String, Asset> getAssetMap() { return this.assetMap; }

    private class Asset {
        private int money;
        private int sToken;
        private int lToken;

        Asset() {
            this.money = 2000;
            this.sToken = 0;
            this.lToken = 0;
        }

        public String toString() {
            return """
                    Asset {
                        money: %s,
                        sToken: %s,
                        lToken: %s
                    }
                    """.formatted(this.money, this.sToken, this.lToken);
        }
    }
}
