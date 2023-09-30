package model.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import db.DB;
import db.DbException;
import model.dao.SellerDao;
import model.entities.Department;
import model.entities.Seller;

public class SellerDaoJDBC implements SellerDao{

	private Connection conn;
	
	public SellerDaoJDBC(Connection conn) {
		this.conn = conn;
	}
	
	@Override
	public void insert(Seller obj) {
		// Implementei de maneira diferente
		String sql = "INSERT INTO seller "
				+ "(Name, Email, BirthDate, BaseSalary, DepartmentId) "
				+ "VALUES "
				+ "(?, ?, ?, ?, ?)";
		try (PreparedStatement st = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
			conn.setAutoCommit(false); //Testando desativar o commit auto e implementando o rollback caso der errado o comando
	
			st.setString(1, obj.getName());
			st.setString(2, obj.getEmail());
			st.setDate(3, new java.sql.Date(obj.getBirthDate().getTime()));
			st.setDouble(4, obj.getBaseSalary());
			st.setInt(5, obj.getDepartment().getId());
			
			int rowsAffected = st.executeUpdate(); //Retorna a quantidade de linhas alteradas
			
			conn.commit();
			
			if (rowsAffected > 0) {
				try (ResultSet rs = st.getGeneratedKeys()){
					if (rs.next()) {
						int id = rs.getInt(1); //Captura a coluna apos o comando SQL, neste caso, 1 = RETURN_GENERATED_KEYS
						obj.setId(id);
					}
				}
			}
			else {
				throw new DbException("Unexpected error! No rows affected");
			}
			
		}catch (SQLException e) {
			try{
				conn.rollback();
			throw new DbException("Insert data rolled back! Cause by: " + e.getMessage());
			}
			catch (SQLException e1) {
				throw new DbException("Error trying to rollback! Caused by: " + e1.getMessage());
			}
		}
	}

	@Override
	public void update(Seller obj) {
		// Implementei de maneira diferente
		String sql = "UPDATE seller "
				+ "SET Name = ?, Email = ?, BirthDate = ?, BaseSalary = ?, DepartmentId = ? "
				+ "WHERE Id = ?";
		try (PreparedStatement st = conn.prepareStatement(sql)) { 
	
			st.setString(1, obj.getName());
			st.setString(2, obj.getEmail());
			st.setDate(3, new java.sql.Date(obj.getBirthDate().getTime()));
			st.setDouble(4, obj.getBaseSalary());
			st.setInt(5, obj.getDepartment().getId());
			st.setInt(6,obj.getId());
			
			st.execute(); //execute nao retorna nada
		}
		catch (SQLException e) {
			throw new DbException(e.getMessage());
		}
	}

	@Override
	public void deleteById(Integer id) {
		// Implementei de maneira diferente
		String sql = "DELETE FROM seller "
				+ "WHERE Id = ?";
		try (PreparedStatement st = conn.prepareStatement(sql)){
			
			st.setInt(1, id);
			
			st.execute();
		}
		catch (SQLException e) {
			throw new DbException(e.getMessage());
		}
	}

	@Override
	public Seller findById(Integer id) {
		//Implementacao do professor
		PreparedStatement st = null;
		ResultSet rs = null;
		try {
			st = conn.prepareStatement(
					"SELECT seller.*, department.Name as DepName " // Pesquisa todas as categorias da tabela seller e a coluna name da tabela department(apelidando ele de DepName)(Operacao: Projecao)
					+ "FROM seller " // Pesquisa na tabela seller
					+ "INNER JOIN department " // Junta as duas tabelas (Operacao: Produto cartesiano)
					+ "ON seller.DepartmentId = department.Id " // Filtra apenas os elementos onde a chave estrangeira deparmentID seja igual ao ID da tabela deparment
					+ "WHERE seller.id = ?"); // Filtra a tabela onde o seller.id seja igual ao valor informado
			
			st.setInt(1, id);
			rs = st.executeQuery(); // Retorna a tabela com os valores(ResultSet)
			
			if (rs.next()) {
				Department dep = instantiateDepartment(rs);
				Seller seller =  instantiateSeller(rs, dep);
				return seller;
			}
			return null;
			
		}catch (SQLException e) {
			throw new DbException(e.getMessage());
		}finally {
			DB.closeStatement(st);
			DB.closeResultSet(rs);
		}
	}

	private Seller instantiateSeller(ResultSet rs, Department dep) throws SQLException {
		Seller seller = new Seller();
		seller.setId(rs.getInt("Id"));
		seller.setName(rs.getString("Name"));
		seller.setEmail(rs.getString("Email"));
		seller.setBirthDate(rs.getTimestamp("BirthDate"));  //getDate retorna apenas dd/mm/yyyy, para recuperar com a hora, precisa usar getTimestramp
		seller.setBaseSalary(rs.getDouble("BaseSalary"));
		seller.setDepartment(dep);
		return seller;
	}

	private Department instantiateDepartment(ResultSet rs) throws SQLException {
		Department dep = new Department();
		dep.setId(rs.getInt("DepartmentId"));
		dep.setName(rs.getString("DepName"));
		return dep;
	}

	@Override
	public List<Seller> findAll() {
		// Implementei de outra maneira para testar
		String sql = "SELECT seller.*, department.Name as DepName "
				+ "FROM seller INNER JOIN department "
				+ "ON seller.DepartmentId = department.Id "
				+ "ORDER BY Name asc";
		try (PreparedStatement st = conn.prepareStatement(sql)) {

			try (ResultSet rs = st.executeQuery()) {
				List<Seller> list = new ArrayList<>();
				Map<Integer, Department> map = new HashMap<>();
				
				while (rs.next()) {
					Department dep = map.get(rs.getInt("DepartmentId"));
					
					if (dep == null) {
						dep = instantiateDepartment(rs);
						map.put(rs.getInt("DepartmentId"), dep);
					}
					Seller seller = instantiateSeller(rs, dep);
					list.add(seller);
				}
				return list;
			}
		}catch (SQLException e) {
			throw new DbException(e.getMessage());
		}
	}

	@Override
	public List<Seller> findByDepartment(Department department) {
		PreparedStatement st = null;
		ResultSet rs = null;
		try {
			st = conn.prepareStatement(
					"SELECT seller.*, department.Name as DepName "
					+ "FROM seller INNER JOIN department "
					+ "ON seller.DepartmentId = department.Id "
					+ "WHERE DepartmentId = ? "
					+ "ORDER BY Name asc");
			
			st.setInt(1, department.getId());
			rs = st.executeQuery();
			
			List<Seller> list = new ArrayList<>();
			Map<Integer, Department> map = new HashMap<>();
			
			while (rs.next()) {
				Department dep = map.get(rs.getInt("DepartmentId"));
				
				if (dep == null) {
					dep = instantiateDepartment(rs);
					map.put(rs.getInt("DepartmentId"), dep);
				}
				Seller seller = instantiateSeller(rs, dep);
				list.add(seller);
			}
			return list;
			
		}
		catch (SQLException e) {
			throw new DbException(e.getMessage());
		}
		finally {
			DB.closeResultSet(rs);
			DB.closeResultSet(rs);
		}
	}
}
