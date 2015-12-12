package eu.hcomb.authz.service.impl;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.List;

import org.mybatis.guice.transactional.Transactional;

import com.google.inject.Inject;

import eu.hcomb.authz.dto.UserDTO;
import eu.hcomb.authz.service.UserService;
import eu.hcomb.authz.service.mapper.RoleMapper;
import eu.hcomb.authz.service.mapper.UserMapper;
import eu.hcomb.common.crypt.PasswordHash;

public class UserServiceImpl implements UserService {

    @Inject
    protected UserMapper userMapper;
    
    @Inject
    protected RoleMapper roleMapper;

	@Transactional
	public UserDTO login(String username, String password) {
		
		UserDTO user = userMapper.getUserByName(username);

		try {
		
			boolean valid = PasswordHash.validatePassword(password, user.getPassword());

			if(valid){
				List<String> roles = roleMapper.getRolesByUser(username);
				user.setRoles(roles);
				return user;
			}
		} catch(Exception e) {
			e.printStackTrace();
		}
	
		return null;
	}
	
	@Transactional
	public List<UserDTO> getAllUsers() {
		return userMapper.getAllUsers();
	}
	
	@Transactional
	public UserDTO getUserById(Long id) {
		return userMapper.getUserById(id);
	}
	
	@Transactional
	public UserDTO insertUser(UserDTO user) throws NoSuchAlgorithmException, InvalidKeySpecException {
		String password = PasswordHash.createHash(user.getPassword());
		user.setPassword(password);
		userMapper.insertUser(user);
		return userMapper.getUserById(user.getId());
	}
	
	@Transactional
	public UserDTO updateUser(UserDTO user) throws NoSuchAlgorithmException, InvalidKeySpecException {
		String password = PasswordHash.createHash(user.getPassword());
		user.setPassword(password);
		userMapper.updateUser(user);
		return userMapper.getUserById(user.getId());
	}
	
	@Transactional
	public void deleteUser(Long id) {
		userMapper.deleteUser(id);
	}
}
