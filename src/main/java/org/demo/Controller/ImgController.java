package org.demo.Controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;

@RestController
public class ImgController {
    @Value("${cbs.imagesStorePath}")
    private String imagesPath;

    @RequestMapping(value = "/api/addImg", method = RequestMethod.POST)
    public String uploadImage(HttpServletRequest request, HttpServletResponse response) throws IOException {
        MultipartHttpServletRequest req = (MultipartHttpServletRequest) request;
        MultipartFile multipartFile = req.getFile("file");

        String imgName = new String();
        try {
            File dir = new File(imagesPath);
            if (!dir.exists()) {
                dir.mkdir();
            }
            File file = new File(imagesPath, System.currentTimeMillis() + ".jpg");
            if (file.exists() == false)
                file.createNewFile();
            multipartFile.transferTo(file);
            imgName = "http://121.89.204.192:8080/images/"+file.getName();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (IllegalStateException e) {
            e.printStackTrace();
        }
        return imgName;
    }
}
