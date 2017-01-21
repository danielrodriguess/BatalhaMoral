<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<jsp:include page="cabecalho.jsp">
	<jsp:param value="Cadastro de Usuário" name="titulo"/>
</jsp:include>
<c:if test="${not empty sessionScope.usuario}">
	<meta http-equiv='refresh' content='0, url=index'>
</c:if>
<c:if test="${empty sessionScope.usuario}">
	<form name="usuario" id="contactForm" action="usuario" method="post">
		<input type="hidden" name="acao" value="salvar">
       <div class="row control-group">
           <div class="form-group col-xs-12 floating-label-form-group controls">
               <label>Nome</label>
               <input type="text" name="nome" class="form-control" placeholder="Nome" id="name" required data-validation-required-message="Informe o nome do usuario">
               <p class="help-block text-danger"></p>
           </div>
       </div>
       
       <div class="row control-group">
           <div class="form-group col-xs-12 floating-label-form-group controls">
               <label>E-mail</label>
               <input type="email" name="email" class="form-control" placeholder="E-mail" id="email" required data-validation-required-message="Informe o e-mail">
               <p class="help-block text-danger"></p>
           </div>
       </div>
       
       <div class="row control-group">
           <div class="form-group col-xs-12 floating-label-form-group controls">
               <label>Senha</label>
               <input type="password" name="senha" class="form-control" placeholder="Senha" id="senha" required data-validation-required-message="Informe sua senha">
               <p class="help-block text-danger"></p>
           </div>
       </div>
       <div class="row control-group">
           <div class="form-group col-xs-12 floating-label-form-group controls">
               <label>Lembrete</label>
               <input type="text" name="lembrete" class="form-control" placeholder="Lembrete de senha" id="lembrete" required data-validation-required-message="Informe o lembrete">
               <p class="help-block text-danger"></p>
           </div>
       </div>
      <div class="col-sm-12 text-center margin-top5">
				<c:if test="${not empty captcha}">
				<div class="alert alert-danger alert-dismissible" role="alert">
					<button type="button" class="close" data-dismiss="alert"
						aria-label="Close">
						<span aria-hidden="true">&times;</span>
					</button>
					<strong>Oooops!</strong> ${captcha}
				</div>
				</c:if>
				<div class="confirmation g-recaptcha" data-sitekey="6LdkawgUAAAAAIRFPtr8xXxIHjkdBOsFLAsYl-p-"></div>
			</div>                  
       <br>
           <p>Já possui cadastro? <a href="usuario">Efetue login</a></p>
       <br>
       <div id="success"></div>
       <div class="row">
           <div class="form-group col-xs-12">
               <button type="submit" class="btn btn-success btn-lg">Cadastrar</button>&nbsp;
               <a href="usuario" class="btn btn-default btn-lg">Cancelar</a>
           </div>
       </div>
   </form>              	
</c:if>

<jsp:include page="rodape.jsp"></jsp:include>