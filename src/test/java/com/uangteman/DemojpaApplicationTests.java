package com.uangteman;

import java.util.ArrayList;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.embedded.LocalServerPort;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import com.uangteman.entity.Category;
import com.uangteman.repo.CategoryRepo;
import com.uangteman.service.UserService;
import junit.framework.Assert;

@RunWith(SpringRunner.class)
@SpringBootTest(classes=DemoJpaApplication.class, webEnvironment=SpringBootTest.WebEnvironment.RANDOM_PORT)
public class DemojpaApplicationTests {
	
	@Autowired
	private UserService uService;
	
	@Autowired
	private CategoryRepo cRepo;
	
	@Autowired
	private TestRestTemplate testRestTemplate;
	
	@LocalServerPort
	int port;
	
	
	@Test
	public void contextLoads() {

	}
	
	@Test
	public void categoryTest() {
		Category category = new Category();
		category.setName("sample");
		
		Category result = cRepo.save(category);
		Assert.assertNotNull(result);
		
		result.setName("Sample Update");
		result = cRepo.save(result);
		Assert.assertEquals("Sample Update", result.getName());
		
		cRepo.delete(result);
		Assert.assertEquals(null, cRepo.findOne(result.getId()));
	}
	
	@Test
	public void loginTest() throws Exception {
		String email = "admin@gmail.com";
		String password = "d8578edf8458ce06fbc5bb76a58c5ca4";
		Assert.assertNotNull(uService.login(email, password));
	}
	
	@Test
	public void testGetCategory() {
		ResponseEntity<ArrayList> entity = this.testRestTemplate.getForEntity("http://localhost:"+this.port+"/category", ArrayList.class);
		Assert.assertEquals(HttpStatus.OK, entity.getStatusCode());
	}

}
