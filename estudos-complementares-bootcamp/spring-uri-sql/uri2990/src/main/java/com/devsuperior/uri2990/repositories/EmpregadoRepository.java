package com.devsuperior.uri2990.repositories;

import com.devsuperior.uri2990.dto.EmpregadoDeptDTO;
import com.devsuperior.uri2990.entities.Empregado;
import com.devsuperior.uri2990.projections.EmpregadoDeptProjection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EmpregadoRepository extends JpaRepository<Empregado, Long> {


    @Query(nativeQuery = true, value =
            "SELECT e.cpf, e.enome, d.dnome " +
            "FROM empregados e " +
            "INNER JOIN departamentos d ON e.dnumero = d.dnumero " +
            "LEFT JOIN trabalha t ON t.cpf_emp = e.cpf " +
            "LEFT JOIN projetos p ON p.pnumero = t.pnumero " +
            "WHERE t.cpf_emp IS NULL " +
            "ORDER BY e.cpf")
    List<EmpregadoDeptProjection> searchSQL();

    @Query("SELECT new com.devsuperior.uri2990.dto.EmpregadoDeptDTO(e.cpf, e.enome, e.departamento.dnome) " +
            "FROM Empregado e " +
            "WHERE e.cpf NOT IN " +
            "( " +
            "SELECT e.cpf " +
            "FROM Empregado e " +
            "INNER JOIN e.projetosOndeTrabalha " +
            ") " +
            "ORDER BY e.cpf")
    List<EmpregadoDeptDTO> searchJPQL();
}
