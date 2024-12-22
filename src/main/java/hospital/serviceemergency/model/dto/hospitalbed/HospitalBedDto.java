package hospital.serviceemergency.model.dto.hospitalbed;

import hospital.serviceemergency.model.enums.ECurrentBedStatus;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class HospitalBedDto {
    private Long id;
    private String bedNumber;
    private String wardSection;
    private ECurrentBedStatus currentStatus;
    private String lastCleanedTimestamp;
}
