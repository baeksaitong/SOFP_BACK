package baeksaitong.sofp.domain.health.service;

import baeksaitong.sofp.domain.health.dto.response.AllergyRes;
import baeksaitong.sofp.domain.health.repository.AllergyRepository;
import baeksaitong.sofp.global.common.entity.Allergy;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class HealthService {

    private final AllergyRepository allergyRepository;

    public AllergyRes getAllergyList() {

        List<String> allergyList = allergyRepository.findAll()
                .stream()
                .map(Allergy::getName)
                .collect(Collectors.toList());

        return new AllergyRes(allergyList);
    }
}
