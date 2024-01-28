package kea.dpang.eventserver.entity;

// enum으로 사용하지 않은 이유
// enum을 String으로 반환하는 과정은 런타임에서 일어난다.
// Types가 활용되는 곳은 class의 외부 어노테이션의 상수값으로 활용된다.
// 이 때의 상수값은 컴파일에서 결정되어야 하므로 enum 사용이 불가하다.
/**
 * 이벤트의 타입을 나눈 상수
 */
public class Types{
    /**
     * 상품 이벤트 타입을 나타내는 상수
     */
    public static final String ITEM = "item";

    /**
     * 판매처 이벤트 타입을 나타내는 상수
     */
    public static final String SELLER = "seller";
}
