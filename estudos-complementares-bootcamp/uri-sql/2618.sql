SELECT p.name, prov.name, c.name
FROM products p 
INNER JOIN providers prov 
    ON p.id_providers = prov.id
INNER JOIN categories c 
    ON p.id_categories = c.id
WHERE UPPER(prov.name) = UPPER('Sansul SA') 
    AND UPPER(c.name) = UPPER('Imported');