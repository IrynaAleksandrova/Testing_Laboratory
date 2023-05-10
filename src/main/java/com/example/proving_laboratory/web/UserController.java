package com.example.proving_laboratory.web;

import com.example.proving_laboratory.dto.ClientDto;
import com.example.proving_laboratory.dto.UserDto;
import com.example.proving_laboratory.entity.Client;
import com.example.proving_laboratory.entity.Department;
import com.example.proving_laboratory.entity.Role;
import com.example.proving_laboratory.entity.User;
import com.example.proving_laboratory.mapper.ClientMapper;
import com.example.proving_laboratory.service.ClientService;
import com.example.proving_laboratory.service.DepartmentService;
import com.example.proving_laboratory.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Controller
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {

    private final DepartmentService departmentService;
    private final UserService userService;
    private final ClientService clientService;
    private final ClientMapper clientMapper;
//    private final ProcessService processService;

    @GetMapping("/showEmployeeList")
    public String showEmployeeList(Model model, HttpServletRequest request) {
        String username = request.getRemoteUser();
        User user = userService.findUserByUsername(username).get();
        List<User> employeeList;
        if (user.getAuthorities().contains(Role.ADMIN)) {
            employeeList = userService.findAllEmployees();
        } else {
            employeeList = userService.findEmployeesOfDepartment(user.getDepartment().getId());
        }
        model.addAttribute("employeeList", employeeList);
        return "employeeList";
    }

    @GetMapping("/addDepartment")
    public String addDepartment(@ModelAttribute Department department) {
        return "user/addDepartment";
    }

    @PostMapping("/addDepartment")
    public String addDepartment(@Valid @ModelAttribute Department department,
                                BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            return "user/addDepartment";
        }
        departmentService.saveDepartment(department);
        model.addAttribute("departmentName", department.getName());
        model.addAttribute("department", new Department());
        return "user/addDepartment";
    }

    @GetMapping("/addEmployee")
    public String addEmployee(Model model, HttpServletRequest request) {
        String username = request.getRemoteUser();
        User user = userService.findUserByUsername(username).get();
        List<Department> departments = new ArrayList<>();
        if (!user.getAuthorities().contains(Role.ADMIN)) {
            departments.add(user.getDepartment());
        } else {
            departments = departmentService.findAllDepartments();
        }
        model.addAttribute("departments", departments);
        model.addAttribute("userDto", new UserDto());
        return "user/addEmployee";
    }

    @PostMapping("/addEmployee")
    public String addEmployee(@Valid @ModelAttribute UserDto userDto, BindingResult bindingResult, Model model, HttpServletRequest request) {
        String username = request.getRemoteUser();
        User currentUser = userService.findUserByUsername(username).get();
        List<Department> departments = new ArrayList<>();
        if (!currentUser.getAuthorities().contains(Role.ADMIN)) {
            departments.add(currentUser.getDepartment());
        } else {
            departments = departmentService.findAllDepartments();
        }
        model.addAttribute("departments", departments);
        if (bindingResult.hasErrors()) {
            return "user/addEmployee";
        }
        Optional<User> user = userService.saveUser(userDto);
        model.addAttribute("user", user.get());
        model.addAttribute("userDto", new UserDto());
        return "user/addEmployee";
    }

    @PostMapping("/{id}/deleteEmployee")
    public String deleteEmployee(@PathVariable UUID id, HttpServletRequest request, Model model) {
        userService.deleteEmployee(id);
        String username = request.getRemoteUser();
        List<User> employeeList = userService.findEmployeeList(username);
        model.addAttribute("employeeList", employeeList);
        return "employeeList";
    }

    @GetMapping("/addClient")
    public String addClient(Model model, HttpServletRequest request) {
        String username = request.getRemoteUser();
        User currentUser = userService.findUserByUsername(username).get();
        List<Department> departments = departmentService.findDepartmentList(currentUser);
        model.addAttribute("departments", departments);
        model.addAttribute("equipmentDto", new ClientDto());
        return "user/addClient";
    }

    @PostMapping("/addClient")
    public String addClient(@Valid @ModelAttribute ClientDto clientDto, BindingResult bindingResult, Model model, HttpServletRequest request) {
        String username = request.getRemoteUser();
        User currentUser = userService.findUserByUsername(username).get();
        List<Department> departments = departmentService.findDepartmentList(currentUser);
        model.addAttribute("departments", departments);
        if (bindingResult.hasErrors()) {
            return "user/addClient";
        }
        Client client = clientMapper.convertClientDtoToClient(clientDto);
        clientService.saveClient(client);
        model.addAttribute("client", client);
        model.addAttribute("clientDto", new ClientDto());
        return "user/addClient";
    }

    @PostMapping("/{id}/deleteClient")
    public String deleteClient(@PathVariable UUID id, HttpServletRequest request, Model model) {
        clientService.deleteClient(id);
        String username = request.getRemoteUser();
        User user = userService.findUserByUsername(username).get();
        List<Client> clientList;
        if (user.getAuthorities().contains(Role.ADMIN)) {
            clientList = clientService.findAllClient();
        } else {
            UUID departmentId = user.getDepartment().getId();
            clientList = clientService.findClientOfDepartment(departmentId);
        }
        model.addAttribute("clientList", clientList);
        return "clientList";
    }

    @GetMapping("/showClientList")
    public String showClientList(HttpServletRequest request, Model model) {
        String username = request.getRemoteUser();
        User user = userService.findUserByUsername(username).get();
        List<Client> clientList;
        if (user.getAuthorities().contains(Role.ADMIN)
                || user.getAuthorities().contains(Role.SERVICE_ENGINEER)
                || user.getAuthorities().contains(Role.AUDITOR)) {
            clientList = clientService.findAllClient();
        } else {
            UUID departmentId = user.getDepartment().getId();
            clientList = clientService.findClientOfDepartment(departmentId);
        }
        model.addAttribute("clientList", clientList);
        return "clientList";
    }

//    @GetMapping("/selectProcess")
//    public String selectProcess(Model model, HttpServletRequest request) {
//        String username = request.getRemoteUser();
//        User user = userService.findUserByUsername(username).get();
//        List<Client> clientList;
//        if (user.getAuthorities().contains(Role.SERVICE_ENGINEER)) {
//            clientList = clientService.findAllClient();
//        } else {
//            UUID departmentId = user.getDepartment().getId();
//            clientList = clientService.findFreeClientOfDepartment(departmentId);
//        }
//        model.addAttribute("clientList", clientList);
//        model.addAttribute("processDto", new ProcessDto());
//        return "user/startProcess";
//    }
//
//    @PostMapping("/selectProcess")
//    public String selectProcess(@Valid @ModelAttribute ProcessDto processDto, BindingResult bindingResult, HttpSession httpSession) {
//        if (bindingResult.hasErrors()) {
//            return "user/startProcess";
//        }
//        httpSession.setAttribute("clientQrCodeList", processDto.getClientQrCodes());
//        return "redirect:/user/" + processDto.getProcessType();
//    }
//
//    @GetMapping("/cleaning")
//    public String cleaningProcess(@ModelAttribute CleaningProcessDto cleaningProcessDto, HttpSession httpSession, Model model) {
//        List<String> clientQrCodeList = (List<String>) httpSession.getAttribute("clientQrCodeList");
//        List<Client> clientList = clientService.findListOfInternalCodes(clientQrCodeList);
//        model.addAttribute("clientList", clientList);
//        return "process/cleaning";
//    }
//
//    @PostMapping("/cleaning")
//    public String cleaningProcess(@Valid @ModelAttribute CleaningProcessDto cleaningProcessDto, BindingResult bindingResult,
//                                  HttpSession httpSession, Model model, HttpServletRequest request) {
//        if (bindingResult.hasErrors()) {
//            return "process/cleaning";
//        }
//        List<String> clientQrCodeList = (List<String>) httpSession.getAttribute("clientQrCodeList");
//        List<Client> clientList = clientService.findListOfInternalCodes(clientQrCodeList);
//        httpSession.removeAttribute("clientQrCodeList");
//        String username = request.getRemoteUser();
//        Optional<User> userByUsername = userService.findUserByUsername(username);
//        assert clientList != null;
//        CleaningProcess cleaningProcess = clientService.startCleaningProcess(userByUsername.get(), clientList, cleaningProcessDto);
//        Optional<AbstractProcess> process = clientService.saveProcess(cleaningProcess);
//        model.addAttribute("processType", "cleaning");
//        model.addAttribute("process", process.get());
//        return "process/inProcess";
//    }
//
//    @GetMapping("/production")
//    public String productionProcess(@ModelAttribute ProductionProcessDto productionProcessDto, HttpSession httpSession, Model model) {
//        List<String> clientQrCodeList = (List<String>) httpSession.getAttribute("clientQrCodeList");
//        List<Client> clientList = clientService.findListOfInternalCodes(clientQrCodeList);
//        model.addAttribute("equipmentList", clientList);
//        return "process/production";
//    }
//
//    @PostMapping("/production")
//    public String productionProcess(@Valid @ModelAttribute ProductionProcessDto productionProcessDto, BindingResult bindingResult,
//                                    HttpSession httpSession, Model model, HttpServletRequest request) {
//        if (bindingResult.hasErrors()) {
//            return "process/production";
//        }
//        List<String> clientQrCodeList = (List<String>) httpSession.getAttribute("clientQrCodeList");
//        List<Client> clientList = clientService.findListOfInternalCodes(clientQrCodeList);
//        httpSession.removeAttribute("clientQrCodeList");
//        String username = request.getRemoteUser();
//        Optional<User> userByUsername = userService.findUserByUsername(username);
//        assert clientList != null;
//        ProductionProcess productionProcess = clientService.startProductionProcess(userByUsername.get(), clientList, productionProcessDto);
//        Optional<AbstractProcess> process = clientService.saveProcess(productionProcess);
//        model.addAttribute("processType", "production");
//        model.addAttribute("process", process.get());
//        return "process/inProcess";
//    }
//
//    @GetMapping("/maintenance")
//    public String maintenanceService(@ModelAttribute MaintenanceServiceDto maintenanceServiceDto, HttpSession httpSession, Model model) {
//        List<String> clientQrCodeList = (List<String>) httpSession.getAttribute("clientQrCodeList");
//        List<Client> clientList = clientService.findListOfInternalCodes(clientQrCodeList);
//        model.addAttribute("clientList", clientList);
//        return "process/maintenance";
//    }
//
//    @PostMapping("/maintenance")
//    public String maintenanceService(@Valid @ModelAttribute MaintenanceServiceDto maintenanceServiceDto, BindingResult bindingResult,
//                                     HttpSession httpSession, Model model, HttpServletRequest request) {
//        if (bindingResult.hasErrors()) {
//            return "process/maintenance";
//        }
//        List<String> clientQrCodeList = (List<String>) httpSession.getAttribute("clientQrCodeList");
//        List<Client> clientList = clientService.findListOfInternalCodes(clientQrCodeList);
//        httpSession.removeAttribute("clientQrCodeList");
//        String username = request.getRemoteUser();
//        Optional<User> userByUsername = userService.findUserByUsername(username);
//        assert clientList != null;
//        MaintenanceService maintenanceService = processService.startMaintenanceService(userByUsername.get(), clientList, maintenanceServiceDto);
//        Optional<AbstractProcess> process = processService.saveProcess(maintenanceService);
//        model.addAttribute("processType", "maintenance");
//        model.addAttribute("process", process.get());
//        return "process/inProcess";
//    }
//
//    @GetMapping("/qualification")
//    public String qualificationProcess(@ModelAttribute QualificationProcessDto qualificationProcessDto, HttpSession httpSession, Model model) {
//        List<String> clientQrCodeList = (List<String>) httpSession.getAttribute("clientQrCodeList");
//        List<Client> clientList = clientService.findListOfInternalCodes(clientQrCodeList);
//        model.addAttribute("clientList", clientList);
//        return "process/qualification";
//    }
//
//    @PostMapping("/qualification")
//    public String qualificationProcess(@Valid @ModelAttribute QualificationProcessDto qualificationProcessDto, BindingResult bindingResult,
//                                       HttpSession httpSession, Model model, HttpServletRequest request) {
//        if (bindingResult.hasErrors()) {
//            return "process/qualification";
//        }
//        List<String> clientQrCodeList = (List<String>) httpSession.getAttribute("clientQrCodeList");
//        List<Client> clientList = clientService.findListOfInternalCodes(clientQrCodeList);
//        httpSession.removeAttribute("clientQrCodeList");
//        String username = request.getRemoteUser();
//        Optional<User> userByUsername = userService.findUserByUsername(username);
//        assert clientList != null;
//        QualificationProcess qualificationProcess = processService.startQualificationProcess(userByUsername.get(), clientList, qualificationProcessDto);
//        Optional<AbstractProcess> process = processService.saveProcess(qualificationProcess);
//        model.addAttribute("processType", "qualification");
//        model.addAttribute("process", process.get());
//        return "process/inProcess";
//    }
//
//    @GetMapping("/stopProcess")
//    public String stopProcess(@RequestParam String equipmentQrCode) {
//        processService.stopProcess(equipmentQrCode);
//        return "redirect:/";
//    }

}
