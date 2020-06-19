package org.demo;

import java.io.*;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.MultipartConfigElement;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.web.servlet.MultipartConfigFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories(basePackages = "org.demo.DAO")
@EntityScan(basePackages = "org.demo.Entity")
public class EchoApplication {
    static List<String> names = new ArrayList<String>(); //random nickname array

    public static void main(String[] args) {
        readNicknames();
        SpringApplication.run(EchoApplication.class, args);
    }

    @Bean
    MultipartConfigElement multipartConfigElement() {
        MultipartConfigFactory factory = new MultipartConfigFactory();
        //String location = "/usr/local/voiceImgs/";
        String location = "D:/echoImgs/";
        File tmpFile = new File(location);
        if (!tmpFile.exists()) {
            tmpFile.mkdirs();
        }
        factory.setLocation(location);
        return factory.createMultipartConfig();
    }

    public static void readNicknames() {
        System.out.println("reading nicknames");
        String fileName = "D:/source group/IDEA/ECHO/resource/nickname.txt";
        try {
            BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(fileName), "GBK"));
            String str = null;
            int cnt = 0;
            while ((str = br.readLine()) != null) {
                names.add(str);
                cnt++;
                if (cnt == 10000)
                    break;
            }
            br.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static String getRandomName() {
        int seed = (int) (Math.random()*10000);
        return names.get(seed);
    }
}
