package board.server.domain.board.service;

import board.server.common.util.CommonUtil;
import board.server.domain.board.entity.Board;
import board.server.domain.board.entity.BoardFile;
import board.server.domain.board.repository.BoardFileRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.UUID;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class BoardFileService {

    private final BoardFileRepository boardFileRepository;
    private final CommonUtil commonUtil;

    @Value("${file.dir}")
    private String fileDir;

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

    public String getPullPath(String filename) {
        return fileDir + filename;
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
