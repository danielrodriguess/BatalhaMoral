<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<jsp:include page="cabecalho.jsp">
	<jsp:param value="Digite o código" name="titulo"/>
</jsp:include>
<c:if test="${not empty sessionScope.usuario}">
	<meta http-equiv='refresh' content='0, url=index'>
</c:if>
<c:if test="${empty sessionScope.usuario}">
	<form name="candidato" id="contactForm" action="usuario" method="post">
		<input type="hidden" name="acao" value="codtentativa">
       <div class="row control-group">
           <div class="form-group col-xs-12 floating-label-form-group controls">
               <label>Código</label>
               <input type="text" name="codigo" class="form-control" placeholder="Digite o código" id="codigo" required data-validation-required-message="Informe o código">
               <p class="help-block text-danger"></p>
           </div>
       </div>
       <br>
       <div id="success"></div>
       <div class="row">
           <div class="form-group col-xs-12">
               <button type="submit" class="btn btn-success btn-lg">Continuar</button>&nbsp;
               <a href="usuario?acao=cancelar" class="btn btn-default btn-lg">Cancelar</a>
           </div>
       </div>
   </form>              	
</c:if>
<jsp:include page="rodape.jsp"></jsp:include>