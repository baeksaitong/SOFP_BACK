package baeksaitong.sofp.domain.favorite.service;

import baeksaitong.sofp.domain.favorite.dto.enums.SearchType;
import baeksaitong.sofp.domain.favorite.dto.request.FavoriteReq;
import baeksaitong.sofp.domain.favorite.dto.response.FavoriteDto;
import baeksaitong.sofp.domain.favorite.dto.response.FavoriteRes;
import baeksaitong.sofp.domain.favorite.error.FavoriteErrorCode;
import baeksaitong.sofp.domain.favorite.repository.FavoriteRepository;
import baeksaitong.sofp.domain.pill.error.PillErrorCode;
import baeksaitong.sofp.domain.pill.repository.PillRepository;
import baeksaitong.sofp.domain.profile.error.ProfileErrorCode;
import baeksaitong.sofp.domain.profile.repository.ProfileRepository;
import baeksaitong.sofp.global.common.entity.Favorite;
import baeksaitong.sofp.global.common.entity.Member;
import baeksaitong.sofp.global.common.entity.Pill;
import baeksaitong.sofp.global.common.entity.Profile;
import baeksaitong.sofp.global.error.exception.BusinessException;
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
    private final ProfileRepository profileRepository;
    private final AwsS3Service s3Service;

    public void addFavorite(FavoriteReq req, String name, Member member) {
        Pill pill = pillRepository.findBySerialNumber(req.getPillSeralNumber())
                .orElseThrow(() -> new BusinessException(PillErrorCode.NO_SUCH_PILL));

        Profile profile = profileRepository.findByNameAndMember(name, member)
                .orElseThrow(() -> new BusinessException(ProfileErrorCode.NO_SUCH_PROFILE));

        if(favoriteRepository.existsByPillAndProfile(pill,profile)) {
           throw new BusinessException(FavoriteErrorCode.DUPLICATE_FAVORITE);
        }

        Favorite favorite = favoriteRepository.save(
                Favorite.builder()
                        .pill(pill)
                        .profile(profile)
                        .build()
        );

        if(req.getSearchType().equals(SearchType.IMAGE)) {
            //favorite.setImgUrl(s3Service.upload(req.getImage(), favorite.getId()));
        }

        favoriteRepository.save(favorite);
    }



    public void deleteFavorite(Long favoriteId) {
        Favorite favorite = favoriteRepository.findById(favoriteId).get();

        String imgUrl = favorite.getImgUrl();
        if(imgUrl != null){
            s3Service.deleteImage(imgUrl);
        }

        favoriteRepository.delete(favorite);
    }

    public FavoriteRes getFavorite(String name, Member member) {
        Profile profile = profileRepository.findByNameAndMember(name, member)
                .orElseThrow(() -> new BusinessException(ProfileErrorCode.NO_SUCH_PROFILE));

        List<Favorite> favoriteList = favoriteRepository.findAllByProfile(profile);

        List<FavoriteDto> favoriteDtoList = favoriteList.stream().map(
                favorite -> {
                    Pill pill = favorite.getPill();

                    return new FavoriteDto(
                            pill, (favorite.getImgUrl() != null) ? favorite.getImgUrl() : pill.getImgUrl()
                    );
                }
        ).collect(Collectors.toList());
        return new FavoriteRes(favoriteDtoList);
    }
}
