package model;

public class Candidato {
	
	private Integer id;
	private String nome;
	private String nome1;
	private String cargo;
	private String url;
	private String cidade;
	private String partido;
	private String numero;
	private Integer votos;
	private Integer vitorias;
	private String cad1;
	private String cad2;
	private Integer idresp;
	private Integer contandonotificacoes;
	private Integer idvenc;
	public Candidato() {
	
	}

	public Integer getIdvenc() {
		return idvenc;
	}

	public void setIdvenc(Integer idvenc) {
		this.idvenc = idvenc;
	}

	public String getCad1() {
		return cad1;
	}

	public void setCad1(String cad1) {
		this.cad1 = cad1;
	}

	public String getCad2() {
		return cad2;
	}

	public void setCad2(String cad2) {
		this.cad2 = cad2;
	}

	public Candidato(Integer id) {
		super();
		this.id = id;
	}

	public Candidato(String nome, String cargo, String url, String cidade, String partido, String numero) {
		super();
		this.nome = nome;
		this.cargo = cargo;
		this.url = url;
		this.cidade = cidade;
		this.partido = partido;
		this.numero = numero;
	}
	
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	
	public String getNome1() {
		return nome1;
	}

	public void setNome1(String nome1) {
		this.nome1 = nome1;
	}

	public String getCargo() {
		return cargo;
	}
	public void setCargo(String cargo) {
		this.cargo = cargo;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String getCidade() {
		return cidade;
	}
	public void setCidade(String cidade) {
		this.cidade = cidade;
	}
	public String getPartido() {
		return partido;
	}
	public void setPartido(String partido) {
		this.partido = partido;
	}
	public String getNumero() {
		return numero;
	}
	public void setNumero(String numero) {
		this.numero = numero;
	}

	public Integer getVotos() {
		return votos;
	}

	public void setVotos(Integer votos) {
		this.votos = votos;
	}

	public Integer getIdresp() {
		return idresp;
	}

	public void setIdresp(Integer idresp) {
		this.idresp = idresp;
	}

	public Integer getContandonotificacoes() {
		return contandonotificacoes;
	}

	public void setContandonotificacoes(Integer contandonotificacoes) {
		this.contandonotificacoes = contandonotificacoes;
	}

}
