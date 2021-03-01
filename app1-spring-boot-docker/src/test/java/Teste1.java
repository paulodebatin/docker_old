import static org.mockito.Mockito.doReturn;

import org.junit.Test;
import org.mockito.Mockito;

import com.app.Service1;
import com.app.respositori.Repository1;

public class Teste1 {
	
	@Test
	public void recuperaTest() {
		Service1 service = criaServiceComRepositorioMockeado();
		System.out.println(service.recupera());
	}
	
	private Service1 criaServiceComRepositorioMockeado() {
		Repository1 repositorio = Mockito.mock(Repository1.class);
		String resultadoRecuperacao = "Dados recuperados pelo Mock";
		
		doReturn(resultadoRecuperacao).when(repositorio).recupera();
		
		Service1 service = new Service1(repositorio);
		return service;
	}

}
