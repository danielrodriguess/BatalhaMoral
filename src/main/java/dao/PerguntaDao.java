package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.Normalizer;
import java.util.ArrayList;

import model.Candidato;
import model.Perguntas;
import model.Usuario;

public class PerguntaDao {

	private Connection connection;
	public static String nome,email,candidato,resposta,nomecad;
	public static Integer idcad;
	public PerguntaDao() {
		this.connection = new ConnectionFactory().getConnection();
	}
	public void salvar(Perguntas p){
		String sql = "INSERT INTO perguntas (pergunta,iddocad,iddapessoa,recebernoemail) values (?,?,?,?)";
		try{
			PreparedStatement ps = connection.prepareStatement(sql);
			ps.setString(1, p.getPergunta());
			ps.setInt(2, p.getIddocad());
			ps.setInt(3, p.getIddapessoa());
			ps.setString(4, p.getRecebernoemail());
			ps.execute();
			ps.close();
		}catch(SQLException e2){
			
		}
	}
	
	public void removerpergunta(Integer id, String pergunta){
		String sql = "delete from perguntas where id = ? and pergunta = ?";
		try{
			PreparedStatement ps = connection.prepareStatement(sql);
			ps.setInt(1, id);
			ps.setString(2, pergunta);
			ps.execute();
			ps.close();
		}catch(SQLException e2){
			
		}
	}
	
	public void verificarrespondera(Integer id,String pergunta){
		String sql = "select * from perguntas where pergunta = ? and id = ?";
		try{
			PreparedStatement ps = connection.prepareStatement(sql);
			ps.setString(1, pergunta);
			ps.setInt(2, id);
			ResultSet rs = ps.executeQuery();
			while(rs.next()){
				resposta = rs.getString("resposta");
			}
			ps.close();
		}catch(SQLException e2){
			
		}
	}
	
	public void responder(String r,Integer id){
		String sql = "update perguntas set resposta = ? where id = ?";
		try{
			PreparedStatement ps = connection.prepareStatement(sql);
			ps.setString(1, r);
			ps.setInt(2, id);
			ps.execute();
			ps.close();
		}catch(SQLException e2){
			
		}
	}
	
	public String veremail(Integer id){
		String email = "";
		String sql = "select * from perguntas where id = ?";
		try{
			PreparedStatement ps = connection.prepareStatement(sql);
			ps.setInt(1, id);
			ResultSet rs = ps.executeQuery();
			while(rs.next()){
				email = rs.getString("recebernoemail");
			}
			ps.close();
		}catch(SQLException e2){
			
		}
		return email;
	}
	
	public void pegaruser(Integer id){
		Integer iduser = 0;
		String sql = "select * from perguntas where id = ?";
		String sql1 = "select * from usuario where id = ?";
		String sql2 = "select * from candidato where id = ?";
		try{
			PreparedStatement ps = connection.prepareStatement(sql);
			ps.setInt(1, id);
			ResultSet rs = ps.executeQuery();
			while(rs.next()){
				iduser = rs.getInt("iddapessoa");
				idcad = rs.getInt("iddocad");
				PreparedStatement ps1 = connection.prepareStatement(sql1);
				ps1.setInt(1, iduser);
				ResultSet rs1 = ps1.executeQuery();
				while(rs1.next()){
					nome = rs1.getString("nome");
					email = rs1.getString("email");
					PreparedStatement ps2 = connection.prepareStatement(sql2);
					ps2.setInt(1, idcad);
					ResultSet rs2 = ps2.executeQuery();
					while(rs2.next()){
						nomecad = rs2.getString("nome");
					}
				}
			}
			ps.close();
		}catch(SQLException e2){
			
		}
	}
	
	public void vercad(){
		String sql = "select * from candidato where id = ?";
		try{
			PreparedStatement ps = connection.prepareStatement(sql);
			ps.setInt(1, idcad);
			ResultSet rs = ps.executeQuery();
			while(rs.next()){
				candidato = rs.getString("nome");
			}
			ps.close();
		}catch(SQLException e2){
			
		}
	}
	
	public ArrayList<Perguntas> todasasperguntas(Candidato c){
		String sql = "SELECT * FROM perguntas where iddocad = ? order by id desc";
		ArrayList<Perguntas> listaCandidatos = new ArrayList<Perguntas>();
		try{
			PreparedStatement ps = connection.prepareStatement(sql);
			ps.setInt(1, c.getId());
			ResultSet rs = ps.executeQuery();
			while(rs.next()){
				Perguntas p = new Perguntas();
				p = preencherPerguntas(rs);
				listaCandidatos.add(p);
			}
			ps.close();
		}catch(SQLException e2){
			
		}
		return listaCandidatos;
	}

	
	public Integer veruserecad(Candidato c){
		int a = 0;
		String sql = "SELECT * FROM candidato where id = ?";
		try{
			PreparedStatement ps = connection.prepareStatement(sql);
			ps.setInt(1, c.getId());
			ResultSet rs = ps.executeQuery();
			while(rs.next()){
				a = rs.getInt("userresponsavel");
			}
			ps.close();
		}catch(SQLException e2){
			
		}
		return a;
	}
	
	public Perguntas preencherPerguntas(ResultSet rs) throws SQLException{
		Perguntas p = new Perguntas();
		p.setId(rs.getInt("id"));
		p.setIddapessoa(rs.getInt("iddapessoa"));
		p.setIddocad(rs.getInt("iddocad"));
		p.setPergunta(rs.getString("pergunta"));
		p.setRespostas(rs.getString("resposta"));
		return p;
	}
}