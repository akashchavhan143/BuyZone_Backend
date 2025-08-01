package com.ecommerce.storage;

import java.io.IOException;
import java.util.List;

import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;


public interface StorageService {

	List<String> loadAll();

	String store(MultipartFile file) ;

	Resource load(String fileName);

	void delete(String fileName);

}