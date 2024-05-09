package baeksaitong.sofp.domain.health.service;

import baeksaitong.sofp.domain.health.dto.response.DiseaseRes;
import baeksaitong.sofp.domain.health.repository.DiseaseAndAllergyRepository;
import baeksaitong.sofp.global.common.entity.DiseaseAndAllergy;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class HealthService {

    private final DiseaseAndAllergyRepository diseaseAndAllergyRepository;
    public DiseaseRes getDiseaseAndAllergyList() {
        List<String> diseaseList = diseaseAndAllergyRepository.findAll()
                .stream()
                .map(DiseaseAndAllergy::getName)
                .collect(Collectors.toList());

        return new DiseaseRes(diseaseList);
    }
}
