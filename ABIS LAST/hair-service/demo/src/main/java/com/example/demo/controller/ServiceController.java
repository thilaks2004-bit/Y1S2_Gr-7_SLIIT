//package com.example.demo.controller;
//
//import com.example.demo.model.HairService;
//import com.example.demo.model.Service;
//import com.example.demo.model.SkinService;
//import com.example.demo.service.ServiceManager;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Controller;
//import org.springframework.ui.Model;
//import org.springframework.web.bind.annotation.*;
//
//import java.io.IOException;
//import java.util.List;
//
//@Controller
//public class ServiceController {
//
//    @Autowired
//    private ServiceManager serviceManager;
//
////    @GetMapping("/add-service")
////    public String addServicePage() {
////        return "add-service";
////    }
//@GetMapping("/add-service")
//public String addServicePage() {
//    return "add-service"; // Matches templates/add-service.html
//}
//
//    @PostMapping("/add-service")
//    public String addService(@RequestParam String name, @RequestParam int duration,
//                             @RequestParam double price, @RequestParam String category,
//                             @RequestParam String type) throws IOException {
//        Service service;
//        if (category.equals("Hair")) {
//            service = new HairService(name, duration, price, type);
//        } else {
//            service = new SkinService(name, duration, price, type);
//        }
//        serviceManager.addService(service);
//        return "redirect:/list-services";
//    }
//
////    @GetMapping("/search-service")
////    public String searchServicePage() {
////        return "search-service";
////    }
//@GetMapping("/search-service")
//public String searchServicePage() {
//    return "search-service"; // Matches templates/search-service.html
//}
//
//    @PostMapping("/search-service")
//    public String searchService(@RequestParam String keyword, Model model) throws IOException {
//        List<Service> services = serviceManager.searchServices(keyword);
//        model.addAttribute("services", services);
//        return "search-service";
//    }
//
//    @GetMapping("/list-services")
//    public String listServices(Model model) throws IOException {
//        List<Service> services = serviceManager.getAllServices();
//        model.addAttribute("services", services);
//        return "list-services";
//    }
//
//    @GetMapping("/edit-service")
//    public String editServicePage(@RequestParam String name, Model model) throws IOException {
//        List<Service> services = serviceManager.getAllServices();
//        Service service = services.stream().filter(s -> s.getName().equals(name)).findFirst().orElse(null);
//        model.addAttribute("service", service);
//        return "edit-service";
//    }
//
//    @PostMapping("/edit-service")
//    public String editService(@RequestParam String oldName, @RequestParam String name,
//                              @RequestParam int duration, @RequestParam double price,
//                              @RequestParam String category, @RequestParam String type) throws IOException {
//        Service updatedService;
//        if (category.equals("Hair")) {
//            updatedService = new HairService(name, duration, price, type);
//        } else {
//            updatedService = new SkinService(name, duration, price, type);
//        }
//        serviceManager.updateService(oldName, updatedService);
//        return "redirect:/list-services";
//    }
//
//    @PostMapping("/delete-service")
//    public String deleteService(@RequestParam String name) throws IOException {
//        serviceManager.deleteService(name);
//        return "redirect:/list-services";
//    }
//}
package com.example.demo.controller;

import com.example.demo.model.Service;
import com.example.demo.model.HairService;
import com.example.demo.model.SkinService;


import com.example.demo.service.ServiceManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@Controller
public class ServiceController {

    @Autowired
    private ServiceManager serviceManager;

    // Add this new mapping for the root URL
    @GetMapping("/list")
    public String rootRedirect() {
        return "redirect:/list-services"; // Redirect to the list-services page
    }

    @GetMapping("/add-service")
    public String addServicePage() {
        return "add-service";
    }

    @PostMapping("/add-service")
    public String addService(@RequestParam String name, @RequestParam int duration,
                             @RequestParam double price, @RequestParam String category,
                             @RequestParam String type) throws IOException {
        Service service;
        if (category.equals("Hair")) {
            service = new HairService(name, duration, price, type);
        } else {
            service = new SkinService(name, duration, price, type);
        }
        serviceManager.addService(service);
        return "redirect:/list-services";
    }

    @GetMapping("/search-service")
    public String searchServicePage() {
        return "search-service";
    }

    @PostMapping("/search-service")
    public String searchService(@RequestParam String keyword, Model model) throws IOException {
        List<Service> services = serviceManager.searchServices(keyword);
        model.addAttribute("services", services);
        return "search-service";
    }

    @GetMapping("/list-services")
    public String listServices(Model model) throws IOException {
        List<Service> services = serviceManager.getAllServices();
        model.addAttribute("services", services);
        return "list-services";
    }

//    @GetMapping("/edit-service")
//    public String editServicePage(@RequestParam String name, Model model) throws IOException {
//        List<Service> services = serviceManager.getAllServices();
//        Service service = services.stream().filter(s -> s.getName().equals(name)).findFirst().orElse(null);
//        model.addAttribute("service", service);
//        return "edit-service";
//    }
@GetMapping("/edit-services")
public String editServices() {
    return "edit-service"; // or another response
}

    @PostMapping("/edit-service")
    public String editService(@RequestParam String oldName, @RequestParam String name,
                              @RequestParam int duration, @RequestParam double price,
                              @RequestParam String category, @RequestParam String type) throws IOException {
        Service updatedService;
        if (category.equals("Hair")) {
            updatedService = new HairService(name, duration, price, type);
        } else {
            updatedService = new SkinService(name, duration, price, type);
        }
        serviceManager.updateService(oldName, updatedService);
        return "redirect:/list-services";
    }


    @GetMapping("/De")
    public String deleteServices() {
        return "redirect:/amincancel"; // or another response
    }
    @GetMapping("/")
    public String home() {
        return "home"; // or another response
    }
}