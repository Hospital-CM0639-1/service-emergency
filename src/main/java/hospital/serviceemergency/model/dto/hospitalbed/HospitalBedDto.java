package hospital.serviceemergency.model.dto.hospitalbed;

import hospital.serviceemergency.model.enums.ECurrentBedStatus;
import hospital.serviceemergency.model.enums.EWardSection;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class HospitalBedDto {
    private Long id;
    private String bedNumber;
    private EWardSection wardSection;
    private ECurrentBedStatus currentStatus;
}
