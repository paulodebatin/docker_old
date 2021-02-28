package com.app.agendamento;

import java.util.Calendar;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class Teste {
	
	@Scheduled(cron = "0/20 * * * * ?")
	public void create() {
		System.out.println("Exemplo agendamento: " + Calendar.getInstance().getTime());
	}


}
