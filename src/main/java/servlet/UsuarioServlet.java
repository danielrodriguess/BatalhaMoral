package servlet;

import java.io.IOException;
import java.net.URISyntaxException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.CandidatoDao;
import dao.UsuarioDao;
import model.Candidato;
import model.Usuario;
import util.Criptografia;
import util.ReCaptchaImpl;
import util.ReCaptchaResponse;
import util.SendMail;

/**
 * Servlet implementation class IndexServlet
 */
@WebServlet("/usuario")
public class UsuarioServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	UsuarioDao usuarioDao;
	CandidatoDao candidatoDao;
      public static String link,senha,id,codigo,email;
      public static Integer iddouser;
    /**
     * @see HttpServlet#HttpServlet()
     */
    public UsuarioServlet() {
        usuarioDao = new UsuarioDao();
        candidatoDao = new CandidatoDao();
    }

	/**
	 * @see HttpServlet#service(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String acao = request.getParameter("acao");
		if ("cadastrar".equals(acao)) {
			cadastrar(request, response);
		} else if ("logar".equals(acao)) {
			iddouser = 0;
			logar(request, response);
		} else if ("salvar".equals(acao)) {
			try {
				salvar(request, response);
			} catch (URISyntaxException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} else if ("esqueci".equals(acao)) {
			esqueci(request, response);
		} else if ("sair".equals(acao)) {
			iddouser = 0;
			sair(request, response);
		} else if ("recuperar".equals(acao)) {
			recuperar(request, response);
		} else if ("codigo".equals(acao)) {
			Usuario u = new Usuario();
			id = request.getParameter("id");
			senha = request.getParameter("hash");
			link = "http://batalhamoral.herokuapp.com/usuario?acao=codigo&id="+request.getParameter("id")+"&hash="+request.getParameter("hash");
			u = usuarioDao.buscarPorLink(senha, link);
			if(usuarioDao.esqueceu == 1){
				encaminharRequisicao(request, response, "codigo.jsp");
				usuarioDao.esqueceu = 0;
			}else{
				request.setAttribute("mensagemErro", "Link inválido e/ou expirou :(");
				encaminharRequisicao(request, response, "usuario-recuperar.jsp");
			}
		}else if ("codtentativa".equals(acao)) {
			codtentativa(request, response);
		}  else if ("senha".equals(acao)) {
			id = request.getParameter("id");
			senha = request.getParameter("hash");
			link = "http://batalhamoral.herokuapp.com/usuario?acao=codigo&id="+request.getParameter("id")+"&hash="+request.getParameter("hash");
			usuarioDao.buscarPorLink(senha, link);
			if(usuarioDao.esqueceu == 1){
				encaminharRequisicao(request, response, "novasenha.jsp");
				usuarioDao.esqueceu = 0;
			}else{
				request.setAttribute("mensagemErro", "Link inválido e/ou expirou :(");
				encaminharRequisicao(request, response, "usuario-recuperar.jsp");
			}
		} else if ("alterarsenha".equals(acao)) {
			alterarsenha(request, response);
		} else if ("cancelar".equals(acao)) {
			usuarioDao.cancelarSolicitacao(link,senha);
			encaminharRequisicao(request, response, "login.jsp");
		} else if ("verificar".equals(acao)) {
			codigo = request.getParameter("hash");
			email = request.getParameter("email");
			verificar(request, response);
		} else {
			mostrarLogin(request, response);
		}
	}
	
	private void sair(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.getSession().invalidate();
		encaminharRequisicao(request, response, "index");
	}
	
	private void verificar(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		usuarioDao.verificarconf(codigo,email);
		if(usuarioDao.salvar == 1){
			request.setAttribute("mensagemSucesso", "Cadastro efetuado com sucesso");
			encaminharRequisicao(request, response, "login.jsp");
		}else if(usuarioDao.salvar == 2){
			request.setAttribute("mensagemErro", "Link inválido");
			encaminharRequisicao(request, response, "index");
		}
	}
	
	private void alterarsenha(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Usuario u = capturarNovaSenha(request, response);
		usuarioDao.mudarsenha(u);
		request.setAttribute("mensagemSucesso", "Senha alterada com sucesso! Faça login e aproveite");
		encaminharRequisicao(request, response, "login.jsp");
	}
	
	private void codtentativa(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Usuario u = capturarRecuperacao(request, response);
		usuarioDao.verificarcodigo(u);
		if(usuarioDao.esqueceu == 1){
			encaminharRequisicao(request, response, "usuario?acao=senha&id="+id+"&hash="+senha);
		}else{
			request.setAttribute("mensagemErro", "Código inválido");
			encaminharRequisicao(request, response, "usuario?acao=codigo&id="+id+"&hash="+senha);
		}	
	}
	
	private void cadastrar(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		encaminharRequisicao(request, response, "usuario-form.jsp");
	}
	
	private void mostrarLogin(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		encaminharRequisicao(request, response, "login.jsp");
	}
	
	private void esqueci(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		encaminharRequisicao(request, response, "usuario-recuperar.jsp");
	}
	
	private void recuperar(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Usuario usuario = capturarUsuario(request, response);
		usuario = usuarioDao.buscarPorEmailRecuperacaoDeSenha(usuario);
		try {
			String c = usuarioDao.codigo;
			if(c.equals("codigo")){
				usuarioDao.inserirRecuperar(usuario);
				if(usuarioDao.ver == 0){
					SendMail email = new SendMail();
					email.send(usuario.getEmail(), usuario.getCodigo(),usuario.getLink(),usuario.getSenha(),usuario.getNome());
					request.setAttribute("mensagemSucesso", "Clique no link que enviamos no seu e-mail");
					request.setAttribute("lembrete", usuario.getLembrete());
					encaminharRequisicao(request, response, "usuario-recuperar.jsp");
				}else if(usuarioDao.ver == 2){
					request.setAttribute("mensagemErro", "E-mail não encontrado");
					encaminharRequisicao(request, response, "usuario-recuperar.jsp");
				}else{
					request.setAttribute("mensagemErro", "Esse e-mail já possui uma solicitação em aberto");
					encaminharRequisicao(request, response, "usuario-recuperar.jsp");
				}
			}else{
				request.setAttribute("mensagemErro", "Confirme seu e-mail por favor");
				encaminharRequisicao(request, response, "usuario-recuperar.jsp");
			}
		} catch (Exception e) {
			request.setAttribute("mensagemErro", "Não foi possível encontrar o email informado :(");
		}
	}
	
	private void logar(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Usuario usuarioForm = capturarUsuario(request, response);
		Usuario usuarioBanco = usuarioDao.buscarPorEmail(usuarioForm);
		if(usuarioDao.testar == 1){
			candidatoDao.meucad(usuarioBanco);
			String destino = "";
			
			String senhaCriptografada = "";
					
			try {
				senhaCriptografada = Criptografia.criptografar(usuarioForm.getSenha());
			} catch (NoSuchAlgorithmException e) {
				System.out.println(e.getMessage());
			}
			
			if (senhaCriptografada.equals(usuarioBanco.getSenha())) {
				String cod = usuarioDao.codigo;
				if(!cod.equals("codigo")){
					request.setAttribute("mensagemErro", "Você precisa confirmar seu e-mail");
					destino = "login.jsp";
				}else if(cod.equals("codigo")){
					request.getSession().setAttribute("nott", candidatoDao.not);
					request.getSession().setAttribute("meucand", candidatoDao.tem);
					request.getSession().setAttribute("usuario", usuarioBanco);
					request.setAttribute("mensagemSucesso", "Login efetuado com sucesso");
					destino = "index";
				}
			} else {
				request.setAttribute("mensagemErro", "E-mail ou senha inválidos");
				destino = "login.jsp";
			}
			encaminharRequisicao(request, response, destino);
		}else{
			request.setAttribute("mensagemErro", "E-mail ou senha inválidos");
			encaminharRequisicao(request, response, "login.jsp");			
		}
	}
	
	private void salvar(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, URISyntaxException {
		Usuario usuario = capturarUsuario(request, response);
		try {
			usuario.setSenha(Criptografia.criptografar(usuario.getSenha()));
		} catch (NoSuchAlgorithmException e) {
			System.out.println(e.getMessage());
		}
		if(verificaCaptcha(request, response)) {
			usuarioDao.salvar(usuario);
			if(usuarioDao.salvar == 1){
				request.setAttribute("mensagemErro", "E-mail já cadastrado");
				encaminharRequisicao(request, response, "usuario-form.jsp");
			}else{
				int codigo = 500000+(int)(600000*Math.random());
				String cod = ""+codigo;
				try {
					String a = Criptografia.criptografar(cod);
					String link = "https://batalhamoral.herokuapp.com/usuario?acao=verificar&hash="+a+"&email="+usuario.getEmail();
					SendMail bemvindo = new SendMail();
					usuarioDao.atualizarcodigo(usuario.getEmail(),a);
					bemvindo.sendBemVindo(usuario.getEmail(), usuario.getNome(),link);
					request.setAttribute("mensagemSucesso", "Usuario cadastrado com sucesso. Basta clicar no link para confirmar");
					encaminharRequisicao(request, response, "login.jsp");
				} catch (NoSuchAlgorithmException e) {
					System.out.println(e.getMessage());
				}
			}
		} else {
			request.setAttribute("captcha", "Por favor responda a verificação  humana antes de cadastrar");
			encaminharRequisicao(request, response, "usuario-form.jsp");
		}
	}
	
	private Usuario capturarNovaSenha(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Usuario u = new Usuario();
		String senha1 = request.getParameter("senha");
		u.setSenha(senha1);
		u.setLink(link);
		u.setSenha1(senha);		
		return u;
	}
	
	private Usuario capturarRecuperacao(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Usuario u = new Usuario();
		String codigo = request.getParameter("codigo");
		u.setLink(link);
		u.setCodigo(codigo);
		u.setSenha(senha);
		return u;
	}
	
	private Usuario capturarUsuario(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Usuario u = new Usuario();
		u.setNome(request.getParameter("nome"));
		u.setEmail(request.getParameter("email"));
		u.setSenha(request.getParameter("senha"));
		u.setLembrete(request.getParameter("lembrete"));
		return u;
	}
	
	private Boolean verificaCaptcha(HttpServletRequest request, HttpServletResponse response) throws URISyntaxException {
		ReCaptchaImpl reCaptcha  = new ReCaptchaImpl("6LdkawgUAAAAAIRFPtr8xXxIHjkdBOsFLAsYl-p-","6LdkawgUAAAAANNM2O56UWPuKk2TBkAxTJGXSBjl");
		ReCaptchaResponse reCaptchaResponse = reCaptcha.verifyResponse(request.getParameter("g-recaptcha-response"), null);
		return reCaptchaResponse.isSuccess();
	}
	
	private void encaminharRequisicao(HttpServletRequest request, HttpServletResponse response, String caminho) throws ServletException, IOException {
		RequestDispatcher rd = request.getRequestDispatcher(caminho);
		rd.forward(request, response);
	}
}
