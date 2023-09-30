SELECT p.name, prov.name, p.price
FROM products p 
INNER JOIN providers prov 
    ON p.id_providers = prov.id
INNER JOIN categories c 
    ON p.id_categories = c.id
WHERE p.price > 1000.0 
    AND UPPER(c.name) = UPPER('Super Luxury');