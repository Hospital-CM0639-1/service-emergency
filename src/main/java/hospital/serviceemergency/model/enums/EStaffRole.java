package hospital.serviceemergency.model.enums;

public enum EStaffRole {
    DOCTOR("doctor"),
    NURSE("nurse"),
    SECRETARY("secretary");

    private final String role;

    EStaffRole (String role) {
        this.role = role.toLowerCase();
    }

    public String getRole() {
        return role;
    }


}
