package com.ylab.servlet;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ylab.dto.WorkspaceDTO;
import com.ylab.model.Workspace;
import com.ylab.repository.impl.ResourceRepositoryImpl;
import com.ylab.service.impl.ResourceServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.*;


import java.io.IOException;
import java.util.Set;

@WebServlet("/addWorkspace")
public class AddWorkspaceServlet extends HttpServlet {

    private final ResourceServiceImpl resourceServiceImpl;
    private final ObjectMapper objectMapper;
    private final Validator validator;

    public AddWorkspaceServlet() {
        this.resourceServiceImpl = new ResourceServiceImpl(new ResourceRepositoryImpl());
        this.objectMapper = new ObjectMapper();
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        this.validator = factory.getValidator();
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        WorkspaceDTO workspaceDTO = objectMapper.readValue(req.getInputStream(), WorkspaceDTO.class);
        Set<ConstraintViolation<WorkspaceDTO>> violations = validator.validate(workspaceDTO);

        if (!violations.isEmpty()) {
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            objectMapper.writeValue(resp.getWriter(), violations);
            return;
        }

        Workspace workspace = new Workspace(null, workspaceDTO.getName(), workspaceDTO.isAvailable());
        resourceServiceImpl.addWorkspace(workspace);
        resp.setStatus(HttpServletResponse.SC_CREATED);
        objectMapper.writeValue(resp.getWriter(), "Workspace added successfully");
    }
}
