package com.Simple_mvc.Controller;

import com.Simple_mvc.Config.Sender;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartRequest;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author TB
 * @date 2019/4/11 - 9:49
 **/
@RestController
public class TestController {

    @Autowired
    private Sender sender;
    @PostMapping("xls")
    public String addList(HttpServletRequest request, HttpServletResponse response) throws Exception {
        //HttpServletRequest request, HttpServletResponse response
       MultipartFile file =((MultipartRequest) request).getFile("xlsfile");
        if(file!=null) {
           sender.send(file);
            return   "导入成功";
        }
        else{
            return  "请上传文件";
        }
    }

}