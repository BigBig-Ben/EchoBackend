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
    @RequestMapping(value = "/api/addImg", method = RequestMethod.POST)
    public String uploadImage(HttpServletRequest request, HttpServletResponse response) throws IOException {
        MultipartHttpServletRequest req = (MultipartHttpServletRequest) request;
        MultipartFile multipartFile = req.getFile("file");
        //String realPath = "/usr/local/voiceImgs/";
        String realPath = "D:/echoImgs/";
        String imgPath = new String();
        String imgName = new String();
        try {
            File dir = new File(realPath);
            if (!dir.exists()) {
                dir.mkdir();
            }
            File file = new File(realPath, System.currentTimeMillis() + ".jpg");
            if (file.exists() == false)
                file.createNewFile();
            multipartFile.transferTo(file);
            imgName = "http://3p233v4064.qicp.vip/images/"+file.getName();
            //imgName = "http://192.168.123.164:8080/images/" + file.getName();
            //System.out.println("real path: " + realPath);
            //System.out.println("image name: " + imgName);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (IllegalStateException e) {
            e.printStackTrace();
        }
        return imgName;
    }
}
