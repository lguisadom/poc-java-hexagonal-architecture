package com.lagm.ah.arquitecturahexagonaljava.infrastructure.controller.dto;

import com.lagm.ah.arquitecturahexagonaljava.application.port.in.CreateUserUseCase;
import com.lagm.ah.arquitecturahexagonaljava.application.port.in.GetUserUseCase;
import com.lagm.ah.arquitecturahexagonaljava.domain.model.User;
import org.springframework.web.bind.annotation.*;

@RestController()
@RequestMapping("/users")
public class UserController {

    private final CreateUserUseCase createUserUseCase;
    private final GetUserUseCase getUserUseCase;

    public UserController(CreateUserUseCase createUserUseCase, GetUserUseCase getUserUseCase) {
        this.createUserUseCase = createUserUseCase;
        this.getUserUseCase = getUserUseCase;
    }

    @PostMapping()
    public UserResponse createUser(@RequestBody UserRequest userRequest) {
        final User user = new User(null, userRequest.firstName(), userRequest.lastName());
        final User userCreated = this.createUserUseCase.createUser(user);
        return new UserResponse(userCreated.id(), userCreated.firstName(), userCreated.lastName());
    }

    @GetMapping("/{id}")
    public UserResponse getUser(@PathVariable Long id) {
        final User user = getUserUseCase.findById(id);
        return new UserResponse(user.id(), user.firstName(), user.lastName());
    }
}
