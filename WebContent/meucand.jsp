<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<jsp:include page="cabecalho.jsp">
	<jsp:param value="Meus candidatos" name="titulo"/>
</jsp:include>
<c:if test="${sessionScope.meucand == 1}">
<p><span class="label label-default">Novas notificações: ${nott}</span></p>
<c:forEach items="${meuscandidatos}" var="meucan">
	<form method='post' action='candidato'>
	<input type='hidden' name='acao' value='histo'>
	<input type='hidden' name='idd' value='${meucan.nome }'>
		 <div class="row col-xs-12 bottom-divisor">
         <div class="col-sm-2">
         	<img src="${meucan.url}" class="img-responsive mini-profile img-circle" alt="">
      	 </div>
      	 <div class="col-sm-10 padding-top1">
         	<h2>${meucan.nome}</h2>
         	<p><span class="label label-default">${meucan.numero}</span> Candidato a <b>${meucan.cargo}</b> em <b>${meucan.cidade}</b> pelo partido <b>${meucan.partido}</b></p>
         	<p><span class="label label-default">Notificações desse candidato: ${meucan.contandonotificacoes}</span></p>
         	       <div id="success"></div>
      		 <div class="row">
           <div class="form-group col-xs-12">
               <button type="submit" class="btn btn-success btn-lg">Ir até candidato</button>&nbsp;
           </div>
       </div>
         	<c:if test="${empty sessionScope.usuario}">
         	
         	</c:if>
      	 </div>
       </div>  
	</form>
	</c:forEach>
	</c:if>
<c:if test="${empty sessionScope.usuario}">
	<meta http-equiv='refresh' content='0, url=index'>
</c:if>
<jsp:include page="rodape.jsp"></jsp:include>