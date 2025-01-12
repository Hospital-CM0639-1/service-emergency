package hospital.serviceemergency.model.enums;

public enum EWardSection {
    CARDIAC("cardiac"),
    ORTHOPEDIC("orthopedic"),
    SURGERY("surgery"),
    GENERAL("general");

    private final String wardSection;

    EWardSection(String wardSection) {
        this.wardSection = wardSection.toLowerCase();
    }

    public String getWardSection() {
        return wardSection;
    }
}
