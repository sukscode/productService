--Creation of table Category
CREATE TABLE IF NOT EXISTS category (
    id BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    description VARCHAR(500),
    created_at DATETIME DEFAULT CURRENT_TIMESTAMP,
    last_updated_at DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    is_deleted BOOLEAN DEFAULT FALSE
);

-- Insert categories
INSERT INTO category (name, description) VALUES
    ('Electronics', 'Devices and gadgets'),
    ('Clothing', 'Apparel for men and women'),
    ('Books', 'Fiction and non-fiction books'),
    ('Home', 'Home appliances and furniture');

-- Creation of table Product
CREATE TABLE IF NOT EXISTS product (
    id BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    title VARCHAR(255) NOT NULL,
    price DECIMAL(10,2) NOT NULL,
    description TEXT,
    category_id BIGINT,
    image_url VARCHAR(255),
    created_at DATETIME DEFAULT CURRENT_TIMESTAMP,
    last_updated_at DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    is_deleted BOOLEAN DEFAULT FALSE,
    FOREIGN KEY (category_id) REFERENCES category(id)
    );

-- Electronics
INSERT INTO product (title, price, description, category_id, image_url)
SELECT 'iPhone 15', 799.99, 'Latest Apple iPhone', id, 'iphone15.jpg' FROM category WHERE name='Electronics';

INSERT INTO product (title, price, description, category_id, image_url)
SELECT 'Samsung Galaxy S23', 699.99, 'Samsung flagship phone', id, 'galaxy_s23.jpg' FROM category WHERE name='Electronics';

INSERT INTO product (title, price, description, category_id, image_url)
SELECT 'Apple AirPods Pro', 249.99, 'Noise-cancelling earbuds', id, 'airpods_pro.jpg' FROM category WHERE name='Electronics';

INSERT INTO product (title, price, description, category_id, image_url)
SELECT 'Sony WH-1000XM5', 349.99, 'Wireless over-ear headphones', id, 'sony_headphones.jpg' FROM category WHERE name='Electronics';

-- Clothing
INSERT INTO product (title, price, description, category_id, image_url)
SELECT 'Leather Jacket', 199.99, 'Stylish men''s jacket', id, 'leather_jacket.jpg' FROM category WHERE name='Clothing';

INSERT INTO product (title, price, description, category_id, image_url)
SELECT 'Yoga Mat', 29.99, 'Eco-friendly yoga mat', id, 'yoga_mat.jpg' FROM category WHERE name='Clothing';

INSERT INTO product (title, price, description, category_id, image_url)
SELECT 'Running Shoes', 89.99, 'Comfortable sports shoes', id, 'running_shoes.jpg' FROM category WHERE name='Clothing';

INSERT INTO product (title, price, description, category_id, image_url)
SELECT 'Denim Jeans', 49.99, 'Blue slim fit jeans', id, 'denim_jeans.jpg' FROM category WHERE name='Clothing';

-- Books
INSERT INTO product (title, price, description, category_id, image_url)
SELECT 'The Alchemist', 9.99, 'Novel by Paulo Coelho', id, 'alchemist.jpg' FROM category WHERE name='Books';

INSERT INTO product (title, price, description, category_id, image_url)
SELECT 'Harry Potter and the Philosopher''s Stone', 12.99, 'Fantasy novel', id, 'hp1.jpg' FROM category WHERE name='Books';

INSERT INTO product (title, price, description, category_id, image_url)
SELECT 'Atomic Habits', 14.99, 'Self-help book', id, 'atomic_habits.jpg' FROM category WHERE name='Books';

INSERT INTO product (title, price, description, category_id, image_url)
SELECT 'The Subtle Art of Not Giving a F*ck', 16.99, 'Self-improvement book', id, 'subtle_art.jpg' FROM category WHERE name='Books';

-- Home
INSERT INTO product (title, price, description, category_id, image_url)
SELECT 'Cookware Set', 59.99, 'Non-stick cookware', id, 'cookware_set.jpg' FROM category WHERE name='Home';

INSERT INTO product (title, price, description, category_id, image_url)
SELECT 'Vacuum Cleaner', 129.99, 'Powerful home vacuum', id, 'vacuum_cleaner.jpg' FROM category WHERE name='Home';

INSERT INTO product (title, price, description, category_id, image_url)
SELECT 'LED Desk Lamp', 39.99, 'Adjustable desk lamp', id, 'desk_lamp.jpg' FROM category WHERE name='Home';

INSERT INTO product (title, price, description, category_id, image_url)
SELECT 'Memory Foam Pillow', 49.99, 'Comfortable sleep pillow', id, 'memory_pillow.jpg' FROM category WHERE name='Home';
