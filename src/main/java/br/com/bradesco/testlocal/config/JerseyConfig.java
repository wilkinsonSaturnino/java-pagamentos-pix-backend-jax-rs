package br.com.bradesco.testlocal.config;

import org.glassfish.jersey.server.ResourceConfig;
import org.springframework.stereotype.Component;

import br.com.bradesco.testlocal.controller.PessoaController;

/*
 * Classe de configuração dos recursos REST. 
 * Configuração necessária para o funcionamento de uma API REST utilizando JAX-RS com Spring.
 * Essa configuração é feita no web.xml quando não se usa Spring. 
 * ResourceConfig: configuração de recursos para configurar uma web application.
 * @Component: permite que essa classe seja registrada enquanto o spring boot verifica 
 * automaticamente as classes java na pasta de origem.
 *  
 */

@Component
public class JerseyConfig extends ResourceConfig {

	public JerseyConfig() {
		register(PessoaController.class);
		register(CORSFilter.class);
	}
	
}
