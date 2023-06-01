package board.server.domain.board.service;

import board.server.common.exception.BoardFileNotFoundException;
import board.server.common.util.CommonUtil;
import board.server.domain.board.dto.BoardDto;
import board.server.domain.board.dto.BoardFileDto;
import board.server.domain.board.entity.Board;
import board.server.domain.board.entity.BoardFile;
import board.server.domain.board.repository.BoardFileRepository;
import board.server.domain.board.util.BoardUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class BoardFileService {

    private final BoardFileRepository boardFileRepository;
    private final CommonUtil commonUtil;
    private final BoardUtil boardUtil;

    @Value("${file.dir}")
    private String fileDir;

    public String getPullPath(String filename) {
        return fileDir + filename;
    }

    /**
     * 게시글 파일 업로드
     *
     * @param boardId        : 게시글 식별자
     * @param multipartFiles : 업로드할 파일들
     */
    @Transactional
    public void storeFiles(Long boardId, List<MultipartFile> multipartFiles) throws IOException {
        for (MultipartFile multipartFile : multipartFiles) {
            if (!multipartFile.isEmpty()) {
                storeFile(boardId, multipartFile);
            }
        }
    }

    /**
     * 파일을 서버에 저장 + 파일 정보를 DB에 저장
     *
     * @param boardId
     * @param multipartFile
     */
    @Transactional
    public void storeFile(Long boardId, MultipartFile multipartFile) throws IOException {
        String originalFilename = multipartFile.getOriginalFilename(); // 본래 파일명
        String storeFileName = createStoreFileName(originalFilename); // 서버에 저장하는 파일명
        String uploadPath = getPullPath(storeFileName);

        multipartFile.transferTo(new File(uploadPath)); // 서버에 파일 저장

        Board board = commonUtil.findBoard(boardId);
        BoardFile file = BoardFile.builder()
                .originFileName(originalFilename)
                .storeFileName(storeFileName)
                .uploadDir(uploadPath)
                .board(board)
                .build();

        saveFile(file); // DB에 파일 정보 저장
    }

    @Transactional
    public void saveFile(BoardFile file) {
        boardFileRepository.save(file);
    }

    /**
     * 기존에 존재하는 파일 수정
     *
     * @param boardId
     * @param boardDto
     */
    @Transactional
    public void update(Long boardId, BoardDto boardDto) {
        List<Long> fileIdList = new ArrayList<>();
        for (BoardFileDto file : boardDto.getFiles()) {
            fileIdList.add(file.getId());
        }
        List<BoardFile> files = boardUtil.findFilesByBoardId(boardId);
        for (BoardFile file : files) {
            if (!fileIdList.contains(file.getId())) {
                boardFileRepository.deleteById(file.getId());
            }
        }
    }

    /**
     * 특정 게시글의 첨부 파일들 모두 삭제
     *
     * @param boardId : 게시글 식별자
     */
    @Transactional
    public void deleteAll(Long boardId) {
        boardFileRepository.deleteAllByBoardId(boardId);
    }

    /**
     * 특정 파일 검색
     */
    public BoardFile findBoardFile(Long id) {
        return boardFileRepository.findById(id)
                .orElseThrow(() -> new BoardFileNotFoundException(id));
    }

    /**
     * 서버 내부에서 관리하는 파일명
     */
    private String createStoreFileName(String originalFilename) {
        String ext = extractExt(originalFilename);
        String uuid = UUID.randomUUID().toString();
        return uuid + "." + ext;
    }

    /**
     * 확장자 추출
     */
    private String extractExt(String originalFilename) {
        int index = originalFilename.lastIndexOf(".");
        return originalFilename.substring(index + 1);
    }
}
