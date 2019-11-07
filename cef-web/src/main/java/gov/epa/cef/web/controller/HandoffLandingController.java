package gov.epa.cef.web.controller;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

/**
 * Landing controller for the CDX handoff POST
 * @author amahfouz
 *
 */
@Controller
@EnableConfigurationProperties
public class HandoffLandingController {

    public static final String HANDOFF_LANDING_PATH = "/handoffLanding";

    @RequestMapping(path = HANDOFF_LANDING_PATH, method = { RequestMethod.GET, RequestMethod.POST })
    public ModelAndView home() {
        return new ModelAndView("home");
    }

}
