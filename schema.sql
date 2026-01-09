-- =============================================
-- GraveRun Sneaker E-Commerce Database Setup
-- =============================================

CREATE DATABASE IF NOT EXISTS graverun;
USE graverun;

-- ==========================
-- USERS TABLE
-- ==========================
DROP TABLE IF EXISTS users;
CREATE TABLE users (
    id INT AUTO_INCREMENT PRIMARY KEY,
    full_name VARCHAR(255) NOT NULL,
    email VARCHAR(255) UNIQUE NOT NULL,
    username VARCHAR(255) UNIQUE NOT NULL,
    password_hash VARCHAR(255) NOT NULL,
    phone VARCHAR(50),
    address TEXT,
    role ENUM('customer', 'admin') DEFAULT 'customer',
    is_active BOOLEAN DEFAULT TRUE,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- ==========================
-- PRODUCTS TABLE
-- ==========================
DROP TABLE IF EXISTS products;
CREATE TABLE products (
    product_id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    brand VARCHAR(100),
    category VARCHAR(100),
    description TEXT,
    price DECIMAL(10,2) NOT NULL,
    stock_quantity INT DEFAULT 50,
    image_url VARCHAR(500),
    sales_count INT,
    size ENUM('US 7/EU 40','US 8/EU 41','US 9/EU 42.5','US 10/EU 44','US 11/EU 45')
        DEFAULT 'US 7/EU 40',
    color VARCHAR(50),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- ==========================
-- CART TABLE
-- ==========================
DROP TABLE IF EXISTS cart_items;
CREATE TABLE cart_items (
    id INT AUTO_INCREMENT PRIMARY KEY,
    user_id INT NOT NULL,
    product_id INT NOT NULL,
    quantity INT DEFAULT 1,
    added_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE,
    FOREIGN KEY (product_id) REFERENCES products(product_id) ON DELETE CASCADE
);

-- ==========================
-- FAVORITES TABLE
-- ==========================
DROP TABLE IF EXISTS favorites;
CREATE TABLE favorites (
    id INT AUTO_INCREMENT PRIMARY KEY,
    user_id INT NOT NULL,
    product_id INT NOT NULL,
    added_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE,
    FOREIGN KEY (product_id) REFERENCES products(product_id) ON DELETE CASCADE
);

-- ==========================
-- ORDERS TABLE
-- ==========================
DROP TABLE IF EXISTS orders;
CREATE TABLE orders (
    id INT AUTO_INCREMENT PRIMARY KEY,
    user_id INT NOT NULL,
    total_amount DECIMAL(10,2) NOT NULL,
    shipping_address TEXT,
    payment_method ENUM('Esewa','Khalti','Card') NOT NULL,
    status ENUM('Pending','Processing','Shipped','Delivered','Cancelled')
        DEFAULT 'Pending',
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE
);

-- ==========================
-- ORDER ITEMS TABLE
-- ==========================
DROP TABLE IF EXISTS order_items;
CREATE TABLE order_items (
    id INT AUTO_INCREMENT PRIMARY KEY,
    order_id INT NOT NULL,
    product_id INT NOT NULL,
    quantity INT DEFAULT 1,
    price DECIMAL(10,2) NOT NULL,
    FOREIGN KEY (order_id) REFERENCES orders(id) ON DELETE CASCADE,
    FOREIGN KEY (product_id) REFERENCES products(product_id) ON DELETE CASCADE
);

-- ==========================
-- INSERT SAMPLE USERS
-- ==========================
INSERT INTO users (full_name, email, username, password_hash, phone, role) VALUES
('Jibesh Shrestha', 'jibeshshrestha1621@gmail.com', 'jibesh1621', '123456', '9808404997', 'customer'),
('Saksham Raj Shrestha ', 'sakshamshrestha@gmail.com', 'admin_srs', 'admin123', '9705398711', 'admin');

-- ==========================
-- INSERT SAMPLE PRODUCTS
-- ==========================
INSERT INTO products
(name, brand, category, description, price, stock_quantity, image_url, sales_count, size, color)
VALUES
('Adidas Yeezy Boost 700 V3 Azael','Adidas','Running',
 'Futuristic metallic silver and white design with Boost cushioning',
 28800,50,'adidas.jpg',150,'US 9/EU 42.5','Silver/White'),

('Air Jordan 14 Golf NRG Bordeaux','Jordan','Casual',
 'Premium quilted leather golf edition in white, black, and bordeaux',
 33840,50,'AIR+JORDAN+14+G+NRG.jpg',250,'US 10/EU 44','White/Black/Bordeaux'),

('Joma R.2000 Italia Edition','Joma','Performance',
 'Lightweight multicolor running shoe with Fly Reactive technology',
 20160,50,'Joma.jpg',200,'US 8/EU 41','Multicolor'),

('Nike Dunk Low Retro Panda Orange','Nike','Basketball',
 'Classic black/white leather with bold orange accents',
 16560,50,'NIKE+DUNK+LOW+RETRO.jpg',175,'US 9/EU 42.5','Black/White/Orange'),

('Lil Nas X Blood Moon Edition','GraveRun','Performance',
 'Crafted for the Fearless. Exclusive Blood Moon design with premium materials and superior comfort.',
 15999.00,55,'/images/blood_moon.png',150,'US 9/EU 42.5','Black'),

('Shadow Runner Pro','Puma','Performance',
 'High-performance running shoes with advanced cushioning and breathable mesh upper.',
 8999.00,100,'/images/shadow_runner.png',230,'US 8/EU 41','Grey'),

('Phantom Strike','Converse','Basketball',
 'Professional basketball shoes with enhanced grip and ankle support.',
 11999.00,75,'/images/phantom_strike.png',180,'US 10/EU 44','Multicolor'),

('Night Walker Classic','GoldStar','Casual',
 'Timeless design meets modern comfort. Perfect for everyday wear.',
 6999.00,120,'/images/night_walker.png',320,'US 8/EU 41','White/Black/Bordeaux'),

('Dark Velocity','Reebok','Casual',
 'Cross-training shoes built for versatility and performance.',
 10999.00,60,'/images/dark_velocity.png',190,'US 9/EU 42.5','Black/White/Orange'),

('Ghost Grip','Netbalance','Running',
 'Skateboarding shoes with reinforced toe cap and superior board feel.',
 7999.00,80,'/images/ghost_grip.png',240,'US 10/EU 44','Multicolor');

-- ==========================
-- SAFE SCHEMA CHANGES
-- ==========================
ALTER TABLE products MODIFY size VARCHAR(255);
ALTER TABLE users MODIFY full_name VARCHAR(255) DEFAULT '';
ALTER TABLE products DROP COLUMN color;

-- ==========================
-- SAFE UPDATE (KEY-BASED)
-- ==========================
UPDATE products
SET size = 'US 7/EU 40, US 8/EU 41, US 9/EU 42.5, US 10/EU 44, US 11/EU 45'
WHERE product_id > 0;

-- ==========================
-- SAFE DELETE (KEY-BASED)
-- ==========================
DELETE FROM products
WHERE name = 'The North Face OFFTRAIL MID GORE TEX - Hiking shoes'
AND product_id > 0;

-- ==========================
-- VIEW DATA
-- ==========================
SELECT * FROM users;
SELECT * FROM products;
SELECT * FROM cart_items;
SELECT * FROM favorites;
SELECT * FROM orders;
SELECT * FROM order_items;
