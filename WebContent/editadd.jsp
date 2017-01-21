<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<jsp:include page="cabecalho.jsp">
	<jsp:param value="Editar vínculo" name="titulo"/>
</jsp:include>
<c:if test="${not empty listac}">
	<form name="candidato" id="contactForm" action="add" method="post">
		<input type="hidden" name="acao" value="editar">
       <div class="row control-group">	      
	           <div class="form-group col-xs-12 floating-label-form-group controls">
	               <label>Selecione o candidato</label>
	              <select name='candidato' style='width:240px;height:25px;cursor:pointer;'>
	              <option value="Selecione o candidato">Selecione o candidato</option>
		      		  <c:forEach var="lc" items="${listac}">
			        	  <option value=${lc.id }>${lc.nome} ${lc.numero}</option>
		      		  </c:forEach>
	    		  </select>
	               <p class="help-block text-danger"></p>
	           </div>      
	       	</div>
       <div class="row control-group">
           <div class="form-group col-xs-12 floating-label-form-group controls">
               <label>Selecione o usuário</label>
               <select name='usuario' style='width:240px;height:25px;cursor:pointer;'>
               <option value="Selecione o usuário">Selecione o usuário</option>
	      		  <c:forEach var="lu" items="${listau}">
		        	  <option value=${lu.id }>${lu.email}</option>
	      		  </c:forEach>
    		  </select>
               <p class="help-block text-danger"></p>
           </div>
       </div>
       <br>
       <div id="success"></div>
       <div class="row">
           <div class="form-group col-xs-12">
               <button type="submit" class="btn btn-success btn-lg">Vincular</button>&nbsp;
               <a href="add?acao=vertudo" class="btn btn-default btn-lg">Cancelar</a>
           </div>
       </div>
   </form>
        </c:if>
        <c:if test="${empty listac}">
        <center><p>Nenhum candidato está disponível</p></center>
        </c:if>    	
<jsp:include page="rodape.jsp"></jsp:include>