package board.server.domain.board.api;

import board.server.common.exception.BoardFileNotFoundException;
import board.server.domain.board.entity.BoardFile;
import board.server.domain.board.repository.BoardFileRepository;
import board.server.domain.board.service.BoardFileService;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriUtils;

import java.net.MalformedURLException;
import java.nio.charset.StandardCharsets;

@RestController
@RequiredArgsConstructor
public class BoardFileController {

    private final BoardFileRepository boardFileRepository;
    private final BoardFileService boardFileService;

    /**
     * 첨부파일 다운로드
     */
    @GetMapping("/files/{fileId}")
    public ResponseEntity<Resource> downloadFile(@PathVariable Long fileId) throws MalformedURLException {
        BoardFile file = findBoardFile(fileId);
        String storeFileName = file.getStoreFileName();
        String originFileName = file.getOriginFileName();

        UrlResource resource = new UrlResource("file:" + boardFileService.getPullPath(storeFileName));

        String encodedOriginFileName = UriUtils.encode(originFileName, StandardCharsets.UTF_8); // 본래 파일명을 인코딩
        String contentDisposition = "attachment; filename=\"" + encodedOriginFileName + "\"";

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, contentDisposition)
                .body(resource);
    }

    private BoardFile findBoardFile(Long id) {
        return boardFileRepository.findById(id)
                .orElseThrow(() -> new BoardFileNotFoundException(id));
    }
}
