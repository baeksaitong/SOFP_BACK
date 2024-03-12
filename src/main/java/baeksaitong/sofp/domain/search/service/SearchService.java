package baeksaitong.sofp.domain.search.service;

import baeksaitong.sofp.domain.health.repository.PillRepository;
import baeksaitong.sofp.domain.search.dto.request.KeywordReq;
import baeksaitong.sofp.domain.search.dto.response.KeywordDto;
import baeksaitong.sofp.domain.search.dto.response.KeywordRes;
import baeksaitong.sofp.global.common.entity.Pill;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class SearchService {

    private final PillRepository pillRepository;
    public KeywordRes findByKeyword(KeywordReq req) {
        Page<Pill> result = pillRepository.findByKeyword(req);

        List<KeywordDto> results = result.stream()
                .map(pill -> new KeywordDto(
                        pill.getSerialNumber(),
                        pill.getName(),
                        pill.getClassification(),
                        pill.getImgUrl()
                ))
                .collect(Collectors.toList());

        return new KeywordRes(result.getTotalPages(), results);
    }
}
