package gdg.hongik.mission.dto;

import java.util.List;
import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "물품 삭제 요청 (INPUT)")
public class DeleteRequest {
    @Schema(description = "삭제할 물품 이름 목록 (1개 이상)", example = "[\"apple\", \"banana\"]")
    private List<String> names;

    public DeleteRequest() {}
    public List<String> getNames() { return names; }
    public void setNames(List<String> names) { this.names = names; }
}