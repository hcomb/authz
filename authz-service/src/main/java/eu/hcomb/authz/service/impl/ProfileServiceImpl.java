package eu.hcomb.authz.service.impl;

import java.util.List;

import org.mybatis.guice.transactional.Transactional;

import com.google.inject.Inject;
import com.google.inject.Singleton;

import eu.hcomb.authz.service.ProfileService;
import eu.hcomb.authz.service.mapper.ProfileMapper;

@Singleton
public class ProfileServiceImpl implements ProfileService {

    @Inject
    protected ProfileMapper profileMapper;

	@Transactional
    public List<String> getRolesByUser(String username){
    	return profileMapper.getRolesByUser(username);
    }
}
