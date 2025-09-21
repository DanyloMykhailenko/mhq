package mhq.dto;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class LevelResultDto {

    @NotNull
    private final Long userId;

    @NotNull
    private final Long levelId;

    @NotNull
    private final Integer result;

}
