package kaizone.songmaya.smartns.controller;

import kaizone.songmaya.smartns.jpa.CustomerExtraJpa;
import kaizone.songmaya.smartns.model.CustomerExtra;
import kaizone.songmaya.smartns.util.DateUtils;
import kaizone.songmaya.smartns.util.ImageUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Date;
import java.util.Map;

@RestController
public class ApplyController extends BaseController {

    @Autowired
    private CustomerExtraJpa customerExtraJpa;

    @Value("${common.app.rootDir}")
    private String rootDir;

    @RequestMapping(value = "/setup1", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> setup1(@RequestBody CustomerExtra params) {
        String ssoId = params.getSsoId();
        if (StringUtils.isEmpty(ssoId)) {
            return fail("参数错误");
        }
        CustomerExtra extraInfo = customerExtraJpa.findBySsoId(ssoId);
        if (extraInfo != null) {
            params.setId(extraInfo.getId());
            params.setSsoId(ssoId);
            params.fill(extraInfo);
        }

        customerExtraJpa.save(params);
        return success("提交功能");
    }


    @RequestMapping(value = "/setup2", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> setup2(@RequestBody CustomerExtra params) throws Exception {
        String ssoId = params.getSsoId();
        if (StringUtils.isEmpty(ssoId)) {
            return fail("参数错误");
        }

        CustomerExtra extraInfo = customerExtraJpa.findBySsoId(ssoId);
        if (extraInfo == null) {
            return fail("不存在的用户");
        }

        if (StringUtils.isEmpty(params.getIdFront())) {
            return fail("身份证正面未提交");
        }

        if (StringUtils.isEmpty(params.getIdReverse())) {
            return fail("身份证反面未提交");
        }

        if (StringUtils.isEmpty(params.getIdHold())) {
            return fail("手持身份证未提交");
        }
        Date now = new Date();
        String nowDir = DateUtils.toDateString(now);
        String dirPath = rootDir + File.separator + nowDir + File.separator + ssoId;
        Path saveDir = Paths.get(dirPath);
        if (!Files.exists(saveDir)) {
            Files.createDirectories(saveDir);
        }
        ImageUtil.save(params.getIdFront(), saveDir + File.separator + "idFront.jpg");
        ImageUtil.save(params.getIdReverse(), saveDir + File.separator + "idReverse.jpg");
        ImageUtil.save(params.getIdHold(), saveDir + File.separator + "idHold.jpg");

        customerExtraJpa.save(extraInfo);
        return success("提交成功");
    }

    @RequestMapping(value = "/setup3", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> setup3(@RequestParam("ssoId") String ssoId, @RequestParam("files") MultipartFile[] files) throws Exception {
        if (StringUtils.isEmpty(ssoId)) {
            return fail("参数错误");
        }

        CustomerExtra extraInfo = customerExtraJpa.findBySsoId(ssoId);
        if (extraInfo == null) {
            return fail("不存在的用户");
        }

        for (int i = 0; i < files.length; i++) {
            MultipartFile file = files[i];
            String fileName = files[0].getOriginalFilename();
            if (fileName != null && !"".equalsIgnoreCase(fileName.trim())) {
                Date now = new Date();
                String nowDir = DateUtils.toDateString(now);
                String dirPath = rootDir + File.separator + nowDir + File.separator + ssoId;
                Path saveDir = Paths.get(dirPath);
                if (!Files.exists(saveDir)) {
                    Files.createDirectories(saveDir);
                }

                Path filePath = Paths.get(dirPath, file.getOriginalFilename());
                Files.copy(file.getInputStream(), filePath);
                if (i == 0) {
                    extraInfo.setIdFront(filePath.toFile().getAbsolutePath());
                }
                if (i == 1) {
                    extraInfo.setIdReverse(filePath.toFile().getAbsolutePath());
                }
                if (i == 2) {
                    extraInfo.setIdHold(filePath.toFile().getAbsolutePath());
                }
            }
        }

        customerExtraJpa.save(extraInfo);
        return success("提交成功");
    }
}
