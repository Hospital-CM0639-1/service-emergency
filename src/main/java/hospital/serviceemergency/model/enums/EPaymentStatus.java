package hospital.serviceemergency.model.enums;

public enum EPaymentStatus {
    PENDING("pending"),
    COMPLETED("completed");

    private final String paymentStatus;

    EPaymentStatus(String paymentStatus) {
        this.paymentStatus = paymentStatus.toLowerCase();
    }

    public String getPaymentStatus() {
        return this.paymentStatus;
    }
}
