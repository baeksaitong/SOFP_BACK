package baeksaitong.sofp.domain.history.collection;

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
    private Long id;
    private List<Long> recentViewPill;

}
