package scoreboard;

import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface ScoreRepository extends CrudRepository<Score, Integer> {

    @Override
    List<Score> findAll();

}