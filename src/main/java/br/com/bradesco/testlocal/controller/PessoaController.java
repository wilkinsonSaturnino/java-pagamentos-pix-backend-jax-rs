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
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;
import io.swagger.v3.oas.annotations.servers.Server;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;

@Path(value = "/api/v1")
@OpenAPIDefinition(info = @Info(title = "[Pagamentos Pix API]: Microserviço que faz a persistência dos dados da entidade Pessoa.\"", version = "1.0.0", description = "Este swagger é gerado automaticamente com uso da biblioteca swagger-jaxrs2.", license = @License(name = "OpenSource"), contact = @Contact(name = "Wilkinson Saturnino Dev")), servers = { @Server(url = "") })
public class PessoaController {

	@Inject
	private PessoaRepository pessoaRepository;

	@GET
	@Path(value = "/usuarios")
	@Produces(MediaType.APPLICATION_JSON)
	@Operation(summary = "Lista todos os Usuários.", description = "Lista todos os Usuários.", responses = {
			@ApiResponse(responseCode = "200", description = "Retorno de sucesso.", content = @Content(mediaType = "application/json")),
			@ApiResponse(responseCode = "401", description = "Você não tem autorização.", content = @Content(mediaType = "application/json")),
			@ApiResponse(responseCode = "403", description = "Proibido de ver estar informações.", content = @Content(mediaType = "application/json")),
			@ApiResponse(responseCode = "404", description = "O recurso não foi encontrado.", content = @Content(mediaType = "application/json")), 
			@ApiResponse(responseCode = "422", description = "O item não foi encontrado.", content = @Content(mediaType = "application/json")), })
	public List<Pessoa> listarTodos() {
		return pessoaRepository.findAll();
	}

	@POST
	@Path(value = "/usuarios")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@Operation(summary = "Salva Usuário.", description = "Salva Usuário.", responses = {
			@ApiResponse(responseCode = "200", description = "Retorno de sucesso.", 
					content = @Content(mediaType = "application/json", schema = @Schema(type = "array", implementation = Pessoa.class))),
			@ApiResponse(responseCode = "201", description = "Recurso criado com sucesso.", content = @Content(mediaType = "application/json")),
			@ApiResponse(responseCode = "401", description = "Você não tem autorização.", content = @Content(mediaType = "application/json")),
			@ApiResponse(responseCode = "403", description = "Proibido de ver estar informações.", content = @Content(mediaType = "application/json")),
			@ApiResponse(responseCode = "404", description = "O recurso não foi encontrado.", content = @Content(mediaType = "application/json")), 
			@ApiResponse(responseCode = "422", description = "O item não foi encontrado.", content = @Content(mediaType = "application/json")), })	
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
