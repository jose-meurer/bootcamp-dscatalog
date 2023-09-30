SELECT prod.name, prov.name
FROM products prod
INNER JOIN providers prov 
    ON prod.id_providers = prov.id
WHERE UPPER(prov.name) = UPPER('Ajax SA');