package com.sprint.mission.discodeit.service.basic;

import com.sprint.mission.discodeit.dto.binarycontent.BinaryContentResponse;
import com.sprint.mission.discodeit.dto.binarycontent.request.BinaryContentCreateServiceRequest;
import com.sprint.mission.discodeit.entity.FileType;
import com.sprint.mission.discodeit.repository.BinaryContentRepository;
import com.sprint.mission.discodeit.service.BinaryContentService;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BasicBinaryContentService implements BinaryContentService {

    @Qualifier("fileBinaryContentRepository")
    private final BinaryContentRepository binaryContentRepository;

    @Override
    public void createBinaryContent(BinaryContentCreateServiceRequest request) {
        FileType fileType = FileType.getFileTypeByCode(request.getContentType());
        binaryContentRepository.save(request.toEntity());
    }

    @Override
    public void deleteById(UUID binaryContentId) {
        binaryContentRepository.deleteById(binaryContentId);
    }

    @Override
    public BinaryContentResponse findById(UUID binaryContentId) {
        return binaryContentRepository.findBinaryContentById(binaryContentId)
                .map(BinaryContentResponse::new)
                .orElseThrow(() -> new IllegalArgumentException("BinaryContent not found"));
    }

    @Override
    public List<BinaryContentResponse> findAllByIdIn(List<UUID> ids) {
        return ids.stream()
                .map(id -> binaryContentRepository.findBinaryContentById(id).orElseThrow(
                                () -> new IllegalArgumentException("BinaryContent not found"))
                )
                .map(BinaryContentResponse::new)
                .toList();
    }
}
