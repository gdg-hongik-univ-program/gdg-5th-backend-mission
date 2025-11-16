package gdg.hongik.mission.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity // 엔티티니까
@Getter // 변수 리턴 get문 자동 작성
@Setter // 변수 설정 set문 자동 작성
@NoArgsConstructor // 기본 생성자 자동 생성
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // id 생성 전략
    private Long id;

    private String name;
    private int price;
    private int stock;
}