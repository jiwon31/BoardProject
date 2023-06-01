package board.server.domain.board.util;

import board.server.domain.board.entity.BoardFile;
import board.server.domain.board.repository.BoardFileRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BoardUtil {

    private final BoardFileRepository boardFileRepository;

    public List<BoardFile> findFiles(Long boardId) {
        return boardFileRepository.findAllByBoardId(boardId);
    }
}
