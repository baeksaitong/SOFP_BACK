package baeksaitong.sofp.domain.history.service;

import baeksaitong.sofp.domain.history.error.HistoryErrorCode;
import baeksaitong.sofp.domain.history.repository.HistoryRepository;
import baeksaitong.sofp.global.common.collection.History;
import baeksaitong.sofp.global.common.entity.Member;
import baeksaitong.sofp.global.error.exception.BusinessException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class HistoryService {
    private final HistoryRepository historyRepository;

    public void addRecentView(Long memberId, Long pillId){
        History history = historyRepository.findById(memberId).orElse(null);

        if(history == null){
            history = History.builder()
                    .id(memberId)
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

    public List<Long> getRecentViewPill(Member member) {
        History history = historyRepository.findById(member.getId()).orElse(null);

        if(history == null){
            return null;
        }

        return history.getRecentViewPill();
    }

    public void deleteRecentViewPill(Long memberId, List<Long> removePillIdList){
        History history = historyRepository.findById(memberId).orElse(null);

        if(history != null){
            throw new BusinessException(HistoryErrorCode.NO_SUCH_RECENT_VIEW_PILL);
        }

        List<Long> recentViewPill = history.getRecentViewPill();

        for (Long removePillId : removePillIdList) {
            recentViewPill.remove(removePillId);
        }

        historyRepository.save(history);

    }
}
