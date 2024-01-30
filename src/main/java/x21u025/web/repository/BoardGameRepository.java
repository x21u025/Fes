package x21u025.web.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import x21u025.web.entity.BoardGame;

@Repository
public interface BoardGameRepository extends JpaRepository<BoardGame, String> {

	public BoardGame findById(int id);

	public List<BoardGame> findByEnName(String enName);

}
