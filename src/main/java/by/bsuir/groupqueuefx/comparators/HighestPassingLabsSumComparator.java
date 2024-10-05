package by.bsuir.groupqueuefx.comparators;

import by.bsuir.groupqueuefx.models.dto.Pair;
import by.bsuir.groupqueuefx.models.entities.PreQueueEntity;
import by.bsuir.groupqueuefx.utils.GenerateQueueUtil;

import java.util.Comparator;

public class HighestPassingLabsSumComparator implements Comparator<PreQueueEntity> {
	@Override
	public int compare(PreQueueEntity o1, PreQueueEntity o2) {
		Pair<Integer, Integer> labsSum = GenerateQueueUtil.getPreQueueEntityLabSum(o1, o2);
		return Integer.compare(labsSum.getSecond(), labsSum.getFirst());
	}
}
