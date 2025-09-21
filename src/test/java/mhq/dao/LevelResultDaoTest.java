package mhq.dao;

import mhq.model.LevelResult;
import mhq.model.LevelResultId;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class LevelResultDaoTest {

    @Test
    void givenResultsOfUser_whenRetrievingTopResultsOfUser_thenReturnOrderedAndLimitedTopResultsOfUser() {
        LevelResultDao levelResultDao = new LevelResultDao();
        Long userId = 1L;
        Long anotherUserId = 2L;
        int limit = 5;
        LevelResultId levelOneResultId = new LevelResultId(userId, 1L);
        levelResultDao.save(levelOneResultId, 65);
        LevelResultId levelTwoResultId = new LevelResultId(userId, 2L);
        levelResultDao.save(levelTwoResultId, 92);
        LevelResultId levelThreeResultId = new LevelResultId(userId, 3L);
        levelResultDao.save(levelThreeResultId, 25);
        LevelResultId levelFourResultId = new LevelResultId(userId, 4L);
        levelResultDao.save(levelFourResultId, 89);
        LevelResultId levelFiveResultId = new LevelResultId(userId, 5L);
        levelResultDao.save(levelFiveResultId, 92);
        LevelResultId levelSixResultId = new LevelResultId(userId, 6L);
        levelResultDao.save(levelSixResultId, 94);
        LevelResultId levelSixAnotherUserResultId = new LevelResultId(anotherUserId, 6L);
        levelResultDao.save(levelSixAnotherUserResultId, 99);

        List<LevelResult> actualLevelResults = levelResultDao.findTopByUserId(userId, limit);

        assertEquals(limit, actualLevelResults.size());
        assertEquals(levelSixResultId, actualLevelResults.get(0).getId());
        assertEquals(levelFiveResultId, actualLevelResults.get(1).getId());
        assertEquals(levelTwoResultId, actualLevelResults.get(2).getId());
        assertEquals(levelFourResultId, actualLevelResults.get(3).getId());
        assertEquals(levelOneResultId, actualLevelResults.get(4).getId());
    }

    @Test
    void givenResultsOnLevel_whenRetrievingTopResultsOnLevel_thenReturnOrderedAndLimitedTopResultsOnLevel() {
        LevelResultDao levelResultDao = new LevelResultDao();
        Long levelId = 1L;
        Long anotherLevelId = 2L;
        int limit = 5;
        LevelResultId userOneResultId = new LevelResultId(1L, levelId);
        levelResultDao.save(userOneResultId, 42);
        LevelResultId userTwoResultId = new LevelResultId(2L, levelId);
        levelResultDao.save(userTwoResultId, 84);
        LevelResultId userThreeResultId = new LevelResultId(3L, levelId);
        levelResultDao.save(userThreeResultId, 20);
        LevelResultId userFourResultId = new LevelResultId(4L, levelId);
        levelResultDao.save(userFourResultId, 84);
        LevelResultId userFiveResultId = new LevelResultId(5L, levelId);
        levelResultDao.save(userFiveResultId, 90);
        LevelResultId userSixResultId = new LevelResultId(6L, levelId);
        levelResultDao.save(userSixResultId, 98);
        LevelResultId userSixAnotherLevelResultId = new LevelResultId(6L, anotherLevelId);
        levelResultDao.save(userSixAnotherLevelResultId, 99);

        List<LevelResult> actualLevelResults = levelResultDao.findTopByLevelId(levelId, limit);

        assertEquals(limit, actualLevelResults.size());
        assertEquals(userSixResultId, actualLevelResults.get(0).getId());
        assertEquals(userFiveResultId, actualLevelResults.get(1).getId());
        assertEquals(userFourResultId, actualLevelResults.get(2).getId());
        assertEquals(userTwoResultId, actualLevelResults.get(3).getId());
        assertEquals(userOneResultId, actualLevelResults.get(4).getId());
    }

}
