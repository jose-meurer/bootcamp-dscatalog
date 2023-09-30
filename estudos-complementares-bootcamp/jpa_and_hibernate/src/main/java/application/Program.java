package application;

import entities.Person;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class Program {

	public static void main(String[] args) {

		List<Person> personList = new ArrayList<>();

		Person p1 = new Person(null, "Carlos da Silva", "carlos@gmail.com");
		Person p2 = new Person(null, "Joaquim Torres", "joaquim@gmail.com");
		Person p3 = new Person(null, "Ana Maria", "ana@gmail.com");

		personList.add(p1);
		personList.add(p2);
		personList.add(p3);

		EntityManagerFactory emf = Persistence.createEntityManagerFactory("jpa_hibernate");
		EntityManager em = emf.createEntityManager(); // Cria a ponte com o banco;

		/**
		 * inserir no banco de dados
		 */

//		em.getTransaction().begin(); // Inicia a conexao com o banco;
//		personList.forEach(em::persist);
//		em.getTransaction().commit(); // Salva os dados no banco;

		
		/** 
		 * Buscar no banco de dados 
		 */
		
		Person p = em.find(Person.class, 2);
		
		System.out.println(p);
		
		
		/**
		 *  Para excluir do banco de dados
		 */
		p = em.find(Person.class, 2);
		em.getTransaction().begin();
		em.remove(p); //JPA é monitorada, entao precisamos primeiro recuperar do banco para depois excluir;
		em.getTransaction().commit(); //Se o dado acabou de ser inserido sem fechar a ponte, nao precisa recuperar no banco;
		
		
		em.close();
		emf.close(); // Fecha as pontes;
		System.out.println("Ok!");

	}
}
