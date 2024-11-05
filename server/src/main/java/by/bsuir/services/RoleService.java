package by.bsuir.services;


import by.bsuir.enums.entityAttributes.RoleType;
import by.bsuir.repo.RoleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RoleService {
	private final RoleRepository roleRepository;

	public Long getRoleIdByType(RoleType roleType) {
		return roleRepository.getRoleIdByType(roleType);
	}
}
