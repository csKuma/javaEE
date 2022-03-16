package Model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

// TODO: Auto-generated Javadoc
/**
 * The Class DAO.
 */
public class DAO {
	
	/**  Módulo de conexão. */
	// Parametros de conexão
	private String driver = "com.mysql.cj.jdbc.Driver";
	
	/** The url. */
	private String url = "jdbc:mysql://localhost:3306/dbagenda?useTimezone=true&serverTimezone=UTC";
	
	/** The user. */
	private String user = "root";
	
	/** The password. */
	private String password = "kuma1234";

	/**
	 * Conectar.
	 *
	 * @return the connection
	 */
	// Método de conexão
	private Connection conectar() {
		Connection con = null;
		try {
			Class.forName(driver);
			con = DriverManager.getConnection(url, user, password);
			return con;
		} catch (Exception e) {
			System.out.println(e);
			return null;
		}
	}

	/**
	 * Insert contatos.
	 *
	 * @param contato the contato
	 */
	// crud create
	public void insertContatos(JavaBeans contato) {
		String create = "insert Into contatos (nome,fone,email) values (?,?,?)";

		try {
			// abri conexao
			Connection con = conectar();

			// preparar a query para execuçao no banco de dados
			PreparedStatement pst = con.prepareStatement(create);
			pst.setString(1, contato.getNome());
			pst.setString(2, contato.getFone());
			pst.setString(3, contato.getEmail());

			// execultar a query
			pst.executeUpdate();

			// encerrar a conexão
			con.close();
		} catch (Exception e) {
			System.out.println(e);
		}
	}

	/**
	 * Listarcontatos.
	 *
	 * @return the array list
	 */
	// crud Read
	public ArrayList<JavaBeans> listarcontatos() {
		String read = "select * from contatos order by nome";
		ArrayList<JavaBeans> contatos = new ArrayList<>();
		try {

			// abri conexao
			Connection con = conectar();
			PreparedStatement pst = con.prepareStatement(read);
			ResultSet rs = pst.executeQuery();

			// o laço abaixo será execultado enquanto houver contatos
			while (rs.next()) {
				// variaveis de apoio

				String Idcon = rs.getString(1);
				String nome = rs.getString(2);
				String fone = rs.getString(3);
				String email = rs.getString(4);

				// populando o arrayList
				contatos.add(new JavaBeans(Idcon, nome, fone, email));
			}
			// encerrar a conexão
			con.close();
			return contatos;
		} catch (Exception e) {
			System.out.println(e);
			return null;
		}

	}

	/**
	 * Selecionar contato.
	 *
	 * @param contato the contato
	 */
	// crud Update
	public void selecionarContato(JavaBeans contato) {
		String read = "select * from contatos where idcon=?";
		try {
			Connection con = conectar();
			PreparedStatement pst = con.prepareStatement(read);
			pst.setString(1, contato.getIdcon());
			ResultSet rs = pst.executeQuery(); // executa a instrução sql
			while (rs.next()) {
				// variaveis de apoio

				contato.setIdcon(rs.getString(1));
				contato.setNome(rs.getString(2));
				contato.setFone(rs.getString(3));
				contato.setEmail(rs.getString(4));
			}
			// encerrar a conexão
			con.close();

		} catch (Exception e) {
			System.out.println(e);

		}

	}

	/**
	 * Editar contato.
	 *
	 * @param contato the contato
	 */
	// editar contato
	public void editarContato(JavaBeans contato) {
		String update = "update contatos set nome=?, fone=?, email=? where idcon=?";
		try {
			Connection con = conectar();
			PreparedStatement pst = con.prepareStatement(update);

			pst.setString(1, contato.getNome());
			pst.setString(2, contato.getFone());
			pst.setString(3, contato.getEmail());
			pst.setString(4, contato.getIdcon());
			pst.executeUpdate();

			// encerrar a conexão
			con.close();

		} catch (Exception e) {
			System.out.println(e);

		}

	}

	/**
	 * Deletar contato.
	 *
	 * @param contato the contato
	 */
	// delete contato
	public void deletarContato(JavaBeans contato) {

		String delete = "delete from contatos where idcon=?";
		try {
			Connection con = conectar();
			PreparedStatement pst = con.prepareStatement(delete);

			pst.setString(1, contato.getIdcon());
			pst.executeUpdate();

			// encerrar a conexão
			con.close();

		} catch (Exception e) {
			System.out.println(e);

		}

	}

//	//Teste De Conexão
//	
//	public void testeConexao() {
//		try {
//			Connection con = conectar();
//			System.out.println(con);
//			
//		} catch (Exception e) {
//			System.out.println(e);
//		}
//	}
}
