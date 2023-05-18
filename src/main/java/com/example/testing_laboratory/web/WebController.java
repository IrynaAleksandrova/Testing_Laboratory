package com.example.testing_laboratory.web;

import com.example.testing_laboratory.dto.AbstractProcessDto;
import com.example.testing_laboratory.entity.Client;
import com.example.testing_laboratory.entity.Role;
import com.example.testing_laboratory.entity.User;
import com.example.testing_laboratory.service.ClientService;
import com.example.testing_laboratory.service.ProcessService;
import com.example.testing_laboratory.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Controller
@RequestMapping
@RequiredArgsConstructor
public class WebController {

    private final UserService userService;
    private final ClientService clientService;
    private final ProcessService processService;

    @GetMapping
    public String startPage(Model model) {
        model.addAttribute("clientList", clientService.findAllClient());
        return "index";
    }

    @GetMapping("/login")
    public String login() {
        userService.saveAdmin();
        userService.saveAuditor();
        return "login";
    }


    @PostMapping("/clientInfo")
    public String showClientInfo(String clientQr, Model model) {
        Optional<Client> clientByQrCode = clientService.findClientByQrCode(clientQr);
        clientByQrCode.ifPresent(client -> model.addAttribute("client", client));
        return "clientInfo";
    }

    @GetMapping("/selectClient")
    public String selectClient(Model model, HttpServletRequest request) {
        String username = request.getRemoteUser();
        User user = userService.findUserByUsername(username).get();
        List<Client> clientList;
        if (user.getAuthorities().contains(Role.AUDITOR)) {
            clientList = clientService.findAllClient();
        } else {
            UUID departmentId = user.getDepartment().getId();
            clientList = clientService.findClientOfDepartment(departmentId);
        }
        model.addAttribute("clientList", clientList);
        return "selectClient";
    }

    @GetMapping("/clientLog")
    public String showClientLog(String clientQr, Model model) {
        Optional<Client> clientByQrCode = clientService.findClientByQrCode(clientQr);
        clientByQrCode.ifPresent(client -> model.addAttribute("client", client));
        List<AbstractProcessDto> processListByClient = processService.findProcessListByClient(clientQr);
        model.addAttribute("processList", processListByClient);
        return "clientLog";
    }
}
