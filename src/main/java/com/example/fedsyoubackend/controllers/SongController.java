package com.example.fedsyoubackend.controllers;

import com.example.fedsyoubackend.model.Song;
import com.example.fedsyoubackend.repository.SongRepository;
import com.example.fedsyoubackend.services.StorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.view.RedirectView;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/adminHome")
public class SongController {

    private final StorageService storageService;
    private final SongRepository songRepository;

    @Autowired
    public SongController(StorageService storageService, SongRepository songRepository) {
        this.storageService = storageService;
        this.songRepository = songRepository;
    }

    //this is a special obj to specify the http response code and our status code and you can also put data in that
    @GetMapping({"/api/songs"})
    public ResponseEntity<List<Song>> getSongs(){
        return ResponseEntity.ok(songRepository.findAll());     //when u make a gui request to our /api
    }

    @GetMapping("/api/songs/{id}")        //localhost:8080/api/songs/12324253244 - id
    public ResponseEntity<Song> getSong(@PathVariable String id){

        Optional<Song> song = songRepository.findById(id);

        if(song.isPresent()){
            return ResponseEntity.ok(song.get());
        }else{
            return ResponseEntity.notFound().build();
        }

    }

    //<?> is a wildcard => it can return anything
    @PostMapping(value ="/upload", consumes = "multipart/form-data")  //multipart because we have the song part and the file part
    public Object createSong(@RequestPart("title")String title, @RequestPart("artist")String artist, @RequestPart("file")MultipartFile file) throws IOException {

        RedirectView redirectView = new RedirectView();
        redirectView.setUrl("/adminUploadSong");

        Song song = new Song(title, artist);
        //see if there is already a song with that filename
        if(songRepository.existsSongByFileNameEquals(file.getOriginalFilename()) || songRepository.existsSongByTitleEquals(song.getTitle())){
            System.out.println("taken");
            return ResponseEntity.badRequest().body("taken");

        }else{
            System.out.println("Upload the file ... ");
            storageService.uploadSong(file);                    //insert into digitalocean

            //Saving the song data into the database
            song.setFileName(file.getOriginalFilename());
            song.setFavorited(false);
            Song insertedSong = songRepository.insert(song);    //insert into mongodb

            return redirectView;
//            return new ResponseEntity<>(insertedSong, HttpStatus.CREATED);
        }
    }

    @PutMapping("/admin/update/{filename}")
    public RedirectView updateSong(@PathVariable String filename, @RequestPart("title")String title, @RequestPart("artist")String artist){

        System.out.println("aici");
        System.out.println("SONG UPDATE: " + filename + "New Title " + title + "New Artist " + artist);
        RedirectView redirectView = new RedirectView();
        redirectView.setUrl("/adminUpdateSong");

        if(songRepository.existsSongByFileNameEquals(filename)){
            Song song = songRepository.findByFileName(filename);

            if(title != null){
                song.setTitle(title);
            }

            if(artist != null){
                song.setArtist(artist);
            }

            song.setFavorited(false);

            songRepository.save(song);

            return redirectView;
        }
        return redirectView;

    }

    @DeleteMapping("/delete/{filename}")
    public RedirectView deleteSong(@PathVariable String filename){
        RedirectView redirectView = new RedirectView();
        redirectView.setUrl("/adminDeleteSong");

        System.out.println(filename + "DELETED");

        if(songRepository.existsSongByFileNameEquals(filename)){
            songRepository.deleteSongByFileNameEquals(filename);
            storageService.deleteSong(filename);
            return redirectView;
        }else{
            return redirectView;
        }
    }

}
