package mhq.controller;

import lombok.RequiredArgsConstructor;
import mhq.dto.LevelResultDto;
import mhq.service.LevelResultService;

import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class LevelResultController {

    private final LevelResultService levelResultService;

    @GetMapping("/userinfo/{userId}")
    private List<LevelResultDto> getUserTopResults(@PathVariable Long userId) {
        return levelResultService.getUserTopResults(userId);
    }

    @GetMapping("/levelinfo/{levelId}")
    private List<LevelResultDto> getLevelTopResults(@PathVariable Long levelId) {
        return levelResultService.getLevelTopResults(levelId);
    }

    @PutMapping("/setinfo")
    private void addLevelResult(@Valid @RequestBody LevelResultDto result) {
        levelResultService.addLevelResult(result);
    }

}
