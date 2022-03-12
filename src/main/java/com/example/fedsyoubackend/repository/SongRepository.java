package com.example.fedsyoubackend.repository;

import com.example.fedsyoubackend.model.Song;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;

public interface SongRepository extends MongoRepository<Song, String> {

    List<Song> findSongsByTitle(String title);

    Song findSongByTitle(String title);

    List<Song> findSongsByFileName(List<String> filename);

    @Override
    List<Song> findAll();

    void deleteSongById(String id);

    void deleteSongByTitle(String title);

    Song findByFileName(String filename);

    void deleteSongByFileNameEquals(String filename);

    boolean existsSongByFileNameEquals(String filename);

    boolean existsSongByTitleEquals(String title);

}
