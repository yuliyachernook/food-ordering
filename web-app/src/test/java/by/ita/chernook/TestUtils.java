package by.ita.chernook;

public class TestUtils {
    protected static final String REQUEST_CREATE_CARTITEM = "/cart/item/create?";
    protected static final String REQUEST_UPDATE_CARTITEM = "/cart/item/update?";
    protected static final String REQUEST_READ_ALL_CART_ITEMS_BY_CART_UUID = "/cart/item/read/all/cart/%s";
    protected static final String REQUEST_TRANSFER = "/cart/item/transfer?";
    protected static final String REQUEST_DELETE_CARTITEM = "/cart/item/delete?uuid=%s";
    protected static final String REQUEST_CART_CREATE = "/cart/create";
    protected static final String REQUEST_DELETE_ALL_CART_ITEMS_BY_CART_UUID = "/cart/item/delete/all/cart/%s";

    protected static final String REQUEST_DELIVERY_ADDRESS_DELETE = "/customer/delete/address?uuid=%s";

    protected static final String REQUEST_USER_READ_ALL = "/user/read/all";
    protected static final String REQUEST_USER_READ_USER_BY_LOGIN = "/user/read/login/";
    protected static final String REQUEST_USER_READ_CUSTOMER_BY_ID = "/customer/read?uuid={uuid}";
    protected static final String REQUEST_USER_READ_CUSTOMER_BY_USER_UUID = "/customer/read/user/";


}
