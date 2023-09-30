SELECT p.name
FROM products p
INNER JOIN providers prov 
    ON p.id_providers = prov.id
WHERE UPPER(prov.name) LIKE UPPER('P%')
    AND p.amount > 10 AND p.amount < 20;