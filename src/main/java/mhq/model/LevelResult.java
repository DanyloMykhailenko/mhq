package mhq.model;

import lombok.*;

@Getter
@Setter
@EqualsAndHashCode
@AllArgsConstructor
public class LevelResult {

    @NonNull
    private final LevelResultId id;

    private Integer score;

    public Long getUserId() {
        return id.getUserId();
    }

    public Long getLevelId() {
        return id.getLevelId();
    }

}
