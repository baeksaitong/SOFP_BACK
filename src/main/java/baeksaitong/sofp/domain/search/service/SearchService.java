package baeksaitong.sofp.domain.search.service;

import baeksaitong.sofp.domain.health.repository.PillRepository;
import baeksaitong.sofp.domain.search.dto.request.ImageReq;
import baeksaitong.sofp.domain.search.dto.response.PillInfoRes;
import baeksaitong.sofp.domain.search.dto.request.KeywordReq;
import baeksaitong.sofp.domain.search.dto.response.KeywordDto;
import baeksaitong.sofp.domain.search.dto.response.KeywordRes;
import baeksaitong.sofp.domain.search.error.SearchErrorCode;
import baeksaitong.sofp.domain.search.feign.PillFeignClient;
import baeksaitong.sofp.global.common.entity.Pill;
import baeksaitong.sofp.global.error.exception.BusinessException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class SearchService {

    private final PillRepository pillRepository;
    private final PillFeignClient pillFeignClient;

    @Value("${api.public-data.url.pill-info}")
    private String pillInfoUrl;

    @Value("${api.public-data.serviceKey}")
    private String serviceKey;

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

    public PillInfoRes getPillInfo(String serialNumber) {
        PillInfoRes result;
        try {
             result = pillFeignClient.getPillInfo(new URI(pillInfoUrl), serviceKey, "json", serialNumber);
        } catch (URISyntaxException e) {
            throw new BusinessException(SearchErrorCode.PILL_INFO_ERROR);
        }

        if(result.getName() == null){
            throw new BusinessException(SearchErrorCode.PILL_INFO_ERROR);
        }

        return result;

    }

    public void findByImage(ImageReq req) {

    }
}
