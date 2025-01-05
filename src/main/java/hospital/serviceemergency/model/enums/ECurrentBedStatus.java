package hospital.serviceemergency.model.enums;

public enum ECurrentBedStatus {
    AVAILABLE("available"),
    OCCUPIED("occupied");

    private final String status;

    ECurrentBedStatus(String status) {
        this.status = status.toLowerCase();
    }

    public String getStatus() {
        return status;
    }
}
