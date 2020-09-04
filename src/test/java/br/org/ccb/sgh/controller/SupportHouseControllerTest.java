package br.org.ccb.sgh.controller;

import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.Matchers.endsWith;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Locale;

import org.hibernate.ObjectNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.servlet.i18n.FixedLocaleResolver;

import com.fasterxml.jackson.databind.ObjectMapper;

import br.org.ccb.sgh.TestUtils;
import br.org.ccb.sgh.controller.handler.ResourceExceptionHandler;
import br.org.ccb.sgh.entity.SupportHouse;
import br.org.ccb.sgh.http.dto.SupportHouseDto;
import br.org.ccb.sgh.http.dto.SupportHouseRequestParamsDto;
import br.org.ccb.sgh.service.SupportHouseService;

@SpringBootTest
class SupportHouseControllerTest {

	private static final String URL = "/supporthouses";
	private static final String BY_INVALID_ID = URL.concat("/a");
	private static final String BY_ID = URL.concat("/1");
	private MockMvc mockMvc;

	@MockBean
	private SupportHouseService supportHouseService;

	@InjectMocks
	private SupportHouseController supportHouseController;

	@BeforeEach
	void setUp() {
		this.mockMvc = MockMvcBuilders.standaloneSetup(supportHouseController)
				.setControllerAdvice(new ResourceExceptionHandler())
				.setLocaleResolver(new FixedLocaleResolver(new Locale("pt", "BR"))).build();
		MockitoAnnotations.initMocks(this);
	}

	@Test
	void findAllBadRequestErrorTest() throws Exception {
		when(this.supportHouseService.findAll(any(SupportHouseRequestParamsDto.class)))
				.thenThrow(new DataIntegrityViolationException("Error"));
		this.mockMvc.perform(get(URL).contentType(MediaType.APPLICATION_JSON)).andExpect(status().isBadRequest())
				.andExpect(jsonPath("timestamp", notNullValue())).andExpect(jsonPath("status", is(400)))
				.andExpect(jsonPath("message", is("Não é possível remover este registro pois está sendo utilizado")))
				.andExpect(jsonPath("error", is("Error"))).andExpect(jsonPath("path", is(URL)));
	}

	@Test
	void findAllInvalidIdTest() throws Exception {
		when(this.supportHouseService.findAll(any(SupportHouseRequestParamsDto.class)))
				.thenReturn(new PageImpl<>(TestUtils.createSupportHouseList()));
		this.mockMvc.perform(get(URL.concat("?id=a")).contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isBadRequest()).andExpect(jsonPath("timestamp", notNullValue()))
				.andExpect(jsonPath("status", is(400)))
				.andExpect(jsonPath("error", is(
						"Failed to convert value of type 'java.lang.String' to required type 'java.lang.Long'; nested exception is java.lang.NumberFormatException: For input string: \"a\"")))
				.andExpect(jsonPath("message", is("Valor incorreto passado para o parâmetro: id")))
				.andExpect(jsonPath("path", is(URL)));
	}

	@Test
	void findAllSuccessTest() throws Exception {
		when(this.supportHouseService.findAll(any(SupportHouseRequestParamsDto.class)))
				.thenReturn(new PageImpl<>(TestUtils.createSupportHouseList()));
		this.mockMvc.perform(get(URL).contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk())
				.andExpect(jsonPath("$.content", hasSize(2))).andExpect(jsonPath("$.content.[0].id", is(1)))
				.andExpect(jsonPath("$.content.[0].name", is("Test")))
				.andExpect(jsonPath("$.content.[0].cnpj", is("48183050000188")))
				.andExpect(jsonPath("$.content.[0].address.id", is(1)))
				.andExpect(jsonPath("$.content.[0].address.street", is("Street")))
				.andExpect(jsonPath("$.content.[0].address.city", is("Test")))
				.andExpect(jsonPath("$.content.[0].address.district", is("District")))
				.andExpect(jsonPath("$.content.[0].address.number", is("1F")))
				.andExpect(jsonPath("$.content.[0].address.state", is("SP")))
				.andExpect(jsonPath("$.content.[0].address.zipCode", is("11111111")))
				.andExpect(jsonPath("$.content.[1].id", is(2))).andExpect(jsonPath("$.content.[1].name", is("Test2")))
				.andExpect(jsonPath("$.content.[1].cnpj", is("39313910000160")))
				.andExpect(jsonPath("$.content.[1].address.id", is(2)))
				.andExpect(jsonPath("$.content.[1].address.street", is("Street2")))
				.andExpect(jsonPath("$.content.[1].address.city", is("Test2")))
				.andExpect(jsonPath("$.content.[1].address.district", is("District2")))
				.andExpect(jsonPath("$.content.[1].address.number", is("1G")))
				.andExpect(jsonPath("$.content.[1].address.state", is("MG")))
				.andExpect(jsonPath("$.content.[1].address.zipCode", is("22222222")));
	}

	@Test
	void findByIdNotFoundErrorTest() throws Exception {
		when(this.supportHouseService.byId(1l))
				.thenThrow(new ObjectNotFoundException(1l, SupportHouse.class.getName()));
		this.mockMvc.perform(get(BY_ID).contentType(MediaType.APPLICATION_JSON)).andExpect(status().isNotFound())
				.andExpect(jsonPath("timestamp", notNullValue())).andExpect(jsonPath("status", is(404)))
				.andExpect(jsonPath("message", is("Registro com id 1 não encontrado")))
				.andExpect(jsonPath("error",
						is("No row with the given identifier exists: [br.org.ccb.sgh.entity.SupportHouse#1]")))
				.andExpect(jsonPath("path", is(BY_ID)));
	}

	@Test
	void findByInvalidIdTest() throws Exception {
		when(this.supportHouseService.byId(1l))
				.thenThrow(new ObjectNotFoundException(1l, SupportHouse.class.getName()));
		this.mockMvc.perform(get(BY_INVALID_ID).contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isBadRequest()).andExpect(jsonPath("timestamp", notNullValue()))
				.andExpect(jsonPath("status", is(400)))
				.andExpect(jsonPath("error", is(
						"Failed to convert value of type 'java.lang.String' to required type 'java.lang.Long'; nested exception is java.lang.NumberFormatException: For input string: \"a\"")))
				.andExpect(jsonPath("message", is("Valor incorreto passado para o parâmetro: id")))
				.andExpect(jsonPath("path", is(BY_INVALID_ID)));
	}

	@Test
	void findByIdSuccessTest() throws Exception {
		when(this.supportHouseService.byId(1l)).thenReturn(TestUtils.createSupportHouseList().get(0));
		this.mockMvc.perform(get(BY_ID).contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk())
				.andExpect(jsonPath("id", is(1))).andExpect(jsonPath("name", is("Test")))
				.andExpect(jsonPath("cnpj", is("48183050000188"))).andExpect(jsonPath("address.id", is(1)))
				.andExpect(jsonPath("address.street", is("Street"))).andExpect(jsonPath("address.city", is("Test")))
				.andExpect(jsonPath("address.district", is("District"))).andExpect(jsonPath("address.number", is("1F")))
				.andExpect(jsonPath("address.state", is("SP"))).andExpect(jsonPath("address.zipCode", is("11111111")));
	}

	@Test
	void saveBadRequestErrorTest() throws Exception {
		when(this.supportHouseService.save(any(SupportHouseDto.class)))
				.thenThrow(new DataIntegrityViolationException("Error"));
		this.mockMvc
				.perform(post(URL).content(new ObjectMapper().writeValueAsString(TestUtils.createSupportHouseDto()))
						.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isBadRequest()).andExpect(jsonPath("timestamp", notNullValue()))
				.andExpect(jsonPath("status", is(400)))
				.andExpect(jsonPath("message", is("Não é possível remover este registro pois está sendo utilizado")))
				.andExpect(jsonPath("error", is("Error"))).andExpect(jsonPath("path", is(URL)));
	}

	@Test
	void saveInvalidNameTest() throws Exception {
		SupportHouseDto supportHouseDto = TestUtils.createSupportHouseDto();
		supportHouseDto.setName(supportHouseDto.getName().repeat(255));
		this.mockMvc
				.perform(post(URL).content(new ObjectMapper().writeValueAsString(supportHouseDto))
						.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isUnprocessableEntity()).andExpect(jsonPath("timestamp", notNullValue()))
				.andExpect(jsonPath("status", is(422)))
				.andExpect(jsonPath("error", is(
						"Validation failed for argument [0] in public org.springframework.http.ResponseEntity<java.lang.Void> br.org.ccb.sgh.controller.SupportHouseController.save(br.org.ccb.sgh.http.dto.SupportHouseDto): [Field error in object 'supportHouseDto' on field 'name': rejected value [TestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTest]; codes [Size.supportHouseDto.name,Size.name,Size.java.lang.String,Size]; arguments [org.springframework.context.support.DefaultMessageSourceResolvable: codes [supportHouseDto.name,name]; arguments []; default message [name],255,1]; default message [tamanho deve ser entre 1 e 255]] ")))
				.andExpect(jsonPath("message", is("Erro de validação: name: tamanho deve ser entre 1 e 255 ")))
				.andExpect(jsonPath("path", is(URL)));
	}

	@Test
	void saveInvalidCnpjTest() throws Exception {
		SupportHouseDto supportHouseDto = TestUtils.createSupportHouseDto();
		supportHouseDto.setCnpj("11223344556677");
		this.mockMvc
				.perform(post(URL).content(new ObjectMapper().writeValueAsString(supportHouseDto))
						.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isUnprocessableEntity()).andExpect(jsonPath("timestamp", notNullValue()))
				.andExpect(jsonPath("status", is(422)))
				.andExpect(jsonPath("error", is(
						"Validation failed for argument [0] in public org.springframework.http.ResponseEntity<java.lang.Void> br.org.ccb.sgh.controller.SupportHouseController.save(br.org.ccb.sgh.http.dto.SupportHouseDto): [Field error in object 'supportHouseDto' on field 'cnpj': rejected value [11223344556677]; codes [CNPJ.supportHouseDto.cnpj,CNPJ.cnpj,CNPJ.java.lang.String,CNPJ]; arguments [org.springframework.context.support.DefaultMessageSourceResolvable: codes [supportHouseDto.cnpj,cnpj]; arguments []; default message [cnpj]]; default message [número do registro de contribuinte corporativo brasileiro (CNPJ) inválido]] ")))
				.andExpect(jsonPath("message", is(
						"Erro de validação: cnpj: número do registro de contribuinte corporativo brasileiro (CNPJ) inválido ")))
				.andExpect(jsonPath("path", is(URL)));
	}

	@Test
	void saveSuccessTest() throws Exception {
		when(this.supportHouseService.save(any(SupportHouseDto.class)))
				.thenReturn(TestUtils.createSupportHouseList().get(0));
		this.mockMvc
				.perform(post(URL).content(new ObjectMapper().writeValueAsString(TestUtils.createSupportHouseDto()))
						.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isCreated()).andExpect(header().exists("location"))
				.andExpect(header().string("location", endsWith(BY_ID)));
	}

	@Test
	void updateNotFoundErrorTest() throws Exception {
		when(this.supportHouseService.update(eq(1l), any(SupportHouseDto.class)))
				.thenThrow(new ObjectNotFoundException(1l, SupportHouse.class.getName()));
		this.mockMvc
				.perform(put(BY_ID).content(new ObjectMapper().writeValueAsString(TestUtils.createSupportHouseDto()))
						.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isNotFound()).andExpect(jsonPath("timestamp", notNullValue()))
				.andExpect(jsonPath("status", is(404)))
				.andExpect(jsonPath("message", is("Registro com id 1 não encontrado")))
				.andExpect(jsonPath("error",
						is("No row with the given identifier exists: [br.org.ccb.sgh.entity.SupportHouse#1]")))
				.andExpect(jsonPath("path", is(BY_ID)));

	}

	@Test
	void updateInvalidNameTest() throws Exception {
		SupportHouseDto supportHouseDto = TestUtils.createSupportHouseDto();
		supportHouseDto.setName(supportHouseDto.getName().repeat(255));
		this.mockMvc
				.perform(put(BY_ID).content(new ObjectMapper().writeValueAsString(supportHouseDto))
						.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isUnprocessableEntity()).andExpect(jsonPath("status", is(422)))
				.andExpect(jsonPath("error", is(
						"Validation failed for argument [1] in public org.springframework.http.ResponseEntity<java.lang.Void> br.org.ccb.sgh.controller.SupportHouseController.update(java.lang.Long,br.org.ccb.sgh.http.dto.SupportHouseDto): [Field error in object 'supportHouseDto' on field 'name': rejected value [TestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTest]; codes [Size.supportHouseDto.name,Size.name,Size.java.lang.String,Size]; arguments [org.springframework.context.support.DefaultMessageSourceResolvable: codes [supportHouseDto.name,name]; arguments []; default message [name],255,1]; default message [tamanho deve ser entre 1 e 255]] ")))
				.andExpect(jsonPath("message", is("Erro de validação: name: tamanho deve ser entre 1 e 255 ")))
				.andExpect(jsonPath("path", is(BY_ID)));
	}

	@Test
	void updateInvalidCnpjTest() throws Exception {
		SupportHouseDto supportHouseDto = TestUtils.createSupportHouseDto();
		supportHouseDto.setCnpj("11223344556677");
		this.mockMvc
				.perform(put(BY_ID).content(new ObjectMapper().writeValueAsString(supportHouseDto))
						.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isUnprocessableEntity()).andExpect(jsonPath("status", is(422)))
				.andExpect(jsonPath("error", is(
						"Validation failed for argument [1] in public org.springframework.http.ResponseEntity<java.lang.Void> br.org.ccb.sgh.controller.SupportHouseController.update(java.lang.Long,br.org.ccb.sgh.http.dto.SupportHouseDto): [Field error in object 'supportHouseDto' on field 'cnpj': rejected value [11223344556677]; codes [CNPJ.supportHouseDto.cnpj,CNPJ.cnpj,CNPJ.java.lang.String,CNPJ]; arguments [org.springframework.context.support.DefaultMessageSourceResolvable: codes [supportHouseDto.cnpj,cnpj]; arguments []; default message [cnpj]]; default message [número do registro de contribuinte corporativo brasileiro (CNPJ) inválido]] ")))
				.andExpect(jsonPath("message", is(
						"Erro de validação: cnpj: número do registro de contribuinte corporativo brasileiro (CNPJ) inválido ")))
				.andExpect(jsonPath("path", is(BY_ID)));
	}

	@Test
	void updateSuccessTest() throws Exception {
		when(this.supportHouseService.update(eq(1l), any(SupportHouseDto.class)))
				.thenReturn(TestUtils.createSupportHouseList().get(0));
		this.mockMvc
				.perform(put(BY_ID).content(new ObjectMapper().writeValueAsString(TestUtils.createSupportHouseDto()))
						.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isNoContent());
	}

	@Test
	void removeNotFoundErrorTest() throws Exception {
		doThrow(new ObjectNotFoundException(1l, SupportHouse.class.getName())).when(this.supportHouseService)
				.remove(1l);
		this.mockMvc
				.perform(delete(BY_ID).content(new ObjectMapper().writeValueAsString(TestUtils.createSupportHouseDto()))
						.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isNotFound()).andExpect(jsonPath("timestamp", notNullValue()))
				.andExpect(jsonPath("status", is(404)))
				.andExpect(jsonPath("message", is("Registro com id 1 não encontrado")))
				.andExpect(jsonPath("error",
						is("No row with the given identifier exists: [br.org.ccb.sgh.entity.SupportHouse#1]")))
				.andExpect(jsonPath("path", is(BY_ID)));

	}

	@Test
	void removeSuccessTest() throws Exception {
		doNothing().when(this.supportHouseService).remove(1l);
		this.mockMvc
				.perform(delete(BY_ID).content(new ObjectMapper().writeValueAsString(TestUtils.createSupportHouseDto()))
						.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isNoContent());
	}
}
