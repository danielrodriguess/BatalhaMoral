package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.Normalizer;
import java.util.ArrayList;

import model.Candidato;
import model.Usuario;

public class CandidatoDao {

	private Connection connection;
	public static int cad1,cad2,vencedor,v,salvar = 0,vencedordabatalha,not,tem,notp;
	public CandidatoDao() {
		this.connection = new ConnectionFactory().getConnection();
	}
	
	public void salvar(Candidato c) {
		salvar = 0;
		String sql = "INSERT INTO candidato (nome, cargo, cidade, url, partido, numero,numerodevitorias,userresponsavel) VALUES (?,?,?,?,?,?,?,?)";
		
		try {
			salvar = 1;
			PreparedStatement ps = connection.prepareStatement(sql);
			ps = preencherPreparedStatement(ps, c);
			ps.execute();
			ps.close();
			
		} catch (SQLException e) {
			System.out.println(e.getMessage());
			throw new RuntimeException();
		}
		
	}
	
	public ArrayList<Candidato> sobreabatalha(Integer id) {
		String sql = "select * from batalha where candidato1 = ? or candidato2 = ?";
		String sql1 = "select * from candidato where id = ?";
		ArrayList<Candidato> lista = new ArrayList<Candidato>();
		try {
			PreparedStatement ps = connection.prepareStatement(sql);
			ps.setInt(1, id);
			ps.setInt(2, id);
			ResultSet rs = ps.executeQuery();
			while(rs.next()){
				int cad1 = rs.getInt("candidato1");
				int cad2 = rs.getInt("candidato2");
				if(cad1 == id){
					vencedordabatalha = rs.getInt("vencedor");
					PreparedStatement ps1 = connection.prepareStatement(sql1);
					ps1.setInt(1, cad2);
					ResultSet rs1 = ps1.executeQuery();
					while(rs1.next()){
						Candidato c = new Candidato();
						c = preencherCandidato(rs1);
						lista.add(c);
					}
				}else if(cad2 == id){
					vencedordabatalha = rs.getInt("vencedor");
					PreparedStatement ps1 = connection.prepareStatement(sql1);
					ps1.setInt(1, cad1);
					ResultSet rs1 = ps1.executeQuery();
					while(rs1.next()){
						Candidato c = new Candidato();
						c = preencherCandidato(rs1);
						lista.add(c);
					}
				}
			}
			ps.close();
			
		} catch (SQLException e) {
			System.out.println(e.getMessage());
			throw new RuntimeException();
		}
		return lista;
	}

	public void meucad(Usuario u) {
		tem = 0;
		String sql = "SELECT * FROM candidato where userresponsavel = ?";
		try {
			PreparedStatement ps = connection.prepareStatement(sql);
			ps.setInt(1, u.getId());
			ResultSet rs = ps.executeQuery();
				while(rs.next()){
					tem = 1;
				}
			ps.close();
		} catch (SQLException e) {
			System.out.println(e.getMessage());
			throw new RuntimeException();
		}
	}
	
	public ArrayList<Candidato> meucadd(Integer id) {
		not = 0;
		String sql = "SELECT * FROM candidato where userresponsavel = ?";
		String sql1 = "SELECT * FROM perguntas where iddocad = ? and resposta is null";
		ArrayList<Candidato> listaCandidatos = new ArrayList<Candidato>();
		try {
			PreparedStatement ps = connection.prepareStatement(sql);
			ps.setInt(1, id);
			ResultSet rs = ps.executeQuery();
				while(rs.next()){
					notp = 0;
					int idcad = rs.getInt("id");
					PreparedStatement ps1 = connection.prepareStatement(sql1);
					ps1.setInt(1, idcad);
					ResultSet rs1 = ps1.executeQuery();
					while(rs1.next()){
						not++;
						notp++;
					}
					Candidato c = new Candidato();
					c = preencherCandidato(rs);
					listaCandidatos.add(c);
				}
			ps.close();
		} catch (SQLException e) {
			System.out.println(e.getMessage());
			throw new RuntimeException();
		}
		return listaCandidatos;
	}
	
	public void atualizar(Candidato c) {
		String sql = "UPDATE candidato SET nome = ?, cargo = ?, cidade = ?, url = ?, partido = ?, numero = ? WHERE id = ?";
		
		try {
			
			PreparedStatement ps = connection.prepareStatement(sql);
			ps = preencherPreparedStatement(ps, c);
			ps.execute();
			ps.close();
			
		} catch (SQLException e) {
			System.out.println(e.getMessage());
			throw new RuntimeException();
		}
		
	}
	
	public void excluir(Candidato c) {
		String sql = "DELETE FROM candidato WHERE id = ?";
		
		try {
			
			PreparedStatement ps = connection.prepareStatement(sql);
			ps.setInt(1, c.getId());
			ps.execute();
			ps.close();
			
		} catch (SQLException e) {
			System.out.println(e.getMessage());
			throw new RuntimeException();
		}
		
	}
	
	public ArrayList<Candidato> buscarTodos() {
		String sql = "SELECT * FROM candidato";
		ArrayList<Candidato> listaCandidatos = new ArrayList<Candidato>();
		try {
			PreparedStatement ps = connection.prepareStatement(sql);
			ResultSet rs = ps.executeQuery();
			
			while (rs.next()) {
				Candidato c = new Candidato();
				c = preencherCandidato(rs);
				listaCandidatos.add(c);
			}
			
			ps.close();
		} catch (SQLException e) {
			System.out.println(e.getMessage());
			throw new RuntimeException();
		}

		return listaCandidatos;
	}
	
	public ArrayList<Candidato> buscarPorVitoria() {
		String sql = "select * from candidato order by numerodevitorias desc";	
		ArrayList<Candidato> listaCandidatos = new ArrayList<Candidato>();
		try {
			PreparedStatement ps = connection.prepareStatement(sql);
			ResultSet rs = ps.executeQuery();
			while(rs.next()){
				int a = rs.getInt("numerodevitorias");
				if(a == 0){
					
				}else{
					Candidato c = new Candidato();
					c = preencherCandidato(rs);
					listaCandidatos.add(c);
				}
			}
		} catch (SQLException e) {
			System.out.println(e.getMessage());
			throw new RuntimeException();
		}

		return listaCandidatos;
	}
	
	public ArrayList<Candidato> buscarUltimosCadastrados() {
		String sql = "SELECT * FROM candidato ORDER BY id DESC LIMIT 6";
		ArrayList<Candidato> ultimosCadastrados = new ArrayList<Candidato>();
		try {
			PreparedStatement ps = connection.prepareStatement(sql);
			ResultSet rs = ps.executeQuery();
			
			while (rs.next()) {
				Candidato c = new Candidato();
				c = preencherCandidato(rs);
				ultimosCadastrados.add(c);
			}
			
			ps.close();
		} catch (SQLException e) {
			System.out.println(e.getMessage());
			throw new RuntimeException();
		}

		return ultimosCadastrados;
	}
	
	public Candidato buscarRandomico() {
		String sql = "SELECT * FROM candidato ORDER BY random()";
		Candidato candidato = new Candidato();
		try {
			PreparedStatement ps = connection.prepareStatement(sql);
			ResultSet rs = ps.executeQuery();

			while (rs.next()) {
				candidato = preencherCandidato(rs);
			}
			
			ps.close();
		} catch (SQLException e) {
			System.out.println(e.getMessage());
			throw new RuntimeException();
		}

		return candidato;
	}	
	
	public Candidato buscarPorId(Candidato candidato) {
		String sql = "SELECT * FROM candidato WHERE id = ?";
		try {
			PreparedStatement ps = connection.prepareStatement(sql);
			ps.setInt(1, candidato.getId());
			ResultSet rs = ps.executeQuery();
			
			while (rs.next()) {
				candidato = preencherCandidato(rs);
			}
			
			ps.close();
		} catch (SQLException e) {
			System.out.println(e.getMessage());
			throw new RuntimeException();
		}

		return candidato;
	}
	
	public Candidato buscarcad(Candidato c) {
		String sql = "select * from candidato where id = ?";
		try {
			PreparedStatement ps = connection.prepareStatement(sql);
			ps.setInt(1, c.getId());
			ResultSet rs = ps.executeQuery();
			while(rs.next()){
				c = preencherCandidato(rs);				
			}
			ps.close();
			
		} catch (SQLException e) {
			System.out.println(e.getMessage());
			throw new RuntimeException();
		}
		return c;
	}
	
	public Candidato buscarcadurl(String nome) {
		v=0;
		Candidato c = new Candidato();
		String sql = "select * from candidato WHERE nome LIKE ?";
		try {
			PreparedStatement ps = connection.prepareStatement(sql);
			ps.setString(1, nome+"%");
			ResultSet rs = ps.executeQuery();
			if(rs.next()){
				v++;
				c = preencherCandidato(rs);				
			}else{
				v=0;
			}
			ps.close();
			
		} catch (SQLException e) {
			System.out.println(e.getMessage());
			throw new RuntimeException();
		}
		return c;
	}
	
	public Integer contarurl(String nome) {
		int a = 0;
		String sql = "select count(*) from candidato WHERE nome LIKE ?";
		try {
			PreparedStatement ps = connection.prepareStatement(sql);
			ps.setString(1, nome+"%");
			ResultSet rs = ps.executeQuery();
			while(rs.next()){
				a = rs.getInt(1);
			}
			ps.close();
			
		} catch (SQLException e) {
			System.out.println(e.getMessage());
			throw new RuntimeException();
		}
		return a;
	}
	
	public Integer buscarvitoria(Candidato c) {
		String sql = "select count(*) from batalha where vencedor = ?";
		int a = 0;
		try {
			PreparedStatement ps = connection.prepareStatement(sql);
			ps.setInt(1, c.getId());
			ResultSet rs = ps.executeQuery();
			while(rs.next()){
				a = rs.getInt(1);			
			}
			ps.close();
			
		} catch (SQLException e) {
			System.out.println(e.getMessage());
			throw new RuntimeException();
		}
		return a;
	}
	
	public Integer buscarresult(Candidato c) {
		String sql = "select count(*) from batalha where candidato1 = ? or candidato2 = ?";
		int a = 0;
		try {
			PreparedStatement ps = connection.prepareStatement(sql);
			ps.setInt(1, c.getId());
			ps.setInt(2, c.getId());
			ResultSet rs = ps.executeQuery();
			while(rs.next()){
				a = rs.getInt(1);			
			}
			ps.close();
			
		} catch (SQLException e) {
			System.out.println(e.getMessage());
			throw new RuntimeException();
		}
		return a;
	}
	
	
	
	
	public Candidato preencherCandidato(ResultSet rs) throws SQLException{
		Candidato c = new Candidato();
		c.setId(rs.getInt("id"));
		c.setNome(rs.getString("nome"));
		String id = rs.getString("nome");
		String a = id.replaceAll(" ", "-");
		a = a.toLowerCase();
		c.setNome1(a);
		c.setCargo(rs.getString("cargo"));
		c.setIdvenc(vencedordabatalha);
		c.setUrl(rs.getString("url"));
		c.setPartido(rs.getString("partido"));
		c.setCidade(rs.getString("cidade"));
		c.setNumero(rs.getString("numero"));
		c.setIdresp(rs.getInt("userresponsavel"));
		c.setContandonotificacoes(notp);
		if(vencedor == cad1){
			c.setCad1("venceu");
		}else{
			c.setCad1("");
		}
		try {
			c.setVotos(rs.getInt("numerodevitorias"));
		} catch (SQLException e) {
			if (!e.getMessage().contains("numerodevitorias")){
				throw e;
			}
		}
		return c;
	}
	
	private PreparedStatement preencherPreparedStatement(PreparedStatement ps, Candidato c)  throws SQLException {
		ps.setString(1, c.getNome());
		ps.setString(2, c.getCargo());
		ps.setString(3, c.getCidade());
		ps.setString(4, c.getUrl());
		ps.setString(5, c.getPartido());
		ps.setString(6, c.getNumero());
		if(salvar == 1){
			ps.setInt(7, 0);
			ps.setInt(8, 0);
		}
		if (c.getId() != null) {
			ps.setInt(7, c.getId());
		}
		
		return ps;
	}
	
}











