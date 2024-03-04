package baeksaitong.sofp.domain.health.service;

import baeksaitong.sofp.domain.health.dto.response.AllergyRes;
import baeksaitong.sofp.domain.health.dto.response.DiseaseRes;
import baeksaitong.sofp.domain.health.repository.AllergyRepository;
import baeksaitong.sofp.domain.health.repository.DiseaseRepository;
import baeksaitong.sofp.global.common.entity.Allergy;
import baeksaitong.sofp.global.common.entity.Disease;
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
    private final DiseaseRepository diseaseRepository;

    public AllergyRes getAllergyList() {

        List<String> allergyList = allergyRepository.findAll()
                .stream()
                .map(Allergy::getName)
                .collect(Collectors.toList());

        return new AllergyRes(allergyList);
    }

    public DiseaseRes getDiseaseList() {
        List<String> diseaseList = diseaseRepository.findAll()
                .stream()
                .map(Disease::getName)
                .collect(Collectors.toList());

        return new DiseaseRes(diseaseList);
    }
}
