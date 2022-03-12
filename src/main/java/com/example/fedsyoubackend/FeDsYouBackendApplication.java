package com.example.fedsyoubackend;

import com.example.fedsyoubackend.controllers.SongController;
import com.example.fedsyoubackend.model.Song;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import com.example.fedsyoubackend.services.StorageService;
import org.springframework.web.multipart.MultipartFile;

@SpringBootApplication
public class FeDsYouBackendApplication {

    public static void main(String[] args) {
        ApplicationContext context = SpringApplication.run(FeDsYouBackendApplication.class, args);

        StorageService storageService = context.getBean(StorageService.class);

    }

}
