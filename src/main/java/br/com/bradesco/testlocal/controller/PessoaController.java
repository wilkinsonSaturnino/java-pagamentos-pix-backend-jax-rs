package br.com.bradesco.testlocal.controller;

import java.util.List;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import br.com.bradesco.testlocal.domain.Pessoa;
import br.com.bradesco.testlocal.repository.PessoaRepository;

@Path(value = "/api/v1")
public class PessoaController {

	@Inject
	private PessoaRepository pessoaRepository;

	/* Listar todos os Usuários */
	@GET
	@Path(value = "/usuarios")
	@Produces(MediaType.APPLICATION_JSON)
	public List<Pessoa> listarTodos() {
		return pessoaRepository.findAll();
	}

	/* Salvar Usuário */
	@POST
	@Path(value = "/usuarios")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Pessoa salvarPessoa(Pessoa pessoa) {
		return pessoaRepository.save(pessoa);
	}

	/* Buscar Usuário por id */
	@GET
	@Path(value = "/usuarios/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response buscarPessoaPorId(@PathParam("id") Long id) {

		Pessoa pessoa = pessoaRepository.findById(id)
				.orElseThrow( () -> new WebApplicationException("[buscarPessoaPorId] Não existe um usuário com o id: " + id) );
		return Response.ok(pessoa).build();

	}

	/* Atualizar Usuário */
	@PUT
	@Path(value = "/usuarios/{id}")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response atualizarPessoa(@PathParam("id") Long id, Pessoa pessoaDetails) {
		
		Pessoa pessoa = pessoaRepository.findById(id)
				.orElseThrow( () -> new WebApplicationException("[atualizarPessoa] Não existe um usuário com o id: " + id) );
		
		pessoa.setNome(pessoaDetails.getNome());
		pessoa.setAgencia(pessoaDetails.getAgencia());
		pessoa.setConta(pessoaDetails.getConta());
		pessoa.setDigito(pessoaDetails.getDigito());
		
		Pessoa pessoaUpdate = pessoaRepository.save(pessoa);
		return Response.ok(pessoaUpdate).build();
		
	}
	
	/* Deletar Usuário */
	@DELETE
	@Path(value = "/usuarios/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response deletarPessoa(@PathParam("id") Long id) {

		Pessoa pessoa = pessoaRepository.findById(id)
				.orElseThrow( () -> new WebApplicationException("[deletarPessoa] Não existe um usuário com o id: " + id) );		
		
		pessoaRepository.delete(pessoa);
		return Response.ok(pessoa).build();
		
	}
	
	/* Buscar Usuário por nome */
	@GET
	@Path(value = "/usuarios/por-nome/{nomePesquisa}")
	@Produces(MediaType.APPLICATION_JSON)
	public List<Pessoa> buscarPessoaPorNome(@PathParam("nomePesquisa") String nomePesquisa) {
		return pessoaRepository.findPessoaByNomeLike(nomePesquisa);
	}
	
}
