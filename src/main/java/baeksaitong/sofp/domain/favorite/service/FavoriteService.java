package baeksaitong.sofp.domain.favorite.service;

import baeksaitong.sofp.domain.favorite.dto.enums.SearchType;
import baeksaitong.sofp.domain.favorite.dto.request.FavoriteReq;
import baeksaitong.sofp.domain.favorite.repository.FavoriteRepository;
import baeksaitong.sofp.domain.health.repository.PillRepository;
import baeksaitong.sofp.global.common.entity.Favorite;
import baeksaitong.sofp.global.common.entity.Member;
import baeksaitong.sofp.global.common.entity.Pill;
import baeksaitong.sofp.global.s3.AwsS3Service;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class FavoriteService {

    private final FavoriteRepository favoriteRepository;
    private final PillRepository pillRepository;
    private final AwsS3Service s3Service;

    public void addFavorite(FavoriteReq req, Member member) {
        Pill pill = pillRepository.findById(req.getPillId()).get();
        Favorite favorite = favoriteRepository.save(
                Favorite.builder()
                        .pill(pill)
                        .member(member)
                        .build()
        );

        if(req.getSearchType().equals(SearchType.IMAGE)) {
            favorite.setImgUrl(s3Service.upload(req.getImage(), favorite.getId()));
            favoriteRepository.save(favorite);
        }
    }
}
