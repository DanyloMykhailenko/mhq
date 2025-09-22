package mhq.dao;

import mhq.model.LevelResult;
import mhq.model.LevelResultId;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.ToLongFunction;
import java.util.stream.Collectors;

@Component
public class LevelResultDao {

    Map<Long, Map<Long, LevelResult>> levelResultsByUserId = new ConcurrentHashMap<>();
    Map<Long, List<LevelResult>> levelResultsByLevelId = new ConcurrentHashMap<>();

    public List<LevelResult> findTopByUserId(Long userId, int limit) {
        return findByEntityId(levelResultsByUserId.getOrDefault(userId, Collections.emptyMap()).values(),
                LevelResult::getLevelId, limit);
    }

    public List<LevelResult> findTopByLevelId(Long levelId, int limit) {
        return findByEntityId(levelResultsByLevelId.getOrDefault(levelId, Collections.emptyList()),
                LevelResult::getUserId, limit);
    }

    private List<LevelResult> findByEntityId(Collection<LevelResult> levelResults,
                                             ToLongFunction<LevelResult> sortingKeyExtractor,
                                             int limit) {
        return levelResults.stream()
                .sorted(Comparator.comparingInt(LevelResult::getScore)
                        .thenComparingLong(sortingKeyExtractor)
                        .reversed())
                .limit(limit)
                .collect(Collectors.toList());
    }

    public void save(LevelResultId id, Integer score) {
        levelResultsByUserId.compute(id.getUserId(), (userId, userLevelResultsByLevelIdMap) -> {
            if (userLevelResultsByLevelIdMap == null) {
                LevelResult levelResult = new LevelResult(id, score);
                updateLevelResultsByLevelIdMap(id.getLevelId(), levelResult);
                return createAndUpdateUserLevelResultsByLevelIdMap(id.getLevelId(), levelResult);
            } else {
                updateUserLevelResultsByLevelIdMap(id, score, userLevelResultsByLevelIdMap);
                return userLevelResultsByLevelIdMap;
            }
        });
    }

    private Map<Long, LevelResult> createAndUpdateUserLevelResultsByLevelIdMap(Long levelId, LevelResult levelResult) {
        Map<Long, LevelResult> userLevelResultsByLevelId = new HashMap<>();
        userLevelResultsByLevelId.put(levelId, levelResult);
        return userLevelResultsByLevelId;
    }

    private void updateLevelResultsByLevelIdMap(Long levelId, LevelResult levelResult) {
        levelResultsByLevelId.compute(levelId, (key, currentLevelResults) -> {
            if (currentLevelResults == null) {
                List<LevelResult> levelResults = new ArrayList<>();
                levelResults.add(levelResult);
                return levelResults;
            } else {
                currentLevelResults.add(levelResult);
                return currentLevelResults;
            }
        });
    }

    private void updateUserLevelResultsByLevelIdMap(LevelResultId id, Integer score, Map<Long, LevelResult> userLevelResultsByLevelIdMap) {
        userLevelResultsByLevelIdMap.compute(id.getLevelId(), (key, value) -> {
            if (value == null) {
                LevelResult levelResult = new LevelResult(id, score);
                updateLevelResultsByLevelIdMap(id.getLevelId(), levelResult);
                return levelResult;
            } else {
                if (score > value.getScore()) {
                    value.setScore(score);
                }
                return value;
            }
        });
    }

}
