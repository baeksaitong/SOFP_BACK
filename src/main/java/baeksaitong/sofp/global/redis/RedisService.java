package baeksaitong.sofp.global.redis;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;

import java.time.Duration;

@Service
@RequiredArgsConstructor
@Slf4j
public class RedisService {

    private final RedisTemplate<String, Object> redisTemplate;

    public void save(String key, String data) {
        ValueOperations<String, Object> values = redisTemplate.opsForValue();
        values.set(key, data);
    }

    public void save(String key, String data, Duration duration) {
        ValueOperations<String, Object> values = redisTemplate.opsForValue();
        values.set(key, data, duration);
    }

    public void save(RedisPrefix prefix, String key, String data) {
        ValueOperations<String, Object> values = redisTemplate.opsForValue();
        values.set(prefix.name() + key, String.valueOf(data));
    }

    public void save(RedisPrefix prefix, String key, String data, Duration duration) {
        ValueOperations<String, Object> values = redisTemplate.opsForValue();
        values.set(prefix.name() + key, data, duration);
    }

    public String get(String key) {
        ValueOperations<String, Object> values = redisTemplate.opsForValue();
        return (String) values.get(key);
    }

    public String get(RedisPrefix prefix, String key) {
        ValueOperations<String, Object> values = redisTemplate.opsForValue();
        return (String) values.get(prefix.name() + key);
    }

    public void delete(String key) {
        redisTemplate.delete(key);
    }

    public void delete(RedisPrefix prefix, String key) {
        redisTemplate.delete(prefix.name() + key);
    }

    public Boolean hasKey(String key) {
        return redisTemplate.hasKey(key);
    }

    public Boolean hasKey(RedisPrefix prefix, String key) {
        return redisTemplate.hasKey(prefix.name() + key);
    }

    public Boolean hasNoKey(String key) {
        return !redisTemplate.hasKey(key);
    }

    public Boolean hasNoKey(RedisPrefix prefix, String key) {
        return !redisTemplate.hasKey(prefix.name() + key);
    }
}
