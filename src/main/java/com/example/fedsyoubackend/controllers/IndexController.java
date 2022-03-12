package com.example.fedsyoubackend.controllers;

import com.example.fedsyoubackend.model.Song;
import com.example.fedsyoubackend.repository.SongRepository;
import com.example.fedsyoubackend.services.StorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

//MVC model - Model, View, Controller
@Controller
@RequestMapping("/")
public class IndexController {

    private final StorageService storageService;
    private final SongRepository songRepository;

    @Autowired
    public IndexController(StorageService storageService, SongRepository songRepository) {
        this.storageService = storageService;
        this.songRepository = songRepository;
    }

    @GetMapping()
    public String getLoginPage(){

        return "login";
    }

    @GetMapping("/register")
    public String getRegisterPage(){

        return "register";
    }

    @GetMapping("/adminUploadSong")
    public String getUploadPage(){

        return "adminUploadSong";
    }

    @GetMapping("/adminUpdateSong")
    public String getUpdatePage(Model model){

        List<Song> songs = songRepository.findAll();
        model.addAttribute("songs", songs);

        return "adminUpdateSong";
    }

    @GetMapping("/adminDeleteSong")
    public String getDeletePage(Model model){

        List<Song> songs = songRepository.findAll();
        model.addAttribute("songs", songs);

        return "adminDeleteSong";
    }

    @GetMapping(value = "/adminHome")
    public String getAdminHomePage(Model model){

        List<Song> songs = songRepository.findAll();

        model.addAttribute("songs", songs);

        return "adminHome";
    }

    @GetMapping(value = "/userHome")
    public String getUserHomePage(Model model){

        List<Song> songs = songRepository.findAll();

        model.addAttribute("songs", songs);

        return "userHome";
    }

}
