SELECT p.name, providers.name
FROM products p
INNER JOIN providers ON p.id_providers = providers.id
WHERE p.id_categories = 6;