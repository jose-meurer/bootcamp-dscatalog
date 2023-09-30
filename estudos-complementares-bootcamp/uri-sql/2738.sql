SELECT c.name, 
    ROUND(((sc.math*2) + (sc.specific*3) + (project_plan*5))/10, 2) as avg
FROM candidate c
INNER JOIN score sc ON c.id = sc.candidate_id
ORDER BY avg desc;