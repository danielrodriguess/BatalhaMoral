package model;

public class Usuario {

	private Integer id;
	private String nome;
	private String email;
	private String senha;
	private String lembrete;
	private String link;
	private String codigo;
	private String senha1;
	private String confcodigo;
	
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
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getSenha() {
		return senha;
	}
	public void setSenha(String senha) {
		this.senha = senha;
	}
	public String getLembrete() {
		return lembrete;
	}
	public void setLembrete(String lembrete) {
		this.lembrete = lembrete;
	}
	public String getLink() {
		return link;
	}
	public void setLink(String link) {
		this.link = link;
	}
	public String getCodigo() {
		return codigo;
	}
	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}
	public String getConfcodigo() {
		return confcodigo;
	}
	public void setConfcodigo(String confcodigo) {
		this.confcodigo = confcodigo;
	}
	public String getSenha1() {
		return senha1;
	}
	public void setSenha1(String senha1) {
		this.senha1 = senha1;
	}
	public String getPrimeiroNome() {
		if (nome.contains(" ")) {
			return nome.substring(0, nome.indexOf(" "));
		}
		return nome;
	}
}
