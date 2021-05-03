package br.com.bradesco.testlocal.controller;

import java.util.List;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.DELETE;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.Consumes;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import br.com.bradesco.testlocal.domain.Pessoa;
import br.com.bradesco.testlocal.domain.Pagamento;
import br.com.bradesco.testlocal.repository.PagamentoRepository;
import br.com.bradesco.testlocal.repository.PessoaRepository;

@Path(value = "/api/v1")
public class PagamentoController {

	@Inject
	private PessoaRepository pessoaRepository;
	
	@Inject
	private PagamentoRepository pagamentoRepository;
	
	/* Listar todos os Pagamentos */
	@GET
	@Path(value = "/pagamentos")
	@Produces(MediaType.APPLICATION_JSON)
	public List<Pagamento> listarTodos() {
		return pagamentoRepository.findAll();
	}
	
	/* Salvar Pagamento para Usuário */
	@POST
	@Path(value = "/pagamentos/{idUsuario}")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Pagamento salvarPagamento(@PathParam("idUsuario") Long idUsuario, Pagamento pagamentoDetails) {
		
		Pessoa pessoa = pessoaRepository.findById(idUsuario)
				.orElseThrow(() -> new WebApplicationException("[salvarPagamento] Não existe uma pessoa com o id: " + idUsuario));

		pagamentoDetails.setPessoa(pessoa);
		return pagamentoRepository.save(pagamentoDetails);		
		
	}
	
	/* Buscar Pagamentos por Usuário */
	@GET
	@Path(value = "/pagamentos/por-usuario/{idUsuario}")
	@Produces(MediaType.APPLICATION_JSON)	
	public List<Pagamento> buscarPagamentoPorUsuario(@PathParam("idUsuario") Long idUsuario) {
		return pagamentoRepository.findPagamentoByUsuario(idUsuario);
	}
	
	/* Buscar Pagamento por id */
	@GET
	@Path(value = "/pagamentos/{id}")
	@Produces(MediaType.APPLICATION_JSON)	
	public Response buscarPagamentoPorId(@PathParam("id") Long id) {
		
		Pagamento pagamento = pagamentoRepository.findById(id)
				.orElseThrow(() -> new WebApplicationException("[buscarPagamentoPorId] Não existe um pagamento com o id: " + id));
		return Response.ok(pagamento).build();
		
	}
	
	/* Atualizar Pagamento para Usuário */
	@PUT
	@Path(value = "/pagamentos/{idPagamento}/{idUsuario}")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response atualizarPagamento(@PathParam("idPagamento") Long idPagamento, @PathParam("idUsuario") Long idUsuario, Pagamento pagamentoDetails) {
		
		Pessoa pessoa = pessoaRepository.findById(idUsuario)
				.orElseThrow(() -> new WebApplicationException("[atualizarPagamento] Não existe uma pessoa com o id: " + idUsuario));		
		
		Pagamento pagamento = pagamentoRepository.findById(idPagamento)
				.orElseThrow(() -> new WebApplicationException("[atualizarPagamento] Não existe um pagamento com o id: " + idPagamento));
		
		pagamento.setChavePix(pagamentoDetails.getChavePix());
		pagamento.setCpf(pagamentoDetails.getCpf());
		pagamento.setDataPagamento(pagamentoDetails.getDataPagamento());
		pagamento.setDescricao(pagamentoDetails.getDescricao());
		pagamento.setNomeDestinatario(pagamentoDetails.getNomeDestinatario());
		pagamento.setValor(pagamentoDetails.getValor());
		pagamento.setPessoa(pessoa);
		
		Pagamento pagamentoUpdate = pagamentoRepository.save(pagamento);
		return Response.ok(pagamentoUpdate).build();
		
	}
	
	/* Deletar Pagamento do Usuário */
	@DELETE
	@Path(value = "/pagamentos/{id}")
	@Produces(MediaType.APPLICATION_JSON)	
	public Response deletarPagamento(@PathParam("id") Long id) {

		Pagamento pagamento = pagamentoRepository.findById(id)
				.orElseThrow(() -> new WebApplicationException("[deletarPagamento] Não existe um pagamento com o id: " + id));		

		pagamentoRepository.delete(pagamento);
		return Response.ok(pagamento).build();
		
	}
	
}
