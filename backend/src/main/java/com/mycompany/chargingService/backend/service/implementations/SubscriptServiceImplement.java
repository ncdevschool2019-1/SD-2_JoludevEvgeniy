package com.mycompany.chargingService.backend.service.implementations;

import com.mycompany.chargingService.backend.entity.Subscript;
import com.mycompany.chargingService.backend.repository.SubscriptRepository;
import com.mycompany.chargingService.backend.service.SubscriptService;
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
public class SubscriptServiceImplement implements SubscriptService {

    private SubscriptRepository subscriptRepository;

    @Autowired
    public SubscriptServiceImplement(SubscriptRepository subscriptRepository) {
        this.subscriptRepository = subscriptRepository;
    }

    @Override
    public Subscript saveSubscript(Subscript subscript) {
        return this.subscriptRepository.save(subscript);
    }

    @Override
    public Optional<Subscript> getSubscriptById(Long id) {
        return this.subscriptRepository.findById(id);
    }

    @Override
    public Iterable<Subscript> getAllSubscripts() {
        return this.subscriptRepository.findAll();
    }

    @Override
    public void deleteSubscript(Long id) {
        this.subscriptRepository.deleteById(id);
    }

    @Override
    public boolean uploadSubscriptsImage(MultipartFile image, Long id) throws IOException {
        String imageName = image.getOriginalFilename();
        if (this.subscriptRepository.findById(id).isPresent()) {
            Subscript subscript = this.subscriptRepository.findById(id).get();
            String imageNewName = subscript.getId().toString() + "_" + subscript.getName() +
                    imageName.substring(imageName.lastIndexOf('.'));
            File serverFile = new File("backend/src/images/subscriptsImages/", imageNewName);
            BufferedOutputStream stream = new BufferedOutputStream(new FileOutputStream(serverFile));
            stream.write(image.getBytes());
            stream.close();
            subscript.setImagePath(imageNewName);
            this.subscriptRepository.save(subscript);
            return true;
        }
        return false;
    }

    @Override
    public Resource getImage(String imageName) {
        try {
            Path file = Paths.get("backend/src/images/subscriptsImages/" + imageName);
            return new UrlResource(file.toUri());
        }
        catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void deleteImage(String imageName) {
        File image = new File("backend/src/images/subscriptsImages/" + imageName);
        try{
            image.delete();
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }


}
