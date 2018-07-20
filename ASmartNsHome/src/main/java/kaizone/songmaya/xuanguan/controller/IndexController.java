package kaizone.songmaya.xuanguan.controller;

import kaizone.songmaya.xuanguan.entity.DivCellEntity;
import kaizone.songmaya.xuanguan.entity.DivEntity;
import kaizone.songmaya.xuanguan.jpa.DivJPA;
import kaizone.songmaya.xuanguan.jpa.FooterJPA;
import kaizone.songmaya.xuanguan.model.User;
import kaizone.songmaya.xuanguan.model.UserProfile;
import kaizone.songmaya.xuanguan.service.UserProfileService;
import kaizone.songmaya.xuanguan.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.util.List;
import java.util.Map;

@Controller
public class IndexController {

    private Logger logger = LoggerFactory.getLogger(IndexController.class);

    @Autowired
    private DivJPA divJPA;

    @Autowired
    private FooterJPA footerJPA;

    @Autowired
    UserProfileService userProfileService;

    @Autowired
    UserService userService;

    @RequestMapping(value = "/")
    public String home(Model model) {
        model.addAttribute("name", "这是一个thymeleaf");
        model.addAttribute("data", divJPA.findAll());
        model.addAttribute("footer", footerJPA.findOne(1l));
        return "index";
    }

    @RequestMapping(value = "/index")
    public String index(Model model) {
        model.addAttribute("name", "这是一个thymeleaf");
        model.addAttribute("data", divJPA.findAll());
        model.addAttribute("footer", footerJPA.findOne(1l));
        return "index";
    }

    @GetMapping(value = "/login")
    public String login(Model model) {
        return "login";
    }

    @GetMapping(value = "/logout")
    public String logoutPage(HttpServletRequest request, HttpServletResponse response) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null) {
            new SecurityContextLogoutHandler().logout(request, response, auth);
        }
        return "redirect:/login?logout";
    }

    @RequestMapping(value = "/admin", method = RequestMethod.GET)
    public String adminPage(ModelMap model) {
        model.addAttribute("user", getPrincipal());
        return "admin";
    }

    @RequestMapping(value = "/db", method = RequestMethod.GET)
    public String dbaPage(ModelMap model) {
        model.addAttribute("user", getPrincipal());
        return "dba";
    }

    @RequestMapping(value = "/Access_Denied", method = RequestMethod.GET)
    public String accessDeniedPage(ModelMap model) {
        model.addAttribute("user", getPrincipal());
        return "accessDenied";
    }

    @RequestMapping(value = "/admin/editHome")
    public String editHome(Model model) {
        model.addAttribute("name", "这是一个thymeleaf");
        model.addAttribute("data", divJPA.findAll());
        model.addAttribute("footer", footerJPA.findOne(1l));
        return "admin/editHome";
    }

    @RequestMapping(value = "/admin/editHome", method = RequestMethod.POST)
    @ResponseBody
    public String editHomeSave(@RequestBody List<DivEntity> model) {
        logger.debug(model.toString());
        for (DivEntity item : model) {
            List<DivCellEntity> cells = item.getCells();
            if (cells != null) {
                int k = 1;
                for (DivCellEntity cell : cells) {
                    cell.setId(item.getId() * 100 + k);
                    k++;
                }
            }
            divJPA.save(item);
        }
        return "success";
    }


    @PostMapping("/admin/editHome1")
    public String editHomeSave1(@RequestParam Map<String, String> body) {
        System.out.println(body);
        return "successs";
    }

    @RequestMapping(value = "/newUser", method = RequestMethod.GET)
    public String newRegistration(ModelMap model) {
        User user = new User();
        model.addAttribute("user", user);
        return "newuser";
    }

    @RequestMapping(value = "/newUser", method = RequestMethod.POST)
    public String saveRegistration(@Valid User user,
                                   BindingResult result, ModelMap model) {

        if (result.hasErrors()) {
            System.out.println("There are errors");
            logger.error("{}", result.getModel());
            return "newuser";
        }
        userService.save(user);

        System.out.println("First Name : " + user.getFirstName());
        System.out.println("Last Name : " + user.getLastName());
        System.out.println("SSO ID : " + user.getSsoId());
        System.out.println("Password : " + user.getPassword());
        System.out.println("Email : " + user.getEmail());
        System.out.println("Checking UsrProfiles....");
        if (user.getUserProfiles() != null) {
            for (UserProfile profile : user.getUserProfiles()) {
                System.out.println("Profile : " + profile.getType());
            }
        }

        model.addAttribute("success", "User " + user.getFirstName() + " has been registered successfully");
        return "registrationsuccess";
    }

    @RequestMapping(value = "/data")
    @ResponseBody
    public Object getHomeData(Model model) {
        return divJPA.findAll();
    }

    @RequestMapping(value = "/saveData", method = RequestMethod.POST)
    @ResponseBody
    public Object saveHomeData(Model model) {
        System.out.println(model);
        return model.toString();
    }


    private String getPrincipal() {
        String userName = null;
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        if (principal instanceof UserDetails) {
            userName = ((UserDetails) principal).getUsername();
        } else {
            userName = principal.toString();
        }
        return userName;
    }

    @ModelAttribute("roles")
    public List<UserProfile> initializeProfiles() {
        return userProfileService.findAll();
    }

}
