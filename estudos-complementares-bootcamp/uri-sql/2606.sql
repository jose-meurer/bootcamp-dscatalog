SELECT p.id, p.name
FROM products p
INNER JOIN categories c ON p.id_categories = c.id
WHERE UPPER(c.name) LIKE UPPER('SUPER%');