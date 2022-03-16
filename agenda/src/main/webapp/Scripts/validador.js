/**
 * validacaoo de formulario
 
 */
 
 function Validar(){
	let nome= formContato.nome.value
	let fone= formContato.fone.value
	let email= formContato.email.value
	
	if(nome===""){
		alert("Preencha o campo nome")
		formContato.nome.focus()
		return false
	}else if(fone ===""){
		alert("Preencha o campo fone")
		formContato.fone.focus()
		return false
	}else if(email ===""){
		alert("Preencha o campo email")
		formContato.fone.focus()
		return false
	}else{
		document.forms["formContato"].submit();
	}
	
}