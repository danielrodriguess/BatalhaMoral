package servlet;

import java.io.IOException;
import java.util.ArrayList;
import java.util.StringTokenizer;
import java.util.*;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.BatalhaDao;
import dao.CandidatoDao;
import dao.PerguntaDao;
import dao.UsuarioDao;
import model.Candidato;
import model.Perguntas;
import model.Usuario;
import util.SendMail;

/**
 * Servlet implementation class CandidatoServlet
 */
@WebServlet({"/candidato","/candidato/*"})
public class CandidatoServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	CandidatoDao candidatoDao;
	BatalhaDao batalhaDao;
	PerguntaDao pergunta;
	private static String idd = "";
    public CandidatoServlet() {
    	candidatoDao = new CandidatoDao();
    	batalhaDao = new BatalhaDao();
    	pergunta = new PerguntaDao();
    }

	/**
	 * @see HttpServlet#service(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String acao = request.getParameter("acao");
			if ("salvar".equals(acao)) {
				salvar(request, response);
			} else if ("editar".equals(acao)) {
				editar(request, response);
			} else if ("excluir".equals(acao)){
				excluir(request, response);
			} else if ("listar".equals(acao)) {
				listar(request, response);				
			} else if ("relatorio-geral".equals(acao)) {
				mostrarRelatorioGeral(request, response);				
			}else if("hist".equals(acao)){
				mostrarRelatorioPessoal(request,response);
			}else if("histo".equals(acao)){
				mostrarRelatorioPessoalurl(request,response);
			}else if("perguntar".equals(acao)){
				pergunta(request, response);
			}else if("responder".equals(acao)){
				responde(request, response);
			}else if("meuscandidatos".equals(acao)){
				meuscandidatos(request, response);
			}else if("excluirpergunta".equals(acao)){
				removerpergunta(request, response);
			}else if("historico".equals(acao)){
				historico(request, response);
			}else{				
				cadastrar(request, response);
			}
			
	}
	
	private void cadastrar(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		encaminharRequisicao(request, response, "/candidato-form.jsp");
	}
	
	private void meuscandidatos(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String i = request.getParameter("id");
		Integer id = Integer.parseInt(i);
		ArrayList<Candidato> listaCandidatos = new ArrayList<Candidato>();
		listaCandidatos = candidatoDao.meucadd(id);
		request.setAttribute("nott", candidatoDao.not);
		request.setAttribute("meuscandidatos", listaCandidatos);
		encaminharRequisicao(request, response, "/meucand.jsp");
	}
	
	private void historico(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String id = request.getParameter("idd");
		String PrimeiroNome1 = "";
		if(id.contains(" ")){
			int i = id.indexOf(" ");
			id = id.substring(0,i).trim();
			PrimeiroNome1 = id.substring(0,1).toUpperCase() + id.substring(1);
		}else if(id.contains("-")){
			int i = id.indexOf("-");
			id = id.substring(0,i).trim();
			PrimeiroNome1 = id.substring(0,1).toUpperCase() + id.substring(1);
		}else if(!id.contains("-") || !id.contains(" ")){
			PrimeiroNome1 = id.substring(0,1).toUpperCase() + id.substring(1);
		}
		Candidato c = new Candidato();
		Integer a = candidatoDao.contarurl(PrimeiroNome1);
		if(a == 1){
			idd = request.getParameter("idd");
			c = candidatoDao.buscarcadurl(PrimeiroNome1);
			String nome = c.getNome();
			Integer iddocad = c.getId();
			ArrayList<Candidato> lista = new ArrayList<Candidato>();
			lista = candidatoDao.sobreabatalha(c.getId());
			request.setAttribute("listaCandidatos", lista);
			request.setAttribute("nomee", nome);
			request.setAttribute("idd", iddocad);
			encaminharRequisicao(request, response, "historicodebatalhas.jsp");
		}else{
			request.setAttribute("mensagemErro", "Candidato não localizado");
			response.sendRedirect("/candidato?acao=listar");
		}
		/*ArrayList<Candidato> lista = new ArrayList<Candidato>();
		lista = candidatoDao.sobreabatalha(idd);
		request.setAttribute("idd", idd);
		request.setAttribute("listaCandidatos", lista);
		encaminharRequisicao(request, response, "historicodebatalhas.jsp");*/
		
	}
	
	private void removerpergunta(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String perguntaid = request.getParameter("ido");
		Integer id = Integer.parseInt(perguntaid);
		String perguntaa = request.getParameter("iddd");
		pergunta.pegaruser(id);
		pergunta.removerpergunta(id,perguntaa);
		SendMail mail = new SendMail();
		mail.perguntaexcluida(pergunta.email, pergunta.nome, pergunta.nomecad);
		request.setAttribute("mensagemSucesso", "Pergunta excluída");
		encaminharRequisicao(request, response, "candidato?acao=histo&idd="+idd);
	}
	
	private void salvar(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Candidato c = capturarCandidato(request, response);
		
		if (c.getId() == null) {
			candidatoDao.salvar(c);
		} else {
			candidatoDao.atualizar(c);
		}
		
		request.setAttribute("mensagem", "Candidato salvo com sucesso");
		listar(request, response);
	}
	
	private void editar(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Candidato c = capturarCandidato(request, response);
		c = candidatoDao.buscarPorId(c);
		request.setAttribute("candidato", c);
		encaminharRequisicao(request, response, "candidato-form.jsp");
	}
	
	private void excluir(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Candidato c = capturarCandidato(request, response);
		candidatoDao.excluir(c);
		request.setAttribute("mensagem", "Candidato excluído com sucesso");
		listar(request, response);
	}
	
	private void listar(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		ArrayList<Candidato> listaCandidatos = new ArrayList<Candidato>();
		listaCandidatos = candidatoDao.buscarTodos();
		request.setAttribute("listaCandidatos", listaCandidatos);
		encaminharRequisicao(request, response, "candidato-list.jsp");
	}
	
	private void mostrarRelatorioPessoalurl(HttpServletRequest request, HttpServletResponse response)  throws ServletException, IOException {
		String id = request.getParameter("idd");
		String PrimeiroNome1 = "";
		if(id.contains(" ")){
			int i = id.indexOf(" ");
			id = id.substring(0,i).trim();
			PrimeiroNome1 = id.substring(0,1).toUpperCase() + id.substring(1);
		}else if(id.contains("-")){
			int i = id.indexOf("-");
			id = id.substring(0,i).trim();
			PrimeiroNome1 = id.substring(0,1).toUpperCase() + id.substring(1);
		}else if(!id.contains("-") || !id.contains(" ")){
			PrimeiroNome1 = id.substring(0,1).toUpperCase() + id.substring(1);
		}
		Candidato c = new Candidato();
		Integer a = candidatoDao.contarurl(PrimeiroNome1);
		if(a == 1){
			idd = request.getParameter("idd");
			c = candidatoDao.buscarcadurl(PrimeiroNome1);
			Integer numero = candidatoDao.buscarresult(c);
			Integer vitoria = candidatoDao.buscarvitoria(c);
			batalhaDao.totalDeBatalhas();
			Integer totalDeBatalhas = batalhaDao.batalhas;
			Perguntas p = new Perguntas();
			ArrayList<Perguntas> listaPerguntas = new ArrayList<Perguntas>();
			Integer sla = pergunta.veruserecad(c);
			listaPerguntas = pergunta.todasasperguntas(c);
			request.setAttribute("listaCandidatos", c);
			request.setAttribute("numero", numero);
			request.setAttribute("vitoria", vitoria);
			request.setAttribute("total", totalDeBatalhas);
			request.setAttribute("listapergunta", listaPerguntas);
			request.setAttribute("iddessecad", sla);
			encaminharRequisicao(request, response, "relatorio-pessoal.jsp");
		}else{
			request.setAttribute("mensagemErro", "Candidato não localizado");
			response.sendRedirect("/candidato?acao=listar");
		}
	}
	
	private void pergunta(HttpServletRequest request, HttpServletResponse response)  throws ServletException, IOException {
		Perguntas p = capturarPergunta(request,response);
		if(p.getPergunta().equals("")){
			request.setAttribute("mensagemErro", "Preencha o campo com a sua pergunta");
			encaminharRequisicao(request, response, "candidato?acao=histo&idd="+idd);
		}else{
			pergunta.salvar(p);
			encaminharRequisicao(request, response, "candidato?acao=histo&idd="+idd);
		}
	}
	
	private void responde(HttpServletRequest request, HttpServletResponse response)  throws ServletException, IOException {
		String resposta = request.getParameter("resposta");
		String ido = request.getParameter("ido");
		String perguntaa = request.getParameter("iddd");
		int i = Integer.parseInt(ido);
		if(resposta.equals("")){
			request.setAttribute("mensagemErro", "Preencha o campo com a sua resposta");
			encaminharRequisicao(request, response, "candidato?acao=histo&idd="+idd);
		}else{
			pergunta.verificarrespondera(i,perguntaa);
			String verificando = pergunta.resposta;
			if(verificando != null){
				pergunta.responder(resposta, i);
				String verificar = pergunta.veremail(i);
				if(verificar.equals("email")){
					pergunta.pegaruser(i);
					pergunta.vercad();
					String email = pergunta.email;
					String nome = pergunta.nome;
					String candidato = pergunta.candidato;
					SendMail mail = new SendMail();
					mail.perguntaeditada(email, nome,candidato,verificando);
					encaminharRequisicao(request, response, "candidato?acao=histo&idd="+idd);
				}else{
					encaminharRequisicao(request, response, "candidato?acao=histo&idd="+idd);
				}
			}else if(verificando == null){
				pergunta.responder(resposta, i);
				String verificar = pergunta.veremail(i);
				if(verificar.equals("email")){
					pergunta.pegaruser(i);
					pergunta.vercad();
					String email = pergunta.email;
					String nome = pergunta.nome;
					String candidato = pergunta.candidato;
					SendMail mail = new SendMail();
					mail.perguntarespondida(email, nome,candidato);
					encaminharRequisicao(request, response, "candidato?acao=histo&idd="+idd);
				}else{
					encaminharRequisicao(request, response, "candidato?acao=histo&idd="+idd);
				}
			}
		}
	}
	
	private void mostrarRelatorioPessoal(HttpServletRequest request, HttpServletResponse response)  throws ServletException, IOException {
		Candidato c = capturarCandidato(request, response);
		c = candidatoDao.buscarcad(c);
		Integer numero = candidatoDao.buscarresult(c);
		Integer vitoria = candidatoDao.buscarvitoria(c);
		batalhaDao.totalDeBatalhas();
		Integer totalDeBatalhas = batalhaDao.batalhas;
		request.setAttribute("listaCandidatos", c);
		request.setAttribute("numero", numero);
		request.setAttribute("vitoria", vitoria);
		request.setAttribute("total", totalDeBatalhas);
		encaminharRequisicao(request, response, "relatorio-pessoal.jsp");
	}
	
	private void mostrarRelatorioGeral(HttpServletRequest request, HttpServletResponse response)  throws ServletException, IOException {
		ArrayList<Candidato> listaCandidatos = new ArrayList<Candidato>();
		listaCandidatos = candidatoDao.buscarPorVitoria();
		batalhaDao.totalDeBatalhas();
		Integer totalDeBatalhas = batalhaDao.batalhas;
		request.setAttribute("listaCandidatos", listaCandidatos);
		request.setAttribute("totalDeBatalhas", totalDeBatalhas);
		encaminharRequisicao(request, response, "relatorio-geral.jsp");
	}
	
	private Perguntas capturarPergunta(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Perguntas p = new Perguntas();
		p.setPergunta(request.getParameter("pergunta"));
		String email = request.getParameter("email");
		if(email == null){
			email = "naoqueroemail";
		}
		p.setRecebernoemail(email);
		String idcad = request.getParameter("iddocad");
		Integer idc = Integer.parseInt(idcad);
		p.setIddocad(idc);
		String idpes = request.getParameter("iddapessoa");
		Integer idp = Integer.parseInt(idpes);
		p.setIddapessoa(idp);
		return p;
	}
	
	private Candidato capturarCandidato(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Candidato c = new Candidato();

		String sId = request.getParameter("id");
		if (sId != null && !"".equals(sId)) {
			Integer id = Integer.parseInt(sId);
			c.setId(id);
		}
		
		c.setNome(request.getParameter("nome"));
		c.setCargo(request.getParameter("cargo"));
		c.setUrl(request.getParameter("url"));
		c.setCidade(request.getParameter("cidade"));
		c.setPartido(request.getParameter("partido"));
		c.setNumero(request.getParameter("numero"));

		return c;
	}
	
	private void encaminharRequisicao(HttpServletRequest request, HttpServletResponse response, String caminho) throws ServletException, IOException {
		RequestDispatcher rd = request.getRequestDispatcher(caminho);
		rd.forward(request, response);
	}
	
}
