package baeksaitong.sofp.global.common.collection;

import jakarta.persistence.Id;
import lombok.Builder;
import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Data
@Builder
@Document(collection = "history")
public class History {
    @Id
    private String id;
    private List<Long> recentViewPill;

}
