package model;

public class Perguntas {
	private Integer id;
	private String pergunta;
	private String respostas;
	private Integer iddocad;
	private Integer iddapessoa;
	private Integer iddapessoanocand;
	private String recebernoemail;
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getPergunta() {
		return pergunta;
	}
	public void setPergunta(String pergunta) {
		this.pergunta = pergunta;
	}
	public String getRespostas() {
		return respostas;
	}
	public void setRespostas(String respostas) {
		this.respostas = respostas;
	}
	public Integer getIddocad() {
		return iddocad;
	}
	public void setIddocad(Integer iddocad) {
		this.iddocad = iddocad;
	}
	public Integer getIddapessoa() {
		return iddapessoa;
	}
	public void setIddapessoa(Integer iddapessoa) {
		this.iddapessoa = iddapessoa;
	}
	public Integer getIddapessoanocand() {
		return iddapessoanocand;
	}
	public void setIddapessoanocand(Integer iddapessoanocand) {
		this.iddapessoanocand = iddapessoanocand;
	}
	public String getRecebernoemail() {
		return recebernoemail;
	}
	public void setRecebernoemail(String recebernoemail) {
		this.recebernoemail = recebernoemail;
	}
	
}
