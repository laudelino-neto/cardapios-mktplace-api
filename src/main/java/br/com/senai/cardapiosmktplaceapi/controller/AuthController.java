package br.com.senai.cardapiosmktplaceapi.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.google.common.base.Preconditions;

import br.com.senai.cardapiosmktplaceapi.dto.SolicitacaoDeToken;
import br.com.senai.cardapiosmktplaceapi.security.GerenciadorDeTokenJwt;

@RestController
@RequestMapping("/auth")
public class AuthController {
	
	@Autowired
	private GerenciadorDeTokenJwt gerenciadorDeToken;
	
	@Autowired
	private AuthenticationManager authenticationManager;

	@PostMapping	
	public ResponseEntity<?> logar(@RequestBody SolicitacaoDeToken solicitacao){
		Authentication tokenAutenticado = authenticationManager.authenticate(
				new UsernamePasswordAuthenticationToken(
						solicitacao.getLogin(), solicitacao.getSenha()));
		Preconditions.checkArgument(tokenAutenticado.isAuthenticated(), 
				"Login e/ou senha inválidos");
		String tokenGerado = gerenciadorDeToken.gerarTokenPor(solicitacao.getLogin());
		Map<String, Object> response = new HashMap<String, Object>();
		response.put("token", tokenGerado);
		return ResponseEntity.ok(response);
	}
	
}
