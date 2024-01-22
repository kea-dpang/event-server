package kea.dpang.eventserver.dto.response;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.time.LocalDate;

@SuperBuilder
public class ItemEventGetDto extends BasicGetDto {
    //이벤트 사진
    private String image_path;

}
