package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import model.Usuario;
import model.Candidato;

public class AdicionarDao {

	private Connection connection;
	public static Integer verificar = 0;
	public AdicionarDao() {
		this.connection = new ConnectionFactory().getConnection();
	}
	
	public ArrayList<Candidato> cand(){
		verificar = 0;
		String sql = "select * from candidato where userresponsavel is null";
		ArrayList<Candidato> listaCandidatos = new ArrayList<Candidato>();
		try{
			PreparedStatement ps = connection.prepareStatement(sql);
			ResultSet rs = ps.executeQuery();
			if(rs.next()){
				do{
					Candidato c = new Candidato();
					c = preencherCandidato(rs);
					listaCandidatos.add(c);
				}while(rs.next());
			}else{
				verificar++;
			}
		}catch(SQLException e2){
			
		}
		return listaCandidatos;
	}
	
	public ArrayList<Candidato> editcand(){
		verificar = 0;
		String sql = "select * from candidato where userresponsavel != 0";
		ArrayList<Candidato> listaCandidatos = new ArrayList<Candidato>();
		try{
			PreparedStatement ps = connection.prepareStatement(sql);
			ResultSet rs = ps.executeQuery();
			if(rs.next()){
				do{
					Candidato c = new Candidato();
					c = preencherCandidato(rs);
					listaCandidatos.add(c);
				}while(rs.next());
			}else{
				verificar++;
			}
		}catch(SQLException e2){
			
		}
		return listaCandidatos;
	}
	
	public ArrayList<Usuario> user(){
		String sql = "select * from usuario where codigo = 'codigo'";
		ArrayList<Usuario> listaUsuarios = new ArrayList<Usuario>();
		try{
			PreparedStatement ps = connection.prepareStatement(sql);
			ResultSet rs = ps.executeQuery();
			while(rs.next()){
				Usuario u = new Usuario();
				u = preencherUser(rs);
				listaUsuarios.add(u);
			}
		}catch(SQLException e2){
			
		}
		return listaUsuarios;
	}
	
	public void alterar(String cadid, String userid){
		String sql = "update candidato set userresponsavel = ? where id = ?";
		try{
			PreparedStatement ps1 = connection.prepareStatement(sql);
			int user = Integer.parseInt(userid);
			int cad = Integer.parseInt(cadid);
			ps1.setInt(1, user);
			ps1.setInt(2, cad);
			ps1.execute();
			ps1.close();
		}catch(SQLException e2){
			
		}
	}
	
	public String pegarNome(String cadid){
		String sql = "select * from candidato where id = ?";
		String nome = "";
		try{
			PreparedStatement ps1 = connection.prepareStatement(sql);
			int cad = Integer.parseInt(cadid);
			ps1.setInt(1, cad);
			ResultSet rs = ps1.executeQuery();
			while(rs.next()){
				nome = rs.getString("nome");
			}
			ps1.close();
		}catch(SQLException e2){
			
		}
		return nome;
	}
	
	public Candidato preencherCandidato(ResultSet rs) throws SQLException{
		Candidato c = new Candidato();
		c.setId(rs.getInt("id"));
		c.setNome(rs.getString("nome"));
		c.setCargo(rs.getString("cargo"));
		c.setUrl(rs.getString("url"));
		c.setPartido(rs.getString("partido"));
		c.setCidade(rs.getString("cidade"));
		c.setNumero(rs.getString("numero"));		
		return c;
	}
	public Usuario preencherUser(ResultSet rs) throws SQLException{
		Usuario u = new Usuario();
		u.setId(rs.getInt("id"));
		u.setNome(rs.getString("nome"));
		u.setEmail(rs.getString("email"));
		u.setSenha(rs.getString("senha"));
		u.setLembrete(rs.getString("lembrete"));		
		return u;
	}
}