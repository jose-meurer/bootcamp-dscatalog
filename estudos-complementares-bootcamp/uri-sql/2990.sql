SELECT e.cpf, e.enome, d.dnome
FROM empregados e
INNER JOIN departamentos d ON e.dnumero = d.dnumero
LEFT JOIN trabalha t ON t.cpf_emp = e.cpf
LEFT JOIN projetos p ON p.pnumero = t.pnumero
WHERE t.cpf_emp IS NULL
ORDER BY e.cpf