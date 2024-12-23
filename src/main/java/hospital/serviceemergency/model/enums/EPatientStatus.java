package hospital.serviceemergency.model.enums;

public enum EPatientStatus {
    WAITING("waiting"),
    IN_TREATMENT("in_treatment"),
    DISCHARGED("discharged"),
    ADMITTED("admitted");

    private final String status;

    EPatientStatus (String status) {
        this.status = status.toLowerCase();
    }

    public String getStatus() {
        return status;
    }
}
