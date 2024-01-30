package x21u025.web.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import x21u025.web.dto.BoardGameListParam;
import x21u025.web.entity.BoardGame;
import x21u025.web.repository.BoardGameRepository;

@Service
public class BoardGameService {

	@Autowired
	private BoardGameRepository boardGameRepository;

	public List<BoardGame> getAll() {
		return boardGameRepository.findAll();
	}

	public BoardGame getById(int id) {
		return boardGameRepository.findById(id);
	}

	public List<BoardGame> getByEnName(String enName) {
		return boardGameRepository.findByEnName(enName);
	}

	public void updateByBoardGameListParam(BoardGameListParam boardGameListParam) {
		ArrayList<BoardGame> list = new ArrayList<BoardGame>();
		for(BoardGame boardGame : boardGameListParam.getBgList()) {
			BoardGame bg = getById(boardGame.getId());
			if(bg == null) {
				list.add(boardGame);
			} else {
				list.add(changeBoardGame(bg, boardGame));
			}
		}
		boardGameRepository.saveAll(list);
	}


	private BoardGame changeBoardGame(BoardGame base, BoardGame edited) {
		base.setName(edited.getName());
		base.setEnName(edited.getEnName());
		base.setPic(edited.getPic());
		base.setDesc(edited.getDesc());
		base.setPlayer_low(edited.getPlayer_low());
		base.setPlayer_high(edited.getPlayer_high());
		base.setColor(edited.getColor());

		return base;
	}

}
