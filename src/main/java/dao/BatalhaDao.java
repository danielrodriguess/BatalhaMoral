package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import model.Batalha;
import model.Candidato;

public class BatalhaDao {

	private Connection connection;
	public static int batalhas = 0;
	public BatalhaDao() {
		this.connection = new ConnectionFactory().getConnection();
	}
	
	public void salvar(Batalha b) {
		String sql = "INSERT INTO batalha (candidato1, candidato2, vencedor) VALUES (?,?,?)";
		String sql1 = "select * from candidato where id = ?";
		String sql2 = "update candidato set numerodevitorias = ? where id = ?";
		try {			
			PreparedStatement ps = connection.prepareStatement(sql);
			ps = preencherPreparedStatement(ps, b);
			ps.execute();
			ps.close();
			PreparedStatement ps1 = connection.prepareStatement(sql1);
			ps1.setInt(1, b.getVencedor().getId());
			ResultSet rs = ps1.executeQuery();
			while(rs.next()){
				int numero = rs.getInt("numerodevitorias");
				int n = numero + 1;
				PreparedStatement ps2 = connection.prepareStatement(sql2);
				ps2.setInt(1, n);
				ps2.setInt(2, b.getVencedor().getId());
				ps2.execute();
				ps2.close();
			}
		} catch (SQLException e) {
			System.out.println(e.getMessage());
			throw new RuntimeException();
		}
	}
	
	public Batalha gerarBatalha() {
		String sql = "SELECT * FROM candidato ORDER BY random() LIMIT 2";
		Batalha batalha = new Batalha();
		try {
			PreparedStatement ps = connection.prepareStatement(sql);
			ResultSet rs = ps.executeQuery();
			batalha = preencherBatalha(rs);
			ps.close();
		} catch (SQLException e) {
			System.out.println(e.getMessage());
			throw new RuntimeException();
		}

		return batalha;
	}
	
	public Integer totalDeBatalhas() {
		batalhas = 0;
		String sql = "SELECT count(*) FROM batalha";
		Integer total = 0;
		try {
			PreparedStatement ps = connection.prepareStatement(sql);
			ResultSet rs = ps.executeQuery();
			if (rs.next()) {
				batalhas = rs.getInt(1);
			}
			ps.close();
		} catch (SQLException e) {
			System.out.println(e.getMessage());
			throw new RuntimeException();
		}

		return total;
	}
		
	
	private Batalha preencherBatalha(ResultSet rs) throws SQLException{
		Batalha b = new Batalha();
		CandidatoDao candidatoDao = new CandidatoDao();
		
		if (rs.next()) {
			Candidato c1 = candidatoDao.preencherCandidato(rs);
			b.setCandidato1(c1);
		}
		
		if (rs.next()) {
			Candidato c2 = candidatoDao.preencherCandidato(rs);
			b.setCandidato2(c2);
		}
		
		return b;
	}
	
	private PreparedStatement preencherPreparedStatement(PreparedStatement ps, Batalha b)  throws SQLException {
		ps.setInt(1, b.getCandidato1().getId());
		ps.setInt(2, b.getCandidato2().getId());
		ps.setInt(3, b.getVencedor().getId());
		return ps;
	}
	
}











