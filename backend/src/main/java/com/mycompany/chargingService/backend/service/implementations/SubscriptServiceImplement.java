package com.mycompany.chargingService.backend.service.implementations;

import com.mycompany.chargingService.backend.entity.Subscript;
import com.mycompany.chargingService.backend.repository.SubscriptRepository;
import com.mycompany.chargingService.backend.service.SubscriptService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Optional;

@Service
public class SubscriptServiceImplement implements SubscriptService {

    private SubscriptRepository repository;

    @Autowired
    public SubscriptServiceImplement(SubscriptRepository repository) {
        this.repository = repository;
    }

    @Override
    public Subscript saveSubscript(Subscript subscript) {
        return this.repository.save(subscript);
    }

    @Override
    public Optional<Subscript> getSubscriptById(Long id) {
        return this.repository.findById(id);
    }

    @Override
    public Iterable<Subscript> getAllSubscripts() {
        return this.repository.findAll();
    }

    @Override
    public void deleteSubscript(Long id) {
        this.repository.deleteById(id);
    }

    @Override
    public String uploadSubscriptsImage(MultipartFile image, Long id) throws IOException {
        String imageName = image.getOriginalFilename();
        Subscript subscript = this.repository.findById(id).get();
        File dir = new File("backend/src/images/subscriptsImages/" + subscript.getId().toString() + "_" + subscript.getName());
        if (!dir.exists()) {
            try {
                dir.mkdir();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        File serverFile = null;
        if (dir.isDirectory()) {
            serverFile = new File(dir, subscript.getId().toString() + "_" + subscript.getName() +
                    imageName.substring(imageName.lastIndexOf('.')));
            BufferedOutputStream stream = new BufferedOutputStream(new FileOutputStream(serverFile));
            stream.write(image.getBytes());
            stream.close();
        }

        return serverFile.getPath();
    }

}
