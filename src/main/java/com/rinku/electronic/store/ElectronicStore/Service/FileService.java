package com.rinku.electronic.store.ElectronicStore.Service;

import org.springframework.web.multipart.MultipartFile;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

public interface FileService {
  //  public String uploadImage(MultipartFile file,String path) throws IOException;
    public String uploadFile(MultipartFile file,String path) throws IOException;

    InputStream getResource(String path,String name) throws FileNotFoundException;



}
