package scoreboard;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.Arrays;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.core.Is.is;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

@RunWith(SpringRunner.class)
@WebMvcTest
@AutoConfigureMockMvc
public class ScoreControllerIntegrationTest {

    @MockBean
    private ScoreRepository scoreRepository;

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void givenScores_whenGetScores_thenReturnSortedJsonArray()
            throws Exception {
        Score score1 = new Score();
        score1.setPlayerName("Janne");
        score1.setScore(99999);

        Score score2 = new Score();
        score2.setPlayerName("Player2");
        score2.setScore(1);

        List<Score> scores = Arrays.asList(score2, score1);

        given(scoreRepository.findAll()).willReturn(scores);

        mockMvc.perform(get("/scores")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$", hasSize(2)))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].playerName", is(score1.getPlayerName())))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].score", is(score1.getScore())))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].playerName", is(score2.getPlayerName())))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].score", is(score2.getScore())));
    }

    @Test
    public void whenPostRequestToScoresAndValidScore_thenCorrectResponse() throws Exception {
        String score = "{\"playerName\": \"Janne\", \"score\" : 99999}";
        mockMvc.perform(MockMvcRequestBuilders.post("/scores")
                .content(score)
                .contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    public void whenPostRequestToScoresAndPlayerNameMissing_thenCorrectResponse() throws Exception {
        String score = "{\"playerName\": , \"score\" : 99999 }";
        mockMvc.perform(MockMvcRequestBuilders.post("/scores")
                .content(score)
                .contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }

    @Test
    public void whenPostRequestToScoresAndPlayerNameEmptyString_thenCorrectResponse() throws Exception {
        String score = "{\"playerName\": \"\", \"score\" : 99999}";
        mockMvc.perform(MockMvcRequestBuilders.post("/scores")
                .content(score)
                .contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }

    @Test
    public void whenPostRequestToScoresAndPlayerNameTooLong_thenCorrectResponse() throws Exception {
        String score = "{\"playerName\": \"JanneJanneJanneJannee\", \"score\" : 99999}";
        mockMvc.perform(MockMvcRequestBuilders.post("/scores")
                .content(score)
                .contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }

    @Test
    public void whenPostRequestToScoresAndScoreMissing_thenCorrectResponse() throws Exception {
        String score = "{\"playerName\": \"Janne\", \"score\" : }";
        mockMvc.perform(MockMvcRequestBuilders.post("/scores")
                .content(score)
                .contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }

    @Test
    public void whenPostRequestToScoresAndScoreIsTooSmall_thenCorrectResponse() throws Exception {
        String score = "{\"playerName\": \"Janne\", \"score\" : 0}";
        mockMvc.perform(MockMvcRequestBuilders.post("/scores")
                .content(score)
                .contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }

    @Test
    public void whenPostRequestToScoresAndScoreIsTooBig_thenCorrectResponse() throws Exception {
        String score = "{\"playerName\": \"Janne\", \"score\" : 100000}";
        mockMvc.perform(MockMvcRequestBuilders.post("/scores")
                .content(score)
                .contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }
}
