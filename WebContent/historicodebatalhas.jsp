<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<jsp:include page="cabecalho.jsp">
	<jsp:param value="Histórico de batalhas - ${nomee}" name="titulo"/>
</jsp:include>

	<c:forEach items="${listaCandidatos}" var="candidato">
       <div class="row col-xs-12 bottom-divisor">
         <div class="col-sm-2">
         	<img src="${candidato.url}" class="img-responsive mini-profile img-circle" alt="">
      	 </div>
      	 <div class="col-sm-10 padding-top1">
         	<h2>${candidato.nome}</h2>
         	           <c:if test="${idd == candidato.idvenc}">
           ${nomee} venceu este candidato
           </c:if>    
           <c:if test="${idd != candidato.idvenc}">
           ${nomee} perdeu para este candidato
           </c:if>  
      	 </div>
       </div>  
    </c:forEach>            

<jsp:include page="rodape.jsp"></jsp:include>