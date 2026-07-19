package com.jindero.banking.features.user;

import com.jindero.banking.shared.exception.EmailAlreadyExistException;
import com.jindero.banking.shared.exception.UserNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
  @Transactional
  public User createUser(User user)  {
    if (userRepository.existsByEmail(user.getEmail())){
      throw new EmailAlreadyExistException("Email " + user.getEmail() + " already exists");
    } else {
      return userRepository.save(user);
    }
  }

  @Transactional
  public User updateUser(UUID id, User newUserData) {

    // Hledá uživatele – pokud neexistuje, vyhodí exception
    User existingUser = userRepository.findById(id)
            .orElseThrow(() -> new UserNotFoundException("User with ID " + id + " not found"));

    // KOntrola email - konflikt pouze pokud se email mění
    if (!existingUser.getEmail().equals(newUserData.getEmail()) &&
            userRepository.existsByEmail(newUserData.getEmail())) {
      throw new EmailAlreadyExistException("Email " + newUserData.getEmail() + " already exists");
    }

    existingUser.setFirstName(newUserData.getFirstName());
    existingUser.setLastName(newUserData.getLastName());
    existingUser.setPhoneNumber(newUserData.getPhoneNumber());
    existingUser.setEmail(newUserData.getEmail());

    return userRepository.save(existingUser);
  }

  //Smazat User
  @Transactional
  public void deleteUser(UUID id){
    if (userRepository.existsById(id)){

      userRepository.deleteById(id);
    } else {
      throw new UserNotFoundException("User with ID "+ id + " not found");
    }
  }

}
