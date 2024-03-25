package baeksaitong.sofp.domain.history.service;

import baeksaitong.sofp.domain.history.repository.HistoryRepository;
import baeksaitong.sofp.global.common.collection.History;
import baeksaitong.sofp.global.common.entity.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class HistoryService {
    private final HistoryRepository historyRepository;

    public void addRecentView(Long memberId, Long pillId){
        History history = historyRepository.findById(memberId).orElse(null);

        if(history == null){
            history = History.builder()
                    .id(memberId.toString())
                    .recentViewPill(new ArrayList<>())
                    .build();
        }

        List<Long> recentViewPill = history.getRecentViewPill();
        recentViewPill.add(pillId);

        int size = recentViewPill.size();
        if(size > 60){
            recentViewPill = recentViewPill.subList(size - 60,size);
        }

        history.setRecentViewPill(recentViewPill);

        historyRepository.save(history);
    }

    public List<Long> getRecentViewPill(Member member) {
        History history = historyRepository.findById(member.getId()).orElse(null);

        if(history == null){
            return null;
        }

        return history.getRecentViewPill();
    }
}
