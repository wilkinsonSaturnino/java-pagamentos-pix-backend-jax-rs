package br.com.bradesco.testlocal.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import br.com.bradesco.testlocal.domain.Pagamento;

@Repository
public interface PagamentoRepository extends JpaRepository<Pagamento, Long> {

	public final static String FIND_PAGAMENTO_BY_USUARIO = "SELECT p FROM "
			+ "Pagamento p WHERE p.pessoa.id = :idUsuario";
	
	@Query(FIND_PAGAMENTO_BY_USUARIO)
	List<Pagamento> findPagamentoByUsuario(@Param("idUsuario") Long idUsuario);
	
}
