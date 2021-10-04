package com.c4l.fileUploader;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.io.File;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

import org.apache.commons.io.IOUtils;
import org.apache.commons.io.LineIterator;
import org.apache.commons.io.input.NullInputStream;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.io.TempDir;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.multipart.MultipartFile;

import com.c4l.fileUploader.controller.FileUplodController;
import com.c4l.fileUploader.model.GenericResponse;
import com.c4l.fileUploader.service.FileUploadService;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
@SpringBootTest(properties = "spring.cloud.config.enabled=false", webEnvironment = WebEnvironment.RANDOM_PORT)
@TestPropertySource(locations = "classpath:application-test.properties")
@ActiveProfiles("test")
@AutoConfigureMockMvc
public class FileUploadControllerTest {
	@Autowired
	MockMvc mockMvc;
	
	@Mock
	private FileUplodController fileUplodController;
	
	@MockBean
	private FileUploadService fileUploadService;
	
	@Autowired
	private TestRestTemplate template;
	
	@LocalServerPort
	private int port;
	
	@TempDir
	public File temporaryFolder;
	
	@Mock
	InputStream  inStream;
	
	@Mock
	IOUtils ioutils;
	
	@Mock
	MultipartFile multipartFile;
	
	@Mock
	LineIterator lineIterator;
	
	/*
	 * @Test public void shouldSaveUploadedFile() throws Exception {
	 * MockMultipartFile file = new MockMultipartFile("file", "test.txt",
	 * "text/plain", "Spring Framework".getBytes()); InputStream in = new
	 * NullInputStream(100L);
	 * when(multipartFile.getInputStream()).thenReturn(inStream); try
	 * (MockedStatic<IOUtils> mockedIOUtils = Mockito.mockStatic(IOUtils.class)) {
	 * mockedIOUtils.when(() -> IOUtils.lineIterator(in,
	 * StandardCharsets.UTF_8.toString())) .thenReturn(lineIterator); }
	 * ResponseEntity<GenericResponse> response =
	 * template.postForEntity("http://localhost:" + port + "/V1/upload",
	 * getHttpEntity(file), GenericResponse.class); assertThat(202,
	 * is(response.getStatusCode().value()));
	 * 
	 * }
	 * 
	 * @Test public void shouldUploadFile() throws Exception { // ClassPathResource
	 * resource = new // ClassPathResource(
	 * "C:\\Bharath\\Workspace\\Interview\\fileUploader\\src\\main\\java\\com\\c4l\\testupload.txt",
	 * // getClass()); MockMultipartFile file = new MockMultipartFile("file",
	 * "test.txt", "text/plain", "Spring Framework".getBytes());
	 * MultiValueMap<String, Object> map = new LinkedMultiValueMap<String,
	 * Object>(); map.add("file", file); ResponseEntity<String> response =
	 * this.template.postForEntity("http://localhost:" + port + "/V1/upload", map,
	 * String.class); assertThat(202, is(response.getStatusCode().value())); }
	 */
	private <T> HttpEntity<T> getHttpEntity(T body) {
		
		  HttpHeaders headers = new HttpHeaders();
		  headers.setContentType(MediaType.MULTIPART_FORM_DATA);
		 
		return new HttpEntity<T>(body,headers);
	}
	
	
}
