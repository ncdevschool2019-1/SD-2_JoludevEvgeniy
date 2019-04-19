package com.mycompany.chargingService.backend.service.implementations;

import com.mycompany.chargingService.backend.entity.Role;
import com.mycompany.chargingService.backend.entity.User;
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

@Service
public class UserServiceImpl implements UserService {

    private UserRepository userRepository;

    @Autowired
    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public User saveUser(User user) {
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
    public User uploadUsersImage(MultipartFile image, Long id) throws IOException {
        String imageName = image.getOriginalFilename();
        if (this.userRepository.findById(id).isPresent()) {
            User user = this.userRepository.findById(id).get();
            String imageNewName = user.getId().toString() + "_" + user.getLogin() +
                    imageName.substring(imageName.lastIndexOf('.'));
            File serverFile = new File("backend/src/images/usersImages/", user.getId().toString());
            if(serverFile.exists()){
                deleteImage(imageNewName);
            }
            if(!serverFile.exists()){
                serverFile.mkdir();
            }
            serverFile = new File(serverFile.getPath() + "/", imageNewName);
            BufferedOutputStream stream = new BufferedOutputStream(new FileOutputStream(serverFile));
            stream.write(image.getBytes());
            stream.close();
            user.setImagePath(imageNewName);
            return this.userRepository.save(user);
        }
        return null;
    }

    @Override
    public Resource getImage(String imageName) {
        try {
            String imageDir = imageName.substring(0, imageName.indexOf('_')) + "/" + imageName;
            Path file = Paths.get("backend/src/images/usersImages/" + imageDir);
            return new UrlResource(file.toUri());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void deleteImage(String imageName) {
        String imageDir = imageName.substring(0, imageName.indexOf('_'));
        String path = "backend/src/images/usersImages/";
        File image = new File(path, imageDir + "/" + imageName);
        try {
            image.delete();
            if(!image.exists()){
                new File(path, imageDir).delete();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}
