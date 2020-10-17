package com.ua.foxminded.university.controllers;

import com.ua.foxminded.university.model.Lector;
import com.ua.foxminded.university.services.LectorServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class LectorController {

    @Autowired
    @Qualifier("lectorServices")
    private  LectorServices lectorServices;
    @Autowired
    private  MessageSource messageSource;

    @GetMapping("/lectors")
    public ModelAndView getaAllLectors(Model model) {
        ModelAndView mav = new ModelAndView("lector/allLectors");

        mav.addObject("lectors", lectorServices.getAll());

        return mav;
    }

    @GetMapping("/lectorInfo/{lectorId}")
    public ModelAndView getLector(@PathVariable("lectorId") Long id, Model model) {
        ModelAndView mav = new ModelAndView("lector/getLector");

        mav.addObject("lectorFaculty", lectorServices.getById(id));

        return mav;
    }

    @GetMapping("/createLectorForm")
    public ModelAndView createLectorForm(Model model) {
        ModelAndView mav = new ModelAndView("lector/createLectorForm");

        mav.addObject("lector", new Lector());

        return mav;
    }

    @PostMapping("/addLector")
    public ModelAndView addLector(@ModelAttribute Lector lector) {
        ModelAndView mav = new ModelAndView("lector/allLectors");

        lectorServices.create(lector);

        mav.addObject("lectors", lectorServices.getAll());

        return mav;
    }
    @GetMapping(value = "/deleteLector/{lectorId}")
    public ModelAndView deleteLector(@PathVariable("lectorId") long lectorId) {
        ModelAndView mav = new ModelAndView("lector/allLectors");

        lectorServices.deleteById(lectorId);

        mav.addObject("lectors", lectorServices.getAll());

        return mav;
    }

    @GetMapping("/editLector/{lectorId}")
   public ModelAndView showUpdateForm(@PathVariable("lectorId") Long lectorId) {

        ModelAndView mav = new ModelAndView("lector/updateLectorForm");

        Lector lector = lectorServices.getByIdLight(lectorId);

        mav.addObject("lector", lector);

        return mav;
    }

    @PostMapping("/updateLector/{lectorId}")
    public ModelAndView updateLector(@PathVariable("lectorId") Long groupId, Lector lector) {
        ModelAndView mav = new ModelAndView("lector/allLectors");

        lectorServices.update(lector);

        mav.addObject("lectors", lectorServices.getAll());

        return mav;
    }
}
