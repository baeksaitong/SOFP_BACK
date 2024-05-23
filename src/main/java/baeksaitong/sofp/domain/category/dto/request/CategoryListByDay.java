package baeksaitong.sofp.domain.category.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public class CategoryListByDay {
    private List<Long> profileIdList;
    private String day;
}
