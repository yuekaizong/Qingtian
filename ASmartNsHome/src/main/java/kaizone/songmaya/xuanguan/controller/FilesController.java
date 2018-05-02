package kaizone.songmaya.xuanguan.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

@Controller
@RequestMapping("files")
public class FilesController {

    public final static String STORE_DIR = "storage/";

    @Autowired
    private ResourceLoader resourceLoader;

    @RequestMapping(method = RequestMethod.POST, value = "/save")
    @ResponseBody
    public String saveFile(@RequestParam("file") MultipartFile file, RedirectAttributes redirectAttributes, HttpServletRequest request) {
        System.out.println(request.getParameter("member"));
        System.out.println(System.getProperty("user.dir"));
        System.out.println(System.getProperty("java.io.tmpdir"));
        String contextPath = request.getServletContext().getRealPath("/");
        if (!file.isEmpty()) {
            try {
                File dir = new File(contextPath + STORE_DIR);
                if (!dir.exists()) {
                    dir.mkdir();
                }
                Files.copy(file.getInputStream(), Paths.get(contextPath + STORE_DIR, file.getOriginalFilename()));
                redirectAttributes.addFlashAttribute("message",
                        "You successfully uploaded " + file.getOriginalFilename() + "!");
            } catch (IOException | RuntimeException e) {
                redirectAttributes.addFlashAttribute("message", "Failued to upload " + file.getOriginalFilename() + " => " + e.getMessage());
            }
        } else {
            redirectAttributes.addFlashAttribute("message", "Failed to upload " + file.getOriginalFilename() + " because it was empty");
        }

        return "file upload success";
    }

    //显示图片的方法关键 匹配路径像 localhost:8080/b7c76eb3-5a67-4d41-ae5c-1642af3f8746.png
    @RequestMapping(method = RequestMethod.GET, value = "{path}/{filename:.+}")
    @ResponseBody
    public ResponseEntity<?> getFile(@PathVariable String path, @PathVariable String filename, HttpServletRequest request) {
        try {
            String contextPath = request.getServletContext().getRealPath("/");
            return ResponseEntity.ok(resourceLoader.getResource("file:" + Paths.get(contextPath + path, filename).toString()));
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    @RequestMapping(method = RequestMethod.GET, value = "static/images/{path}/{filename:.+}")
    @ResponseBody
    public ResponseEntity<?> getResourcesStaticImages(@PathVariable String path, @PathVariable String filename) {
        try {
            Resource resource = resourceLoader.getResource("classpath:/application.properties");
            return ResponseEntity.ok(resourceLoader.getResource("classpath:/static/images/" + path + "/" + filename));
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }
}
