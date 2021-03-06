<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<jsp:include page="cabecalho.jsp">
	<jsp:param value="Cadastro de Candidato" name="titulo"/>
</jsp:include>
<c:if test="${not empty sessionScope.usuario}">
	<form name="candidato" id="contactForm" action="candidato" method="post">
		<input type="hidden" name="acao" value="salvar">
		<input type="hidden" name="id" value="${candidato.id}">
       <div class="row control-group">
           <div class="form-group col-xs-12 floating-label-form-group controls">
               <label>Nome</label>
               <input type="text" name="nome" value="${candidato.nome}" class="form-control" placeholder="Nome" id="name" required data-validation-required-message="Informe o nome do candidato">
               <p class="help-block text-danger"></p>
           </div>
       </div>
       
       <div class="row control-group">
           <div class="form-group col-xs-12 floating-label-form-group controls">
               <label>Cargo</label>
               <input type="text" name="cargo" value="${candidato.cargo}" class="form-control" placeholder="Cargo" id="cargo" required data-validation-required-message="Informe o cargo">
               <p class="help-block text-danger"></p>
           </div>
       </div>
       
       <div class="row control-group">
           <div class="form-group col-xs-12 floating-label-form-group controls">
               <label>Foto</label>
               <input type="text" name="url" value="${candidato.url}" class="form-control" placeholder="Url da imagem" id="url" required data-validation-required-message="Inclua a url da foto">
               <p class="help-block text-danger"></p>
           </div>
       </div>
       <div class="row control-group">
           <div class="form-group col-xs-12 floating-label-form-group controls">
               <label>Cidade</label>
               <input type="text" name="cidade" value="${candidato.cidade}" class="form-control" placeholder="Cidade" id="cidade" required data-validation-required-message="Informe a cidade">
               <p class="help-block text-danger"></p>
           </div>
       </div>                    
       <div class="row control-group">
           <div class="form-group col-xs-12 floating-label-form-group controls">
               <label>Partido</label>
               <input type="text" name="partido" value="${candidato.partido}" class="form-control" placeholder="Partido" id="partido" required data-validation-required-message="Informe o partido">
               <p class="help-block text-danger"></p>
           </div>
       </div>
       <div class="row control-group">
           <div class="form-group col-xs-12 floating-label-form-group controls">
               <label>N�mero</label>
               <input type="text" name="numero" value="${candidato.numero}" class="form-control" placeholder="N�mero" id="numero" required data-validation-required-message="Informe o n�mero">
               <p class="help-block text-danger"></p>
           </div>
       </div>
       <br>
       <div id="success"></div>
       <div class="row">
           <div class="form-group col-xs-12">
               <button type="submit" class="btn btn-success btn-lg">Salvar Candidato</button>&nbsp;
               <a href="candidato?acao=listar" class="btn btn-default btn-lg">Cancelar</a>
           </div>
       </div>
   </form>              	
</c:if>
<c:if test="${empty sessionScope.usuario}">
	<meta http-equiv='refresh' content='0, url=/index'>
</c:if>
<jsp:include page="rodape.jsp"></jsp:include>