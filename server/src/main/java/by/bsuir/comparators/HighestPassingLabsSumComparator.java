package by.bsuir.comparators;

import by.bsuir.models.dto.Pair;
import by.bsuir.models.entities.PreQueueEntity;
import by.bsuir.utils.GenerateQueueUtil;

import java.util.Comparator;

public class HighestPassingLabsSumComparator implements Comparator<PreQueueEntity> {
	@Override
	public int compare(PreQueueEntity o1, PreQueueEntity o2) {
		Pair<Integer, Integer> labsSum = GenerateQueueUtil.getPreQueueEntityLabSum(o1, o2);
		return Integer.compare(labsSum.getSecond(), labsSum.getFirst());
	}
}
