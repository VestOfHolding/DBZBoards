package dbz;

import dbz.domain.Emblem;

import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

public class Utils {
    public static final int MIN = 25;

    public static Set<Emblem> getEmblemsAndLinkedEmblems(LinkBonusFactory linkFactory, List<Emblem> emblems, Function<Emblem, Integer> bonusSupplier) {
        return getEmblems(emblems, bonusSupplier).stream()
                .map(linkFactory::getLinkBonusesByEmblem)
                .flatMap(Collection::stream)
                .map(LinkBonus::getLinkMembers)
                .flatMap(Collection::stream)
                .collect(Collectors.toSet());
    }

    public static Set<Emblem> getEmblems(List<Emblem> emblems, Function<Emblem, Integer> bonusSupplier) {
        return emblems.stream()
                .filter(emblem -> bonusSupplier.apply(emblem) >= MIN)
                .collect(Collectors.toSet());
    }
}
