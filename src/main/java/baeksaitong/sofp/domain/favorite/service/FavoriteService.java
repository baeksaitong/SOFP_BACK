package baeksaitong.sofp.domain.favorite.service;

import baeksaitong.sofp.domain.favorite.dto.request.FavoriteReq;
import baeksaitong.sofp.domain.favorite.dto.response.FavoriteDto;
import baeksaitong.sofp.domain.favorite.dto.response.FavoriteRes;
import baeksaitong.sofp.domain.favorite.entity.Favorite;
import baeksaitong.sofp.domain.favorite.error.FavoriteErrorCode;
import baeksaitong.sofp.domain.favorite.repository.FavoriteRepository;
import baeksaitong.sofp.domain.pill.entity.Pill;
import baeksaitong.sofp.domain.pill.error.PillErrorCode;
import baeksaitong.sofp.domain.pill.repository.PillRepository;
import baeksaitong.sofp.domain.profile.entity.Profile;
import baeksaitong.sofp.domain.profile.service.ProfileService;
import baeksaitong.sofp.global.error.exception.BusinessException;
import baeksaitong.sofp.global.s3.service.AwsS3Service;
import baeksaitong.sofp.global.util.EncryptionUtil;
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
    private final ProfileService profileService;
    private final AwsS3Service s3Service;

    public void addFavorite(FavoriteReq req, String encryptedProfileId) {
        Long profileId = EncryptionUtil.decrypt(encryptedProfileId);

        Pill pill = pillRepository.findBySerialNumber(req.getPillSeralNumber())
                .orElseThrow(() -> new BusinessException(PillErrorCode.NO_SUCH_PILL));

        Profile profile = profileService.getProfile(profileId);

        if(favoriteRepository.existsByPillAndProfile(pill,profile)) {
           throw new BusinessException(FavoriteErrorCode.DUPLICATE_FAVORITE);
        }

        Favorite favorite = favoriteRepository.save(
                Favorite.builder()
                        .pill(pill)
                        .profile(profile)
                        .build()
        );

        if(req.getImage() != null && req.getImage().getName().isEmpty()) {
            favorite.setImgUrl(s3Service.upload(req.getImage(), favorite.getId()));
        }

        favoriteRepository.save(favorite);
    }



    public void deleteFavorite(Long pillSerialNumber, String encryptedProfileId) {
        Long profileId = EncryptionUtil.decrypt(encryptedProfileId);

        Profile profile = profileService.getProfile(profileId);
        Pill pill = pillRepository.findBySerialNumber(pillSerialNumber)
                .orElseThrow(() -> new BusinessException(PillErrorCode.NO_SUCH_PILL));

        Favorite favorite = favoriteRepository.findByPillAndProfile(pill, profile)
                .orElseThrow(() -> new BusinessException(FavoriteErrorCode.NO_SUCH_FAVORITE));

        String imgUrl = favorite.getImgUrl();
        if(imgUrl != null){
            s3Service.deleteImage(imgUrl);
        }

        favoriteRepository.delete(favorite);
    }

    public FavoriteRes getFavorite(String encryptedProfileId) {
        Long profileId = EncryptionUtil.decrypt(encryptedProfileId);

        Profile profile = profileService.getProfile(profileId);

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
