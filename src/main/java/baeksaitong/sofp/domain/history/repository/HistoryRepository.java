package baeksaitong.sofp.domain.history.repository;

import baeksaitong.sofp.global.common.collection.History;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface HistoryRepository extends MongoRepository<History, Long> {
}
