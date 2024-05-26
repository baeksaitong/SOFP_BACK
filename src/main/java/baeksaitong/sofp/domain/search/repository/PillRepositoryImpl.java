package baeksaitong.sofp.domain.search.repository;


import baeksaitong.sofp.domain.search.dto.KeywordSearchDto;
import baeksaitong.sofp.domain.pill.entity.Pill;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

import static baeksaitong.sofp.domain.pill.entity.QPill.*;


@Repository
@RequiredArgsConstructor
public class PillRepositoryImpl implements PillRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    @Override
    public List<Pill> findByKeyword(KeywordSearchDto req) {
        BooleanBuilder cond = filterBuilder(req);

        return queryFactory.select(pill)
                .from(pill)
                .where(cond)
                .where(lastIdCondition(req.getLastId()))
                .orderBy(pill.id.asc())
                .limit(req.getLimit())
                .fetch();
    }

    private BooleanExpression lastIdCondition(Long lastId) {
        return lastId == null ? null : pill.id.gt(lastId);
    }

    private BooleanBuilder filterBuilder(KeywordSearchDto req) {
        BooleanBuilder cond = new BooleanBuilder();

        return cond.and(keywordLike(req.getKeyword()))
                .and(shapeEq(req.getShape()))
                .and(signLike(req.getSign()))
                .and(colorLike(req.getColor()))
                .and(formulationEq(req.getFormulation()))
                .and(lineEq(req.getLine()));
    }

    private BooleanExpression lineEq(String value) {
        return value == null || value.isEmpty() || value.equals("null") ? null : pill.lineFront.eq(value).or(pill.lineBack.eq(value));
    }

    private BooleanExpression colorLike(String value) {
        return value == null || value.isEmpty() || value.equals("null") ? null : pill.colorFront.contains(value).or(pill.colorBack.contains(value));
    }

    private BooleanExpression signLike(String value) {
        return value == null || value.isEmpty() || value.equals("null") ? null : pill.signFront.contains(value).or(pill.signBack.contains(value));
    }

    private BooleanExpression shapeEq(String value) {
        return value == null || value.isEmpty() || value.equals("null") ? null : pill.shape.eq(value);
    }

    private BooleanExpression formulationEq(String value) {
        return value == null || value.isEmpty() || value.equals("null") ? null : pill.formClassification.eq(value);
    }

    private BooleanExpression keywordLike(String keyword) {
        return keyword == null || keyword.isEmpty() || keyword.equals("null") ? null : pill.name.contains(keyword).or(pill.efficacy.contains(keyword)).or(pill.ingredient.contains(keyword));
    }
}
