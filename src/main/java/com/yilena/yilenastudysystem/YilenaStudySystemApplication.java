package com.yilena.yilenastudysystem;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;


@ServletComponentScan
@SpringBootApplication
public class YilenaStudySystemApplication {

    public static void main(String[] args) {
        SpringApplication.run(YilenaStudySystemApplication.class, args);
    }

}
