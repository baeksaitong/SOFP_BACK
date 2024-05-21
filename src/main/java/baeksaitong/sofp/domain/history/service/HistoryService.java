package baeksaitong.sofp.domain.history.service;

import baeksaitong.sofp.domain.history.dto.request.HistoryDeleteReq;
import baeksaitong.sofp.domain.history.dto.request.HistoryReq;
import baeksaitong.sofp.domain.history.dto.response.HistoryDto;
import baeksaitong.sofp.domain.history.dto.response.HistoryRes;
import baeksaitong.sofp.domain.history.error.HistoryErrorCode;
import baeksaitong.sofp.domain.history.repository.HistoryRepository;
import baeksaitong.sofp.domain.pill.repository.PillRepository;
import baeksaitong.sofp.domain.profile.service.ProfileService;
import baeksaitong.sofp.domain.history.collection.History;
import baeksaitong.sofp.domain.pill.entity.Pill;
import baeksaitong.sofp.domain.profile.entity.Profile;
import baeksaitong.sofp.global.error.exception.BusinessException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class HistoryService {
    private final HistoryRepository historyRepository;
    private final PillRepository pillRepository;
    private final ProfileService profileService;

    public void addRecentView(Long profileId, Long pillId){
        History history = historyRepository.findById(profileId).orElse(null);

        if(history == null){
            history = History.builder()
                    .id(profileId)
                    .recentViewPill(new ArrayList<>())
                    .build();
        }

        List<Long> recentViewPill = history.getRecentViewPill();
        recentViewPill.remove(pillId);

        recentViewPill.add(0, pillId);

        int endIdx = Math.min(recentViewPill.size(), 60);
        history.setRecentViewPill(recentViewPill.subList(0,endIdx));

        historyRepository.save(history);
    }

    public HistoryRes getRecentViewPill(HistoryReq req, Long profileId) {
        Profile profile = profileService.getProfile(profileId);

        History history = historyRepository.findById(profile.getId()).orElse(null);

        if(history == null){
            return new HistoryRes(0,new ArrayList<>());
        }

        List<Long> recentViewPill = history.getRecentViewPill();

        return changePillIdListToHistoryRes(recentViewPill, req.getCount(), req.getSize());
    }

    public HistoryRes deleteRecentViewPill(HistoryDeleteReq req, Long profileId){
        Profile profile = profileService.getProfile(profileId);

        History history = historyRepository.findById(profile.getId()).orElseThrow(
                () -> new BusinessException(HistoryErrorCode.NO_SUCH_RECENT_VIEW_PILL)
        );

        List<Long> recentViewPill = history.getRecentViewPill();

        List<Long> updatedRecentViewPill = recentViewPill.stream()
                .filter(pillId -> !req.getPillIdList().contains(pillId))
                .collect(Collectors.toList());

        history.setRecentViewPill(updatedRecentViewPill);

        historyRepository.save(history);

        return changePillIdListToHistoryRes(history.getRecentViewPill(), req.getCount(), req.getSize());

    }

    private HistoryRes changePillIdListToHistoryRes(List<Long> pillIdList, int count, int size){
        List<Pill> pills = pillRepository.findAllBySerialNumberIn(pillIdList);


        List<HistoryDto> historyDtoList = pills.stream()
                .map(HistoryDto::new)
                .collect(Collectors.toList());

        int totalPage = (int) Math.ceil((double) count / size);

        return new HistoryRes(totalPage, historyDtoList);
    }

}
