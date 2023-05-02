package board.server.domain.board.service;

import board.server.domain.board.dto.BoardDto;
import board.server.domain.board.entity.Board;
import board.server.domain.board.mapper.BoardMapper;
import board.server.domain.board.repository.querydsl.BoardQueryRepository;
import lombok.RequiredArgsConstructor;
import org.mapstruct.factory.Mappers;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class BoardSearchService {

    private final BoardQueryRepository boardQueryRepository;
    private final BoardMapper boardMapper = Mappers.getMapper(BoardMapper.class);

    public List<BoardDto> getBoardListBySearchConditions(String title, String content, Pageable pageable) {
        List<Board> boardList = boardQueryRepository.search(title, content, pageable);
        return boardMapper.toDtoList(boardList);
    }
}
