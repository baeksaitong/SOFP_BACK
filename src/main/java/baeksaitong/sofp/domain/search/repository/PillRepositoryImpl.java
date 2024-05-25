package baeksaitong.sofp.domain.search.repository;


import baeksaitong.sofp.domain.search.dto.KeywordSearchDto;
import baeksaitong.sofp.domain.pill.entity.Pill;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.SimpleExpression;
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
                .and(shapeEq(pill.shape, req.getShape()))
                .and(signLike(req.getSign()))
                .and(ColorLike(req.getColor()))
                .and(formulationEq(req.getFormulation()))
                .and(LineEq(pill.lineFront, pill.lineBack, req.getLine()));
    }

    private <T> BooleanExpression LineEq(SimpleExpression<T> front, SimpleExpression<T> back, T value) {
        return value == null || value.equals("전체") ? null : front.eq(value).or(back.eq(value));
    }

    private BooleanExpression ColorLike(String value) {
        return value == null ? null : pill.colorFront.contains(value).or(pill.colorBack.contains(value));
    }

    private BooleanExpression signLike(String value) {
        return value == null ? null : pill.signFront.contains(value).or(pill.signBack.contains(value));
    }

    private <T> BooleanExpression shapeEq(SimpleExpression<T> path, T value) {
        return value == null || value.equals("전체") ? null : path.eq(value);
    }

    private BooleanExpression formulationEq(String value) {
        return value == null || value.equals("전체") ? null : pill.formClassification.eq(value);
    }

    private BooleanExpression keywordLike(String keyword) {
        return keyword == null || keyword.isEmpty()  ? null : pill.name.contains(keyword).or(pill.efficacy.contains(keyword)).or(pill.ingredient.contains(keyword));
    }
}
