package x21u025.web.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import x21u025.web.entity.TableBoardGame;

@Repository
public interface TableBoardGameRepository extends JpaRepository<TableBoardGame, String> {
	public TableBoardGame findByRowAndColumn(@Param("row") int row, @Param("column") int column);
}
