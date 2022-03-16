<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ page import="Model.JavaBeans"%>
<%@ page import="java.util.ArrayList"%>
<%
@SuppressWarnings("unchecked")
ArrayList<JavaBeans> lista = (ArrayList<JavaBeans>) request.getAttribute("contatos");
%>
<!DOCTYPE html>
<html lang="pt-br">
<head>
<meta charset="utf-8">
<title>Agenda de Contato</title>
<link rel="Icon" href="Imagens/Phone.png">
<link rel="stylesheet" href="style.css">
</head>
<body>
	<h1>Agenda de Contatos</h1>
	<a href="novo.html" class="Botao">Novo Contato</a>
	<a href="report" Class="Botao2">Relatorio</a>
	<table id="tabela">
		<thead>
			<tr>
				<th>ID</th>
				<th>NOME</th>
				<th>FONE</th>
				<th>E-MAil</th>
			</tr>
		</thead>
		<tbody>
			<%
			for (int i = 0; i < lista.size(); i++) {
			%>
			<tr>
				<td><%=lista.get(i).getIdcon()%></td>
				<td><%=lista.get(i).getNome()%></td>
				<td><%=lista.get(i).getFone()%></td>
				<td><%=lista.get(i).getEmail()%></td>

				<td><a href="select?idcon=<%=lista.get(i).getIdcon()%>"
					class="Botao">Editar</a></td>
				<td><a href="javascript:confirmar(<%=lista.get(i).getIdcon()%>)"
					class="Botao2">Excluir</a></td>
			</tr>
			<%
			}
			%>
		</tbody>
	</table>
	<script src="Scripts/confirmacao.js"></script>
</body>
</html>