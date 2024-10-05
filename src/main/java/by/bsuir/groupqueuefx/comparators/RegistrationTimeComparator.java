package by.bsuir.groupqueuefx.comparators;

import by.bsuir.groupqueuefx.models.entities.PreQueueEntity;

import java.util.Comparator;

public class RegistrationTimeComparator implements Comparator<PreQueueEntity> {
	@Override
	public int compare(PreQueueEntity o1, PreQueueEntity o2) {
		return o1.getRegistrationTime().compareTo(o2.getRegistrationTime());
	}
}
