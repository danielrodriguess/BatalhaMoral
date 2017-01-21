package servlet;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.AdicionarDao;
import dao.CandidatoDao;
import model.Candidato;
import model.Usuario;

/**
 * Servlet implementation class Adicionar
 */
@WebServlet("/add")
public class Adicionar extends HttpServlet {
	private static final long serialVersionUID = 1L;
	AdicionarDao addd; 
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Adicionar() {
        super();
        addd = new AdicionarDao();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#service(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String acao = request.getParameter("acao");
		if(acao.equals("add")){
			adicionar(request,response);
		}else if(acao.equals("vertudo")){
			add(request,response);		
		}else if(acao.equals("vereditar")){
			vereditar(request,response);		
		}else if(acao.equals("editar")){
			editar(request,response);		
		}
	}

	private void adicionar(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String cadid = request.getParameter("candidato");
		String userid = request.getParameter("usuario");
		if(cadid.equals("Selecione o candidato") || userid.equals("Selecione o usuário")){
			request.setAttribute("mensagemErro", "Selecione o candidato e o usuário");
			encaminharRequisicao(request, response, "add?acao=vertudo");
		}else{
			addd.alterar(cadid, userid);
			String candidato = addd.pegarNome(cadid);
			request.setAttribute("mensagemSucesso", "Candidato <b>"+candidato+"</b> adicionado com sucesso");
			encaminharRequisicao(request, response, "add?acao=vertudo");
		}
	}
	
	private void vereditar(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		ArrayList<Candidato> listac = new ArrayList<Candidato>();
		ArrayList<Usuario> listau = new ArrayList<Usuario>();
		listac = addd.editcand();
		listau = addd.user();
		request.setAttribute("listac", listac);
		request.setAttribute("listau", listau);
		encaminharRequisicao(request,response,"editadd.jsp");
	}
	
	private void editar(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String cadid = request.getParameter("candidato");
		String userid = request.getParameter("usuario");
		if(cadid.equals("Selecione o candidato") || userid.equals("Selecione o usuário")){
			request.setAttribute("mensagemErro", "Selecione o candidato e o usuário");
			encaminharRequisicao(request, response, "add?acao=vereditar");
		}else{
			addd.alterar(cadid, userid);
			String candidato = addd.pegarNome(cadid);
			request.setAttribute("mensagemSucesso", "O vínculo do candidato <b>"+candidato+"</b> foi editado com sucesso");
			encaminharRequisicao(request, response, "add?acao=vereditar");
		}
	}

	private void add(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		ArrayList<Candidato> listaCandidatos = new ArrayList<Candidato>();
		ArrayList<Usuario> listaUsuario = new ArrayList<Usuario>();
		listaCandidatos = addd.cand();
		listaUsuario = addd.user();
		request.setAttribute("listaCandidatos", listaCandidatos);
		request.setAttribute("listaUsuario", listaUsuario);
		encaminharRequisicao(request,response,"add.jsp");
	}

	private void encaminharRequisicao(HttpServletRequest request, HttpServletResponse response, String caminho) throws ServletException, IOException {
		// TODO Auto-generated method stub
		RequestDispatcher rd = request.getRequestDispatcher(caminho);
		rd.forward(request, response);
	}

}
