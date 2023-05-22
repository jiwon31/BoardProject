package board.server.domain.board.service;

import board.server.domain.board.dto.BoardDto;
import board.server.domain.board.repository.querydsl.BoardQueryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class BoardSearchService {

    private final BoardQueryRepository boardQueryRepository;

    public Page<BoardDto> getBoardListBySearchConditions(String title, String content, Pageable pageable) {
        return boardQueryRepository.search(title, content, pageable);
    }
}
