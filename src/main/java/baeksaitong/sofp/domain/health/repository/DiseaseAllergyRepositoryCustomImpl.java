package baeksaitong.sofp.domain.health.repository;

import baeksaitong.sofp.domain.health.entity.DiseaseAllergy;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.stream.Collectors;

import static baeksaitong.sofp.domain.health.entity.QDiseaseAllergy.diseaseAllergy;


@Repository
@RequiredArgsConstructor
public class DiseaseAllergyRepositoryCustomImpl implements DiseaseAllergyRepositoryCustom{

    private final JPAQueryFactory queryFactory;

    @Override
    public List<String> findByKeyword(String keyword) {
        List<DiseaseAllergy> diseaseAllergyList = queryFactory.selectFrom(diseaseAllergy)
                .where(diseaseAllergy.name.contains(keyword))
                .fetch();

        return diseaseAllergyList.stream()
                .map(DiseaseAllergy::getName)
                .collect(Collectors.toList());
    }
}
