package gdg.hongik.mission.common.message;

public class ErrorMessage {

    //DTO
    public static final String PRODUCT_NOT_FOUND = "상품을 찾을 수 없습니다";
    public static final String PRODUCT_ALREADY_EXISTS = "이미 존재하는 상품입니다";

    //Product
    public static final String PRODUCT_NAME_NOT_NULL = "상품명은 필수입니다";
    public static final String PRODUCT_NAME_SIZE = "상품명은 4자 이상 20자 이하";
    public static final String PRODUCT_STOCK_NOT_ENOUGH = "상품명은 4자 이상 20자 이하";

    //Order
    public static final String USER_CART_NONE = "사용자에게 장바구니가 존재하지 않습니다";
    public static final String USER_CART_EMPTY = "장바구니에 상품이 존재하지 않습니다";
}
