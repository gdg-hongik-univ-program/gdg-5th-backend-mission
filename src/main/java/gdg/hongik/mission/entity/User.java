package gdg.hongik.mission.entity;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "USER S")
@Getter
@NoArgsConstructor

public class  User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;


    public User(Long id, String name) {
        this.id = id;
        this.name = name;
    }
}
