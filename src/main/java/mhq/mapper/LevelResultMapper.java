package mhq.mapper;

import mhq.dto.LevelResultDto;
import mhq.model.LevelResult;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING, unmappedTargetPolicy = ReportingPolicy.ERROR)
public interface LevelResultMapper {

    List<LevelResultDto> toDtos(List<LevelResult> levelResults);

    @Mapping(target = "result", source = "score")
    LevelResultDto toDto(LevelResult levelResult);

}
