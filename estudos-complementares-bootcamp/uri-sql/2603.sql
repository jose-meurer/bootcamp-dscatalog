SELECT name, street
FROM customers
WHERE UPPER(city) = UPPER('PORTO ALEGRE');