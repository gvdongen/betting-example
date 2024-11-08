package org.example.types;

public class DepositRequest {
    private String walletId;
    private String userId;
    private String email;
    private int amount;
    private String paymentMethod;

    public DepositRequest(String walletId, String userId, String email, int amount, String paymentMethod) {
        this.amount = amount;
        this.walletId = walletId;
        this.userId = userId;
        this.email = email;
        this.paymentMethod = paymentMethod;
    }

    public DepositRequest() {
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }


    public String getWalletId() {
        return walletId;
    }

    public void setWalletId(String walletId) {
        this.walletId = walletId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email){
        this.email = email;
    }

    public String getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(String paymentMethod) {
        this.paymentMethod = paymentMethod;
    }
}
