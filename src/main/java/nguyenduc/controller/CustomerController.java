package nguyenduc.controller;

import nguyenduc.model.Customer;
import nguyenduc.service.CustomerService;
import nguyenduc.service.ICustomerService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
public class CustomerController {

    private final ICustomerService customerService = new CustomerService();

    @GetMapping("")
    public String index(Model model) {

        List<Customer> customerList = customerService.findAll();
        model.addAttribute("customers", customerList);
        return "/index";
    }

    @GetMapping("/customer/{id}/view")
    public ModelAndView viewCustomerInfo(@PathVariable int id) {
        Customer customer = customerService.findById(id);
        ModelAndView modelAndView = new ModelAndView("view");
        modelAndView.addObject("customer", customer);
        return modelAndView;
    }

    @GetMapping("/customer/create")
    public String showFormCreate(Model model) {
        model.addAttribute("customer", new Customer());
        return "create";
    }

    @PostMapping("/customer/create")
    public String createCustomer(Customer customer, RedirectAttributes attributes) {
        int id = (int) (Math.random() * 1000000);
        customer.setId(id);
        customerService.save(customer);
        attributes.addFlashAttribute("message", "Created");
        return "redirect:/";
    }

    @GetMapping("customer/{id}/edit")
    public String showFormEdit(@PathVariable int id, Model model) {
        Customer customer = customerService.findById(id);
        model.addAttribute("customer", customer);
        return "edit";
    }

    @PostMapping("customer/edit")
    public String edit(Customer customer, RedirectAttributes attributes) {
        customerService.update(customer.getId(), customer);
        attributes.addFlashAttribute("message", "Edited");
        return "redirect:/";
    }

    @GetMapping("customer/{id}/delete")
    public String delete(@PathVariable int id, RedirectAttributes attributes) {
        customerService.remove(id);
        attributes.addFlashAttribute("message", "Deleted");
        return "redirect:/";
    }
}
