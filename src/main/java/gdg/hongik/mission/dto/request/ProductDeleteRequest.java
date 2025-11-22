package gdg.hongik.mission.dto.request;

import lombok.Getter;

import java.util.List;

@Getter

public class ProductDeleteRequest {
    // 💡 단 하나의 필드에 리스트를 담습니다.
    private List<Long> productIds;
    // Getters, Setters, Constructors 필요
}