package com.jindero.banking.features.user;

import com.jindero.banking.shared.exception.EmailAlreadyExistException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class UserService {

private final UserRepository userRepository;

  public UserService(UserRepository userRepository) {
    this.userRepository = userRepository;
  }


  // Vyhledat všechny Usery
    public List<User> findAll(){
    return userRepository.findAll();
    }

    //Vyhledat User podle id
  public Optional<User> findById(UUID id){
    if (id == null){
      return Optional.empty();
    } else {
      return userRepository.findById(id);
    }
  }

  //Vyhledat podle emailu
  public Optional<User> findByEmail(String email){
    if (email == null){
      return Optional.empty();
    } else {
      return userRepository.findByEmail(email);
    }
  }

  // Vytvořit User
  public User createUser(User user)  {
    if (userRepository.existsByEmail(user.getEmail())){
      throw new EmailAlreadyExistException("Email " + user.getEmail() + " already exists");
    } else {
      return userRepository.save(user);
    }
  }

  //Update User
  public User updateUser( UUID id, User newUserData){

    User existingUser = userRepository.findById(id).get();

    if (userRepository.existsById(id)){
      if (!existingUser.getEmail().equals(newUserData.getEmail()) &&
              userRepository.existsByEmail(newUserData.getEmail())){
        throw new EmailAlreadyExistException("Email " + newUserData.getEmail() + " already exists");
      }

      existingUser.setFirstName(newUserData.getFirstName());
      existingUser.setLastName(newUserData.getLastName());
      existingUser.setPhoneNumber(newUserData.getPhoneNumber());
      existingUser.setEmail(newUserData.getEmail());

      return userRepository.save(existingUser);
    } else {
      throw new RuntimeException("User with ID " + id + " not found");
    }
  }

  //Smazat User
  public void deleteUser(UUID id){
    if (userRepository.existsById(id)){

      userRepository.deleteById(id);
    } else {
      throw new RuntimeException("User with ID "+ id + " not found");
    }
  }

}
