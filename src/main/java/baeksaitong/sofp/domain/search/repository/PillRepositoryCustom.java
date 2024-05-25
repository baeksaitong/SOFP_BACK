package baeksaitong.sofp.domain.search.repository;

import baeksaitong.sofp.domain.search.dto.KeywordDto;
import baeksaitong.sofp.domain.pill.entity.Pill;

import java.util.List;


public interface PillRepositoryCustom {
    List<Pill> findByKeyword(KeywordDto req);
}
