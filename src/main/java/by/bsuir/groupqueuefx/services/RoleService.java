package by.bsuir.groupqueuefx.services;


import by.bsuir.groupqueuefx.enums.entityAttributes.RoleType;
import by.bsuir.groupqueuefx.repo.RoleRepository;
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
