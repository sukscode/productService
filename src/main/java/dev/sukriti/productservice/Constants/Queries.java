package dev.sukriti.productservice.Constants;

public class Queries {

    // ================= Product =================
    public static final String GET_ALL_PRODUCTS =
            "SELECT * FROM product";

    public static final String GET_PRODUCT_BY_ID =
            "SELECT * FROM product WHERE id = :id";

    public static final String DELETE_PRODUCT_BY_ID =
            "DELETE FROM product WHERE id = :id";

    public static final String INSERT_PRODUCT =
            "INSERT INTO product (title, price, description, category_id, image_url, created_at, last_updated_at, is_deleted) " +
                    "VALUES (:title, :price, :description, :categoryId, :imageUrl, NOW(), NOW(), false)";

    public static final String UPDATE_PRODUCT =
            "UPDATE product " +
                    "SET title = COALESCE(:title, title), " +
                    "    price = COALESCE(:price, price), " +
                    "    description = COALESCE(:description, description), " +
                    "    category_id = COALESCE(:categoryId, category_id), " +
                    "    image_url = COALESCE(:imageUrl, image_url), " +
                    "    last_updated_at = NOW() " +
                    "WHERE id = :id";

    public static final String REPLACE_PRODUCT =
            "UPDATE product " +
                    "SET title = :title, " +
                    "    price = :price, " +
                    "    description = :description, " +
                    "    category_id = :categoryId, " +
                    "    image_url = :imageUrl, " +
                    "    last_updated_at = NOW() " +
                    "WHERE id = :id";

    // ================= Category =================
    public static final String GET_CATEGORY_BY_NAME =
            "SELECT * FROM category WHERE name = :name";

    public static final String INSERT_CATEGORY =
            "INSERT INTO category (name, description, created_at, last_updated_at, is_deleted) " +
                    "VALUES (:name, :description, NOW(), NOW(), false)";
}
