SELECT name
FROM customers
WHERE id IN(SELECT id_customers
    FROM legal_person);