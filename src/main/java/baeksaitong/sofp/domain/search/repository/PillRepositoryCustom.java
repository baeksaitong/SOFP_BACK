package baeksaitong.sofp.domain.search.repository;

import baeksaitong.sofp.domain.search.dto.request.KeywordReq;
import baeksaitong.sofp.global.common.entity.Pill;
import org.springframework.data.domain.Page;


public interface PillRepositoryCustom {
    Page<Pill> findByKeyword(KeywordReq req);
}
