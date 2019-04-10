package com.mycompany.chargingService.backend.service.implementations;

import com.mycompany.chargingService.backend.entity.User;
import com.mycompany.chargingService.backend.repository.RoleRepository;
import com.mycompany.chargingService.backend.repository.UserRepository;
import com.mycompany.chargingService.backend.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Optional;

@Service
public class UserServiceImplement implements UserService {

    private UserRepository userRepository;
    private RoleRepository roleRepository;

    @Autowired
    public UserServiceImplement(UserRepository userRepository, RoleRepository roleRepository) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
    }

    @Override
    public User saveUser(User user) {
        user.setRole(this.roleRepository.findById(1l).get());
        return this.userRepository.save(user);
    }

    @Override
    public User getUserById(Long id) {
        return this.userRepository.findById(id).isPresent() ?
                this.userRepository.findById(id).get() : null;
    }

    @Override
    public Iterable<User> getAllUsers() {
        return this.userRepository.findAll();
    }

    @Override
    public void deleteUser(Long id) {
        this.userRepository.deleteById(id);
    }

    @Override
    public User updateUsersLogin(Long id, String login) {
        this.userRepository.updateUsersLogin(id, login);
        return this.userRepository.findById(id).isPresent() ?
                this.userRepository.findById(id).get() : null;
    }

    @Override
    public User updateUsersPassword(Long id, String password) {
        this.userRepository.updateUsersPassword(id, password);
        return this.userRepository.findById(id).isPresent() ?
                this.userRepository.findById(id).get() : null;
    }

    @Override
    public User updateUsersEmail(Long id, String email) {
        this.userRepository.updateUsersEmail(id, email);
        return this.userRepository.findById(id).isPresent() ?
                this.userRepository.findById(id).get() : null;
    }

    @Override
    public User getLoginUser(String login, String password) {
        return this.userRepository.getLoginUser(login, password);
    }

    @Override
    public boolean uploadUsersImage(MultipartFile image, Long id) throws IOException {
        String imageName = image.getOriginalFilename();
        if (this.userRepository.findById(id).isPresent()) {
            User user = this.userRepository.findById(id).get();
            String imageNewName = user.getId().toString() + "_" + user.getLogin() +
                    imageName.substring(imageName.lastIndexOf('.'));
            File serverFile = new File("backend/src/images/usersImages/", imageNewName);
            BufferedOutputStream stream = new BufferedOutputStream(new FileOutputStream(serverFile));
            stream.write(image.getBytes());
            stream.close();
            user.setImagePath(imageNewName);
            this.userRepository.save(user);
            return true;
        }
        return false;
    }

    @Override
    public Resource getImage(String imageName) {
        try {
            Path file = Paths.get("backend/src/images/usersImages/" + imageName);
            return new UrlResource(file.toUri());
        }
        catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void deleteImage(String imageName) {
        File image = new File("backend/src/images/usersImages/" + imageName);
        try{
            image.delete();
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }


}
