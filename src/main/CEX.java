package main;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CEX {
    private Map<String, Asset> assetMap;
    private int LTokenPrice;
    private PrivateWalletService pws;
    public CEX() {}

    public void setPrivateWalletService(PrivateWalletService pws) {
        this.pws = pws;
    }

    public void init() {
        this.assetMap = new HashMap<>();

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

    public void deposit(String accessKey, String tokenType, int quantity) throws Exception {
        Asset target = this.getAssetMap().get(accessKey);

        pws.minus(accessKey, tokenType, quantity);

        if(tokenType.equals("SToken")) {
            target.depositSToken(quantity);
        } else if(tokenType.equals("LToken")) {
            target.depositLToken(quantity);
        }
    }

    public void withDraw(String accessKey, String tokenType, int quantity) throws Exception {
        Asset target = this.getAssetMap().get(accessKey);

        if(tokenType.equals("SToken")) {
            target.withDrawSToken(quantity);
        } else if(tokenType.equals("LToken")) {
            target.withDrawLToken(quantity);
        }

        this.pws.add(accessKey, tokenType, quantity);
    }

    public Map<String, Asset> getAssetMap() { return this.assetMap; }

    public int getLTokenPrice () { return this.LTokenPrice; }

    public void stake(String accessKey, int quantity) throws Exception {
        Asset target = this.getAssetMap().get(accessKey);

        target.stake(quantity);
    }

    public void unStake(String accessKey) throws Exception {
        Asset target = this.getAssetMap().get(accessKey);

        target.unStake();
    }

    public void changeLTokenPrice(int quantity) {
        this.LTokenPrice += quantity;
    }

    public void triggerNextRound(int quantity) {
        changeLTokenPrice(quantity);

        getAssetMap().forEach((_a, asset) -> {
            asset.getStakeList().forEach((sm) -> {
                sm.addFeeAmount();
            });
        });
    }

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
        private final List<StakeManager> stakeList;

        Asset() {
            this.money = 200000;
            this.SToken = 0;
            this.LToken = 0;
            this.stakeList = new ArrayList<>();
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

        public List<StakeManager> getStakeList() { return this.stakeList; }

        public boolean purchaseSToken(int quantity) throws Exception {
            int price = quantity * 1000;
            if(price <= this.money) {
                this.SToken += quantity;
                this.money -= price;
            } else {
                throw new Exception("Your asset's money are insufficient for the input quantity.");
            }
            return true;
        }

        public boolean saleSToken(int quantity) throws Exception{
            int price = quantity * 1000;
            if(quantity <= this.SToken) {
                this.SToken -= quantity;
                this.money += price;
            } else {
                throw new Exception("Your asset's SToken balance is insufficient for the input quantity.");
            }
            return true;
        }

        public boolean purchaseLToken(int quantity, int LTokenPrice) throws Exception {
            int price = quantity * LTokenPrice;
            if(price <= this.money) {
                this.LToken += quantity;
                this.money -= price;
            }else {
                throw new Exception("Your asset's money are insufficient for the input quantity.");
            }
            return true;
        }

        public boolean saleLToken(int quantity, int LTokenPrice) throws Exception {
            int price = quantity * LTokenPrice;
            if(quantity <= this.LToken) {
                this.LToken -= quantity;
                this.money += price;
            } else {
                throw new Exception("Your asset's LToken balance is insufficient for the input quantity.");
            }

            return true;
        }

        public boolean depositSToken(int quantity) {
            this.SToken += quantity;

            return true;
        }

        public boolean depositLToken(int quantity) {
            this.LToken += quantity;

            return true;
        }

        public boolean withDrawSToken(int quantity) throws Exception {
            int STokenAmountWithFee = quantity + 2;

            if(STokenAmountWithFee <= this.SToken) {
                this.SToken -= STokenAmountWithFee;
            } else {
                throw new Exception("Your asset's SToken balance is insufficient for the input quantity.");
            }

            return true;
        }

        public boolean withDrawLToken(int quantity) throws Exception {
            int LTokenAmountWithFee = quantity + 4;

            if(LTokenAmountWithFee <= this.LToken) {
                this.LToken -= LTokenAmountWithFee;
            } else {
                throw new Exception("Your asset's LToken balance is insufficient for the input quantity.");
            }

            return true;
        }

        public boolean stake(int amount) throws Exception {
            if(amount % 20 != 0) {
                throw new Exception("You can stake in increments of 20 SToken.");
            }

            if(amount <= this.SToken) {
                for(int count=0; count < amount / 20; count++) {
                    this.SToken -= amount;
                    this.stakeList.add(new StakeManager(20));
                }
            } else {
                throw new Exception("Your asset's SToken balance is insufficient for the input quantity.");
            }

            return true;
        }

        public boolean unStake() throws Exception {
            if(this.stakeList.size() == 0) {
                throw new Exception("You don't have stake amount.");
            }

            StakeManager sm = this.stakeList.get(0);
            this.SToken +=  sm.getStakeAmount() + sm.getFeeAmount();
            this.stakeList.remove(0);

            return true;
        }

        public String toString() {
            return """
                    Asset {
                        money: %s,
                        SToken: %s,
                        LToken: %s,
                        StakeList: %s
                    }
                    """.formatted(this.money, this.SToken, this.LToken, this.stakeList);
        }

    }

    public class StakeManager {
        private final int stakeAmount;
        private int feeAmount;

        StakeManager(int stakeAmount) {
            this.stakeAmount = stakeAmount;
            this.feeAmount = 0;
        }

        public int getStakeAmount() {
            return this.stakeAmount;
        }

        public int getFeeAmount() {
            return this.feeAmount;
        }

        public void addFeeAmount() { this.feeAmount += (this.stakeAmount * 0.15); }

        public String toString() {
            return """
                    StakeManager {
                        stakeAmount: %s,
                        feeAmount: %s
                    }
                    """.formatted(this.stakeAmount, this.feeAmount);
        }
    }
}
