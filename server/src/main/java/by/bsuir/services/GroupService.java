package by.bsuir.services;

import by.bsuir.api.BsuirAPI;
import by.bsuir.repo.GroupRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class GroupService {
	private final GroupRepository groupRepository;

	public boolean groupExists(int groupNumber) {
		if(isGroupExist(groupNumber)) {
			return true;
		}
		return BsuirAPI.isGroupExist(groupNumber);
	}

	public boolean isGroupExist(int groupNumber) {
		return groupRepository.isGroupExist(groupNumber);
	}

	public Long getGroupIdByNumber(int groupNumber) {
		return groupRepository.getGroupIdByNumber(groupNumber);
	}
}
