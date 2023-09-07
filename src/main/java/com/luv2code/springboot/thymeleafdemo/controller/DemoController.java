package com.luv2code.springboot.thymeleafdemo.controller;

import java.util.List;

import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.luv2code.springboot.thymeleafdemo.entity.Details;
import com.luv2code.springboot.thymeleafdemo.entity.Login;
import com.luv2code.springboot.thymeleafdemo.entity.NewDetails;
import com.luv2code.springboot.thymeleafdemo.entity.Token;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Controller
public class DemoController {

	private final RestTemplateBuilder restTemplateBuilder;

	public Token token = null;

	ObjectMapper obj = new ObjectMapper();

	@GetMapping("/login")
	public String login(Model theModal) {

		Login theLogin = new Login();

		theModal.addAttribute("login", theLogin);

		return "login-page";
	}

	@PostMapping("/loginFormSubmit")
	public String loginSubmit(@ModelAttribute("login") Login theLogin) {

		String url = "https://qa2.sunbasedata.com/sunbase/portal/api/assignment_auth.jsp";

		RestTemplate restTemplate = restTemplateBuilder.build();

		String jsonUser = null;
		try {
			jsonUser = obj.writeValueAsString(theLogin);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}

		ResponseEntity<String> response = restTemplate.postForEntity(url, jsonUser, String.class);

		try {
			token = obj.readValue(response.getBody(), Token.class);
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}

		return "redirect:/list";
	}

	@GetMapping("/list")
	public String sayHello(Model theModel) {

		String url = "https://qa2.sunbasedata.com/sunbase/portal/api/assignment.jsp?cmd=get_customer_list";

		RestTemplate restTemplate = restTemplateBuilder.build();

		org.springframework.http.HttpHeaders headers = new org.springframework.http.HttpHeaders();

		headers.set("Authorization", "Bearer " + token.getAccess_token());

		HttpEntity<Void> requestEntity = new HttpEntity<>(headers);

		ParameterizedTypeReference<List<Details>> responseType = new ParameterizedTypeReference<List<Details>>() {
		};

		ResponseEntity<List<Details>> response = restTemplate.exchange(url, HttpMethod.GET, requestEntity,
				responseType);

		List<Details> detailsList = response.getBody();

		theModel.addAttribute("detailsList", detailsList);

		return "employees/list-employees";
	}

	@GetMapping("/showFormForAdd")
	public String showFormForAdd(Model theModal) {

		NewDetails theNewDetails = new NewDetails("", "", "", "", "", "", "", "");

		theModal.addAttribute("details", theNewDetails);

		return "employees/employee-add";
	}

	@PostMapping("/add")
	public String addDetails(@ModelAttribute("details") NewDetails theNewDetails) {

		String url = "https://qa2.sunbasedata.com/sunbase/portal/api/assignment.jsp?cmd=create";

		RestTemplate restTemplate = restTemplateBuilder.build();

		org.springframework.http.HttpHeaders headers = new org.springframework.http.HttpHeaders();

		headers.set("Authorization", "Bearer " + token.getAccess_token());

		HttpEntity<NewDetails> requestEntity = new HttpEntity<>(theNewDetails, headers);

		restTemplate.exchange(url, HttpMethod.POST, requestEntity, String.class);

		return "redirect:/list";
	}

	@GetMapping("/showFormForUpdate")
	public String showFormForUpdate(@RequestParam("detailsId") String theId, Model theModal) {

		String url = "https://qa2.sunbasedata.com/sunbase/portal/api/assignment.jsp?cmd=get_customer_list";

		RestTemplate restTemplate = restTemplateBuilder.build();

		org.springframework.http.HttpHeaders headers = new org.springframework.http.HttpHeaders();

		headers.set("Authorization", "Bearer " + token.getAccess_token());

		HttpEntity<Void> requestEntity = new HttpEntity<>(headers);

		ParameterizedTypeReference<List<Details>> responseType = new ParameterizedTypeReference<List<Details>>() {
		};

		ResponseEntity<List<Details>> response = restTemplate.exchange(url, HttpMethod.GET, requestEntity,
				responseType);

		List<Details> detailsList = response.getBody();

		Details theDetails = null;

		for (Details detail : detailsList) {
			if (detail.getUuid().equals(theId)) {
				theDetails = detail;
			}
		}

		theModal.addAttribute("details", theDetails);

		return "employees/employee-update";
	}

	@PostMapping("/save")
	public String saveDetails(@ModelAttribute("details") Details theDetails) {

		String url = "https://qa2.sunbasedata.com/sunbase/portal/api/assignment.jsp?cmd=update&uuid="
				+ theDetails.getUuid();

		RestTemplate restTemplate = restTemplateBuilder.build();

		org.springframework.http.HttpHeaders headers = new org.springframework.http.HttpHeaders();

		headers.set("Authorization", "Bearer " + token.getAccess_token());

		HttpEntity<Details> requestEntity = new HttpEntity<>(theDetails, headers);

		restTemplate.exchange(url, HttpMethod.POST, requestEntity, String.class);

		return "redirect:/list";
	}

	@GetMapping("/delete")
	public String deleteEmployee(@RequestParam("detailsId") String theId) {

		String url = "https://qa2.sunbasedata.com/sunbase/portal/api/assignment.jsp?cmd=delete&uuid=" + theId;

		RestTemplate restTemplate = restTemplateBuilder.build();

		org.springframework.http.HttpHeaders headers = new org.springframework.http.HttpHeaders();

		headers.set("Authorization", "Bearer " + token.getAccess_token());

		HttpEntity<Void> requestEntity = new HttpEntity<>(headers);

		restTemplate.exchange(url, HttpMethod.POST, requestEntity, String.class);

		return "redirect:/list";
	}
}
