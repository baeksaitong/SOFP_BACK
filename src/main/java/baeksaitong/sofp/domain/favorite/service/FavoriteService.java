package baeksaitong.sofp.domain.favorite.service;

import baeksaitong.sofp.domain.favorite.dto.enums.SearchType;
import baeksaitong.sofp.domain.favorite.dto.request.FavoriteReq;
import baeksaitong.sofp.domain.favorite.dto.response.FavoriteRes;
import baeksaitong.sofp.domain.favorite.repository.FavoriteRepository;
import baeksaitong.sofp.domain.health.repository.PillRepository;
import baeksaitong.sofp.global.common.entity.Favorite;
import baeksaitong.sofp.global.common.entity.Member;
import baeksaitong.sofp.global.common.entity.Pill;
import baeksaitong.sofp.global.s3.AwsS3Service;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class FavoriteService {

    private final FavoriteRepository favoriteRepository;
    private final PillRepository pillRepository;
    private final AwsS3Service s3Service;

    public void addFavorite(FavoriteReq req, Member member) {
        Pill pill = pillRepository.findById(req.getPillSeralNumber()).get();
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



    public void deleteFavorite(Long favoriteId) {
        Favorite favorite = favoriteRepository.findById(favoriteId).get();

        String imgUrl = favorite.getImgUrl();
        if(imgUrl != null){
            s3Service.deleteImage(imgUrl);
        }

        favoriteRepository.delete(favorite);
    }

    public List<FavoriteRes> getFavorite(Member member) {
        List<Favorite> favoriteList = favoriteRepository.findAllByMember(member);

        return favoriteList.stream().map(
                favorite -> {
                    Pill pill = favorite.getPill();

                    return new FavoriteRes(
                            pill.getId(),
                            pill.getName(),
                            pill.getClassification(),
                            pill.getEnterprise(),
                            pill.getChart(),
                            (favorite.getImgUrl() != null) ? favorite.getImgUrl() : pill.getImgUrl()
                    );
                }
        ).collect(Collectors.toList());
    }
}
