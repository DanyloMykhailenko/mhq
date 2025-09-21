package mhq.service;

import mhq.dao.LevelResultDao;
import mhq.dto.LevelResultDto;
import mhq.mapper.LevelResultMapper;
import mhq.model.LevelResultId;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LevelResultService {

    private final int responseLimit;
    private final LevelResultDao levelResultDao;
    private final LevelResultMapper levelResultMapper;

    public LevelResultService(@Value("${response.limit:20}") int responseLimit,
                              LevelResultDao levelResultDao,
                              LevelResultMapper levelResultMapper) {
        this.responseLimit = responseLimit;
        this.levelResultDao = levelResultDao;
        this.levelResultMapper = levelResultMapper;
    }

    public List<LevelResultDto> getUserTopResults(Long userId) {
        return levelResultMapper.toDtos(levelResultDao.findTopByUserId(userId, responseLimit));
    }

    public List<LevelResultDto> getLevelTopResults(Long levelId) {
        return levelResultMapper.toDtos(levelResultDao.findTopByLevelId(levelId, responseLimit));
    }

    public void addLevelResult(LevelResultDto result) {
        levelResultDao.save(new LevelResultId(result.getUserId(), result.getLevelId()), result.getResult());
    }

}
