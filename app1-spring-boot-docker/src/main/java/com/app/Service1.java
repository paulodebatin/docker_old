package com.app;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.app.respositori.Repository1;

@Service
public class Service1 {
	
	private Repository1 repositorio;

	public String recupera() {
		return repositorio.recupera();
	}
	
	@Autowired
	public Service1(Repository1 repositorio) {
		this.repositorio = repositorio;
				
	}
}
