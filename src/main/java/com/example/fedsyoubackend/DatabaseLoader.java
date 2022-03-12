package com.example.fedsyoubackend;

import com.example.fedsyoubackend.model.Song;
import com.example.fedsyoubackend.model.User;
import com.example.fedsyoubackend.repository.SongRepository;
import com.example.fedsyoubackend.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component  //it is automatically picked up by SpringBootApplication
public class DatabaseLoader implements CommandLineRunner {

    private final SongRepository songRepository;
    private final UserRepository userRepository;

    @Autowired
    public DatabaseLoader(SongRepository songRepository, UserRepository userRepository){
        this.songRepository = songRepository;
        this.userRepository = userRepository;
    }

    @Override
    public void run(String... args) throws Exception {

        //UPLOAD IN BAZA DE DATE
        User admin = new User("admin", "admin", "ADMIN");
        User client1 = new User("robi", "1234", "USER");
        User client2 = new User("lari", "1234", "USER");

        if(!this.userRepository.existsUserByUsernameEquals(admin.getUsername())){
            this.userRepository.save(admin);
        }

        if(!this.userRepository.existsUserByUsernameEquals(client1.getUsername())){
            this.userRepository.save(client1);
        }

        if(!this.userRepository.existsUserByUsernameEquals(client2.getUsername())){
            this.userRepository.save(client2);
        }

    }


}
