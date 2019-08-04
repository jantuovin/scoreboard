package scoreboard;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@DataJpaTest
public class ScoreRepositoryIntegrationTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private ScoreRepository scoreRepository;

    @Test
    public void whenFindAllScores_thenReturnOneScoreWithCorrectPlayerNameAndScore() {
        // given
        String name = "Janne";
        int score = 99999;

        Score newScore = new Score();
        newScore.setPlayerName(name);
        newScore.setScore(score);
        entityManager.persist(newScore);
        entityManager.flush();

        // when
        List<Score> scores = scoreRepository.findAll();

        // then
        assertEquals(scores.size(), 1);

        Score foundScore = scores.get(0);

        // and
        assertThat(foundScore.getPlayerName())
                .isEqualTo(name);
        //and
        assert(foundScore.getScore()).equals(score);
    }
}
