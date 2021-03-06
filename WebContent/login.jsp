<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<jsp:include page="cabecalho.jsp">
	<jsp:param value="Entrar no Batalha Moral" name="titulo"/>
</jsp:include>
<c:if test="${not empty sessionScope.usuario}">
	<meta http-equiv='refresh' content='0, url=index'>
</c:if>
<c:if test="${empty sessionScope.usuario}">
	<form name="usuario" id="contactForm" action="usuario" method="post">
		<input type="hidden" name="acao" value="logar">
       <div class="row control-group">
           <div class="form-group col-xs-12 floating-label-form-group controls">
               <label>E-mail</label>
               <input type="text" name="email" class="form-control" placeholder="Seu e-mail" id="email" required data-validation-required-message="Informe seu e-mail">
               <p class="help-block text-danger"></p>
           </div>
       </div>
       <div class="row control-group">
           <div class="form-group col-xs-12 floating-label-form-group controls">
               <label>Senha</label>
               <input type="password" name="senha" class="form-control" placeholder="Sua senha" id="senha" required data-validation-required-message="Informe sua senha">
               <p class="help-block text-danger"></p>
           </div>
       </div>
       <br>
       <p><a href="usuario?acao=esqueci">Esqueceu sua senha</a> ou <a href="usuario?acao=cadastrar">ainda n�o � cadastrado?</a></p>
       <br>
       <div class="row">
           <div class="form-group col-xs-12">
               <button type="submit" class="btn btn-success btn-lg">Entrar</button>&nbsp;
               <a href="index" class="btn btn-default btn-lg">Cancelar</a>
           </div>
       </div>
   </form> 	  
   </c:if>
<jsp:include page="rodape.jsp"></jsp:include>