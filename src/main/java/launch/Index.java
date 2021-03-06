package launch;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.CandidatoDao;
import model.Candidato;


/**
 * Servlet implementation class IndexServlet
 */
@WebServlet("/index")
public class Index extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	CandidatoDao candidatoDao;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Index() {
        candidatoDao = new CandidatoDao();
    }

	/**
	 * @see HttpServlet#service(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		buscarUltimosCadastrados(request, response);
		buscarCandidatoRandomico(request, response);
		encaminharRequisicao(request, response, "index2.jsp");
	}
	
	private void buscarUltimosCadastrados (HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		ArrayList<Candidato> ultimosCadastrados = candidatoDao.buscarUltimosCadastrados();
		request.setAttribute("ultimosCadastrados", ultimosCadastrados);
	}
	
	private void buscarCandidatoRandomico (HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Candidato candidatoRandomico = candidatoDao.buscarRandomico();
		request.setAttribute("candidatoRandomico", candidatoRandomico);
	}
	
	private void encaminharRequisicao(HttpServletRequest request, HttpServletResponse response, String caminho) throws ServletException, IOException {
		RequestDispatcher rd = request.getRequestDispatcher(caminho);
		rd.forward(request, response);
	}

}
