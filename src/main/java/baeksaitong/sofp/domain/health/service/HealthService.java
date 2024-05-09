package baeksaitong.sofp.domain.health.service;

import baeksaitong.sofp.domain.health.repository.DiseaseAllergyRepository;
import baeksaitong.sofp.global.common.entity.DiseaseAllergy;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class HealthService {
    private final DiseaseAllergyRepository diseaseAllergyRepository;
    public List<String> getDiseaseAllergyList() {
        return diseaseAllergyRepository.findAll().stream()
                .map(DiseaseAllergy::getName)
                .collect(Collectors.toList());
    }

    public List<String> searchDiseaseAllergyList(String keyword) {
        return diseaseAllergyRepository.findByKeyword(keyword);
    }
}
