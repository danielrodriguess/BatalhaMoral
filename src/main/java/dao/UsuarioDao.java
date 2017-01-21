package dao;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import model.Usuario;
import util.Criptografia;
import util.SendMail;

public class UsuarioDao {

	private Connection connection;
	public static int esqueceu = 0,ver = 0,salvar = 0,testar=0;
	public static String codigo = "";
	public UsuarioDao() {
		this.connection = new ConnectionFactory().getConnection();
	}
	
	public Usuario buscarPorEmailRecuperacaoDeSenha(Usuario usuario) throws UnsupportedEncodingException {
		codigo = "";
		Usuario usuarioBanco = new Usuario();
		String sql = "SELECT * FROM usuario WHERE email = ?";
		try {
			PreparedStatement ps = connection.prepareStatement(sql);
			ps.setString(1, usuario.getEmail().toLowerCase());
			ResultSet rs = ps.executeQuery();
			
			while (rs.next()) {
				codigo = rs.getString("codigo");
				usuarioBanco = preenchersenhaelembrete(rs);
			}
			
			ps.close();
		} catch (SQLException e) {
			System.out.println(e.getMessage());
			throw new RuntimeException();
		}

		return usuarioBanco;
	}
	
	public Usuario verificarcodigo(Usuario u) {
		Usuario usuarioBanco = new Usuario();
		String sql = "SELECT * FROM esqueceuasenha where link = ? and codigo = ? and senha = ?";
		try {
			PreparedStatement ps = connection.prepareStatement(sql);
			ps.setString(1, u.getLink());
			ps.setString(2, u.getCodigo());
			ps.setString(3, u.getSenha());
			ResultSet rs = ps.executeQuery();		
			if(rs.next()){
				esqueceu++;
			}
			
			ps.close();
		} catch (SQLException e) {
			System.out.println(e.getMessage());
			throw new RuntimeException();
		}
		return u;
	}
	
	public void atualizarcodigo(String email,String codigo) {
		Usuario usuarioBanco = new Usuario();
		String sql = "update usuario set codigo = ? where email = ?";
		try {
			PreparedStatement ps = connection.prepareStatement(sql);
			ps.setString(1, codigo);
			ps.setString(2, email);
			ps.execute();				
			ps.close();
		} catch (SQLException e) {
			System.out.println(e.getMessage());
			throw new RuntimeException();
		}
	}
	
	public Usuario buscarPorLink(String senha,String link) {
		Usuario u = new Usuario();
		esqueceu = 0;
		String sql = "SELECT * FROM esqueceuasenha where senha = ? and link = ?";
		try {
			PreparedStatement ps = connection.prepareStatement(sql);
			ps.setString(1, senha);
			ps.setString(2, link);
			ResultSet rs = ps.executeQuery();		
			if(rs.next()){
				esqueceu++;
			}
			
			ps.close();
		} catch (SQLException e) {
			System.out.println(e.getMessage());
			throw new RuntimeException();
		}
		return u;
	}
	
	public void cancelarSolicitacao(String link, String senha) {
		String sql = "delete from esqueceuasenha where link = ? and senha = ?";
		try {
			PreparedStatement ps = connection.prepareStatement(sql);
			ps.setString(1, link);
			ps.setString(2, senha);
			ps.execute();			
			ps.close();
		} catch (SQLException e) {
			System.out.println(e.getMessage());
			throw new RuntimeException();
		}
	}
	
	public Usuario buscarPorEmail(Usuario usuario) {
		codigo = "";
		testar = 0;
		Usuario usuarioBanco = new Usuario();
		String sql = "SELECT * FROM usuario WHERE email = ?";
		try {
			PreparedStatement ps = connection.prepareStatement(sql);
			ps.setString(1, usuario.getEmail().toLowerCase());
			ResultSet rs = ps.executeQuery();
			
			while (rs.next()) {
				testar++;
				codigo = rs.getString("codigo");
				usuarioBanco = preencherUsuario(rs);
			}
			
			ps.close();
		} catch (SQLException e) {
			System.out.println(e.getMessage());
			throw new RuntimeException();
		}

		return usuarioBanco;
	}
	
	public void mudarsenha(Usuario u) throws UnsupportedEncodingException {
		String sql = "select * from esqueceuasenha where link = ? and senha = ?";
		String sql1 = "select * from usuario where email = ? and senha = ?";
		String sql2 = "update usuario set senha = ? where email = ? and senha = ?";
		String sql3 = "delete from esqueceuasenha where email = ? and link = ? and senha = ?";
		try {			
			PreparedStatement ps = connection.prepareStatement(sql);
			ps.setString(1, u.getLink());
			ps.setString(2, u.getSenha1());
			ResultSet rs = ps.executeQuery();
			while(rs.next()){
				String email = rs.getString("email");
				PreparedStatement ps1 = connection.prepareStatement(sql1);
				ps1.setString(1, email);
				ps1.setString(2, u.getSenha1());
				ResultSet rs1 = ps1.executeQuery();
				while(rs1.next()){
					String nome = rs1.getString("nome");
					try {
						String senha = Criptografia.criptografar(u.getSenha());
						PreparedStatement ps2 = connection.prepareStatement(sql2);
						ps2.setString(1, senha);
						ps2.setString(2, email);
						ps2.setString(3, u.getSenha1());
						ps2.execute();
						ps2.close();
						PreparedStatement ps3 = connection.prepareStatement(sql3);
						ps3.setString(1, email);
						ps3.setString(2, u.getLink());
						ps3.setString(3, u.getSenha1());
						ps3.execute();
						ps3.close();
						SendMail enviar = new SendMail();
						enviar.sendConfirmacao(email,nome);
					} catch (NoSuchAlgorithmException e) {
						System.out.println(e.getMessage());
					}
				}
			}
			ps.close();
		} catch (SQLException e) {
			System.out.println(e.getMessage());
			throw new RuntimeException();
		}
		
	}
	
	public void inserirRecuperar(Usuario u) {
		ver = 0;
		String sql1 = "select * from esqueceuasenha where email = ? and senha = ?";
		String sql2 = "select * from usuario where email = ? and senha = ?";
		String sql = "INSERT INTO esqueceuasenha (email, senha, codigo, link) VALUES (?,?,?,?)";
		String link = "http://batalhamoral.herokuapp.com/usuario?acao=codigo&id="+u.getLink()+"&hash="+u.getSenha();
		try {
			PreparedStatement ps1 = connection.prepareStatement(sql1);
			ps1.setString(1, u.getEmail());
			ps1.setString(2, u.getSenha());
			ResultSet rs = ps1.executeQuery();
			if(rs.next()){
				ver++;
			}else{
				PreparedStatement ps2 = connection.prepareStatement(sql2);
				ps2.setString(1, u.getEmail());
				ps2.setString(2, u.getSenha());
				ResultSet rs1 = ps2.executeQuery();
				if(rs1.next()){
					PreparedStatement ps = connection.prepareStatement(sql);
					ps.setString(1, u.getEmail());
					ps.setString(2, u.getSenha());
					ps.setString(3, u.getCodigo());
					ps.setString(4, link);
					ps.execute();
					ps.close();
					ps1.close();
				}else{
					ver = 2;
				}
			}			
		} catch (SQLException e) {
			System.out.println(e.getMessage());
			throw new RuntimeException();
		}
		
	}
	
	public void salvar(Usuario u) {
		salvar = 0;
		String sql1 = "select * from usuario where email = ?";
		String sql = "INSERT INTO usuario (nome, email, senha, lembrete,codigo) VALUES (?,?,?,?,?)";
		
		try {
			PreparedStatement ps1 = connection.prepareStatement(sql1);
			ps1.setString(1, u.getEmail());
			ResultSet rs = ps1.executeQuery();
			if(rs.next()){
				salvar++;
			}else{
				PreparedStatement ps = connection.prepareStatement(sql);
				ps = preencherPreparedStatement(ps, u);
				ps.execute();
				ps.close();
			}
		} catch (SQLException e) {
			System.out.println(e.getMessage());
			throw new RuntimeException();
		}
		
	}
	
	public void verificarconf(String hash,String email) {
		salvar = 0;
		String sql = "select * from usuario where email = ? and codigo = ?";
		String sql1 = "update usuario set codigo = ? where email = ?";
		try {
			PreparedStatement ps1 = connection.prepareStatement(sql);
			ps1.setString(1, email);
			ps1.setString(2, hash);
			ResultSet rs = ps1.executeQuery();
			if(rs.next()){
				salvar++;
				PreparedStatement ps2 = connection.prepareStatement(sql1);
				String codigo = "codigo";
				ps2.setString(1, codigo);
				ps2.setString(2, email);
				ps2.execute();
			}else{
				salvar = 2;
			}
		} catch (SQLException e) {
			System.out.println(e.getMessage());
			throw new RuntimeException();
		}
		
	}
	
	public Usuario preencherUsuario(ResultSet rs) throws SQLException{
		Usuario u = new Usuario();
		u.setId(rs.getInt("id"));
		u.setNome(rs.getString("nome"));
		u.setEmail(rs.getString("email"));
		u.setSenha(rs.getString("senha"));
		u.setLembrete(rs.getString("lembrete"));
		return u;
	}
	
	public Usuario preencherRecuperacao(ResultSet rs) throws SQLException{
		Usuario u = new Usuario();
		u.setId(rs.getInt("id"));
		u.setNome(rs.getString("nome"));
		u.setEmail(rs.getString("email"));
		u.setSenha(rs.getString("senha"));
		u.setLembrete(rs.getString("lembrete"));
		return u;
	}
	
	public Usuario preenchersenhaelembrete(ResultSet rs) throws SQLException, UnsupportedEncodingException{
		Usuario u = new Usuario();
		u.setEmail(rs.getString("email"));
		u.setNome(rs.getString("nome"));
		u.setSenha(rs.getString("senha"));
		u.setLembrete(rs.getString("lembrete"));
		int codigo = 500000+(int)(600000*Math.random());
		String cod = ""+codigo;
		u.setCodigo(cod);
		try{
			String emailcriptografado = Criptografia.criptografarEmail(u.getEmail());
			u.setLink(emailcriptografado);
		} catch (NoSuchAlgorithmException e) {
			
		}
		return u;
	}
	
	
	
	private PreparedStatement preencherPreparedStatement(PreparedStatement ps, Usuario u)  throws SQLException {
		ps.setString(1, u.getNome());
		ps.setString(2, u.getEmail());
		ps.setString(3, u.getSenha());
		ps.setString(4, u.getLembrete());
		ps.setString(5, "codigo");
		return ps;
	}
		
}











