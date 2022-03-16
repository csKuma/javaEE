/**
 * confirmacaoo de exclisão de contato
 * param idcon
 */
 
 
 function confirmar(idcon){
	let resposta=confirm("confirmar a exclusão deste contato?")
	if(resposta == true){
		//alert(idcon);
		window.location.href="delete?idcon="+idcon
	}
}