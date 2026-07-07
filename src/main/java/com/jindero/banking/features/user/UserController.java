package com.jindero.banking.features.user;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/api/users")
public class UserController {

  private final UserService userService;

  //Konstruktor
  public UserController(UserService userService){
    this.userService = userService;
  }

  //Get all
  @GetMapping("")
  public List<User> getAllUsers(){
    return userService.findAll();
  }

  // Get podle id
  @GetMapping("/{id}")
  public ResponseEntity<User> getUserById(@PathVariable UUID id){
    Optional<User> user = userService.findById(id);

    if (user.isPresent()){
      return ResponseEntity.ok(user.get());
    } else {
      return ResponseEntity.notFound().build();
    }
  }

  // Post create
  @PostMapping("")
  public ResponseEntity<User> createUser(@RequestBody User user){
    User creatUser = userService.createUser(user);
    return ResponseEntity.status(HttpStatus.CREATED).body(creatUser);
  }

  //Put update
  @PutMapping("/{id}")
  public ResponseEntity<User> updateUser(@PathVariable UUID id, @RequestBody User user){
    User updateUser = userService.updateUser(id,user);
    return ResponseEntity.ok(updateUser);
  }

  //Delete user
  @DeleteMapping("/{id}")
  public ResponseEntity<String> deleteUser(@PathVariable UUID id){
    userService.deleteUser(id);
    return ResponseEntity.ok("User deleted successfully");
  }

}
