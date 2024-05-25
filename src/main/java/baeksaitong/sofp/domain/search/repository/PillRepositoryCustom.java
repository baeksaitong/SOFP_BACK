package baeksaitong.sofp.domain.search.repository;

import baeksaitong.sofp.domain.search.dto.KeywordSearchDto;
import baeksaitong.sofp.domain.pill.entity.Pill;

import java.util.List;


public interface PillRepositoryCustom {
    List<Pill> findByKeyword(KeywordSearchDto req);
}
