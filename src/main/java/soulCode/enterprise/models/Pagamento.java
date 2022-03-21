package soulCode.enterprise.models;

import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.NumberFormat;

import com.fasterxml.jackson.annotation.JsonIgnore;
/**
 * classe que define o modelo dos dados do objeto pagamento
 * @author tatiana
 * @author lucas

 */
@Entity
public class Pagamento {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer codigo;
	
	@NumberFormat(pattern = "#.##0,00")
	@Column(nullable = false)
	private Double pa_valor;
	
	@Column(nullable = false)
	private String pa_descricao;
	
	@DateTimeFormat(pattern = "dd-MM-yyyy")
	//@Temporal(TemporalType.DATE)
	@Column(columnDefinition = "date", nullable = false)
	private Date pa_lancamento;
	
	@Enumerated(EnumType.STRING)
	@Column(nullable = false)
	private Categoria pa_categoria;
	
	@ManyToOne
	@JsonIgnore
	@JoinColumn(name="id_funcionario")
	private Funcionario funcionario;

	public Integer getCodigo() {
		return codigo;
	}

	public void setCodigo(Integer codigo) {
		this.codigo = codigo;
	}

	public Double getPa_valor() {
		return pa_valor;
	}

	public void setPa_valor(Double pa_valor) {
		this.pa_valor = pa_valor;
	}

	public String getPa_descricao() {
		return pa_descricao;
	}

	public void setPa_descricao(String pa_descricao) {
		this.pa_descricao = pa_descricao;
	}

	public Date getPa_lancamento() {
		return pa_lancamento;
	}

	public void setPa_lancamento(Date pa_lancamento) {
		this.pa_lancamento = pa_lancamento;
	}

	public Categoria getPa_categoria() {
		return pa_categoria;
	}

	public void setPa_categoria(Categoria pa_categoria) {
		this.pa_categoria = pa_categoria;
	}

	public Funcionario getFuncionario() {
		return funcionario;
	}

	public void setFuncionario(Funcionario funcionario) {
		this.funcionario = funcionario;
	}
}
