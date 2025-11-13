package gdg.hongik.mission.dto.response;

import gdg.hongik.mission.dto.request.ProductDeleteRequest;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class ProductDeleteResponse {

        private List<Item> items;


        @Getter
        @Setter
        public static class Item{
            private String name;
            private Long stock;

    }
}
