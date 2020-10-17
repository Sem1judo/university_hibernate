package com.ua.foxminded.university.controllers;


import com.ua.foxminded.university.model.Group;
import com.ua.foxminded.university.services.GroupServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;


@Controller
public class GroupController {

    @Autowired
    @Qualifier("groupServices")
    private GroupServices groupServices;
    @Autowired
    private MessageSource messageSource;

    @GetMapping("/groups")
    public ModelAndView getAllGroups() {
        ModelAndView mav = new ModelAndView("group/allGroups");

        mav.addObject("groups", groupServices.getAll());

        return mav;
    }

    @GetMapping("/groupInfo/{groupId}")
    public ModelAndView getGroup(@PathVariable("groupId") Long id) {
        ModelAndView mav = new ModelAndView("group/getGroup");

        mav.addObject("groupFaculty", groupServices.getById(id));

        return mav;
    }

    @GetMapping("/createGroupForm")
    public ModelAndView createGroupForm() {
        ModelAndView mav = new ModelAndView("group/createGroupForm");

        mav.addObject("group", new Group());

        return mav;
    }

    @PostMapping("/addGroup")
    public ModelAndView addGroup(@ModelAttribute Group group) {
        ModelAndView mav = new ModelAndView("group/allGroups");

        groupServices.create(group);

        mav.addObject("groups", groupServices.getAll());

        return mav;
    }
    @GetMapping(value = "/deleteGroup/{groupId}")
    public ModelAndView deleteGroup(@PathVariable("groupId") long groupId) {

        ModelAndView mav = new ModelAndView("group/allGroups");

        groupServices.deleteById(groupId);

        mav.addObject("groups",  groupServices.getAll());

        return mav;
    }

    @GetMapping("/editGroup/{groupId}")
    public ModelAndView showUpdateForm(@PathVariable("groupId") Long groupId) {

        ModelAndView mav = new ModelAndView("group/updateGroupForm");

        Group group = groupServices.getByIdLight(groupId);

        mav.addObject("group", group);

        return mav;
    }

    @PostMapping("/updateGroup/{groupId}")
    public ModelAndView updateGroup(@PathVariable("groupId") Long groupId, Group group) {

        ModelAndView mav = new ModelAndView("group/allGroups");

        groupServices.update(group);

        mav.addObject("groups", groupServices.getAll());

        return mav;
    }

}
