package mhq.dao;

import mhq.model.LevelResult;
import mhq.model.LevelResultId;
import org.springframework.stereotype.Component;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Predicate;
import java.util.function.ToLongFunction;
import java.util.stream.Collectors;

@Component
public class LevelResultDao {

    Map<LevelResultId, LevelResult> levelResultsById = new ConcurrentHashMap<>();

    public List<LevelResult> findTopByUserId(Long userId, int limit) {
        return findByEntityId(levelResult -> levelResult.getUserId().equals(userId),
                LevelResult::getLevelId, limit);
    }

    public List<LevelResult> findTopByLevelId(Long levelId, int limit) {
        return findByEntityId(levelResult -> levelResult.getLevelId().equals(levelId),
                LevelResult::getUserId, limit);
    }

    private List<LevelResult> findByEntityId(Predicate<LevelResult> entityIdPredicate,
                                             ToLongFunction<LevelResult> sortingKeyExtractor,
                                             int limit) {
        return levelResultsById.values().stream()
                .filter(entityIdPredicate)
                .sorted(Comparator.comparingInt(LevelResult::getScore)
                        .thenComparingLong(sortingKeyExtractor)
                        .reversed())
                .limit(limit)
                .collect(Collectors.toList());
    }

    public void save(LevelResultId id, Integer score) {
        levelResultsById.compute(id, (key, value) -> {
            if (value == null) {
                return new LevelResult(id, score);
            } else {
                if (score > value.getScore()) {
                    value.setScore(score);
                }
                return value;
            }
        });
    }

}
