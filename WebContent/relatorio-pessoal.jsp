<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<jsp:include page="cabecalho.jsp">
	<jsp:param value="Histórico" name="titulo"/>
</jsp:include>
<c:if test="${not empty sessionScope.usuario}">
<div class="container margin-top3">
	<img src="${listaCandidatos.url}" class="img-responsive small-profile img-circle" alt="">
	<h2 class="padding-top2">${listaCandidatos.nome}</h2>
	<p>Candidato a <b>${listaCandidatos.cargo}</b> em <b>${listaCandidatos.cidade}</b> pelo partido <b>${listaCandidatos.partido}</b> - Número <b>${listaCandidatos.numero}</b></p>
	<form method='post' action='/candidato/${listaCandidatos.nome1}/historico'>
	<input type='hidden' name='acao' value='historico'>
	<input type='hidden' name='idd' value='${listaCandidatos.nome}'>
				<div id="success"></div>
	        <div class="row">
	           <div class="form-group col-xs-12">
	               <button type="submit" class="btn btn-success btn-lg">Histórico de batalhas</button>&nbsp;
	           </div>
	        </div> 
	</form>
	<h2 class="padding-top2">Estatísticas</h2>
	<p>Batalhas: <b>${numero}</b><br>
	Vitórias: <b>${vitoria}</b><br>
	Derrotas: <b>${numero - vitoria}</b><br>
	<c:if test="${numero == 0}">
		Aceitação: <b>Não disputou nenhuma batalha ainda</b></p><br>
	</c:if>
		<c:if test="${numero != 0}">
		Aceitação: <b>${vitoria / numero * 100}%</b></p><br>
	</c:if>
	</div>
	<c:if test="${usuario.id == iddessecad}">
	<center><h2 class="padding-top2">Perguntas anteriores</h2>
	<c:forEach items="${listapergunta}" var="perguntar">
		<b>Pergunta: </b>${perguntar.pergunta }<br>
			<c:if test="${not empty perguntar.respostas}">
				<b>Resposta: </b>${perguntar.respostas}<br>
							<form action='/candidato' method='post'>
					<input type='hidden' name='acao' value='responder'>
					<input type='hidden' name='ido' value='${perguntar.id }'>
					<input type='hidden' name='iddd' value='${perguntar.pergunta }'>
					<textarea name='resposta' rows='4' cols='50' style='resize: none;'></textarea>
			<div id="success"></div>
	        <div class="row">
	           <div class="form-group col-xs-12">
	               <button type="submit" class="btn btn-success btn-lg">Editar resposta</button>&nbsp;
	           </div>
	        </div> 
				</form>
			</c:if>
			<c:if test="${empty perguntar.respostas}">
				<b>Responda agora</b><br>
				
				<form action='/candidato' method='post'>
					<input type='hidden' name='acao' value='responder'>
					<input type='hidden' name='ido' value='${perguntar.id }'>
					<input type='hidden' name='iddd' value='${perguntar.pergunta }'>
					<textarea name='resposta' rows='4' cols='50' style='resize: none;'></textarea>
			<div id="success"></div>
	        <div class="row">
	           <div class="form-group col-xs-12">
	               <button type="submit" class="btn btn-success btn-lg">Enviar resposta</button>&nbsp;
	           </div>
	        </div> 
				</form>
				<form action='/candidato' method='post'>
			<input type='hidden' name='acao' value='excluirpergunta'>
				<input type='hidden' name='ido' value='${perguntar.id }'>
				<input type='hidden' name='iddd' value='${perguntar.pergunta }'>
			<div id="success"></div>
	        <div class="row">
	           <div class="form-group col-xs-12">
	               <button type="submit" class="btn btn-success btn-lg">Remover pergunta</button>&nbsp;
	           </div>
	        </div>   
		</form>
			</c:if>
	</c:forEach></center>
	</c:if>
	<c:if test="${usuario.id != iddessecad}">
	<c:if test="${listaCandidatos.idresp != 0}">
		<center><h2 class="padding-top2">Perguntas</h2>
		<form action='/candidato' method='post'>
			<input type='hidden' name='acao' value='perguntar'>
			<input type='hidden' name='iddocad' value='${listaCandidatos.id }'>
			<input type='hidden' name='iddapessoa' value='${usuario.id }'>
			<textarea name='pergunta' rows='4' cols='50' style='resize: none;'></textarea><br>
			<input type="checkbox" name="email" value="email">Deseja receber notificação no seu email quando alguém responder?<BR><br>
			<div id="success"></div>
	        <div class="row">
	           <div class="form-group col-xs-12">
	               <button type="submit" class="btn btn-success btn-lg">Enviar pergunta</button>&nbsp;
	           </div>
	        </div>   
		</form>
	<h2 class="padding-top2">Perguntas anteriores</h2>
	<c:forEach items="${listapergunta}" var="perguntar">
		<b>Pergunta: </b>${perguntar.pergunta }<br>
			<c:if test="${not empty perguntar.respostas}">
				<b>Resposta: </b>${perguntar.respostas}<br><br><br>
			</c:if>
			<c:if test="${empty perguntar.respostas}">
				<b>Sem respostas ainda</b><br><br><br>
			</c:if>
	</c:forEach>
	
	
	</center> 
	</c:if>
	<c:if test="${listaCandidatos.idresp == 0}">
		<center><h2 class="padding-top2">Não tem nenhum perfil administrando esse candidato. Tente mais tarde</h2>
	</c:if>
	</c:if>
</c:if>
<c:if test="${empty sessionScope.usuario}">
	<meta http-equiv='refresh' content='0, url=/index'>
</c:if>
<jsp:include page="rodape.jsp"></jsp:include>