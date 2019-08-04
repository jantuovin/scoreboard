package scoreboard;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.LocalDate;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@RestController
public class ScoreController {
    @Autowired
    private ScoreRepository scoreRepository;

    @GetMapping("/scores")
    List<Score> getAllScoresAsSorted() {
        return scoreRepository.findAll().stream()
                .sorted(Comparator.comparing(Score::getScore).reversed())
                .collect(Collectors.toList());
    }

    @PostMapping("/scores")
    Score createNewScore(@Valid @RequestBody Score newScore) {
        newScore.setId(null);
        newScore.setCreatedDate(LocalDate.now());

        return scoreRepository.save(newScore);
    }
}