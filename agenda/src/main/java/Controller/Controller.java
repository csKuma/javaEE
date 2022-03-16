package Controller;

import java.io.IOException;
import java.util.ArrayList;

import com.itextpdf.text.Document;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import Model.DAO;
import Model.JavaBeans;

@WebServlet(urlPatterns = { "/main", "/insert", "/select", "/update", "/delete", "/report" })
public class Controller extends HttpServlet {
	private static final long serialVersionUID = 1L;
	DAO dao = new DAO();
	JavaBeans contato = new JavaBeans();

	public Controller() {
		super();

	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		String action = request.getServletPath();

		if (action.equals("/main")) {
			Contatos(request, response);

		} else if (action.equals("/insert")) {
			adicionarContato(request, response);
			// redirecionar para o documento agenda.jsps

		} else if (action.equals("/select")) {
			listarContato(request, response);

		} else if (action.equals("/delete")) {
			excluirContato(request, response);

		} else if (action.equals("/report")) {
			gerarRelatorio(request, response);

		} else if (action.equals("/update")) {
			editarContato(request, response);
			response.sendRedirect("main");

		} else {
			response.sendRedirect("Index.html");
		}

	}

	// Listar Contatos
	protected void Contatos(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// response.sendRedirect("agenda.jsp");
		// criando um objeto que irar receber os dados de javabens

		ArrayList<JavaBeans> lista = dao.listarcontatos();

		// emcaminhar a lista para agenda.jsp

		request.setAttribute("contatos", lista);
		RequestDispatcher rd = request.getRequestDispatcher("agenda.jsp");
		rd.forward(request, response);

//		for (int i=0;i<lista.size(); i++) {
//			System.out.println(lista.get(i).getIdcon());
//			System.out.println(lista.get(i).getNome());
//			System.out.println(lista.get(i).getFone());
//			System.out.println(lista.get(i).getEmail());
//			
//		}
//	    for (int i=0;i<lista.size(); i++) {
//			out.println(lista.get(i).getIdcon());
//			out.println(lista.get(i).getNome());
//			out.println(lista.get(i).getFone());
//			out.println(lista.get(i).getEmail());
//	}

	}

	// novo contato
	protected void adicionarContato(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

//		System.out.println(request.getParameter("nome"));
//		System.out.println(request.getParameter("fone"));
//		System.out.println(request.getParameter("email"));

		// setar as variaveis javabeans
		contato.setNome(request.getParameter("nome"));
		contato.setFone(request.getParameter("fone"));
		contato.setEmail(request.getParameter("email"));

		// invocar o metodo inserir contato passando o objeto contato como parametro
		dao.insertContatos(contato);
		response.sendRedirect("main");
	}

	// selecionar contato
	protected void listarContato(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// recebendo o id do contato que será editado
		String idcon = request.getParameter("idcon");

		// setando a variavel javabeans
		contato.setIdcon(idcon);
		// executar o metodo selecionar contato
		dao.selecionarContato(contato);

		// setar os atributos do formulario com conteudo javabenas
		request.setAttribute("idcon", contato.getIdcon());
		request.setAttribute("nome", contato.getNome());
		request.setAttribute("fone", contato.getFone());
		request.setAttribute("email", contato.getEmail());

		RequestDispatcher rd = request.getRequestDispatcher("editar.jsp");
		rd.forward(request, response);
	}

	// editar contato
	protected void editarContato(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// teste de recebibemto
//		System.out.println(request.getParameter("idcon"));
//		System.out.println(request.getParameter("nome"));
//		System.out.println(request.getParameter("fone"));
//		System.out.println(request.getParameter("email"));

		// setar as variaveis javabenas
		contato.setIdcon(request.getParameter("idcon"));
		contato.setNome(request.getParameter("nome"));
		contato.setFone(request.getParameter("fone"));
		contato.setEmail(request.getParameter("email"));

		dao.editarContato(contato);
	}

	// excluir contato
	protected void excluirContato(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// teste de recebibemto
		// System.out.println(request.getParameter("idcon"));

		contato.setIdcon(request.getParameter("idcon"));

		// executar o metodo deletar contato
		dao.deletarContato(contato);
		response.sendRedirect("main");

	}

	// gerar relatorio
	protected void gerarRelatorio(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		Document documento = new Document();

		try {

			// tipo de contéudo
			response.setContentType("application/pdf");

			// nome do documento
			response.addHeader("Contente-Disposition", "inline; filename=" + "Contatos.pdf");

			// criar documento
			PdfWriter.getInstance(documento, response.getOutputStream());

			// Abrir documento pra gerar o conteudo
			documento.open();
			documento.add(new Paragraph("lista de contatos:"));
			documento.add(new Paragraph(" "));

			// criar uma tabela
			PdfPTable tabela = new PdfPTable(3);

			// cabeçalho
			PdfPCell col1 = new PdfPCell(new Paragraph("Nome"));
			PdfPCell col2 = new PdfPCell(new Paragraph("Fone"));
			PdfPCell col3 = new PdfPCell(new Paragraph("Email"));
			tabela.addCell(col1);
			tabela.addCell(col2);
			tabela.addCell(col3);

			// populando tabela
			ArrayList<JavaBeans> lista = dao.listarcontatos();
			for (int i = 0; i < lista.size(); i++) {
				tabela.addCell(lista.get(i).getNome());
				tabela.addCell(lista.get(i).getFone());
				tabela.addCell(lista.get(i).getEmail());
			}
			documento.add(tabela);
			documento.close();

		} catch (Exception e) {
			System.out.println(e);
			documento.close();
		}

	}

}
